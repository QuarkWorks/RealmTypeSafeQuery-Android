package com.quarkworks.android.realmtypesafequery;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Pair;

import com.quarkworks.android.realmtypesafequery.fields.RealmStringField;
import com.quarkworks.android.realmtypesafequery.interfaces.RealmComparableField;
import com.quarkworks.android.realmtypesafequery.interfaces.RealmEmptyableField;
import com.quarkworks.android.realmtypesafequery.interfaces.RealmIndexedField;
import com.quarkworks.android.realmtypesafequery.fields.RealmDateField;
import com.quarkworks.android.realmtypesafequery.interfaces.RealmField;
import com.quarkworks.android.realmtypesafequery.interfaces.SortableRealmField;
import com.quarkworks.android.realmtypesafequery.relationships.RealmToManyRelationship;
import com.quarkworks.android.realmtypesafequery.relationships.RealmToOneRelationship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmCollection;
import io.realm.RealmModel;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

public class RealmTypeSafeQuery<Model extends RealmModel> {

    /*
        Static Constructors
     */

    @NonNull
    public static <M extends RealmModel> RealmTypeSafeQuery<M> create(@NonNull RealmQuery<M> realmQuery) {
        return new RealmTypeSafeQuery<>(realmQuery);
    }

    @NonNull
    public static <M extends RealmModel> RealmTypeSafeQuery<M> where(@NonNull Class<M> clazz) {
        return new RealmTypeSafeQuery<>(clazz);
    }

    @NonNull
    public static <M extends RealmModel> RealmTypeSafeQuery<M> where(@NonNull Realm realm, @NonNull Class<M> clazz) {
        return new RealmTypeSafeQuery<>(realm, clazz);
    }

    @NonNull
    public static <M extends RealmModel> RealmTypeSafeQuery<M> where(@NonNull RealmCollection<M> realmCollection) {
        return new RealmTypeSafeQuery<>(realmCollection);
    }

    /*
         Quick Queries
     */

    @Nullable
    public static <M extends RealmModel, V> M findFirst(@NonNull Realm realm, @NonNull RealmField<M, V> field, @Nullable V value) {
        return RealmTypeSafeQuery.where(realm, field.getModelClass()).equalTo(field, value).findFirst();
    }

    @Nullable
    public static <M extends RealmModel, V> M findFirst(@NonNull Realm realm, @NonNull SortableRealmField<M, V> field, @Nullable V value, @NonNull Sort sort) {
        return RealmTypeSafeQuery.where(realm, field.getModelClass()).equalTo(field, value).sort(field, sort).findFirst();
    }

    @NonNull
    public static <M extends RealmModel, V> RealmResults<M> findAll(@NonNull Realm realm, @NonNull RealmField<M, V> field, @Nullable V value) {
        return new RealmTypeSafeQuery<>(realm, field.getModelClass()).equalTo(field, value).findAll();
    }

    @NonNull
    public static <M extends RealmModel, V> RealmResults<M> findAll(@NonNull Realm realm, @NonNull SortableRealmField<M, V> field, @Nullable V value, @NonNull Sort sort) {
        return new RealmTypeSafeQuery<>(realm, field.getModelClass()).equalTo(field, value).sort(field, sort).findAll();
    }

    @NonNull
    final RealmQuery<Model> realmQuery;

    @NonNull
    final List<Pair<String, Sort>> sortOrders = new LinkedList<>();

    /*
        Constructors
     */

    public RealmTypeSafeQuery(@NonNull RealmQuery<Model> realmQuery) {
        this.realmQuery = realmQuery;
    }

    public RealmTypeSafeQuery(@NonNull Class<Model> modelClass) {
        Realm realm = Realm.getDefaultInstance();
        this.realmQuery = realm.where(modelClass);
        realm.close();
        if (realm.isClosed()) {
            throw new IllegalStateException("Using global realm that is not already opened");
        }
    }

