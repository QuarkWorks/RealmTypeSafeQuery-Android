package com.quarkworks.android.realmtypesafequery.primary;

import android.support.annotation.NonNull;

import com.quarkworks.android.realmtypesafequery.converter.RealmFieldConverter;
import com.quarkworks.android.realmtypesafequery.indexed.RealmIndexedShortField;

import io.realm.RealmModel;

public class RealmPrimaryShortField<Model extends RealmModel, Value> extends RealmIndexedShortField<Model, Value> {
    @NonNull
    public static <Model extends RealmModel> RealmPrimaryShortField<Model, Short> create(@NonNull Class<Model> modelType, @NonNull String name) {
        return new RealmPrimaryShortField<>(modelType, name, RealmShortFieldConverter.SHARED_INSTANCE);
    }

    @NonNull
    public static <Model extends RealmModel, Value> RealmPrimaryShortField<Model, Value> create(@NonNull Class<Model> modelType, @NonNull String name, RealmFieldConverter<Short, Value> converter) {
        return new RealmPrimaryShortField<>(modelType, name, converter);
    }

    public RealmPrimaryShortField(@NonNull Class<Model> modelType, @NonNull String name, @NonNull RealmFieldConverter<Short, Value> converter) {
        super(modelType, name, converter);
    }
}
