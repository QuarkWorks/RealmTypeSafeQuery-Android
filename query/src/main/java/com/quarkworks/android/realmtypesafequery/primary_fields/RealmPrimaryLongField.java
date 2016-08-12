package com.quarkworks.android.realmtypesafequery.primary_fields;

import android.support.annotation.NonNull;

import com.quarkworks.android.realmtypesafequery.converter.RealmFieldConverter;
import com.quarkworks.android.realmtypesafequery.indexed_fields.RealmIndexedLongField;

import io.realm.RealmModel;

public class RealmPrimaryLongField<Model extends RealmModel, Value> extends RealmIndexedLongField<Model, Value> {
    @NonNull
    public static <Model extends RealmModel> RealmPrimaryLongField<Model, Long> create(@NonNull Class<Model> modelType, @NonNull String name) {
        return new RealmPrimaryLongField<>(modelType, name, RealmLongFieldConverter.SHARED_INSTANCE);
    }

    @NonNull
    public static <Model extends RealmModel, Value> RealmPrimaryLongField<Model, Value> create(@NonNull Class<Model> modelType, @NonNull String name, RealmFieldConverter<Long, Value> converter) {
        return new RealmPrimaryLongField<>(modelType, name, converter);
    }

    public RealmPrimaryLongField(@NonNull Class<Model> modelType, @NonNull String name, @NonNull RealmFieldConverter<Long, Value> converter) {
        super(modelType, name, converter);
    }
}
