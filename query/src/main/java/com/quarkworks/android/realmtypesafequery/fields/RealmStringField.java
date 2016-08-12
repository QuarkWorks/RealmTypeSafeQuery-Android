package com.quarkworks.android.realmtypesafequery.fields;

import android.support.annotation.NonNull;

import com.quarkworks.android.realmtypesafequery.RealmField;
import com.quarkworks.android.realmtypesafequery.RealmFieldType;
import com.quarkworks.android.realmtypesafequery.converter.RealmFieldConverter;

import io.realm.RealmModel;

public class RealmStringField<Model extends RealmModel, Value> extends RealmField<Model, String, Value> {

    @NonNull
    public static <Model extends RealmModel> RealmStringField<Model, String> create(@NonNull Class<Model> modelType, @NonNull String name) {
        return new RealmStringField<>(modelType, name, RealmStringFieldConverter.SHARED_INSTANCE);
    }

    @NonNull
    public static <Model extends RealmModel, Value> RealmStringField<Model, Value> create(@NonNull Class<Model> modelType, @NonNull String name, RealmFieldConverter<String, Value> converter) {
        return new RealmStringField<>(modelType, name, converter);
    }

    public RealmStringField(@NonNull Class<Model> modelType, @NonNull String name, @NonNull RealmFieldConverter<String, Value> attributeConverter) {
        super(modelType, name, attributeConverter, RealmFieldType.STRING);
    }

    public static class RealmStringFieldConverter implements RealmFieldConverter<String, String> {

        public static final RealmStringFieldConverter SHARED_INSTANCE = new RealmStringFieldConverter();

        @Override
        public String convertToValue(String attribute) {
            return attribute;
        }

        @Override
        public String convertToRealmValue(String value) {
            return value;
        }
    }
}
