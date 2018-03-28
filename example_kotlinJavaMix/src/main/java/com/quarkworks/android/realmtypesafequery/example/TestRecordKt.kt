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
public open class TestRecordKt : RealmModel {

    public var booleanField: Boolean? = null

    internal var boolValue: Boolean = false

    public var byteArrayField: ByteArray? = null

    public var byteField: Byte? = null

    public var byteValue: Byte = 0

    public var dateField: Date? = null

    public var doubleField: Double? = null

    internal var doubleValue: Double = 0.toDouble()

    public var floatField: Float? = null

    internal var floatValue: Float = 0.toFloat()

    public var integerField: Integer? = null

    public var intValue: Int = 0

    public var longField: Long? = null

    public var longValue: Long = 0

    public var shortField: Short? = null

    public var shortValue: Short = 0

    public var stringField: String? = null

    @Ignore
    public var ignoredField: Object? = null

    @Index
    public var indexedField: String? = null

    @PrimaryKey
    public var primaryKey: String? = null

    @Required
    public var requiredField = RealmDefaults.STRING

    public var parent: TestRecordKt? = null

    public var children: RealmList<TestRecordKt> = RealmList()
}
