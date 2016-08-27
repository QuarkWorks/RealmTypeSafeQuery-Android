package com.quarkworks.android.realmtypesafequery.fields.indexed;

import android.support.annotation.NonNull;

import com.quarkworks.android.realmtypesafequery.interfaces.RealmIndexedField;
import com.quarkworks.android.realmtypesafequery.fields.RealmShortField;

import io.realm.RealmModel;

public class RealmIndexedShortField<Model extends RealmModel> extends RealmShortField<Model>
        implements RealmIndexedField<Model, Short> {

    public RealmIndexedShortField(@NonNull Class<Model> modelClass, @NonNull String keyPath) {
        super(modelClass, keyPath);
    }
}

