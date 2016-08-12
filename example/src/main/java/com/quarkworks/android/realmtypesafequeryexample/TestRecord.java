package com.quarkworks.android.realmtypesafequeryexample;


import com.quarkworks.android.realmtypesafequeryexample.annotations.GenerateRealmStringFields;

import java.util.Date;

import io.realm.RealmModel;
import io.realm.annotations.Ignore;
import io.realm.annotations.RealmClass;

@RealmClass
@GenerateRealmStringFields
public class TestRecord implements RealmModel {
    public String stringField;
    public Date dateField;

    @Ignore
    public Object ingnoredField;

    public String hostValue;
}
