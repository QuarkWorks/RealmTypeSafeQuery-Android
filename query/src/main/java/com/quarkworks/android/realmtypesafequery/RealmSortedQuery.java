package com.quarkworks.android.realmtypesafequery;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Pair;

import com.quarkworks.android.realmtypesafequery.fields.RealmSortableField;

import java.util.LinkedList;
import java.util.List;

import io.realm.RealmModel;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by berbschloe on 12/11/17.
 */

@SuppressWarnings("WeakerAccess")
public final class RealmSortedQuery<Model extends RealmModel> {

    private static class SortParams {

        public final String[] fieldNames;
        public final Sort[] sortOrders;

        public SortParams(String[] fieldNames, Sort[] sortOrders) {
            this.fieldNames = fieldNames;
            this.sortOrders = sortOrders;
        }
    }

    @NonNull
    private final List<Pair<String, Sort>> sortParams = new LinkedList<>();
    @NonNull
    private final RealmQuery<Model> realmQuery;

    protected RealmSortedQuery(@NonNull RealmQuery<Model> realmQuery) {
        this.realmQuery = realmQuery;
    }

    /*
        Sorts
     */

    @NonNull
    public RealmSortedQuery<Model> sort(RealmSortableField<Model> field, Sort sortOrder) {
        sortParams.add(new Pair<>(field.getName(), sortOrder));
        return this;
    }

    @NonNull
    protected RealmSortedQuery<Model> sort(RealmSortableField<Model>[] fields, Sort[] sortOrders) {
        if (fields.length != sortOrders.length) {
            throw new IllegalArgumentException("fields length(" + fields.length + ") must equal sortOrders length (" + sortOrders.length +")");
        }

        for (int i = 0; i < fields.length; i++) {
            sort(fields[i], sortOrders[i]);
        }

        return this;
    }

    /*
        Results
     */

    @NonNull
    public RealmResults<Model> findAll() {
        final SortParams sortParams = getSortParams();
        return realmQuery.sort(sortParams.fieldNames, sortParams.sortOrders).findAll();
    }

    @Nullable
    public Model findFirst() {
        // findAll() is needed because Realm's findFirst() ignores sort orders.
        return findAll().first(null);
    }

    @NonNull
    public RealmResults<Model> findAllAsync() {
        final SortParams sortParams = getSortParams();
        return realmQuery.sort(sortParams.fieldNames, sortParams.sortOrders).findAllAsync();
    }

    @NonNull
    private SortParams getSortParams() {
        final String[] fieldNames = new String[sortParams.size()];
        final Sort[] sortOrders = new Sort[sortParams.size()];

        for (int i = 0; i < sortParams.size(); i++) {
            Pair<String, Sort> pair = sortParams.get(i);
            fieldNames[i] = pair.first;
            sortOrders[i] = pair.second;
        }

        return new SortParams(fieldNames, sortOrders);
    }
}
