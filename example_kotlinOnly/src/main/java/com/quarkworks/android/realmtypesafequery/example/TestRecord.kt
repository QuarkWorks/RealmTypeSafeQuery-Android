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
class TestRecord : RealmModel {

    @Nullable
    var booleanField: Boolean? = null

    internal var boolValue: Boolean = false

    @Nullable
    var byteArrayField: ByteArray? = null

    @Nullable
    var byteField: Byte? = null

    var byteValue: Byte = 0

    @Nullable
    var dateField: Date? = null

    @Nullable
    var doubleField: Double? = null

    internal var doubleValue: Double = 0.toDouble()

    @Nullable
    var floatField: Float? = null

    internal var floatValue: Float = 0.toFloat()

    @Nullable
    var integerField: Integer? = null

    var intValue: Int = 0

    @Nullable
    var longField: Long? = null

    var longValue: Long = 0

    @Nullable
    var shortField: Short? = null

    var shortValue: Short = 0

    @Nullable
    var stringField: String? = null

    @Ignore
    @Nullable
    var ignoredField: Object? = null

    @Index
    var indexedField: String? = null

    @PrimaryKey
    var primaryKey: String? = null

    @Required
    @NonNull
    var requiredField = RealmDefaults.STRING

    @Nullable
    var parent: TestRecord? = null

    @NonNull
    var children: RealmList<TestRecord> = RealmList()
}
