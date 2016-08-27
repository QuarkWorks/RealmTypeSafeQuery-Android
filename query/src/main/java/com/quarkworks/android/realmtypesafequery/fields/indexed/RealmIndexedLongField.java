package com.quarkworks.android.realmtypesafequery.fields.indexed;

import android.support.annotation.NonNull;

import com.quarkworks.android.realmtypesafequery.interfaces.RealmIndexedField;
import com.quarkworks.android.realmtypesafequery.fields.RealmLongField;

import io.realm.RealmModel;

public class RealmIndexedLongField<Model extends RealmModel> extends RealmLongField<Model>
        implements RealmIndexedField<Model, Long> {

    public RealmIndexedLongField(@NonNull Class<Model> modelClass, @NonNull String keyPath) {
        super(modelClass, keyPath);
    }
}

