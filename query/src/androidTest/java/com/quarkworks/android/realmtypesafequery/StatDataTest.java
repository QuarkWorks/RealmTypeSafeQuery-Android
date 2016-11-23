package com.quarkworks.android.realmtypesafequery;

import android.annotation.SuppressLint;
import android.support.test.runner.AndroidJUnit4;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.ExternalResource;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ListIterator;

import io.realm.Realm;
import io.realm.RealmConfiguration;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static com.quarkworks.android.realmtypesafequery.StatData.AVG_DATA;
import static com.quarkworks.android.realmtypesafequery.StatData.DATA_;
import static com.quarkworks.android.realmtypesafequery.StatData.SUM_DATA;
import static com.quarkworks.android.realmtypesafequery.generated.StatDataFields.INTEGER_FIELD;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

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

    }

    @AfterClass
    public static void tearDownClass() {
        delete_Data();
        defaultInstance.close();
        defaultInstance = null;
    }




    public static void init_Data()
    {
        ArrayList<Object[]> data = new ArrayList<>(Arrays.asList(DATA_));
        ListIterator<Object[]> dli = data.listIterator();
        while(dli.hasNext())
        {
            if (dli.nextIndex() == AVG_DATA)
                break;
            Object[] vals = dli.next();
            StatData record = defaultInstance.createObject(StatData.class);
            init(vals, record);
        }
    }

    public static void delete_Data()
    {
        defaultInstance.deleteAll();
    }
    private static void init(Object[] vals, StatData record) {
        record.integerField = (int) vals[0];
        record.floatField = (float) vals[1];
    }
    @Test
    public void sum ()
    {
        assertThat((int) RealmTypeSafeQuery.where(defaultInstance, StatData.class)
                .sum(INTEGER_FIELD), is(DATA_[SUM_DATA][0]));

    }

}
