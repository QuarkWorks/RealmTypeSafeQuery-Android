package com.quarkworks.android.realmtypesafequery;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Pair;

import com.quarkworks.android.realmtypesafequery.fields.RealmDateField;
import com.quarkworks.android.realmtypesafequery.fields.RealmStringField;
import com.quarkworks.android.realmtypesafequery.interfaces.RealmComparableField;
import com.quarkworks.android.realmtypesafequery.interfaces.RealmEmptyableField;
import com.quarkworks.android.realmtypesafequery.interfaces.RealmField;
import com.quarkworks.android.realmtypesafequery.interfaces.RealmIndexedField;
import com.quarkworks.android.realmtypesafequery.interfaces.SortableRealmField;
import com.quarkworks.android.realmtypesafequery.relationships.RealmToManyRelationship;
import com.quarkworks.android.realmtypesafequery.relationships.RealmToOneRelationship;

import java.util.Arrays;
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

@SuppressWarnings({"WeakerAccess", "unused"})
public class RealmTypeSafeQuery<Model extends RealmModel> {

    /*
        Static Constructors
     */

    public static RealmTypeSafeQuery1 with(@NonNull Realm realm) {
        return new RealmTypeSafeQuery1(realm);
    }

    /*
        Intermediary builder class
     */

    public static final class RealmTypeSafeQuery1 {
        @NonNull
        private final Realm realm;

        private RealmTypeSafeQuery1(@NonNull Realm realm) {
            this.realm = realm;
        }

        public <M extends RealmModel> RealmTypeSafeQuery<M> where(@NonNull Class<M> clazz) {
            return new RealmTypeSafeQuery<>(clazz, realm);
        }
    }

    @NonNull
    public static <M extends RealmModel> RealmTypeSafeQuery<M> where(@NonNull Class<M> clazz, @NonNull Realm realm) {
        return new RealmTypeSafeQuery<>(clazz, realm);
    }

    /*
         Quick Queries
     */

    @Nullable
    public static <M extends RealmModel, V> M findFirst(@NonNull Realm realm, @NonNull RealmField<M, V> field, @Nullable V value) {
        return RealmTypeSafeQuery.with(realm).where(field.getModelClass())
                .equalTo(field, value).findFirst();
    }

    @Nullable
    public static <M extends RealmModel, V> M findFirst(@NonNull Realm realm, @NonNull SortableRealmField<M, V> field, @Nullable V value, @NonNull Sort sort) {
        return RealmTypeSafeQuery.with(realm).where(field.getModelClass())
                .equalTo(field, value).sort(field, sort).findFirst();
    }

    @NonNull
    public static <M extends RealmModel, V> RealmResults<M> findAll(@NonNull Realm realm, @NonNull RealmField<M, V> field, @Nullable V value) {
        return new RealmTypeSafeQuery<>(field.getModelClass(), realm)
                .equalTo(field, value).findAll();
    }

    @NonNull
    public static <M extends RealmModel, V> RealmResults<M> findAll(@NonNull Realm realm, @NonNull SortableRealmField<M, V> field, @Nullable V value, @NonNull Sort sort) {
        return new RealmTypeSafeQuery<>(field.getModelClass(), realm)
                .equalTo(field, value).sort(field, sort).findAll();
    }

    @NonNull
    private final RealmQuery<Model> realmQuery;

    @NonNull
    private final List<Pair<String, Sort>> sortParams = new LinkedList<>();

    /*
        Constructors
     */

    public RealmTypeSafeQuery(@NonNull RealmQuery<Model> realmQuery) {
        this.realmQuery = realmQuery;
    }

    public RealmTypeSafeQuery(@NonNull Class<Model> modelClass, @NonNull Realm realm) {
        realmQuery = realm.where(modelClass);
    }

    public RealmTypeSafeQuery(@NonNull RealmCollection<Model> realmCollection) {
        realmQuery = realmCollection.where();
    }

    @NonNull
    public RealmQuery<Model> getRealmQuery() {
        return realmQuery;
    }

    public boolean isValid() {
        return realmQuery.isValid();
    }

    /*
        Null
     */

    @NonNull
    public RealmTypeSafeQuery<Model> isNull(@NonNull RealmField<Model, ?> field) {
        field.isNull(realmQuery);
        return this;
    }

    @NonNull
    public RealmTypeSafeQuery<Model> isNotNull(@NonNull RealmField<Model, ?> field) {
        field.isNotNull(realmQuery);
        return this;
    }

    @NonNull
    public RealmTypeSafeQuery<Model> isNull(@NonNull RealmToOneRelationship<Model, ?> field) {
        realmQuery.isNull(field.getKeyPath());
        return this;
    }

    @NonNull
    public RealmTypeSafeQuery<Model> isNotNull(@NonNull RealmToOneRelationship<Model, ?> field) {
        realmQuery.isNotNull(field.getKeyPath());
        return this;
    }

