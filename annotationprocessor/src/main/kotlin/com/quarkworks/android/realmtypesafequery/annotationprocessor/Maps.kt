package com.quarkworks.android.realmtypesafequery.annotationprocessor

import com.squareup.kotlinpoet.ClassName

import java.util.HashMap

internal object Maps {
    val BASE_MAP = HashMap<String, ClassName>()
    val INDEX_MAP = HashMap<ClassName, ClassName>()
    
    private const val PACKAGE = "com.quarkworks.android.realmtypesafequery.fields"
    
    private fun getClassName(simpleName: String) : ClassName = ClassName(PACKAGE, simpleName)

    private val REALM_BOOLEAN_FIELD = getClassName("RealmBooleanField")
    private val REALM_BYTE_FIELD = getClassName("RealmByteField")
    private val REALM_SHORT_FIELD = getClassName("RealmShortField")
    private val REALM_INTEGER_FIELD = getClassName("RealmIntegerField")
    private val REALM_LONG_FIELD = getClassName("RealmLongField")
    private val REALM_FLOAT_FIELD = getClassName("RealmFloatField")
    private val REALM_DOUBLE_FIELD = getClassName("RealmDoubleField")
    private val REALM_STRING_FIELD = getClassName("RealmStringField")
    private val REALM_BYTE_ARRAY_FIELD = getClassName("RealmByteArrayField")
    private val REALM_DATE_FIELD = getClassName("RealmDateField")

    private val REALM_INDEXED_BOOLEAN_FIELD = getClassName("RealmIndexedBooleanField")
    private val REALM_INDEXED_BYTE_FIELD = getClassName("RealmIndexedByteField")
    private val REALM_INDEXED_SHORT_FIELD = getClassName("RealmIndexedShortField")
    private val REALM_INDEXED_INTEGER_FIELD = getClassName("RealmIndexedIntegerField")
    private val REALM_INDEXED_LONG_FIELD = getClassName("RealmIndexedLongField")
    private val REALM_INDEXED_STRING_FIELD = getClassName("RealmIndexedStringField")
    private val REALM_INDEXED_DATE_FIELD = getClassName("RealmIndexedDateField")

    val REALM_TO_ONE_RELATIONSHIP = getClassName("RealmToOneRelationship")
    val REALM_TO_MANY_RELATIONSHIP = getClassName("RealmToManyRelationship")

    init {
        BASE_MAP["java.lang.Boolean"] = REALM_BOOLEAN_FIELD
        BASE_MAP["java.lang.Byte"] = REALM_BYTE_FIELD
        BASE_MAP["java.lang.Short"] = REALM_SHORT_FIELD
        BASE_MAP["java.lang.Integer"] = REALM_INTEGER_FIELD
        BASE_MAP["java.lang.Long"] = REALM_LONG_FIELD
        BASE_MAP["java.lang.Float"] = REALM_FLOAT_FIELD
        BASE_MAP["java.lang.Double"] = REALM_DOUBLE_FIELD
        BASE_MAP["java.lang.String"] = REALM_STRING_FIELD
        BASE_MAP["java.util.Date"] = REALM_DATE_FIELD

        BASE_MAP["boolean"] = REALM_BOOLEAN_FIELD
        BASE_MAP["byte"] = REALM_BYTE_FIELD
        BASE_MAP["byte[]"] = REALM_BYTE_ARRAY_FIELD
        BASE_MAP["short"] = REALM_SHORT_FIELD
        BASE_MAP["int"] = REALM_INTEGER_FIELD
        BASE_MAP["long"] = REALM_LONG_FIELD
        BASE_MAP["float"] = REALM_FLOAT_FIELD
        BASE_MAP["double"] = REALM_DOUBLE_FIELD

        INDEX_MAP[REALM_BOOLEAN_FIELD] = REALM_INDEXED_BOOLEAN_FIELD
        INDEX_MAP[REALM_BYTE_FIELD] = REALM_INDEXED_BYTE_FIELD
        INDEX_MAP[REALM_SHORT_FIELD] = REALM_INDEXED_SHORT_FIELD
        INDEX_MAP[REALM_INTEGER_FIELD] = REALM_INDEXED_INTEGER_FIELD
        INDEX_MAP[REALM_LONG_FIELD] = REALM_INDEXED_LONG_FIELD
        INDEX_MAP[REALM_STRING_FIELD] = REALM_INDEXED_STRING_FIELD
        INDEX_MAP[REALM_DATE_FIELD] = REALM_INDEXED_DATE_FIELD
    }
}
