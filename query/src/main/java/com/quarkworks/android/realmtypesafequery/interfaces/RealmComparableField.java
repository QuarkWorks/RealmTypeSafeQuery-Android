package com.quarkworks.android.realmtypesafequery.interfaces;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.realm.RealmModel;
import io.realm.RealmQuery;

public interface RealmComparableField<Model extends RealmModel, Value> extends RealmField<Model, Value> {
    void greaterThan(@NonNull RealmQuery<Model> query, @Nullable Value value);
    void greaterThanOrEqualTo(@NonNull RealmQuery<Model> query, @Nullable Value value);
    void lessThan(@NonNull RealmQuery<Model> query, @Nullable Value value);
    void lessThanOrEqualTo(@NonNull RealmQuery<Model> query, @Nullable Value value);
    void between(@NonNull RealmQuery<Model> query, @Nullable Value start, @Nullable Value end);
}
