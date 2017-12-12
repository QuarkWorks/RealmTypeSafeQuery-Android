package com.quarkworks.android.tests;

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
import io.realm.Sort;

import android.support.test.InstrumentationRegistry;
import android.util.Log;

import com.quarkworks.android.realmtypesafequery.RealmTypeSafeQuery;
import com.quarkworks.android.realmtypesafequery.generated.StatDataFields;
import com.quarkworks.android.tests.models.StatData;

import org.junit.Assert;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("ConstantConditions")
@RunWith(AndroidJUnit4.class)
public class StatDataTest {
    @SuppressLint("StaticFieldLeak")
    private static Realm defaultInstance;

    @BeforeClass
    public static void setUpClass() {
        Realm.init(InstrumentationRegistry.getTargetContext());
        final RealmConfiguration config = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
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
        final ArrayList<Object[]> data = new ArrayList<>(Arrays.asList(StatData.DATA));
        final ListIterator<Object[]> iterator = data.listIterator();
        while (iterator.hasNext()) {
            if (iterator.nextIndex() == StatData.AVG_DATA_INDEX)
                break;
            final Object[] values = iterator.next();
            final StatData record = defaultInstance.createObject(StatData.class);
            initRecord(values, record);
        }
    }


    private static void initRecord(Object[] vals, StatData record) {
        record.integerField = (int) vals[0];
        record.floatField = (float) vals[1];
        record.stringField = (String) vals[2];
    }

    @Test
    public void sumInt() {
        final long sum = (RealmTypeSafeQuery.with(defaultInstance).where(StatData.class)
                .sum(StatDataFields.INTEGER_FIELD));
        assertEquals(StatData.SUM_DATA[0], sum);

    }

    @Test
    public void avgInt() {
        final int average = (int) (RealmTypeSafeQuery.with(defaultInstance).where(StatData.class)
                .average(StatDataFields.INTEGER_FIELD));
        assertEquals(StatData.AVG_DATA[0], average);

    }

    @Test
    public void maxInt() {
        final int max = RealmTypeSafeQuery.with(defaultInstance).where(StatData.class)
                .max(StatDataFields.INTEGER_FIELD);
        assertEquals(StatData.MAX_DATA[0], max);

    }

    @Test
    public void minInt() {
        final int min = RealmTypeSafeQuery.with(defaultInstance).where(StatData.class)
                .min(StatDataFields.INTEGER_FIELD);
        assertEquals(StatData.MIN_DATA[0], min);
    }
    @Test
    public void sortAscending() {
        final StatData min = RealmTypeSafeQuery.with(defaultInstance).where(StatData.class)
                .sort(StatDataFields.STRING_FIELD, Sort.ASCENDING).findFirst();
        Log.d("BUTTS", min.stringField);

        assertEquals(StatData.MIN_DATA[2], min.stringField);
    }
}
