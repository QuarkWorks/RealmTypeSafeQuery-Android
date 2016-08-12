package com.quarkworks.android.realmtypesafequery.indexed_fields;

import android.support.annotation.NonNull;

import com.quarkworks.android.realmtypesafequery.fields.RealmDateField;
import com.quarkworks.android.realmtypesafequery.converter.RealmFieldConverter;

import java.util.Date;

import io.realm.RealmModel;

public class RealmIndexedDateField<Model extends RealmModel, Value> extends RealmDateField<Model, Value> {
    @NonNull
    public static <Model extends RealmModel> RealmIndexedDateField<Model, Date> create(@NonNull Class<Model> modelType, @NonNull String name) {
        return new RealmIndexedDateField<>(modelType, name, RealmDateFieldConverter.SHARED_INSTANCE);
    }

    @NonNull
    public static <Model extends RealmModel, Value> RealmIndexedDateField<Model, Value> create(@NonNull Class<Model> modelType, @NonNull String name, RealmFieldConverter<Date, Value> converter) {
        return new RealmIndexedDateField<>(modelType, name, converter);
    }

    public RealmIndexedDateField(@NonNull Class<Model> modelType, @NonNull String name, @NonNull RealmFieldConverter<Date, Value> converter) {
        super(modelType, name, converter);
    }
}

