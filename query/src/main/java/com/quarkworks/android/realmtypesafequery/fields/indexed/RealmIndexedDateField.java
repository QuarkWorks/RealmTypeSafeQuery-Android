package com.quarkworks.android.realmtypesafequery.fields.indexed;

import android.support.annotation.NonNull;

import com.quarkworks.android.realmtypesafequery.interfaces.RealmIndexedField;
import com.quarkworks.android.realmtypesafequery.fields.RealmDateField;

import java.util.Date;

import io.realm.RealmModel;

public class RealmIndexedDateField<Model extends RealmModel> extends RealmDateField<Model>
        implements RealmIndexedField<Model, Date> {

    public RealmIndexedDateField(@NonNull Class<Model> modelClass, @NonNull String keyPath) {
        super(modelClass, keyPath);
    }
}