    /*
        EqualTo
     */

    @NonNull
    public <V> RealmTypeSafeQuery<Model> equalTo(@NonNull RealmField<Model, V> field, @Nullable V value) {
        field.equalTo(realmQuery, value);
        return this;
    }

    @NonNull
    public <V> RealmTypeSafeQuery<Model> notEqualTo(@NonNull RealmField<Model, V> field, @Nullable V value) {
        field.notEqualTo(realmQuery, value);
        return this;
    }

    @SafeVarargs
    @NonNull
    public final <V> RealmTypeSafeQuery<Model> in(@NonNull RealmField<Model, V> field, V... values) {
        return in(field, Arrays.asList(values));
    }

    @NonNull
    public <V> RealmTypeSafeQuery<Model> in(@NonNull RealmField<Model, V> field, @NonNull List<V> values) {
        // taken from io.realm.RealmQuery.in()
        if (values.size() == 0) {return this;}

        beginGroup().equalTo(field, values.get(0));
        for (int i = 1; i < values.size(); i++) {
            or().equalTo(field, values.get(i));
        }
        return endGroup();
    }

    /*
        String
    */

    @NonNull
    public RealmTypeSafeQuery<Model> equalTo(@NonNull RealmStringField<Model> field, @Nullable String value, @NonNull Case casing) {
        if (value == null) {
            return isNull(field);
        }

        realmQuery.equalTo(field.getKeyPath(), value, casing);
        return this;
    }

    @NonNull
    public RealmTypeSafeQuery<Model> notEqualTo(@NonNull RealmStringField<Model> field, @Nullable String value, @NonNull Case casing) {
        if (value == null) {
            return isNotNull(field);
        }

        realmQuery.notEqualTo(field.getKeyPath(), value, casing);
        return this;
    }

    @NonNull
    public RealmTypeSafeQuery<Model> beginsWith(@NonNull RealmStringField<Model> field, @Nullable String value) {
        return beginsWith(field, value, Case.SENSITIVE);
    }

    @NonNull
    public RealmTypeSafeQuery<Model> beginsWith(@NonNull RealmStringField<Model> field, @Nullable String value, @NonNull Case casing) {
        if (value == null) {
            return isNull(field);
        }

        realmQuery.beginsWith(field.getKeyPath(), value, casing);
        return this;
    }

    @NonNull
    public RealmTypeSafeQuery<Model> endsWith(@NonNull RealmStringField<Model> field, @Nullable String value) {
        return endsWith(field, value, Case.SENSITIVE);
    }

    @NonNull
    public RealmTypeSafeQuery<Model> endsWith(@NonNull RealmStringField<Model> field, @Nullable String value, @NonNull Case casing) {
        if (value == null) {
            return isNull(field);
        }

        realmQuery.endsWith(field.getKeyPath(), value, casing);
        return this;
    }

    @NonNull
    public RealmTypeSafeQuery<Model> contains(@NonNull RealmStringField<Model> field, @Nullable String value) {
        return contains(field, value, Case.SENSITIVE);
    }

    @NonNull
    public RealmTypeSafeQuery<Model> contains(@NonNull RealmStringField<Model> field, @Nullable String value, @NonNull Case casing) {
        if (value == null) {
            return isNull(field);
        }

        realmQuery.contains(field.getKeyPath(), value, casing);
        return this;
    }

    @NonNull
    public RealmTypeSafeQuery<Model> contains(@NonNull RealmStringField<Model> field, @Nullable String value, @NonNull String delimiter) {
        return contains(field, value, delimiter, Case.SENSITIVE);
    }

    @NonNull
    public RealmTypeSafeQuery<Model> contains(@NonNull RealmStringField<Model> field, @Nullable String value, @NonNull String delimiter, @NonNull Case casing) {
        if (value == null) {
            return isNull(field);
        }

        beginGroup();

        realmQuery.contains(field.getKeyPath(), delimiter + value + delimiter, casing);
        realmQuery.equalTo(field.getKeyPath(), value, casing);
        realmQuery.beginsWith(field.getKeyPath(), value + delimiter, casing);
        realmQuery.endsWith(field.getKeyPath(), delimiter + value, casing);

        endGroup();

        return this;
    }

    /*
        Comparison
     */

    public <V> RealmTypeSafeQuery<Model> greaterThan(@NonNull RealmComparableField<Model, V> field, @Nullable V value) {
        field.greaterThan(realmQuery, value);
        return this;
    }

    public <V> RealmTypeSafeQuery<Model> greaterThanOrEqualTo(@NonNull RealmComparableField<Model, V> field, @Nullable V value) {
        field.greaterThanOrEqualTo(realmQuery, value);
        return this;
    }

    public <V> RealmTypeSafeQuery<Model> lessThan(@NonNull RealmComparableField<Model, V> field, @Nullable V value) {
        field.lessThan(realmQuery, value);
        return this;
    }

