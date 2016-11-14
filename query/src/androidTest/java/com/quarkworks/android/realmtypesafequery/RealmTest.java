package com.quarkworks.android.realmtypesafequery;

import android.annotation.SuppressLint;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.ExternalResource;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ListIterator;

import io.realm.Realm;
import io.realm.RealmConfiguration;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static com.quarkworks.android.realmtypesafequery.StatData.*;
import static com.quarkworks.android.realmtypesafequery.generated.StatDataFieldNames.*;
import static com.quarkworks.android.realmtypesafequery.generated.StatDataFields.*;
import static com.quarkworks.android.realmtypesafequery.generated.StatDataFields.INTEGER_FIELD;

@RunWith(Suite.class)
@Suite.SuiteClasses({StatDataTest.class, B.class, C.class})
public class RealmTest {
    @SuppressLint("StaticFieldLeak")
    static Realm defaultInstance;
    @ClassRule
    static ExternalResource realm_setup_teardown = new ExternalResource() {
        @Override
        protected void before() throws Throwable {
            Realm.init(getTargetContext());
            RealmConfiguration config =
                    new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
            Realm.setDefaultConfiguration(config);
            defaultInstance = Realm.getDefaultInstance();
            defaultInstance.beginTransaction();
            defaultInstance.deleteAll();

        }

        @Override
        protected void after() {
            defaultInstance.close();
            defaultInstance = null;
        }
    };

}

class StatDataTest {
    @BeforeClass
    public static void init_Data()
    {
        ArrayList<Object[]> data = new ArrayList<>(Arrays.asList(DATA_));
        ListIterator<Object[]> dli = data.listIterator();
        while(dli.hasNext())
        {
            if (dli.nextIndex() == AVG_DATA)
                break;
            Object[] vals = dli.next();
            StatData record = RealmTest.defaultInstance.createObject(StatData.class);
            init(vals, record);
        }
    }
    @AfterClass
    public static void delete_Data()
    {
        RealmTest.defaultInstance.deleteAll();
    }
    private static void init(Object[] vals, StatData record) {
        record.integerField = (int) vals[0];
        record.floatField = (float) vals[1];
    }
    @Test
    public void sum ()
    {
        RealmTypeSafeQuery.where(RealmTest.defaultInstance, StatData.class)
                .sum(INTEGER_FIELD);
    }




}
class B {

}
class C {

}
