package com.quarkworks.android.realmtypesafequery;

import android.support.annotation.NonNull;

import io.realm.Realm;
import io.realm.RealmCollection;
import io.realm.RealmModel;
import io.realm.RealmQuery;

public class RealmTypeSafeQuery<Model extends RealmModel> {
    @NonNull
    final RealmQuery<Model> realmQuery;

    public RealmTypeSafeQuery(@NonNull RealmQuery<Model> realmQuery) {
        this.realmQuery = realmQuery;
    }

    public RealmTypeSafeQuery(Class<Model> clazz, @NonNull Realm realm) {
        this.realmQuery = realm.where(clazz);
    }

    public RealmTypeSafeQuery(RealmCollection<Model> realmCollection) {
        this.realmQuery = realmCollection.where();
    }

    @NonNull
    public RealmQuery<Model> getRealmQuery() {
        return realmQuery;
    }
}
