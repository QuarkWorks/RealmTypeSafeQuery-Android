package com.quarkworks.android.realmtypesafequery.indexed;

import android.support.annotation.NonNull;

import com.quarkworks.android.realmtypesafequery.attributes.RealmByteField;
import com.quarkworks.android.realmtypesafequery.converter.RealmFieldConverter;

import io.realm.RealmModel;

public class RealmIndexedByteField<Model extends RealmModel, Value> extends RealmByteField<Model, Value> {
    @NonNull
    public static <Model extends RealmModel> RealmIndexedByteField<Model, Byte> create(@NonNull Class<Model> modelType, @NonNull String name) {
        return new RealmIndexedByteField<>(modelType, name, RealmByteFieldConverter.SHARED_INSTANCE);
    }

    @NonNull
    public static <Model extends RealmModel, Value> RealmIndexedByteField<Model, Value> create(@NonNull Class<Model> modelType, @NonNull String name, RealmFieldConverter<Byte, Value> converter) {
        return new RealmIndexedByteField<>(modelType, name, converter);
    }

    public RealmIndexedByteField(@NonNull Class<Model> modelType, @NonNull String name, @NonNull RealmFieldConverter<Byte, Value> converter) {
        super(modelType, name, converter);
    }
}

