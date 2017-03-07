package com.quarkworks.android.realmtypesafequery.fields.indexed;

import android.support.annotation.NonNull;

import com.quarkworks.android.realmtypesafequery.fields.RealmByteField;
import com.quarkworks.android.realmtypesafequery.interfaces.RealmIndexedField;

import io.realm.RealmModel;

public class RealmIndexedByteField<Model extends RealmModel> extends RealmByteField<Model>
        implements RealmIndexedField<Model, Byte> {

    public RealmIndexedByteField(@NonNull Class<Model> modelClass, @NonNull String keyPath) {
        super(modelClass, keyPath);
    }
}

