package com.quarkworks.android.realmtypesafequery.attributes;

import android.support.annotation.NonNull;

import com.quarkworks.android.realmtypesafequery.RealmField;
import com.quarkworks.android.realmtypesafequery.RealmFieldType;
import com.quarkworks.android.realmtypesafequery.converter.RealmFieldConverter;

import io.realm.RealmModel;

public class RealmShortField<Model extends RealmModel, Value> extends RealmField<Model, Short, Value> {

    @NonNull
    public static <Model extends RealmModel> RealmShortField<Model, Short> create(@NonNull Class<Model> modelType, @NonNull String name) {
        return new RealmShortField<>(modelType, name, RealmShortFieldConverter.SHARED_INSTANCE);
    }

    @NonNull
    public static <Model extends RealmModel, Value> RealmShortField<Model, Value> create(@NonNull Class<Model> modelType, @NonNull String name, RealmFieldConverter<Short, Value> converter) {
        return new RealmShortField<>(modelType, name, converter);
    }

    public RealmShortField(@NonNull Class<Model> modelType, @NonNull String name, @NonNull RealmFieldConverter<Short, Value> attributeConverter) {
        super(modelType, name, attributeConverter, RealmFieldType.SHORT);
    }

    public static class RealmShortFieldConverter implements RealmFieldConverter<Short, Short> {

        public static final RealmShortFieldConverter SHARED_INSTANCE = new RealmShortFieldConverter();

        @Override
        public Short convertToValue(Short attribute) {
            return attribute;
        }

        @Override
        public Short convertToRealmValue(Short value) {
            return value;
        }
    }
}
