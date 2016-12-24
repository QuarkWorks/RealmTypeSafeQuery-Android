package com.quarkworks.android.realmtypesafequery;

import android.annotation.SuppressLint;
import android.support.test.runner.AndroidJUnit4;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ListIterator;

import io.realm.Realm;
import io.realm.RealmConfiguration;

import android.support.test.InstrumentationRegistry;
import com.quarkworks.android.realmtypesafequery.generated.StatDataFields;
import org.junit.Assert;

@SuppressWarnings("ConstantConditions")
@RunWith(AndroidJUnit4.class)
public class StatDataTest {
    @SuppressLint("StaticFieldLeak")
    private static Realm defaultInstance;

    @BeforeClass
    public static void setUpClass() {
        Realm.init(InstrumentationRegistry.getTargetContext());
        RealmConfiguration config =
                new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(config);
        defaultInstance = Realm.getDefaultInstance();
        defaultInstance.beginTransaction();
        defaultInstance.deleteAll();
        initRecords();
        defaultInstance.commitTransaction();

    }

    @AfterClass
    public static void tearDownClass() {
        defaultInstance.close();
        defaultInstance = null;
    }


    public static void initRecords() {
        ArrayList<Object[]> data = new ArrayList<>(Arrays.asList(StatData.DATA));
        ListIterator<Object[]> dli = data.listIterator();
        while (dli.hasNext()) {
            if (dli.nextIndex() == StatData.AVG_DATA_INDEX)
                break;
            Object[] vals = dli.next();
            StatData record = defaultInstance.createObject(StatData.class);
            initRecord(vals, record);
        }
    }


    private static void initRecord(Object[] vals, StatData record) {
        record.integerField = (int) vals[0];
        record.floatField = (float) vals[1];
    }

    @Test
    public void sumInt() {
        int sum = (int) (RealmTypeSafeQuery.where(defaultInstance, StatData.class)
                .sum(StatDataFields.INTEGER_FIELD));
        //Assert.assertEquals(sum, StatData.DATA[StatData.SUM_DATA_INDEX][0]);
        Assert.assertEquals(sum, StatData.SUM_DATA[0]);

    }

    @Test
    public void avgInt() {
        int average = (int) (RealmTypeSafeQuery.where(defaultInstance, StatData.class)
                .average(StatDataFields.INTEGER_FIELD));
        //Assert.assertEquals(average, StatData.DATA[StatData.AVG_DATA_INDEX][0]);
        Assert.assertEquals(average, StatData.AVG_DATA[0]);

    }

    @Test
    public void maxInt() {
        int max = RealmTypeSafeQuery.where(defaultInstance, StatData.class)
                .max(StatDataFields.INTEGER_FIELD).intValue();
        //Assert.assertEquals(max, StatData.DATA[StatData.MAX_DATA_INDEX][0]);
        Assert.assertEquals(max, StatData.MAX_DATA[0]);

    }

    @Test
    public void minInt() {
        int min = RealmTypeSafeQuery.where(defaultInstance, StatData.class)
                .min(StatDataFields.INTEGER_FIELD).intValue();
        //Assert.assertEquals(min, (StatData.DATA[StatData.MIN_DATA_INDEX][0]));
        Assert.assertEquals(min, StatData.MIN_DATA[0]);
    }

}
