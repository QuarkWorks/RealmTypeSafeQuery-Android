package com.quarkworks.android.tests.models;
// from intro examples realm-java

import com.quarkworks.android.realmtypesafequery.annotations.GenerateRealmFieldNames;
import com.quarkworks.android.realmtypesafequery.annotations.GenerateRealmFields;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

@RealmClass
@GenerateRealmFieldNames
@GenerateRealmFields
public class Cat implements RealmModel {
    public String name;
}
