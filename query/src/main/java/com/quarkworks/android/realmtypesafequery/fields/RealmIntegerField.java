package com.quarkworks.android.realmtypesafequery.fields;

import android.support.annotation.NonNull;

import com.quarkworks.android.realmtypesafequery.RealmField;
import com.quarkworks.android.realmtypesafequery.RealmFieldType;
import com.quarkworks.android.realmtypesafequery.converter.RealmFieldConverter;

import io.realm.RealmModel;

public class RealmIntegerField<Model extends RealmModel, Value> extends RealmField<Model, Integer, Value> {

    @NonNull
    public static <Model extends RealmModel> RealmIntegerField<Model, Integer> create(@NonNull Class<Model> modelType, @NonNull String name) {
        return new RealmIntegerField<>(modelType, name, RealmIntegerFieldConverter.SHARED_INSTANCE);
    }

    @NonNull
    public static <Model extends RealmModel, Value> RealmIntegerField<Model, Value> create(@NonNull Class<Model> modelType, @NonNull String name, RealmFieldConverter<Integer, Value> converter) {
        return new RealmIntegerField<>(modelType, name, converter);
    }

    public RealmIntegerField(@NonNull Class<Model> modelType, @NonNull String name, @NonNull RealmFieldConverter<Integer, Value> attributeConverter) {
        super(modelType, name, attributeConverter, RealmFieldType.INTEGER);
    }

    public static class RealmIntegerFieldConverter implements RealmFieldConverter<Integer, Integer> {

        public static final RealmIntegerFieldConverter SHARED_INSTANCE = new RealmIntegerFieldConverter();

        @Override
        public Integer convertToValue(Integer attribute) {
            return attribute;
        }

        @Override
        public Integer convertToRealmValue(Integer value) {
            return value;
        }
    }
}
