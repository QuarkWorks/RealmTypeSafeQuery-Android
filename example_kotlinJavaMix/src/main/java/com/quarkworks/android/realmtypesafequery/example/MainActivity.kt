package com.quarkworks.android.realmtypesafequery.example

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log


import com.quarkworks.android.realmtypesafequery.RealmTypeSafeQuery
import com.quarkworks.android.realmtypesafequery.generated.TestRecordKtFieldNames
import com.quarkworks.android.realmtypesafequery.generated.TestRecordKtFields

import java.util.Date

import io.realm.Realm
import io.realm.RealmConfiguration

class MainActivity : AppCompatActivity() {
    @SuppressLint("UseValueOf")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val f1 = TestRecordKtFieldNames.DATE_FIELD
        val f2 = TestRecordKtFieldNames.STRING_FIELD

        Realm.init(this.applicationContext)

        val config = RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build()
        Realm.setDefaultConfiguration(config)

        Realm.getDefaultInstance().use({ realm ->

            realm.executeTransaction { realm ->
                realm.deleteAll()
                for (i in 0..9) {
                    val record = realm.createObject(TestRecordKt::class.java, i.toString())
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

            Log.d(TAG, "Count:" + RealmTypeSafeQuery.with(realm).where(TestRecordKt::class.java).count())

            Log.d(TAG, "Equal To 1: " + RealmTypeSafeQuery.with(realm).where(TestRecordKt::class.java).equalTo(TestRecordKtFields.STRING_FIELD, "1").findAll().toString())
            Log.d(TAG, "Equal To null: " + RealmTypeSafeQuery.with(realm).where(TestRecordKt::class.java).equalTo(TestRecordKtFields.STRING_FIELD, null).findAll().toString())
            Log.d(TAG, "IsNull: " + RealmTypeSafeQuery.with(realm).where(TestRecordKt::class.java).isNull(TestRecordKtFields.STRING_FIELD).findAll().toString())
            Log.d(TAG, "IsNotNull: " + RealmTypeSafeQuery.with(realm).where(TestRecordKt::class.java).isNotNull(TestRecordKtFields.STRING_FIELD).findAll().toString())
        })
    }

    companion object {

        private val TAG = "${MainActivity::class.java.simpleName} kotlinJavaMix"
    }
}
