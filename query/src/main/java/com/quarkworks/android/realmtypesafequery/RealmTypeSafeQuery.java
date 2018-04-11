package com.quarkworks.android.realmtypesafequery;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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

import java.util.List;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmCollection;
import io.realm.RealmModel;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

@SuppressWarnings({"unused", "WeakerAccess", "UnusedReturnValue"})
public class RealmTypeSafeQuery<Model extends RealmModel> {

    /*
        Static Constructors
     */

    @NonNull
    public static Builder with(@NonNull Realm realm) {
        return new Builder(realm);
    }

    /*
        Intermediary builder class
     */

    public static final class Builder {
        @NonNull
        private final Realm realm;

        private Builder(@NonNull Realm realm) {
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
    public static <M extends RealmModel, V, F extends RealmEquatableField<M, V> & RealmSortableField<M>> M findFirst(@NonNull Realm realm, @NonNull F field, @Nullable V value, @NonNull Sort sortOrder) {
        return RealmTypeSafeQuery.with(realm).where(field.getModelClass()).equalTo(field, value)
                .sort(field, sortOrder).findFirst();
    }

    @NonNull
    public static <M extends RealmModel, V> RealmResults<M> findAll(@NonNull Realm realm, @NonNull RealmEquatableField<M, V> field, @Nullable V value) {
        return new RealmTypeSafeQuery<>(field.getModelClass(), realm)
                .equalTo(field, value).findAll();
    }

    @NonNull
    public static <M extends RealmModel, V, F extends RealmEquatableField<M, V> & RealmSortableField<M>> RealmResults<M> findAll(@NonNull Realm realm, @NonNull F field, @Nullable V value, @NonNull Sort sortOrder) {
        return RealmTypeSafeQuery.with(realm).where(field.getModelClass()).equalTo(field, value).sort(field, sortOrder).findAll();
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

    @NonNull
    public final <V> RealmTypeSafeQuery<Model> in(@NonNull RealmInableField<Model, V> field, List<V> values) {
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

    @NonNull
    public <V> RealmTypeSafeQuery<Model> greaterThan(@NonNull RealmComparableField<Model, V> field, @Nullable V value) {
        field.greaterThan(realmQuery, value);
        return this;
    }

    @NonNull
    public <V> RealmTypeSafeQuery<Model> greaterThanOrEqualTo(@NonNull RealmComparableField<Model, V> field, @Nullable V value) {
        field.greaterThanOrEqualTo(realmQuery, value);
        return this;
    }

    @NonNull
    public <V> RealmTypeSafeQuery<Model> lessThan(@NonNull RealmComparableField<Model, V> field, @Nullable V value) {
        field.lessThan(realmQuery, value);
        return this;
    }

    @NonNull
    public <V> RealmTypeSafeQuery<Model> lessThanOrEqualTo(@NonNull RealmComparableField<Model, V> field, @Nullable V value) {
        field.lessThanOrEqualTo(realmQuery, value);
        return this;
    }

    @NonNull
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
    public RealmQuery<Model> distinct(@NonNull RealmDistinctableField<Model> field) {
        return field.distinct(realmQuery);
    }

    @NonNull
    @SafeVarargs
    public final RealmQuery<Model> distinct(@NonNull RealmDistinctableField<Model> firstField, @NonNull RealmDistinctableField<Model>... remainingFields) {
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

    @NonNull
    private static String[] fieldNames(@NonNull RealmField<?>[] fields) {
        final String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            fieldNames[i] = fields[i].getName();
        }

        return fieldNames;
    }

    @NonNull
    public RealmResults<Model> findAll() {
        return realmQuery.findAll();
    }


     /*
        Find First
     */

    @Nullable
    public Model findFirst() {
        return realmQuery.findFirst();
    }

    /*
        Async
     */

    @NonNull
    public RealmResults<Model> findAllAsync() {
        return realmQuery.findAllAsync();
    }

    @NonNull
    public Model findFirstAsync() {
        return realmQuery.findFirstAsync();
    }


    /*
        Sort
     */

    @NonNull
    public RealmQuery<Model> sort(@NonNull RealmSortableField<Model> field) {
        return field.sort(realmQuery);
    }

    @NonNull
    public RealmQuery<Model> sort(@NonNull RealmSortableField<Model> field, @NonNull Sort sortOrder) {
        return field.sort(realmQuery, sortOrder);
    }

    @NonNull
    public RealmQuery<Model> sort(@NonNull RealmSortableField<Model> field1, @NonNull Sort sortOrder1, @NonNull RealmSortableField<Model> field2, @NonNull Sort sortOrder2) {
        return field1.sort(realmQuery, sortOrder1, field2, sortOrder2);
    }

    @NonNull
    public RealmQuery<Model> sort(@NonNull RealmSortableField<Model>[] fields, @NonNull Sort[] sortOrders) {
        return RealmSortableField.Companion.sort(realmQuery, fields, sortOrders);
    }
}