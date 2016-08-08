package com.quarkworks.android.realmtypesafequery.attributes;

import android.support.annotation.NonNull;

import com.quarkworks.android.realmtypesafequery.RealmField;
import com.quarkworks.android.realmtypesafequery.RealmFieldType;
import com.quarkworks.android.realmtypesafequery.converter.RealmFieldConverter;

import io.realm.RealmModel;

public class RealmBooleanField<Model extends RealmModel, Value> extends RealmField<Model, Boolean, Value> {

    @NonNull
    public static <Model extends RealmModel> RealmBooleanField<Model, Boolean> create(@NonNull Class<Model> modelType, @NonNull String name) {
        return new RealmBooleanField<>(modelType, name, RealmBooleanFieldConverter.SHARED_INSTANCE);
    }

    @NonNull
    public static <Model extends RealmModel, Value> RealmBooleanField<Model, Value> create(@NonNull Class<Model> modelType, @NonNull String name, RealmFieldConverter<Boolean, Value> converter) {
        return new RealmBooleanField<>(modelType, name, converter);
    }

    public RealmBooleanField(@NonNull Class<Model> modelType, @NonNull String name, @NonNull RealmFieldConverter<Boolean, Value> attributeConverter) {
        super(modelType, name, attributeConverter, RealmFieldType.BOOLEAN);
    }

    public static class RealmBooleanFieldConverter implements RealmFieldConverter<Boolean, Boolean> {

        public static final RealmBooleanFieldConverter SHARED_INSTANCE = new RealmBooleanFieldConverter();

        @Override
        public Boolean convertToValue(Boolean realmValue) {
            return realmValue != null;
        }

        @Override
        public Boolean convertToRealmValue(Boolean value) {
            return value;
        }

    }
}
