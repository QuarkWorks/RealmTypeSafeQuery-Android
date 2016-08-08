package com.quarkworks.android.realmtypesafequery.indexed;

import android.support.annotation.NonNull;

import com.quarkworks.android.realmtypesafequery.attributes.RealmLongField;
import com.quarkworks.android.realmtypesafequery.converter.RealmFieldConverter;

import io.realm.RealmModel;

public class RealmIndexedLongField<Model extends RealmModel, Value> extends RealmLongField<Model, Value> {
    @NonNull
    public static <Model extends RealmModel> RealmIndexedLongField<Model, Long> create(@NonNull Class<Model> modelType, @NonNull String name) {
        return new RealmIndexedLongField<>(modelType, name, RealmLongFieldConverter.SHARED_INSTANCE);
    }

    @NonNull
    public static <Model extends RealmModel, Value> RealmIndexedLongField<Model, Value> create(@NonNull Class<Model> modelType, @NonNull String name, RealmFieldConverter<Long, Value> converter) {
        return new RealmIndexedLongField<>(modelType, name, converter);
    }

    public RealmIndexedLongField(@NonNull Class<Model> modelType, @NonNull String name, @NonNull RealmFieldConverter<Long, Value> converter) {
        super(modelType, name, converter);
    }
}

