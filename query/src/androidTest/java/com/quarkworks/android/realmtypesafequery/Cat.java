package com.quarkworks.android.realmtypesafequery;
// from intro examples realm-java

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.quarkworks.android.realmtypesafequery.annotations.GenerateRealmFieldNames;
import com.quarkworks.android.realmtypesafequery.annotations.GenerateRealmFields;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.Ignore;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

@RealmClass
@GenerateRealmFieldNames
@GenerateRealmFields
public class Cat implements RealmModel {
    public  String name;
}
