package com.quarkworks.android.realmtypesafequery.indexed_fields;

import android.support.annotation.NonNull;

import com.quarkworks.android.realmtypesafequery.fields.RealmByteArrayField;
import com.quarkworks.android.realmtypesafequery.converter.RealmFieldConverter;

import io.realm.RealmModel;

public class RealmIndexedByteArrayField<Model extends RealmModel, Value> extends RealmByteArrayField<Model, Value> {
    @NonNull
    public static <Model extends RealmModel> RealmIndexedByteArrayField<Model, byte[]> create(@NonNull Class<Model> modelType, @NonNull String name) {
        return new RealmIndexedByteArrayField<>(modelType, name, RealmByteArrayFieldConverter.SHARED_INSTANCE);
    }

    @NonNull
    public static <Model extends RealmModel, Value> RealmIndexedByteArrayField<Model, Value> create(@NonNull Class<Model> modelType, @NonNull String name, RealmFieldConverter<byte[], Value> converter) {
        return new RealmIndexedByteArrayField<>(modelType, name, converter);
    }

    public RealmIndexedByteArrayField(@NonNull Class<Model> modelType, @NonNull String name, @NonNull RealmFieldConverter<byte[], Value> converter) {
        super(modelType, name, converter);
    }
}

