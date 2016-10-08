package com.quarkworks.android.realmtypesafequery;


import android.support.test.runner.AndroidJUnit4;

import com.quarkworks.android.realmtypesafequery.generated.TestRecordFields;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BaseTest {

    private static RealmConfiguration config;
    private Realm defaultInstance;

    @BeforeClass
    public static void setUpClass() {
        config = new RealmConfiguration.Builder(getTargetContext()).deleteRealmIfMigrationNeeded().build();
    }

    @AfterClass
    public static void tearDownClass() {
    }


    @Before
    public void setUp() {
        Realm.setDefaultConfiguration(config);
        defaultInstance = Realm.getDefaultInstance();
        defaultInstance.beginTransaction();
        defaultInstance.deleteAll();
        for (int i = 0; i < 10; i++) {
            TestRecord record = defaultInstance.createObject(TestRecord.class);
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
            record.primaryKey = String.valueOf(i);
            record.requiredField = String.valueOf(i);
        }
        defaultInstance.commitTransaction();

    }

    @After
    public void tearDown() {
        defaultInstance.close();
    }

    @Test
    public void _01_test() {
        assertEquals(10, RealmTypeSafeQuery.where(defaultInstance, TestRecord.class).count());
    }

    @Test
    public void _02_test() {
        TestRecord t = RealmTypeSafeQuery.where(defaultInstance, TestRecord.class).equalTo(TestRecordFields.STRING_FIELD, "1").findFirst();
        assertNotEquals(null, t);
        assertEquals("1", t.stringField);
    }

    @Test
    public void _03_test() {

        TestRecord t = RealmTypeSafeQuery.where(defaultInstance, TestRecord.class).equalTo(TestRecordFields.STRING_FIELD, null).findFirst();
        assertEquals(null, t.stringField);
    }

    @Test
    public void _04_test() {
        // don't understand this
        //RealmTypeSafeQuery.where(TestRecord.class).isNull(TestRecordFields.STRING_FIELD).findAll().toString();
    }

    @Test
    public void _05_test() {
        // don't understand this
        //RealmTypeSafeQuery.where(TestRecord.class).isNotNull(TestRecordFields.STRING_FIELD).findAll().toString();
    }


}