    public RealmTypeSafeQuery(@NonNull Realm realm, @NonNull Class<Model> modelClass) {
        this.realmQuery = realm.where(modelClass);
    }

    public RealmTypeSafeQuery(@NonNull RealmCollection<Model> realmCollection) {
        this.realmQuery = realmCollection.where();
    }

    @NonNull
    public RealmQuery<Model> getRealmQuery() {
        return realmQuery;
    }

    public boolean isValid() {
        return this.realmQuery.isValid();
    }

    /*
        Null
     */

    @NonNull
    public RealmTypeSafeQuery<Model> isNull(@NonNull RealmField<Model, ?> field) {
        field.isNull(this.realmQuery);
        return this;
    }

    @NonNull
    public RealmTypeSafeQuery<Model> isNotNull(@NonNull RealmField<Model, ?> field) {
        field.isNotNull(this.realmQuery);
        return this;
    }

    @NonNull
    public RealmTypeSafeQuery<Model> isNull(@NonNull RealmToOneRelationship<Model, ?> field) {
        this.realmQuery.isNull(field.getKeyPath());
        return this;
    }

    @NonNull
    public RealmTypeSafeQuery<Model> isNotNull(@NonNull RealmToOneRelationship<Model, ?> field) {
        this.realmQuery.isNotNull(field.getKeyPath());
        return this;
    }

    /*
        EqualTo
     */

    @NonNull
    public <V> RealmTypeSafeQuery<Model> equalTo(@NonNull RealmField<Model, V> field, @Nullable V value) {
        field.equalTo(this.realmQuery, value);
        return this;
    }

    @NonNull
    public <V> RealmTypeSafeQuery<Model> notEqualTo(@NonNull RealmField<Model, V> field, @Nullable V value) {
        field.notEqualTo(this.realmQuery, value);
        return this;
    }

    @SafeVarargs
    @NonNull
    public final <V> RealmTypeSafeQuery<Model> in(@NonNull RealmField<Model, V> field, V... values) {
        return this.in(field, Arrays.asList(values));
    }

    @NonNull 
	public <V> RealmTypeSafeQuery<Model> in(@NonNull RealmField<Model, V> field, @NonNull List<V> values) {
        // taken from io.realm.RealmQuery.in()
        if (values.size() == 0) {return this;}

        this.beginGroup().equalTo(field, values.get(0));
        for (int i = 1; i < values.size(); i++) {
            this.or().equalTo(field, values.get(i));
        }
        return endGroup();
    }

    /*
        String
    */

    @NonNull
    public RealmTypeSafeQuery<Model> equalTo(@NonNull RealmStringField<Model> field, @Nullable String value, @NonNull Case casing) {
        if (value == null) {
            return this.isNull(field);
        }

        this.realmQuery.equalTo(field.getKeyPath(), value, casing);
        return this;
    }

    @NonNull
    public RealmTypeSafeQuery<Model> notEqualTo(@NonNull RealmStringField<Model> field, @Nullable String value, @NonNull Case casing) {
        if (value == null) {
            return this.isNotNull(field);
        }

        this.realmQuery.notEqualTo(field.getKeyPath(), value, casing);
        return this;
    }

    @NonNull
    public RealmTypeSafeQuery<Model> beginsWith(@NonNull RealmStringField<Model> field, @Nullable String value) {
        return this.beginsWith(field, value, Case.SENSITIVE);
    }

    @NonNull
    public RealmTypeSafeQuery<Model> beginsWith(@NonNull RealmStringField<Model> field, @Nullable String value, @NonNull Case casing) {
        if (value == null) {
            return this.isNull(field);
        }

        this.realmQuery.beginsWith(field.getKeyPath(), value, casing);
        return this;
    }

    @NonNull
    public RealmTypeSafeQuery<Model> endsWith(@NonNull RealmStringField<Model> field, @Nullable String value) {
        return this.endsWith(field, value, Case.SENSITIVE);
    }

