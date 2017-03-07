package com.quarkworks.android.realmtypesafequery;


import android.annotation.SuppressLint;
import android.support.test.runner.AndroidJUnit4;

import com.quarkworks.android.realmtypesafequery.generated.BaseTestRecordFields;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BaseTest {

    @SuppressLint("StaticFieldLeak")
    private static Realm defaultInstance;

    @BeforeClass
    public static void setUpClass() {
        Realm.init(getTargetContext());
        RealmConfiguration config = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(config);
        defaultInstance = Realm.getDefaultInstance();
        defaultInstance.beginTransaction();
        defaultInstance.deleteAll();
        for (int i = 0; i < 10; i++) {
            BaseTestRecord record = defaultInstance.createObject(BaseTestRecord.class, String.valueOf(i));
            record.booleanField = i % 2 == 0;
            record.byteArrayField = new byte[]{(byte) i};
            record.byteField = (byte) i;
            record.dateField = new Date(i * 1000);
            record.doubleField = i * 1000d;
            record.floatField = i * 2000f;
            record.integerField = i;
            record.longField = i * 10L;
            record.shortField = (short) i;
            record.stringField = i % 3 == 0 ? null : String.valueOf(i);
            record.ignoredField = new Object();
            record.indexedField = "indexed value: " + i;
            record.requiredField = String.valueOf(i);
        }
        defaultInstance.commitTransaction();

    }

    @AfterClass
    public static void tearDownClass() {
        //delete_Data();
        defaultInstance.close();
        defaultInstance = null;
    }

    public static void delete_Data()
    {
        defaultInstance.beginTransaction();
        defaultInstance.deleteAll();
        defaultInstance.commitTransaction();
    }


    @Test
    public void _01_test() {
        assertEquals(10, RealmTypeSafeQuery.where(defaultInstance, BaseTestRecord.class).count());
    }

    @Test
    public void _02_test() {
        BaseTestRecord t = RealmTypeSafeQuery.where(defaultInstance, BaseTestRecord.class).equalTo(BaseTestRecordFields.STRING_FIELD, "1").findFirst();
        assertNotEquals(null, t);
        assertEquals("1", t.stringField);
    }

    @Test
    public void _03_test() {

        BaseTestRecord t = RealmTypeSafeQuery.where(defaultInstance, BaseTestRecord.class).equalTo(BaseTestRecordFields.STRING_FIELD, null).findFirst();
        assertEquals(null, t.stringField);
    }

    @Test
    public void _04_test() {

        RealmResults<BaseTestRecord> r  = RealmTypeSafeQuery.where(BaseTestRecord.class).isNull(BaseTestRecordFields.STRING_FIELD).findAll();
        r.load();
        assertThat(r.size(), is(4));

    }

    @Test
    public void _05_test() {

        RealmResults<BaseTestRecord> r  = RealmTypeSafeQuery.where(BaseTestRecord.class).isNotNull(BaseTestRecordFields.STRING_FIELD).findAll();
        r.load();
        assertThat(r.size(), is(6));
    }

    private Object [] extract(BaseTestRecord in)
    {
        return new Object[] {
        in.booleanField ,
        in.byteArrayField ,
        in.byteField ,
        in.dateField ,
        in.doubleField,
        in.floatField,
        in.integerField,
        in.longField,
        in.shortField,
        in.stringField ,
        in.ignoredField,
        in.indexedField,
        in.requiredField ,
        };
    }
}
