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

    @NonNull
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

        @NonNull
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
        return RealmTypeSafeQuery.with(realm).where(field.getModelClass()).equalTo(field, value).findFirst();
    }

    @Nullable
    public static <M extends RealmModel, V> M findFirst(@NonNull Realm realm, @NonNull SortableRealmField<M, V> field, @Nullable V value, @NonNull Sort sort) {
        return RealmTypeSafeQuery.with(realm).where(field.getModelClass()).equalTo(field, value).findFirstSorted(field, sort);
    }

    @NonNull
    public static <M extends RealmModel, V> RealmResults<M> findAll(@NonNull Realm realm, @NonNull RealmField<M, V> field, @Nullable V value) {
        return RealmTypeSafeQuery.with(realm).where(field.getModelClass()).equalTo(field, value).findAll();
    }

    @NonNull
    public static <M extends RealmModel, V> RealmResults<M> findAll(@NonNull Realm realm, @NonNull SortableRealmField<M, V> field, @Nullable V value, @NonNull Sort sort) {
        return RealmTypeSafeQuery.with(realm).where(field.getModelClass()).equalTo(field, value).findAllSorted(field, sort);
    }

    @NonNull
    private final RealmQuery<Model> realmQuery;

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

    public long count() {
        return realmQuery.count();
    }

    /*
        Results
     */

    private static String[] fieldNames(RealmField<?, ?>[] fields) {
        final String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            fieldNames[i] = fields[i].getKeyPath();
        }

        return fieldNames;
    }

    @NonNull
    public RealmResults<Model> findAll() {
        return realmQuery.findAll();
    }

    @NonNull
    public RealmResults<Model> findAllSorted(SortableRealmField<Model, ?> field, Sort sort) {
        return realmQuery.findAllSorted(field.getKeyPath(), sort);
    }

    @NonNull
    public RealmResults<Model> findAllSorted(SortableRealmField<Model, ?> field1, Sort sort1, SortableRealmField<Model, ?> field2, Sort sort2) {
        return realmQuery.findAllSorted(field1.getKeyPath(), sort1, field2.getKeyPath(), sort2);
    }

    @NonNull
    public RealmResults<Model> findAllSorted(SortableRealmField<Model, ?>[] fields, Sort[] sorts) {
        return realmQuery.findAllSorted(fieldNames(fields), sorts);
    }

     /*
        Find First
     */

    @Nullable
    public Model findFirst() {
        return realmQuery.findFirst();
    }

    @Nullable
    public Model findFirstSorted(SortableRealmField<Model, ?> field, Sort sort) {
        return findAllSorted(field, sort).first(null);
    }

    @Nullable
    public Model findFirstSorted(SortableRealmField<Model, ?> field1, Sort sort1, SortableRealmField<Model, ?> field2, Sort sort2) {
        return findAllSorted(field1, sort1, field2, sort2).first(null);
    }

    @Nullable
    public Model findFirstSorted(SortableRealmField<Model, ?>[] fields, Sort[] sorts) {
        return findAllSorted(fields, sorts).first(null);
    }

    /*
        Async
     */

    @NonNull
    public RealmResults<Model> findAllAsync() {
        return realmQuery.findAllAsync();
    }

    @NonNull
    public RealmResults<Model> findAllSortedAsync(SortableRealmField<Model, ?> field, Sort sort) {
        return realmQuery.findAllSortedAsync(field.getKeyPath(), sort);
    }

    @NonNull
    public RealmResults<Model> findAllSortedAsync(SortableRealmField<Model, ?> field1, Sort sort1, SortableRealmField<Model, ?> field2, Sort sort2) {
        return realmQuery.findAllSortedAsync(field1.getKeyPath(), sort1, field2.getKeyPath(), sort2);
    }

    @NonNull
    public RealmResults<Model> findAllSortedAsync(SortableRealmField<Model, ?>[] fields, Sort[] sorts) {
        return realmQuery.findAllSortedAsync(fieldNames(fields), sorts);
    }

    @NonNull
    public Model findFirstAsync() {
        return realmQuery.findFirstAsync();
    }

    /*
        Buildable Sort
     */

    @NonNull
    public SortBuilder<Model> sort(SortableRealmField<Model, ?> field, Sort sort) {
        return new SortBuilder<>(realmQuery).sort(field, sort);
    }

    public static class SortBuilder<Model extends RealmModel> {

        @NonNull
        private final List<Pair<String, Sort>> sortParams = new LinkedList<>();
        @NonNull
        private final RealmQuery<Model> realmQuery;

        private SortBuilder(@NonNull RealmQuery<Model> realmQuery) {
            this.realmQuery= realmQuery;
        }

        @NonNull
        public SortBuilder<Model> sort(SortableRealmField<Model, ?> field, Sort sort) {
            sortParams.add(new Pair<>(field.getKeyPath(), sort));
            return this;
        }

        @NonNull
        public Pair<String[], Sort[]> getSortParams() {
            String[] fieldNames = new String[sortParams.size()];
            Sort[] sorts = new Sort[sortParams.size()];

            for (int i = 0; i < sortParams.size(); i++) {
                Pair<String, Sort> pair = sortParams.get(i);
                fieldNames[i] = pair.first;
                sorts[i] = pair.second;
            }

            return new Pair<>(fieldNames, sorts);
        }

        @NonNull
        public RealmResults<Model> findAll() {
            final Pair<String[], Sort[]> sortParams = getSortParams();
            return realmQuery.findAllSorted(sortParams.first, sortParams.second);
        }

        @Nullable
        public Model findFirst() {
            return findAll().first(null);
        }

        @NonNull
        public RealmResults<Model> findAllAsync() {
            final Pair<String[], Sort[]> sortParams = getSortParams();
            return realmQuery.findAllSortedAsync(sortParams.first, sortParams.second);
        }
    }
}
