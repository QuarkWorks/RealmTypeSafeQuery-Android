package com.quarkworks.android.realmtypesafequeryexample;


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

@RealmClass
@GenerateRealmFieldNames
@GenerateRealmFields
public class TestRecord implements RealmModel {

    @Nullable
    public Boolean booleanField;

    @Nullable
    public byte[] byteArrayField;

    @Nullable
    public Byte byteField;

    @Nullable
    public Date dateField;

    @Nullable
    public Double doubleField;

    @Nullable
    public Float floatField;

    @Nullable
    public Integer integerField;

    @Nullable
    public Long longField;

    @Nullable
    public Short shortField;

    @Nullable
    public String stringField;

    @Ignore @Nullable
    public Object ingnoredField;

    @Index
    public String indexedField;

    @PrimaryKey
    public String primaryKey;

    @Nullable
    public TestRecord parent = null;

    @NonNull
    public RealmList<TestRecord> children = new RealmList<>();
}
