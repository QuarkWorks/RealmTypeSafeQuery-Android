package com.quarkworks.android.realmtypesafequery;
// from intro examples realm-java

import android.annotation.SuppressLint;
import android.support.test.runner.AndroidJUnit4;

import com.quarkworks.android.realmtypesafequery.generated.CatFields;
import com.quarkworks.android.realmtypesafequery.generated.PersonFieldNames;
import com.quarkworks.android.realmtypesafequery.generated.PersonFields;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.realm.Realm;
import io.realm.RealmConfiguration;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class IntroExampleTest {

    @SuppressLint("StaticFieldLeak")
    private static Realm defaultInstance;
    private final Realm.Transaction delete_all = new Realm.Transaction() {
        @Override
        public void execute(Realm realm) {
            realm.deleteAll();
        }
    };
    @Rule
    public MyErrCol collector = new MyErrCol();
    public void checkSucceeds(Runnable[] cmds) {
        for (Runnable r : cmds) collector.checkSucceeds(r);
    }

    @BeforeClass
    public static void setUpClass() {
        Realm.init(getTargetContext());
        RealmConfiguration config =
                new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(config);
        defaultInstance = Realm.getDefaultInstance();
        defaultInstance.beginTransaction();
        defaultInstance.deleteAll();
        defaultInstance.commitTransaction();

    }

    @AfterClass
    public static void tearDownClass() {
        defaultInstance.close();
        defaultInstance = null;
    }

    @Test
    public void basicLinkQuery() {
        //Performing basic Link Query operation...
        // All writes must be wrapped in a transaction to facilitate safe multi threading
        defaultInstance.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
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
            }
        });
        Runnable[] cmds = {
                new Runnable() {
                    public void run() {
                        assertEquals(
                                RealmTypeSafeQuery.where(Cat.class).count(), 2
                        );
                    }
                },
                new Runnable() {
                    public void run() {
                        assertEquals(
                                RealmTypeSafeQuery.where(Person.class)
                                        .equalTo(PersonFields.CATS.link(CatFields.NAME), "Tiger")
                                        .count(), 1
                        );
                    }
                }
        };
        checkSucceeds(cmds);
        defaultInstance.executeTransaction(delete_all);
    }

    @Test
    public void basicQuery() {
        //Performing basic Query operation...
        // All writes must be wrapped in a transaction to facilitate safe multi threading
        defaultInstance.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // Add two people
                Person person = realm.createObject(Person.class);
                person.setId(1);
                person.setName("Young Person");
                person.setAge(14);
                person = realm.createObject(Person.class);
                person.setId(2);
                person.setName("Senior Person");
                person.setAge(99);
            }
        });
        Runnable[] cmds = {
        new Runnable() {
            public void run() {
                assertEquals(RealmTypeSafeQuery.where(Person.class).count(), 2);
            }
        },
        new Runnable() {
            public void run() {
                assertEquals(RealmTypeSafeQuery.where(Person.class).equalTo(PersonFields.AGE, 99).findAll().size(), 1);
            }
        }};
        checkSucceeds(cmds);
        defaultInstance.executeTransaction(delete_all);
    }

    @Test
    public void basicCRUD() {

        //Perform basic Create/Read/Update/Delete (CRUD) operations...

        // All writes must be wrapped in a transaction to facilitate safe multi threading
        defaultInstance.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // Add a person
                Person person = realm.createObject(Person.class);
                person.setId(1);
                person.setName("Young Person");
                person.setAge(14);
            }
        });
        // Find the first person (no query conditions) and read a field
        final Person person =
                RealmTypeSafeQuery.where(defaultInstance, Person.class).findFirst();
        Runnable[] cmds = {
                new Runnable() {
                    public void run() {
                        assertNotNull(person);
                    }
                },
                new Runnable() {
                    public void run() {
                        //noinspection ConstantConditions
                        assertEquals(person.getName(), "Young Person");
                    }
                },
                new Runnable() {
                    public void run() {
                        //noinspection ConstantConditions
                        assertEquals(person.getAge(), 14);
                    }
                }
        };
        checkSucceeds(cmds);

        // Update person in a transaction
        defaultInstance.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                //noinspection ConstantConditions
                person.setName("Senior Person");
                person.setAge(99);
            }
        });

        cmds = new Runnable[]{
                new Runnable() {
                    public void run() {
                        assertNotNull(person);
                    }
                },
                new Runnable() {
                    public void run() {
                        //noinspection ConstantConditions
                        assertEquals(person.getName(), "Senior Person");
                    }
                },
                new Runnable() {
                    public void run() {
                        //noinspection ConstantConditions
                        assertEquals(person.getAge(), 99);
                    }
                }
        };
        checkSucceeds(cmds);
        defaultInstance.executeTransaction(delete_all);
    }




}