    @NonNull
    public RealmTypeSafeQuery<Model> endsWith(@NonNull RealmStringField<Model> field, @Nullable String value, @NonNull Case casing) {
        if (value == null) {
            return this.isNull(field);
        }


        this.realmQuery.endsWith(field.getKeyPath(), value, casing);
        return this;
    }

    @NonNull
    public RealmTypeSafeQuery<Model> contains(@NonNull RealmStringField<Model> field, @Nullable String value) {
        return this.contains(field, value, Case.SENSITIVE);
    }

    @NonNull
    public RealmTypeSafeQuery<Model> contains(@NonNull RealmStringField<Model> field, @Nullable String value, @NonNull Case casing) {
        if (value == null) {
            return this.isNull(field);
        }

        this.realmQuery.contains(field.getKeyPath(), value, casing);
        return this;
    }

    @NonNull
    public RealmTypeSafeQuery<Model> contains(@NonNull RealmStringField<Model> field, @Nullable String value, @NonNull String delimiter) {
        return this.contains(field, value, delimiter, Case.SENSITIVE);
    }

    @NonNull
    public RealmTypeSafeQuery<Model> contains(@NonNull RealmStringField<Model> field, @Nullable String value, @NonNull String delimiter, @NonNull Case casing) {
        if (value == null) {
            return this.isNull(field);
        }

        this.beginGroup();

        this.realmQuery.contains(field.getKeyPath(), delimiter + value + delimiter, casing);
        this.realmQuery.equalTo(field.getKeyPath(), value, casing);
        this.realmQuery.beginsWith(field.getKeyPath(), value + delimiter, casing);
        this.realmQuery.endsWith(field.getKeyPath(), delimiter + value, casing);

        this.endGroup();

        return this;
    }

    /*
        Comparison
     */

    public <V> RealmTypeSafeQuery<Model> greaterThan(@NonNull RealmComparableField<Model, V> field, @Nullable V value) {
        field.greaterThan(this.realmQuery, value);
        return this;
    }

    public <V> RealmTypeSafeQuery<Model> greaterThanOrEqualTo(@NonNull RealmComparableField<Model, V> field, @Nullable V value) {
        field.greaterThanOrEqualTo(this.realmQuery, value);
        return this;
    }

    public <V> RealmTypeSafeQuery<Model> lessThan(@NonNull RealmComparableField<Model, V> field, @Nullable V value) {
        field.lessThan(this.realmQuery, value);
        return this;
    }

    public <V> RealmTypeSafeQuery<Model> lessThanOrEqualTo(@NonNull RealmComparableField<Model, V> field, @Nullable V value) {
        field.lessThanOrEqualTo(this.realmQuery, value);
        return this;
    }

    public <V> RealmTypeSafeQuery<Model> between(@NonNull RealmComparableField<Model, V> field, @Nullable V start, @Nullable V end) {
        field.between(this.realmQuery, start, end);
        return this;
    }

    /*
        Groups
     */

    public interface Group<Model extends RealmModel> {
        void group(RealmTypeSafeQuery<Model> query);
    }

    @NonNull
    public RealmTypeSafeQuery<Model> group(@NonNull RealmTypeSafeQuery.Group<Model> group) {
        this.realmQuery.beginGroup();
        group.group(this);
        this.realmQuery.endGroup();
        return this;
    }

    @NonNull
    public RealmTypeSafeQuery<Model> beginGroup() {
        this.realmQuery.beginGroup();
        return this;
    }

    @NonNull
    public RealmTypeSafeQuery<Model> endGroup() {
        this.realmQuery.endGroup();
        return this;
    }

    /*
        Or
     */

    @NonNull
    public RealmTypeSafeQuery<Model> or() {
        this.realmQuery.or();
        return this;
    }

    @NonNull
    public RealmTypeSafeQuery<Model> or(@NonNull Group<Model> group) {
        this.or().group(group);
        return this;
    }

    /*
        Not
     */

    @NonNull
    public RealmTypeSafeQuery<Model> not() {
        this.realmQuery.not();
        return this;
    }

