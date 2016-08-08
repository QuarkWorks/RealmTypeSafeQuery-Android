package com.quarkworks.android.realmtypesafequery.attributes;

import android.support.annotation.NonNull;

import com.quarkworks.android.realmtypesafequery.RealmField;
import com.quarkworks.android.realmtypesafequery.RealmFieldType;
import com.quarkworks.android.realmtypesafequery.converter.RealmFieldConverter;

import java.util.Date;

import io.realm.RealmModel;

public class RealmDateField<Model extends RealmModel, Value> extends RealmField<Model, Date, Value> {

    @NonNull
    public static <Model extends RealmModel> RealmDateField<Model, Date> create(@NonNull Class<Model> modelType, @NonNull String name) {
        return new RealmDateField<>(modelType, name, RealmDateFieldConverter.SHARED_INSTANCE);
    }

    @NonNull
    public static <Model extends RealmModel, Value> RealmDateField<Model, Value> create(@NonNull Class<Model> modelType, @NonNull String name, RealmFieldConverter<Date, Value> converter) {
        return new RealmDateField<>(modelType, name, converter);
    }

    public RealmDateField(@NonNull Class<Model> modelType, @NonNull String name, @NonNull RealmFieldConverter<Date, Value> attributeConverter) {
        super(modelType, name, attributeConverter, RealmFieldType.DATE);
    }

    public static class RealmDateFieldConverter implements RealmFieldConverter<Date, Date> {

        public static final RealmDateFieldConverter SHARED_INSTANCE = new RealmDateFieldConverter();

        @Override
        public Date convertToValue(Date attribute) {
            return attribute;
        }

        @Override
        public Date convertToRealmValue(Date value) {
            return value;
        }
    }
}