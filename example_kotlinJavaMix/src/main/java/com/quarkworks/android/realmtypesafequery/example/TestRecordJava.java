package com.quarkworks.android.realmtypesafequery.example;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.quarkworks.android.realmtypesafequery.constants.RealmDefaults;
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
public class TestRecordJava implements RealmModel {

    @Nullable
    public Boolean booleanField;

    boolean boolValue;

    @Nullable
    public byte[] byteArrayField;

    @Nullable
    public Byte byteField;

    public byte byteValue;

    @Nullable
    public Date dateField;

    @Nullable
    public Double doubleField;

    double doubleValue;

    @Nullable
    public Float floatField;

    float floatValue;

    @Nullable
    public Integer integerField;

    public int intValue;

    @Nullable
    public Long longField;

    public long longValue;

    @Nullable
    public Short shortField;

    public short shortValue;

    @Nullable
    public String stringField;

    @Ignore @Nullable
    public Object ignoredField;

    @Index
    public String indexedField;

    @PrimaryKey
    public String primaryKey;

    @Required @NonNull
    public String requiredField = RealmDefaults.STRING;

    @Nullable
    public TestRecordJava parent = null;

    @NonNull
    public RealmList<TestRecordJava> children = new RealmList<>();
}
