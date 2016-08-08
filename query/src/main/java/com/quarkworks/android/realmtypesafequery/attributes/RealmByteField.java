package com.quarkworks.android.realmtypesafequery.attributes;


import android.support.annotation.NonNull;

import com.quarkworks.android.realmtypesafequery.RealmField;
import com.quarkworks.android.realmtypesafequery.RealmFieldType;
import com.quarkworks.android.realmtypesafequery.converter.RealmFieldConverter;

import io.realm.RealmModel;

public class RealmByteField<Model extends RealmModel, Value> extends RealmField<Model, Byte, Value> {

    @NonNull
    public static <Model extends RealmModel> RealmByteField<Model, Byte> create(@NonNull Class<Model> modelType, @NonNull String name) {
        return new RealmByteField<>(modelType, name, RealmByteFieldConverter.SHARED_INSTANCE);
    }

    @NonNull
    public static <Model extends RealmModel, Value> RealmByteField<Model, Value> create(@NonNull Class<Model> modelType, @NonNull String name, RealmFieldConverter<Byte, Value> converter) {
        return new RealmByteField<>(modelType, name, converter);
    }

    public RealmByteField(@NonNull Class<Model> modelType, @NonNull String name, @NonNull RealmFieldConverter<Byte, Value> attributeConverter) {
        super(modelType, name, attributeConverter, RealmFieldType.BYTE);
    }

    public static class RealmByteFieldConverter implements RealmFieldConverter<Byte, Byte> {

        public static final RealmByteFieldConverter SHARED_INSTANCE = new RealmByteFieldConverter();

        @Override
        public Byte convertToValue(Byte attribute) {
            return attribute;
        }

        @Override
        public Byte convertToRealmValue(Byte value) {
            return value;
        }
    }
}
