package com.quarkworks.android.realmtypesafequery.indexed_fields;

import android.support.annotation.NonNull;

import com.quarkworks.android.realmtypesafequery.fields.RealmStringField;
import com.quarkworks.android.realmtypesafequery.converter.RealmFieldConverter;

import io.realm.RealmModel;

public class RealmIndexedStringField<Model extends RealmModel, Value> extends RealmStringField<Model, Value> {
    @NonNull
    public static <Model extends RealmModel> RealmIndexedStringField<Model, String> create(@NonNull Class<Model> modelType, @NonNull String name) {
        return new RealmIndexedStringField<>(modelType, name, RealmStringFieldConverter.SHARED_INSTANCE);
    }

    @NonNull
    public static <Model extends RealmModel, Value> RealmIndexedStringField<Model, Value> create(@NonNull Class<Model> modelType, @NonNull String name, RealmFieldConverter<String, Value> converter) {
        return new RealmIndexedStringField<>(modelType, name, converter);
    }

    public RealmIndexedStringField(@NonNull Class<Model> modelType, @NonNull String name, @NonNull RealmFieldConverter<String, Value> converter) {
        super(modelType, name, converter);
    }
}