    public <V> RealmTypeSafeQuery<Model> lessThanOrEqualTo(@NonNull RealmComparableField<Model, V> field, @Nullable V value) {
        field.lessThanOrEqualTo(realmQuery, value);
        return this;
    }

    public <V> RealmTypeSafeQuery<Model> between(@NonNull RealmComparableField<Model, V> field, @Nullable V start, @Nullable V end) {
        field.between(realmQuery, start, end);
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
        realmQuery.beginGroup();
        group.group(this);
        realmQuery.endGroup();
        return this;
    }

    @NonNull
    public RealmTypeSafeQuery<Model> beginGroup() {
        realmQuery.beginGroup();
        return this;
    }

    @NonNull
    public RealmTypeSafeQuery<Model> endGroup() {
        realmQuery.endGroup();
        return this;
    }

    /*
        Or
     */

    @NonNull
    public RealmTypeSafeQuery<Model> or() {
        realmQuery.or();
        return this;
    }

    @NonNull
    public RealmTypeSafeQuery<Model> or(@NonNull Group<Model> group) {
        or().group(group);
        return this;
    }

    /*
        Not
     */

    @NonNull
    public RealmTypeSafeQuery<Model> not() {
        realmQuery.not();
        return this;
    }

    @NonNull
    public RealmTypeSafeQuery<Model> not(@NonNull Group<Model> group) {
        not().group(group);
        return this;
    }

    /*
        Empty
     */

    @NonNull
    public RealmTypeSafeQuery<Model> isEmpty(@NonNull RealmEmptyableField<Model, ?> field) {
        realmQuery.isEmpty(field.getKeyPath());
        return this;
    }

    @NonNull
    public RealmTypeSafeQuery<Model> isNotEmpty(@NonNull RealmEmptyableField<Model, ?> field) {
        realmQuery.isNotEmpty(field.getKeyPath());
        return this;
    }

    @NonNull
    public RealmTypeSafeQuery<Model> isEmpty(@NonNull RealmToManyRelationship<Model, ?> field) {
        realmQuery.isEmpty(field.getKeyPath());
        return this;
    }

    @NonNull
    public RealmTypeSafeQuery<Model> isNotEmpty(@NonNull RealmToManyRelationship<Model, ?> field) {
        realmQuery.isNotEmpty(field.getKeyPath());
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

    public double sum(@NonNull RealmField<Model, ? extends Number> field) {
        return realmQuery.sum(field.getKeyPath()).doubleValue();
    }

    public double average(@NonNull RealmField<Model, ? extends Number> field) {
        return realmQuery.average(field.getKeyPath());
    }

    @Nullable
    public Number min(@NonNull RealmField<Model, ? extends Number> field) {
        return realmQuery.min(field.getKeyPath());
    }

    @Nullable
    public Number max(@NonNull RealmField<Model, ? extends Number> field) {
        return realmQuery.max(field.getKeyPath());
    }

    @Nullable
    public Date min(@NonNull RealmDateField<Model> field) {
        return realmQuery.minimumDate(field.getKeyPath());
    }

    @Nullable
    public Date max(@NonNull RealmDateField<Model> field) {
        return realmQuery.maximumDate(field.getKeyPath());
    }

    /*
        Sort
     */
    @NonNull
    public RealmTypeSafeQuery<Model> sort(SortableRealmField<Model, ?> field, Sort sort) {
        sortParams.add(new Pair<>(field.getKeyPath(), sort));
        return this;
    }

    @NonNull
    public Pair<String[], Sort[]> getSortParams() {
        String[] fieldKeyPaths = new String[sortParams.size()];
        Sort[] sorts = new Sort[sortParams.size()];

        for (int i = 0; i < sortParams.size(); i++) {
            Pair<String, Sort> pair = sortParams.get(i);
            fieldKeyPaths[i] = pair.first;
            sorts[i] = pair.second;
        }

        return new Pair<>(fieldKeyPaths, sorts);
    }

    /*
        Results
     */

    public long count() {
        return realmQuery.count();
    }

    @NonNull
    public RealmResults<Model> findAll() {
        Pair<String[], Sort[]> sortParams = getSortParams();
        if (sortParams.first.length == 0) {
            return realmQuery.findAll();
        }

        return realmQuery.findAllSorted(sortParams.first, sortParams.second);
    }

    @NonNull
    public RealmResults<Model> findAllAsync() {
        Pair<String[], Sort[]> sortParams = getSortParams();
        if (sortParams.first.length == 0) {
            return realmQuery.findAllAsync();
        }

        return realmQuery.findAllSortedAsync(sortParams.first, sortParams.second);
    }

    @Nullable
    public Model findFirst() {
        return realmQuery.findFirst();
    }

    @NonNull
    public Model findFirstAsync() {
        return realmQuery.findFirstAsync();
    }
}
