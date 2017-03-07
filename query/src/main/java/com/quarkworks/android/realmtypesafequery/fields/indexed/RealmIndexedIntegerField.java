package com.quarkworks.android.realmtypesafequery.fields.indexed;

import android.support.annotation.NonNull;

import com.quarkworks.android.realmtypesafequery.fields.RealmIntegerField;
import com.quarkworks.android.realmtypesafequery.interfaces.RealmIndexedField;

import io.realm.RealmModel;

public class RealmIndexedIntegerField<Model extends RealmModel> extends RealmIntegerField<Model>
        implements RealmIndexedField<Model, Integer> {

    public RealmIndexedIntegerField(@NonNull Class<Model> modelClass, @NonNull String keyPath) {
        super(modelClass, keyPath);
    }
}

