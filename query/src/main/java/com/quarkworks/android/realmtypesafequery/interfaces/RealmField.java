package com.quarkworks.android.realmtypesafequery.interfaces;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.realm.RealmModel;
import io.realm.RealmQuery;

public interface RealmField<Model extends RealmModel, Value> {
    @NonNull String getKeyPath();
    @NonNull Class<Model> getModelClass();

    boolean isRequired();
    boolean isOptional();
    boolean isIndexed();
    boolean isSortable();

    void isNull(@NonNull RealmQuery<Model> query);
    void isNotNull(@NonNull RealmQuery<Model> query);
    void equalTo(@NonNull RealmQuery<Model> query, @Nullable Value value);
    void notEqualTo(@NonNull RealmQuery<Model> query, @Nullable Value value);
    void never(@NonNull RealmQuery<Model> query);
}
