package com.quarkworks.android.realmtypesafequery.fields;

import android.support.annotation.NonNull;

import com.quarkworks.android.realmtypesafequery.RealmField;
import com.quarkworks.android.realmtypesafequery.RealmFieldType;
import com.quarkworks.android.realmtypesafequery.converter.RealmFieldConverter;

import io.realm.RealmModel;

public class RealmFloatField<Model extends RealmModel, Value> extends RealmField<Model, Float, Value> {

    @NonNull
    public static <Model extends RealmModel> RealmFloatField<Model, Float> create(@NonNull Class<Model> modelType, @NonNull String name) {
        return new RealmFloatField<>(modelType, name, RealmFloatFieldConverter.SHARED_INSTANCE);
    }

    @NonNull
    public static <Model extends RealmModel, Value> RealmFloatField<Model, Value> create(@NonNull Class<Model> modelType, @NonNull String name, RealmFieldConverter<Float, Value> converter) {
        return new RealmFloatField<>(modelType, name, converter);
    }

    public RealmFloatField(@NonNull Class<Model> modelType, @NonNull String name, @NonNull RealmFieldConverter<Float, Value> attributeConverter) {
        super(modelType, name, attributeConverter, RealmFieldType.FLOAT);
    }

    public static class RealmFloatFieldConverter implements RealmFieldConverter<Float, Float> {

        public static final RealmFloatFieldConverter SHARED_INSTANCE = new RealmFloatFieldConverter();

        @Override
        public Float convertToValue(Float attribute) {
            return attribute;
        }

        @Override
        public Float convertToRealmValue(Float value) {
            return value;
        }
    }
}
