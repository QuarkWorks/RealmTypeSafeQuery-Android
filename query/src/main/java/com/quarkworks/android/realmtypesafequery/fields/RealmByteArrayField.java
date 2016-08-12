package com.quarkworks.android.realmtypesafequery.fields;

import android.support.annotation.NonNull;

import com.quarkworks.android.realmtypesafequery.RealmField;
import com.quarkworks.android.realmtypesafequery.RealmFieldType;
import com.quarkworks.android.realmtypesafequery.converter.RealmFieldConverter;

import io.realm.RealmModel;

public class RealmByteArrayField<Model extends RealmModel, Value> extends RealmField<Model, byte[], Value> {

    @NonNull
    public static <Model extends RealmModel> RealmByteArrayField<Model, byte[]> create(@NonNull Class<Model> modelType, @NonNull String name) {
        return new RealmByteArrayField<>(modelType, name, RealmByteArrayFieldConverter.SHARED_INSTANCE);
    }

    @NonNull
    public static <Model extends RealmModel, Value> RealmByteArrayField<Model, Value> create(@NonNull Class<Model> modelType, @NonNull String name, RealmFieldConverter<byte[], Value> converter) {
        return new RealmByteArrayField<>(modelType, name, converter);
    }

    public RealmByteArrayField(@NonNull Class<Model> modelType, @NonNull String name, @NonNull RealmFieldConverter<byte[], Value> attributeConverter) {
        super(modelType, name, attributeConverter, RealmFieldType.BYTE_ARRAY);
    }

    public static class RealmByteArrayFieldConverter implements RealmFieldConverter<byte[], byte[]> {

        public static final RealmByteArrayFieldConverter SHARED_INSTANCE = new RealmByteArrayFieldConverter();

        @Override
        public byte[] convertToValue(byte[] realmValue) {
            return realmValue;
        }

        @Override
        public byte[] convertToRealmValue(byte[] value) {
            return value;
        }
    }
}
