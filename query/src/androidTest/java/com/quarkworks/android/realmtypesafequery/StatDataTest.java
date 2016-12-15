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

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static com.quarkworks.android.realmtypesafequery.StatData.AVG_DATA_index;
import static com.quarkworks.android.realmtypesafequery.StatData.DATA_;
import static com.quarkworks.android.realmtypesafequery.StatData.MAX_DATA_index;
import static com.quarkworks.android.realmtypesafequery.StatData.MIN_DATA_index;
import static com.quarkworks.android.realmtypesafequery.StatData.SUM_DATA_index;
import static com.quarkworks.android.realmtypesafequery.generated.StatDataFields.INTEGER_FIELD;
import static org.junit.Assert.assertEquals;

@SuppressWarnings("ConstantConditions")
@RunWith(AndroidJUnit4.class)
public class StatDataTest {
    @SuppressLint("StaticFieldLeak")
    private static Realm defaultInstance;

    @BeforeClass
    public static void setUpClass() {
        Realm.init(getTargetContext());
        RealmConfiguration config =
                new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(config);
        defaultInstance = Realm.getDefaultInstance();
        defaultInstance.beginTransaction();
        defaultInstance.deleteAll();
        init_Data();
        defaultInstance.commitTransaction();

    }

    @AfterClass
    public static void tearDownClass() {
        defaultInstance.close();
        defaultInstance = null;
    }


    public static void init_Data() {
        ArrayList<Object[]> data = new ArrayList<>(Arrays.asList(DATA_));
        ListIterator<Object[]> dli = data.listIterator();
        while (dli.hasNext()) {
            if (dli.nextIndex() == AVG_DATA_index)
                break;
            Object[] vals = dli.next();
            StatData record = defaultInstance.createObject(StatData.class);
            init(vals, record);
        }
    }


    private static void init(Object[] vals, StatData record) {
        record.integerField = (int) vals[0];
        record.floatField = (float) vals[1];
    }

    @Test
    public void sum() {
        int sum = (int) (RealmTypeSafeQuery.where(defaultInstance, StatData.class)
                .sum(INTEGER_FIELD));
        assertEquals(sum, DATA_[SUM_DATA_index][0]);

    }

    @Test
    public void avg() {
        int average = (int) (RealmTypeSafeQuery.where(defaultInstance, StatData.class)
                .average(INTEGER_FIELD));
        assertEquals(average, DATA_[AVG_DATA_index][0]);

    }

    @Test
    public void max() {
        int max = RealmTypeSafeQuery.where(defaultInstance, StatData.class)
                .max(INTEGER_FIELD).intValue();
        assertEquals(max, DATA_[MAX_DATA_index][0]);

    }

    @Test
    public void min() {
        int min = RealmTypeSafeQuery.where(defaultInstance, StatData.class)
                .min(INTEGER_FIELD).intValue();
        assertEquals(min, (DATA_[MIN_DATA_index][0]));

    }

}
