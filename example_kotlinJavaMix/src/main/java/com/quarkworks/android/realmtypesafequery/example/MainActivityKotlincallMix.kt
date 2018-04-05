package com.quarkworks.android.realmtypesafequery.example


import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.quarkworks.android.realmtypesafequery.RealmTypeSafeQuery
import com.quarkworks.android.realmtypesafequery.generated.TestRecordJavaFieldNames
import com.quarkworks.android.realmtypesafequery.generated.TestRecordJavaFields
import io.realm.Realm
import java.util.*

class MainActivityKotlincallMix : AppCompatActivity() {
    @SuppressLint("UseValueOf")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_mix)

        val f1 = TestRecordJavaFieldNames.DATE_FIELD
        val f2 = TestRecordJavaFieldNames.STRING_FIELD

        Realm.init(this.applicationContext)

        Realm.getDefaultInstance().use({ realm ->

            realm.executeTransaction { realm ->
                realm.deleteAll()
                for (i in 0..9) {
                    val record = realm.createObject(TestRecordJava::class.java, i.toString())
                    record.booleanField = i % 2 == 0
                    record.byteArrayField = byteArrayOf(i.toByte())
                    record.byteField = i.toByte()
                    record.dateField = Date((i * 1000).toLong())
                    record.doubleField = i * 1000.0
                    record.floatField = i * 2000f
                    record.integerField = i
                    record.longField = i * 10L
                    record.shortField = i.toShort()
                    record.stringField = if (i % 3 == 0) null else i.toString()
                    record.ignoredField = Object()
                    record.indexedField = "indexed value: $i"
                    record.requiredField = i.toString()
                }
            }

            Log.d(TAG, "TestRecordJava Count:" + RealmTypeSafeQuery.with(realm).where(TestRecordJava::class.java).count())

            Log.d(TAG, "TestRecordJava Equal To 1: " + RealmTypeSafeQuery.with(realm).where(TestRecordJava::class.java).equalTo(TestRecordJavaFields.STRING_FIELD, "1").findAll().toString())
            Log.d(TAG, "TestRecordJava Equal To null: " + RealmTypeSafeQuery.with(realm).where(TestRecordJava::class.java).equalTo(TestRecordJavaFields.STRING_FIELD, null).findAll().toString())
            Log.d(TAG, "TestRecordJava IsNull: " + RealmTypeSafeQuery.with(realm).where(TestRecordJava::class.java).isNull(TestRecordJavaFields.STRING_FIELD).findAll().toString())
            Log.d(TAG, "TestRecordJava IsNotNull: " + RealmTypeSafeQuery.with(realm).where(TestRecordJava::class.java).isNotNull(TestRecordJavaFields.STRING_FIELD).findAll().toString())
        })
    }

    companion object {

        private val TAG = MainActivityKotlincallMix::class.java.simpleName
    }
}
