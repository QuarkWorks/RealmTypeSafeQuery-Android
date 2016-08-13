package com.quarkworks.android.realmtypesafequery.indexed;

import android.support.annotation.NonNull;

import com.quarkworks.android.realmtypesafequery.fields.RealmBooleanField;
import com.quarkworks.android.realmtypesafequery.converter.RealmFieldConverter;

import io.realm.RealmModel;

public class RealmIndexedBooleanField<Model extends RealmModel, Value> extends RealmBooleanField<Model, Value> {
    @NonNull
    public static <Model extends RealmModel> RealmIndexedBooleanField<Model, Boolean> create(@NonNull Class<Model> modelType, @NonNull String name) {
        return new RealmIndexedBooleanField<>(modelType, name, RealmBooleanFieldConverter.SHARED_INSTANCE);
    }

    @NonNull
    public static <Model extends RealmModel, Value> RealmIndexedBooleanField<Model, Value> create(@NonNull Class<Model> modelType, @NonNull String name, RealmFieldConverter<Boolean, Value> converter) {
        return new RealmIndexedBooleanField<>(modelType, name, converter);
    }

    public RealmIndexedBooleanField(@NonNull Class<Model> modelType, @NonNull String name, @NonNull RealmFieldConverter<Boolean, Value> converter) {
        super(modelType, name, converter);
    }
}
