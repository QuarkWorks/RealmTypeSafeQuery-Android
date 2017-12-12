package com.quarkworks.android.tests;
// from intro examples realm-java

import android.annotation.SuppressLint;
import android.support.test.runner.AndroidJUnit4;

import com.quarkworks.android.realmtypesafequery.RealmTypeSafeQuery;
import com.quarkworks.android.realmtypesafequery.generated.CatFields;
import com.quarkworks.android.realmtypesafequery.generated.PersonFields;
import com.quarkworks.android.tests.models.Cat;
import com.quarkworks.android.tests.models.Person;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.realm.Realm;
import io.realm.RealmConfiguration;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class IntroExampleTest {

    @SuppressLint("StaticFieldLeak")
    private static Realm realm;

    private static final Realm.Transaction deleteAllTransaction = realm -> realm.deleteAll();

    @Rule
    public DeferredErrorCollector collector = new DeferredErrorCollector();
    public void checkSucceeds(Runnable[] commands) {
        for (Runnable r : commands) {
            collector.checkSucceeds(r);
        }
    }

    @BeforeClass
    public static void setUpClass() {
        Realm.init(getTargetContext());
        final RealmConfiguration config = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(config);
        realm = Realm.getDefaultInstance();
        realm.executeTransaction(deleteAllTransaction);

    }

    @AfterClass
    public static void tearDownClass() {
        realm.close();
        realm = null;
    }

    @After
    public void tearDown() {
        realm.executeTransaction(deleteAllTransaction);
    }

    @Test
    public void basicLinkQuery() {
        //Performing basic Link Query operation...
        // All writes must be wrapped in a transaction to facilitate safe multi threading
        realm.executeTransaction(realm -> {
            // Add two people and two cats
            Person person = realm.createObject(Person.class);
            person.setId(1);
            person.setName("Young Person");
            person.setAge(14);
            Cat cat = realm.createObject(Cat.class);
            cat.name = "Ralf";
            person.getCats().add(cat);
            person = realm.createObject(Person.class);
            person.setId(2);
            person.setName("Senior Person");
            person.setAge(99);
            cat = realm.createObject(Cat.class);
            cat.name = "Tiger";
            person.getCats().add(cat);
        });
        Runnable[] commands = {
                () -> assertEquals(
                        RealmTypeSafeQuery.with(realm).where(Cat.class).count(), 2
                ),
                () -> assertEquals(
                        RealmTypeSafeQuery.with(realm).where(Person.class)
                                .equalTo(PersonFields.CATS.link(CatFields.NAME), "Tiger")
                                .count(), 1
                )
        };
        checkSucceeds(commands);
    }

    @Test
    public void basicQuery() {
        //Performing basic Query operation...
        // All writes must be wrapped in a transaction to facilitate safe multi threading
        realm.executeTransaction(realm -> {
            // Add two people
            Person person = realm.createObject(Person.class);
            person.setId(1);
            person.setName("Young Person");
            person.setAge(14);
            person = realm.createObject(Person.class);
            person.setId(2);
            person.setName("Senior Person");
            person.setAge(99);
        });

        Runnable[] commands = {
                () -> assertEquals(RealmTypeSafeQuery.with(realm).where(Person.class).count(), 2),
                () -> assertEquals(RealmTypeSafeQuery.with(realm)
                        .where(Person.class).equalTo(PersonFields.AGE, 99).findAll().size(), 1)};

        checkSucceeds(commands);
    }

    @Test
    public void basicCRUD() {

        //Perform basic Create/Read/Update/Delete (CRUD) operations...

        // All writes must be wrapped in a transaction to facilitate safe multi threading
        realm.executeTransaction(realm -> {
            // Add a person
            Person person = realm.createObject(Person.class);
            person.setId(1);
            person.setName("Young Person");
            person.setAge(14);
        });
        // Find the first person (no query conditions) and read a field
        final Person person = RealmTypeSafeQuery.with(realm).where(Person.class).findFirst();
        assert person != null;
        Runnable[] commands = {
                () -> assertNotNull(person),
                () -> assertEquals(person.getName(), "Young Person"),
                () -> assertEquals(person.getAge(), 14)
        };
        checkSucceeds(commands);

        // Update person in a transaction
        realm.executeTransaction(realm -> {
            //noinspection ConstantConditions
            person.setName("Senior Person");
            person.setAge(99);
        });

        commands = new Runnable[]{
                () -> assertNotNull(person),
                () -> assertEquals(person.getName(), "Senior Person"),
                () -> assertEquals(person.getAge(), 99)
        };
        checkSucceeds(commands);
    }
}
