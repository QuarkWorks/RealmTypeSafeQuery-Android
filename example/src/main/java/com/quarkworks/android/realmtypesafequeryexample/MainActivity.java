package com.quarkworks.android.realmtypesafequeryexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.quarkworks.android.realmtypesafequery.RealmTypeSafeQuery;
import com.quarkworks.android.realmtypesafequery.generated.TestRecordFieldNames;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.Sort;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Object f1 = TestRecordFieldNames.DATE_FIELD;
        Object f2 = TestRecordFieldNames.STRING_FIELD;

        RealmConfiguration config = new RealmConfiguration.Builder(getApplicationContext()).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(config);

        Realm realm = Realm.getDefaultInstance();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.deleteAll();
                realm.createObject(TestRecord.class).stringField = "1";
                realm.createObject(TestRecord.class).stringField = "11";
                realm.createObject(TestRecord.class).stringField = "2";
                realm.createObject(TestRecord.class);
                realm.createObject(TestRecord.class);
            }
        });


        RealmTypeSafeQuery.where(realm, TestRecord.class).equalTo(TestRecordFields.STRING_FIELD, "11").findFirst();
        Log.d(TAG, "Find: " + RealmTypeSafeQuery.findFirst(realm, TestRecordFields.STRING_FIELD, "1"));

        realm.close();
    }
}
