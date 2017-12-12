package com.quarkworks.android.tests;


import android.annotation.SuppressLint;
import android.support.test.runner.AndroidJUnit4;

import com.quarkworks.android.realmtypesafequery.RealmTypeSafeQuery;
import com.quarkworks.android.realmtypesafequery.generated.BaseTestRecordFields;
import com.quarkworks.android.tests.models.BaseTestRecord;

import org.junit.AfterClass;
import org.junit.Assert;
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
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BaseTest {

    @SuppressLint("StaticFieldLeak")
    private static Realm realm;

    @BeforeClass
    public static void setUpClass() {
        Realm.init(getTargetContext());
        RealmConfiguration config = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(config);
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.deleteAll();
        for (int i = 0; i < 10; i++) {
            BaseTestRecord record = realm.createObject(BaseTestRecord.class, String.valueOf(i));
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
        realm.commitTransaction();

    }

    @AfterClass
    public static void tearDownClass() {
        //delete_Data();
        realm.close();
        realm = null;
    }

    public static void delete_Data()
    {
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }


    @Test
    public void test01() {
        assertEquals(10, RealmTypeSafeQuery.where(BaseTestRecord.class, realm).count());
    }

    @Test
    public void test02() {

        final BaseTestRecord t = RealmTypeSafeQuery.with(realm).where(BaseTestRecord.class)
                .equalTo(BaseTestRecordFields.STRING_FIELD, "1").findFirst();
        Assert.assertNotEquals(null, t);
        assertEquals("1", t.stringField);
    }

    @Test
    public void test03() {

        final BaseTestRecord t = RealmTypeSafeQuery.with(realm).where(BaseTestRecord.class)
                .equalTo(BaseTestRecordFields.STRING_FIELD, null).findFirst();
        assertEquals(null, t.stringField);
    }

    @Test
    public void test04() {

        final RealmResults<BaseTestRecord> r  = RealmTypeSafeQuery.with(realm)
                .where(BaseTestRecord.class).isNull(BaseTestRecordFields.STRING_FIELD).findAll();
        assertEquals(4, r.size());

    }

    @Test
    public void test05() {

        final RealmResults<BaseTestRecord> r = RealmTypeSafeQuery.with(realm)
                .where(BaseTestRecord.class).isNotNull(BaseTestRecordFields.STRING_FIELD).findAll();
        assertEquals(6, r.size());
    }
}
