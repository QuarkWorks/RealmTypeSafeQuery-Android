package com.quarkworks.android.realmtypesafequery;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.quarkworks.android.realmtypesafequery.converter.RealmFieldConvertable;
import com.quarkworks.android.realmtypesafequery.converter.RealmFieldConverter;

import io.realm.RealmModel;

public abstract class RealmField<Model extends RealmModel, RealmValue, Value> implements RealmFieldConvertable<RealmValue, Value> {

    @NonNull
    private final Class<Model> modelType;

    @NonNull
    private final String name;

    @NonNull
    private final RealmFieldConverter<RealmValue, Value> converter;

    @NonNull
    private final RealmFieldType fieldType;

    @Nullable
    private Boolean isNullable = null;

    public RealmField(@NonNull Class<Model> modelType, @NonNull String name, @NonNull RealmFieldConverter<RealmValue, Value> converter, @NonNull RealmFieldType fieldType) {
        this.modelType = modelType;
        this.name = name;
        this.converter = converter;
        this.fieldType = fieldType;
    }

    @NonNull
    public Class<Model> getModelType() {
        return this.modelType;
    }

    @NonNull
    public String getName() {
        return this.name;
    }

    @NonNull
    public RealmFieldConverter<RealmValue, Value> getConverter() {
        return converter;
    }

     @NonNull
     public RealmFieldType getFieldType() {
        return this.fieldType;
    }
}
