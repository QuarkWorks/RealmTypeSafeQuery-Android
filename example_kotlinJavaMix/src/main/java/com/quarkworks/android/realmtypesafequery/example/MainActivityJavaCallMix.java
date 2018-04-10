package com.quarkworks.android.realmtypesafequery.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import com.quarkworks.android.realmtypesafequery.RealmTypeSafeQuery;
import com.quarkworks.android.realmtypesafequery.generated.TestRecordKtFieldNames;
import com.quarkworks.android.realmtypesafequery.generated.TestRecordKtFields;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivityJavaCallMix extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_mix);

        Object f1 = TestRecordKtFieldNames.DATE_FIELD;
        Object f2 = TestRecordKtFieldNames.STRING_FIELD;

        Realm.init(this.getApplicationContext());

        RealmConfiguration config = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(config);

        try (Realm realm = Realm.getDefaultInstance()) {

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.deleteAll();
                    for (int i = 0; i < 10; i++) {
                        TestRecordKt record = realm.createObject(TestRecordKt.class,  String.valueOf(i));
                        record.setBooleanField  ( i % 2 == 0                            );
                        record.setByteArrayField( new byte[]{(byte) i}                  );
                        record.setByteField     ( (byte) i                              );
                        record.setDateField     ( new Date(i * 1000)                    );
                        record.setDoubleField   ( i * 1000d                             );
                        record.setFloatField    ( i * 2000f                             );
                        record.setIntegerField  ( i                                     );
                        record.setLongField     ( i * 10L                               );
                        record.setShortField    ( (short) i                             );
                        record.setStringField   ( i % 3 == 0 ? null : String.valueOf(i) );
                        record.setIgnoredField  ( new Object()                          );
                        record.setIndexedField  ( "indexed value: " + i                 );
                        record.setRequiredField ( String.valueOf(i)                     );
                    }
                }
            });

            Log.d(TAG, "TestRecordKt Count:" + RealmTypeSafeQuery.with(realm).where(TestRecordKt.class).count());

            Log.d(TAG, "TestRecordKt Equal To 1: " + RealmTypeSafeQuery.with(realm).where(TestRecordKt.class).equalTo(TestRecordKtFields.STRING_FIELD, "1").findAll().toString());
            Log.d(TAG, "TestRecordKt Equal To null: " + RealmTypeSafeQuery.with(realm).where(TestRecordKt.class).equalTo(TestRecordKtFields.STRING_FIELD, null).findAll().toString());
            Log.d(TAG, "TestRecordKt IsNull: " + RealmTypeSafeQuery.with(realm).where(TestRecordKt.class).isNull(TestRecordKtFields.STRING_FIELD).findAll().toString());
            Log.d(TAG, "TestRecordKt IsNotNull: " + RealmTypeSafeQuery.with(realm).where(TestRecordKt.class).isNotNull(TestRecordKtFields.STRING_FIELD).findAll().toString());
        }
    }
}
