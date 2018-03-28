package com.quarkworks.android.realmtypesafequery.example

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log


import com.quarkworks.android.realmtypesafequery.RealmTypeSafeQuery
import com.quarkworks.android.realmtypesafequery.generated.TestRecordFieldNames
import com.quarkworks.android.realmtypesafequery.generated.TestRecordFields

import java.util.Date

import io.realm.Realm
import io.realm.RealmConfiguration

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val f1 = TestRecordFieldNames.DATE_FIELD
        val f2 = TestRecordFieldNames.STRING_FIELD

        Realm.init(this.getApplicationContext())

        val config = RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build()
        Realm.setDefaultConfiguration(config)

        Realm.getDefaultInstance().use({ realm ->

            realm.executeTransaction { realm ->
                realm.deleteAll()
                for (i in 0..9) {
                    val record = realm.createObject(TestRecord::class.java, i.toString())
                    record.booleanField = i % 2 == 0
                    record.byteArrayField = byteArrayOf(i.toByte())
                    record.byteField = i.toByte()
                    record.dateField = Date((i * 1000).toLong())
                    record.doubleField = i * 1000.0
                    record.floatField = i * 2000f
                    record.integerField = Integer(i)
                    record.longField = i * 10L
                    record.shortField = i.toShort()
                    record.stringField = if (i % 3 == 0) null else i.toString()
                    record.ignoredField = Object()
                    record.indexedField = "indexed value: $i"
                    record.requiredField = i.toString()
                }
            }

            Log.d(TAG, "Count:" + RealmTypeSafeQuery.with(realm).where(TestRecord::class.java).count())

            Log.d(TAG, "Equal To 1: " + RealmTypeSafeQuery.with(realm).where(TestRecord::class.java).equalTo(TestRecordFields.STRING_FIELD, "1").findAll().toString())
            Log.d(TAG, "Equal To null: " + RealmTypeSafeQuery.with(realm).where(TestRecord::class.java).equalTo(TestRecordFields.STRING_FIELD, null).findAll().toString())
            Log.d(TAG, "IsNull: " + RealmTypeSafeQuery.with(realm).where(TestRecord::class.java).isNull(TestRecordFields.STRING_FIELD).findAll().toString())
            Log.d(TAG, "IsNotNull: " + RealmTypeSafeQuery.with(realm).where(TestRecord::class.java).isNotNull(TestRecordFields.STRING_FIELD).findAll().toString())
        })
    }

    companion object {

        private val TAG = MainActivity::class.java!!.getSimpleName()
    }
}
