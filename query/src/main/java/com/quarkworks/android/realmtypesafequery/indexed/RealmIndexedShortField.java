package com.quarkworks.android.realmtypesafequery.indexed;

import android.support.annotation.NonNull;

import com.quarkworks.android.realmtypesafequery.attributes.RealmShortField;
import com.quarkworks.android.realmtypesafequery.converter.RealmFieldConverter;

import io.realm.RealmModel;

public class RealmIndexedShortField<Model extends RealmModel, Value> extends RealmShortField<Model, Value> {
    @NonNull
    public static <Model extends RealmModel> RealmIndexedShortField<Model, Short> create(@NonNull Class<Model> modelType, @NonNull String name) {
        return new RealmIndexedShortField<>(modelType, name, RealmShortField.RealmShortFieldConverter.SHARED_INSTANCE);
    }

    @NonNull
    public static <Model extends RealmModel, Value> RealmIndexedShortField<Model, Value> create(@NonNull Class<Model> modelType, @NonNull String name, RealmFieldConverter<Short, Value> converter) {
        return new RealmIndexedShortField<>(modelType, name, converter);
    }

    public RealmIndexedShortField(@NonNull Class<Model> modelType, @NonNull String name, @NonNull RealmFieldConverter<Short, Value> converter) {
        super(modelType, name, converter);
    }
}

