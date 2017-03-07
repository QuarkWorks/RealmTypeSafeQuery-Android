package com.quarkworks.android.realmtypesafequery.fields.indexed;

import android.support.annotation.NonNull;

import com.quarkworks.android.realmtypesafequery.fields.RealmBooleanField;
import com.quarkworks.android.realmtypesafequery.interfaces.RealmIndexedField;

import io.realm.RealmModel;

public class RealmIndexedBooleanField<Model extends RealmModel> extends RealmBooleanField<Model>
        implements RealmIndexedField<Model, Boolean> {

    public RealmIndexedBooleanField(@NonNull Class<Model> modelClass, @NonNull String keyPath) {
        super(modelClass, keyPath);
    }
}
