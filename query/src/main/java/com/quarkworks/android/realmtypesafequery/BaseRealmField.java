package com.quarkworks.android.realmtypesafequery;

import android.support.annotation.NonNull;

import com.quarkworks.android.realmtypesafequery.interfaces.RealmIndexedField;
import com.quarkworks.android.realmtypesafequery.interfaces.RealmField;
import com.quarkworks.android.realmtypesafequery.interfaces.SortableRealmField;

import io.realm.RealmModel;
import io.realm.RealmQuery;

public abstract class BaseRealmField<Model extends RealmModel, Value> implements RealmField<Model, Value> {

    @NonNull
    private final String keyPath;

    @NonNull
    private final Class<Model> modelClass;

    private final boolean required = false;

    public BaseRealmField(@NonNull Class<Model> modelClass, @NonNull String keyPath) {
        this.modelClass = modelClass;
        this.keyPath = keyPath;
    }

    @Override
    @NonNull
    public Class<Model> getModelClass() {
        return this.modelClass;
    }

    @Override
    @NonNull
    public String getKeyPath() {
        return this.keyPath;
    }

    @Override
    public boolean isRequired() {
        return this.required;
    }

    @Override
    public boolean isOptional() {
        return !this.isRequired();
    }

    @Override
    public boolean isIndexed() {
        return this instanceof RealmIndexedField;
    }

    @Override
    public boolean isSortable() {
        return this instanceof SortableRealmField;
    }

    @Override
    public void isNull(@NonNull RealmQuery<Model> query) {
        try {
            query.isNull(this.getKeyPath());
        } catch (IllegalArgumentException ignored) {
            this.never(query);
        }
    }

    @Override
    public void isNotNull(@NonNull RealmQuery<Model> query) {
        try {
            query.isNotNull(this.getKeyPath());
        } catch (IllegalArgumentException ignored) {

        }
    }
}
