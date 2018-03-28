package com.quarkworks.android.realmtypesafequery.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import com.quarkworks.android.realmtypesafequery.RealmTypeSafeQuery;
import com.quarkworks.android.realmtypesafequery.generated.TestRecordFieldNames;
import com.quarkworks.android.realmtypesafequery.generated.TestRecordFields;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Object f1 = TestRecordFieldNames.DATE_FIELD;
        Object f2 = TestRecordFieldNames.STRING_FIELD;

        Realm.init(this.getApplicationContext());

        RealmConfiguration config = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(config);

        try (Realm realm = Realm.getDefaultInstance()) {

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.deleteAll();
                    for (int i = 0; i < 10; i++) {
                        TestRecord record = realm.createObject(TestRecord.class,  String.valueOf(i));
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
                }
            });

            Log.d(TAG, "Count:" + RealmTypeSafeQuery.with(realm).where(TestRecord.class).count());

            Log.d(TAG, "Equal To 1: " + RealmTypeSafeQuery.with(realm).where(TestRecord.class).equalTo(TestRecordFields.STRING_FIELD, "1").findAll().toString());
            Log.d(TAG, "Equal To null: " + RealmTypeSafeQuery.with(realm).where(TestRecord.class).equalTo(TestRecordFields.STRING_FIELD, null).findAll().toString());
            Log.d(TAG, "IsNull: " + RealmTypeSafeQuery.with(realm).where(TestRecord.class).isNull(TestRecordFields.STRING_FIELD).findAll().toString());
            Log.d(TAG, "IsNotNull: " + RealmTypeSafeQuery.with(realm).where(TestRecord.class).isNotNull(TestRecordFields.STRING_FIELD).findAll().toString());
        }
    }
}
