package com.quarkworks.android.realmtypesafequery.fields.indexed;

import android.support.annotation.NonNull;

import com.quarkworks.android.realmtypesafequery.interfaces.RealmIndexedField;
import com.quarkworks.android.realmtypesafequery.fields.RealmStringField;

import io.realm.RealmModel;

public class RealmIndexedStringField<Model extends RealmModel> extends RealmStringField<Model>
        implements RealmIndexedField<Model, String> {

    public RealmIndexedStringField(@NonNull Class<Model> modelClass, @NonNull String keyPath) {
        super(modelClass, keyPath);
    }
}

