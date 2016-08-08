package com.quarkworks.android.realmtypesafequery.primary;

import android.support.annotation.NonNull;

import com.quarkworks.android.realmtypesafequery.converter.RealmFieldConverter;
import com.quarkworks.android.realmtypesafequery.indexed.RealmIndexedStringField;

import io.realm.RealmModel;

public class RealmPrimaryStringField<Model extends RealmModel, Value> extends RealmIndexedStringField<Model, Value> {
    @NonNull
    public static <Model extends RealmModel> RealmPrimaryStringField<Model, String> create(@NonNull Class<Model> modelType, @NonNull String name) {
        return new RealmPrimaryStringField<>(modelType, name, RealmStringFieldConverter.SHARED_INSTANCE);
    }

    @NonNull
    public static <Model extends RealmModel, Value> RealmPrimaryStringField<Model, Value> create(@NonNull Class<Model> modelType, @NonNull String name, RealmFieldConverter<String, Value> converter) {
        return new RealmPrimaryStringField<>(modelType, name, converter);
    }

    public RealmPrimaryStringField(@NonNull Class<Model> modelType, @NonNull String name, @NonNull RealmFieldConverter<String, Value> converter) {
        super(modelType, name, converter);
    }
}
