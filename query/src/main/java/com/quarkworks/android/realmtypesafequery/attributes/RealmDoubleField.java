package com.quarkworks.android.realmtypesafequery.attributes;

import android.support.annotation.NonNull;

import com.quarkworks.android.realmtypesafequery.RealmField;
import com.quarkworks.android.realmtypesafequery.RealmFieldType;
import com.quarkworks.android.realmtypesafequery.converter.RealmFieldConverter;

import io.realm.RealmModel;

public class RealmDoubleField<Model extends RealmModel, Value> extends RealmField<Model, Double, Value> {

    @NonNull
    public static <Model extends RealmModel> RealmDoubleField<Model, Double> create(@NonNull Class<Model> modelType, @NonNull String name) {
        return new RealmDoubleField<>(modelType, name, RealmDoubleFieldConverter.SHARED_INSTANCE);
    }

    @NonNull
    public static <Model extends RealmModel, Value> RealmDoubleField<Model, Value> create(@NonNull Class<Model> modelType, @NonNull String name, RealmFieldConverter<Double, Value> converter) {
        return new RealmDoubleField<>(modelType, name, converter);
    }

    public RealmDoubleField(@NonNull Class<Model> modelType, @NonNull String name, @NonNull RealmFieldConverter<Double, Value> attributeConverter) {
        super(modelType, name, attributeConverter, RealmFieldType.DOUBLE);
    }

    public static class RealmDoubleFieldConverter implements RealmFieldConverter<Double, Double> {

        public static final RealmDoubleFieldConverter SHARED_INSTANCE = new RealmDoubleFieldConverter();

        @Override
        public Double convertToValue(Double attribute) {
            return attribute;
        }

        @Override
        public Double convertToRealmValue(Double value) {
            return value;
        }
    }
}
