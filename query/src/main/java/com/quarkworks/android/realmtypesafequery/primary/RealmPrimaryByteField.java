package com.quarkworks.android.realmtypesafequery.primary;

import android.support.annotation.NonNull;

import com.quarkworks.android.realmtypesafequery.converter.RealmFieldConverter;
import com.quarkworks.android.realmtypesafequery.indexed.RealmIndexedByteField;

import io.realm.RealmModel;

public class RealmPrimaryByteField<Model extends RealmModel, Value> extends RealmIndexedByteField<Model, Value> {
    @NonNull
    public static <Model extends RealmModel> RealmPrimaryByteField<Model, Byte> create(@NonNull Class<Model> modelType, @NonNull String name) {
        return new RealmPrimaryByteField<>(modelType, name, RealmByteFieldConverter.SHARED_INSTANCE);
    }

    @NonNull
    public static <Model extends RealmModel, Value> RealmPrimaryByteField<Model, Value> create(@NonNull Class<Model> modelType, @NonNull String name, RealmFieldConverter<Byte, Value> converter) {
        return new RealmPrimaryByteField<>(modelType, name, converter);
    }

    public RealmPrimaryByteField(@NonNull Class<Model> modelType, @NonNull String name, @NonNull RealmFieldConverter<Byte, Value> converter) {
        super(modelType, name, converter);
    }
}
