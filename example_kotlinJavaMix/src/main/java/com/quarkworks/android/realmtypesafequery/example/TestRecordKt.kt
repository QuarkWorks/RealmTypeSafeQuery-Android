package com.quarkworks.android.realmtypesafequery.example


import com.quarkworks.android.realmtypesafequery.constants.RealmDefaults
import com.quarkworks.android.realmtypesafequery.annotations.GenerateRealmFieldNames
import com.quarkworks.android.realmtypesafequery.annotations.GenerateRealmFields

import java.util.Date

import io.realm.RealmList
import io.realm.RealmModel
import io.realm.annotations.Ignore
import io.realm.annotations.Index
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required

@RealmClass
@GenerateRealmFieldNames
@GenerateRealmFields
open class TestRecordKt : RealmModel {

    var booleanField: Boolean? = null

    internal var boolValue: Boolean = false

    var byteArrayField: ByteArray? = null

    var byteField: Byte? = null

    var byteValue: Byte = 0

    var dateField: Date? = null

    var doubleField: Double? = null

    internal var doubleValue: Double = 0.toDouble()

    var floatField: Float? = null

    internal var floatValue: Float = 0.toFloat()

    var integerField: Int? = null

    var intValue: Int = 0

    var longField: Long? = null

    var longValue: Long = 0

    var shortField: Short? = null

    var shortValue: Short = 0

    var stringField: String? = null

    @Ignore
    var ignoredField: Any? = null

    @Index
    var indexedField: String? = null

    @PrimaryKey
    var primaryKey: String? = null

    @Required
    var requiredField = RealmDefaults.STRING

    var parent: TestRecordKt? = null

    var children: RealmList<TestRecordKt> = RealmList()
}
