package com.quarkworks.android.realmtypesafequery;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Pair;

import com.quarkworks.android.realmtypesafequery.fields.RealmAggregatableField;
import com.quarkworks.android.realmtypesafequery.fields.RealmDistinctableField;
import com.quarkworks.android.realmtypesafequery.fields.RealmEquatableField;
import com.quarkworks.android.realmtypesafequery.fields.RealmField;
import com.quarkworks.android.realmtypesafequery.fields.RealmMinMaxField;
import com.quarkworks.android.realmtypesafequery.fields.RealmNullableField;
import com.quarkworks.android.realmtypesafequery.fields.RealmStringField;
import com.quarkworks.android.realmtypesafequery.fields.RealmComparableField;
import com.quarkworks.android.realmtypesafequery.fields.RealmEmptyableField;
import com.quarkworks.android.realmtypesafequery.fields.RealmInableField;
import com.quarkworks.android.realmtypesafequery.fields.RealmSortableField;

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
    public static <M extends RealmModel, V> M findFirst(@NonNull Realm realm, @NonNull RealmEquatableField<M, V> field, @Nullable V value) {
        return RealmTypeSafeQuery.with(realm).where(field.getModelClass())
                .equalTo(field, value).findFirst();
    }

    @Nullable
    public static <M extends RealmModel, V> M findFirst(@NonNull Realm realm, @NonNull RealmSortableField<M, V> field, @Nullable V value, @NonNull Sort sort) {
        return RealmTypeSafeQuery.with(realm).where(field.getModelClass()).equalTo(field, value).findFirstSorted(field, sort);
    }

    @NonNull
    public static <M extends RealmModel, V> RealmResults<M> findAll(@NonNull Realm realm, @NonNull RealmSortableField<M, V> field, @Nullable V value) {
        return new RealmTypeSafeQuery<>(field.getModelClass(), realm)
                .equalTo(field, value).findAll();
    }

    @NonNull
    public static <M extends RealmModel, V> RealmResults<M> findAll(@NonNull Realm realm, @NonNull RealmSortableField<M, V> field, @Nullable V value, @NonNull Sort sort) {
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
    public RealmTypeSafeQuery<Model> isNull(@NonNull RealmNullableField<Model> field) {
        field.isNull(realmQuery);
        return this;
    }

    @NonNull
    public RealmTypeSafeQuery<Model> isNotNull(@NonNull RealmNullableField<Model> field) {
        field.isNotNull(realmQuery);
        return this;
    }

    /*
        EqualTo
     */

    @NonNull
    public <V> RealmTypeSafeQuery<Model> equalTo(@NonNull RealmEquatableField<Model, V> field, @Nullable V value) {
        field.equalTo(realmQuery, value);
        return this;
    }

    @NonNull
    public <V> RealmTypeSafeQuery<Model> notEqualTo(@NonNull RealmEquatableField<Model, V> field, @Nullable V value) {
        field.notEqualTo(realmQuery, value);
        return this;
    }

    @NonNull
    public final <V> RealmTypeSafeQuery<Model> in(@NonNull RealmInableField<Model, V> field, V[] values) {
        field.in(realmQuery, values);
        return this;
    }

    /*
        String
    */

    @NonNull
    public RealmTypeSafeQuery<Model> equalTo(@NonNull RealmStringField<Model> field, @Nullable String value, @NonNull Case casing) {
        field.equalTo(realmQuery, value, casing);
        return this;
    }

    @NonNull
    public RealmTypeSafeQuery<Model> notEqualTo(@NonNull RealmStringField<Model> field, @Nullable String value, @NonNull Case casing) {
        field.notEqualTo(realmQuery, value, casing);
        return this;
    }

    @NonNull
    public RealmTypeSafeQuery<Model> beginsWith(@NonNull RealmStringField<Model> field, @Nullable String value) {
        field.beginsWith(realmQuery, value);
        return this;
    }

    @NonNull
    public RealmTypeSafeQuery<Model> beginsWith(@NonNull RealmStringField<Model> field, @Nullable String value, @NonNull Case casing) {
        field.beginsWith(realmQuery, value, casing);
        return this;
    }

    @NonNull
    public RealmTypeSafeQuery<Model> endsWith(@NonNull RealmStringField<Model> field, @Nullable String value) {
        field.endsWith(realmQuery, value);
        return this;
    }

    @NonNull
    public RealmTypeSafeQuery<Model> endsWith(@NonNull RealmStringField<Model> field, @Nullable String value, @NonNull Case casing) {
        field.endsWith(realmQuery, value, casing);
        return this;
    }

    @NonNull
    public RealmTypeSafeQuery<Model> contains(@NonNull RealmStringField<Model> field, @Nullable String value) {
        field.contains(realmQuery, value);
        return this;
    }

    @NonNull
    public RealmTypeSafeQuery<Model> contains(@NonNull RealmStringField<Model> field, @Nullable String value, @NonNull Case casing) {
        field.contains(realmQuery, value, casing);
        return this;
    }

    @NonNull
    public RealmTypeSafeQuery<Model> contains(@NonNull RealmStringField<Model> field, @Nullable String value, @NonNull String delimiter) {
        field.contains(realmQuery, value, delimiter);
        return this;
    }

    @NonNull
    public RealmTypeSafeQuery<Model> contains(@NonNull RealmStringField<Model> field, @Nullable String value, @NonNull String delimiter, @NonNull Case casing) {
        field.contains(realmQuery, value, delimiter, casing);
        return this;
    }

    @NonNull
    public RealmTypeSafeQuery<Model> like(@NonNull RealmStringField<Model> field, @Nullable String value) {
        field.like(realmQuery, value);
        return this;
    }

    @NonNull
    public RealmTypeSafeQuery<Model> like(@NonNull RealmStringField<Model> field, @Nullable String value, @NonNull Case casing) {
        field.like(realmQuery, value, casing);
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
    public RealmTypeSafeQuery<Model> isEmpty(@NonNull RealmEmptyableField<Model> field) {
        field.isEmpty(realmQuery);
        return this;
    }

    @NonNull
    public RealmTypeSafeQuery<Model> isNotEmpty(@NonNull RealmEmptyableField<Model> field) {
        field.isNotEmpty(realmQuery);
        return this;
    }

    /*
        Distinct
     */

    @NonNull
    public RealmResults<Model> distinct(@NonNull RealmDistinctableField<Model> field) {
        return field.distinct(realmQuery);
    }

    @NonNull
    public RealmResults<Model> distinctAsync(@NonNull RealmDistinctableField<Model> field) {
        return field.distinctAsync(realmQuery);
    }

    @SafeVarargs
    @NonNull
    public final RealmResults<Model> distinct(@NonNull RealmDistinctableField<Model> firstField, @NonNull RealmDistinctableField<Model>... remainingFields) {
        return firstField.distinct(realmQuery, remainingFields);
    }

    /*
        Aggregate Functions
     */

    @NonNull
    public <T> T sum(@NonNull RealmAggregatableField<Model, T> field) {
        return field.sum(realmQuery);
    }

    public double average(@NonNull RealmAggregatableField<Model, ?> field) {
        return field.average(realmQuery);
    }

    /*
        Min Max
     */

    @Nullable
    public <T> T min(@NonNull RealmMinMaxField<Model, T> field) {
        return field.min(realmQuery);
    }

    @Nullable
    public <T> T max(@NonNull RealmMinMaxField<Model, T> field) {
        return field.max(realmQuery);
    }

    public long count() {
        return realmQuery.count();
    }

    /*
        Results
     */

    private static String[] fieldNames(RealmField<?>[] fields) {
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
    public RealmResults<Model> findAllSorted(RealmSortableField<Model, ?> field, Sort sort) {
        return realmQuery.findAllSorted(field.getKeyPath(), sort);
    }

    @NonNull
    public RealmResults<Model> findAllSorted(RealmSortableField<Model, ?> field1, Sort sort1, RealmSortableField<Model, ?> field2, Sort sort2) {
        return realmQuery.findAllSorted(field1.getKeyPath(), sort1, field2.getKeyPath(), sort2);
    }

    @NonNull
    public RealmResults<Model> findAllSorted(RealmSortableField<Model, ?>[] fields, Sort[] sorts) {
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
    public Model findFirstSorted(RealmSortableField<Model, ?> field, Sort sort) {
        return findAllSorted(field, sort).first(null);
    }

    @Nullable
    public Model findFirstSorted(RealmSortableField<Model, ?> field1, Sort sort1, RealmSortableField<Model, ?> field2, Sort sort2) {
        return findAllSorted(field1, sort1, field2, sort2).first(null);
    }

    @Nullable
    public Model findFirstSorted(RealmSortableField<Model, ?>[] fields, Sort[] sorts) {
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
    public RealmResults<Model> findAllSortedAsync(RealmSortableField<Model, ?> field, Sort sort) {
        return realmQuery.findAllSortedAsync(field.getKeyPath(), sort);
    }

    @NonNull
    public RealmResults<Model> findAllSortedAsync(RealmSortableField<Model, ?> field1, Sort sort1, RealmSortableField<Model, ?> field2, Sort sort2) {
        return realmQuery.findAllSortedAsync(field1.getKeyPath(), sort1, field2.getKeyPath(), sort2);
    }

    @NonNull
    public RealmResults<Model> findAllSortedAsync(RealmSortableField<Model, ?>[] fields, Sort[] sorts) {
        return realmQuery.findAllSortedAsync(fieldNames(fields), sorts);
    }

    @NonNull
    public Model findFirstAsync() {
        return realmQuery.findFirstAsync();
    }

    /*
        Distinct
     */

    @NonNull
    public RealmResults<Model> distinct(RealmEquatableField<Model, ?> field) {
        return realmQuery.distinct(field.getKeyPath());
    }

    /*
        Buildable Sort
     */

    @NonNull
    public SortBuilder<Model> sort(RealmSortableField<Model, ?> field, Sort sort) {
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
        public SortBuilder<Model> sort(RealmSortableField<Model, ?> field, Sort sort) {
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
