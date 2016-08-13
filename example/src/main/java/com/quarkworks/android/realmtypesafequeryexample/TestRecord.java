package com.quarkworks.android.realmtypesafequeryexample;


import com.quarkworks.android.realmtypesafequery.annotations.GenerateRealmFieldNames;

import java.util.Date;

import io.realm.RealmModel;
import io.realm.annotations.Ignore;
import io.realm.annotations.RealmClass;

@RealmClass
@GenerateRealmFieldNames
public class TestRecord implements RealmModel {
    public String stringField;
    public Date dateField;

    @Ignore
    public Object ingnoredField;

    public String hostValue;
}
