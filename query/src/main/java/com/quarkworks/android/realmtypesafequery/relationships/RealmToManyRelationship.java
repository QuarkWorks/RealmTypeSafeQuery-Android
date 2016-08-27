package com.quarkworks.android.realmtypesafequery.relationships;

import android.support.annotation.NonNull;

import io.realm.RealmModel;

public class RealmToManyRelationship<Model extends RealmModel, RelationshipModel extends RealmModel> extends RealmRelationship<Model, RelationshipModel> {

    public RealmToManyRelationship(@NonNull  Class<Model> modelClass, @NonNull String name) {
        super(modelClass, name);
    }
}
