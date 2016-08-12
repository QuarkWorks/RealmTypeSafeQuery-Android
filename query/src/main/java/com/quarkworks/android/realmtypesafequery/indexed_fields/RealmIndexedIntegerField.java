package com.quarkworks.android.realmtypesafequery.indexed_fields;

import android.support.annotation.NonNull;

import com.quarkworks.android.realmtypesafequery.fields.RealmIntegerField;
import com.quarkworks.android.realmtypesafequery.converter.RealmFieldConverter;

import io.realm.RealmModel;

public class RealmIndexedIntegerField<Model extends RealmModel, Value> extends RealmIntegerField<Model, Value> {
    @NonNull
    public static <Model extends RealmModel> RealmIndexedIntegerField<Model, Integer> create(@NonNull Class<Model> modelType, @NonNull String name) {
        return new RealmIndexedIntegerField<>(modelType, name, RealmIntegerFieldConverter.SHARED_INSTANCE);
    }

    @NonNull
    public static <Model extends RealmModel, Value> RealmIndexedIntegerField<Model, Value> create(@NonNull Class<Model> modelType, @NonNull String name, RealmFieldConverter<Integer, Value> converter) {
        return new RealmIndexedIntegerField<>(modelType, name, converter);
    }

    public RealmIndexedIntegerField(@NonNull Class<Model> modelType, @NonNull String name, @NonNull RealmFieldConverter<Integer, Value> converter) {
        super(modelType, name, converter);
    }
}

