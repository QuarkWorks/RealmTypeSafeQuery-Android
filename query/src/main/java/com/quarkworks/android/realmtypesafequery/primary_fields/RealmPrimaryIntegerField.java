package com.quarkworks.android.realmtypesafequery.primary_fields;

import android.support.annotation.NonNull;

import com.quarkworks.android.realmtypesafequery.converter.RealmFieldConverter;
import com.quarkworks.android.realmtypesafequery.indexed_fields.RealmIndexedIntegerField;

import io.realm.RealmModel;

public class RealmPrimaryIntegerField<Model extends RealmModel, Value> extends RealmIndexedIntegerField<Model, Value> {
    @NonNull
    public static <Model extends RealmModel> RealmPrimaryIntegerField<Model, Integer> create(@NonNull Class<Model> modelType, @NonNull String name) {
        return new RealmPrimaryIntegerField<>(modelType, name, RealmIntegerFieldConverter.SHARED_INSTANCE);
    }

    @NonNull
    public static <Model extends RealmModel, Value> RealmPrimaryIntegerField<Model, Value> create(@NonNull Class<Model> modelType, @NonNull String name, RealmFieldConverter<Integer, Value> converter) {
        return new RealmPrimaryIntegerField<>(modelType, name, converter);
    }

    public RealmPrimaryIntegerField(@NonNull Class<Model> modelType, @NonNull String name, @NonNull RealmFieldConverter<Integer, Value> converter) {
        super(modelType, name, converter);
    }
}
