package com.quarkworks.android.realmtypesafequery.attributes;

import android.support.annotation.NonNull;

import com.quarkworks.android.realmtypesafequery.RealmField;
import com.quarkworks.android.realmtypesafequery.RealmFieldType;
import com.quarkworks.android.realmtypesafequery.converter.RealmFieldConverter;

import io.realm.RealmModel;

public class RealmLongField<Model extends RealmModel, Value> extends RealmField<Model, Long, Value> {

    @NonNull
    public static <Model extends RealmModel> RealmLongField<Model, Long> create(@NonNull Class<Model> modelType, @NonNull String name) {
        return new RealmLongField<>(modelType, name, RealmLongFieldConverter.SHARED_INSTANCE);
    }

    @NonNull
    public static <Model extends RealmModel, Value> RealmLongField<Model, Value> create(@NonNull Class<Model> modelType, @NonNull String name, RealmFieldConverter<Long, Value> converter) {
        return new RealmLongField<>(modelType, name, converter);
    }

    public RealmLongField(@NonNull Class<Model> modelType, @NonNull String name, @NonNull RealmFieldConverter<Long, Value> attributeConverter) {
        super(modelType, name, attributeConverter, RealmFieldType.LONG);
    }

    public static class RealmLongFieldConverter implements RealmFieldConverter<Long, Long> {

        public static final RealmLongFieldConverter SHARED_INSTANCE = new RealmLongFieldConverter();

        @Override
        public Long convertToValue(Long attribute) {
            return attribute;
        }

        @Override
        public Long convertToRealmValue(Long value) {
            return value;
        }

    }
}