    @NonNull
    public RealmTypeSafeQuery<Model> not(@NonNull Group<Model> group) {
        this.not().group(group);
        return this;
    }

    /*
        Empty
     */

    @NonNull 
	public RealmTypeSafeQuery<Model> isEmpty(@NonNull RealmEmptyableField<Model, ?> field) {
        this.realmQuery.isEmpty(field.getKeyPath());
        return this;
    }

    @NonNull 
	public RealmTypeSafeQuery<Model> isNotEmpty(@NonNull RealmEmptyableField<Model, ?> field) {
        this.realmQuery.isNotEmpty(field.getKeyPath());
        return this;
    }

    @NonNull 
	public RealmTypeSafeQuery<Model> isEmpty(@NonNull RealmToManyRelationship<Model, ?> field) {
        this.realmQuery.isEmpty(field.getKeyPath());
        return this;
    }

    @NonNull 
	public RealmTypeSafeQuery<Model> isNotEmpty(@NonNull RealmToManyRelationship<Model, ?> field) {
        this.realmQuery.isNotEmpty(field.getKeyPath());
        return this;
    }

    /*
        Distinct
     */

    @NonNull
    public RealmTypeSafeQuery<Model> distinct(@NonNull RealmIndexedField<Model, ?> field) {
        throw new UnsupportedOperationException();
    }

    /*
        Aggregate Functions
     */

    public double sum(@NonNull RealmField<Model, Number> field) {
        return this.realmQuery.sum(field.getKeyPath()).doubleValue();
    }

    public double average(@NonNull RealmField<Model, Number> field) {
        return this.realmQuery.average(field.getKeyPath());
    }

    @Nullable
    public Number min(@NonNull RealmField<Model, Number> field) {
        return this.realmQuery.min(field.getKeyPath());
    }

    @Nullable
    public Number max(@NonNull RealmField<Model, Number> field) {
        return this.realmQuery.max(field.getKeyPath());
    }

    @Nullable
    public Date min(@NonNull RealmDateField<Model> field) {
        return this.realmQuery.minimumDate(field.getKeyPath());
    }

    @Nullable
    public Date max(@NonNull RealmDateField<Model> field) {
        return this.realmQuery.maximumDate(field.getKeyPath());
    }

    /*
        Sort
     */
    @NonNull
    public RealmTypeSafeQuery<Model> sort(SortableRealmField<Model, ?> field, Sort sort) {
        this.sortOrders.add(new Pair<>(field.getKeyPath(), sort));
        return this;
    }

    @NonNull
    public Pair<String[], Sort[]> sortParams() {
        String[] fieldKeyPaths = new String[this.sortOrders.size()];
        Sort[] sorts = new Sort[this.sortOrders.size()];

        for (int i = 0; i < this.sortOrders.size(); i++) {
            Pair<String, Sort> pair = this.sortOrders.get(i);
            fieldKeyPaths[i] = pair.first;
            sorts[i] = pair.second;
        }

        return new Pair<>(fieldKeyPaths, sorts);
    }

    /*
        Results
     */

    public long count() {
        return this.realmQuery.count();
    }

    @NonNull
    public RealmResults<Model> findAll() {
        Pair<String[], Sort[]> sortParams = this.sortParams();
        if (sortParams.first.length == 0) {
            return this.realmQuery.findAll();
        }

        return this.realmQuery.findAllSorted(sortParams.first, sortParams.second);
    }

    @NonNull
    public RealmResults<Model> findAllAsync() {
        Pair<String[], Sort[]> sortParams = this.sortParams();
        if (sortParams.first.length == 0) {
            return this.realmQuery.findAllAsync();
        }

        return this.realmQuery.findAllSortedAsync(sortParams.first, sortParams.second);
    }

    @Nullable
    public Model findFirst() {
        return this.realmQuery.findFirst();
    }

    @NonNull
    public Model findFirstAsync() {
        return this.realmQuery.findFirstAsync();
    }
}
