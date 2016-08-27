package com.quarkworks.android.realmtypesafequery.interfaces;

import io.realm.RealmModel;

public interface RealmPrimaryField<Model extends RealmModel, Value> extends RealmIndexedField<Model, Value> {}
