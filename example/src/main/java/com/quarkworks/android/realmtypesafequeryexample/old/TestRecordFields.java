package com.quarkworks.android.realmtypesafequeryexample.old;

import com.quarkworks.android.realmtypesafequery.fields.RealmBooleanField;
import com.quarkworks.android.realmtypesafequery.fields.RealmByteArrayField;
import com.quarkworks.android.realmtypesafequery.fields.RealmByteField;
import com.quarkworks.android.realmtypesafequery.fields.RealmDateField;
import com.quarkworks.android.realmtypesafequery.fields.RealmDoubleField;
import com.quarkworks.android.realmtypesafequery.fields.RealmFloatField;
import com.quarkworks.android.realmtypesafequery.fields.RealmIntegerField;
import com.quarkworks.android.realmtypesafequery.fields.RealmLongField;
import com.quarkworks.android.realmtypesafequery.fields.RealmShortField;
import com.quarkworks.android.realmtypesafequery.fields.RealmStringField;
import com.quarkworks.android.realmtypesafequery.fields.indexed.RealmIndexedStringField;
import com.quarkworks.android.realmtypesafequeryexample.TestRecord;

@SuppressWarnings("unused")
public class  TestRecordFields {
    public static final RealmBooleanField<TestRecord> BOOLEAN_FIELD = new RealmBooleanField<>(TestRecord.class, "booleanField");
    public static final RealmByteArrayField<TestRecord> BYTE_ARRAY_FIELD = new RealmByteArrayField<>(TestRecord.class, "byteArrayField");
    public static final RealmByteField<TestRecord> BYTE_FIELD = new RealmByteField<>(TestRecord.class, "byteField");
    public static final RealmDateField<TestRecord> DATE_FIELD = new RealmDateField<>(TestRecord.class, "dateField");
    public static final RealmDoubleField<TestRecord> DOUBLE_FIELD = new RealmDoubleField<>(TestRecord.class, "doubleField");
    public static final RealmFloatField<TestRecord> FLOAT_FIELD = new RealmFloatField<>(TestRecord.class, "floatField");
    public static final RealmIntegerField<TestRecord> INTEGER_FIELD = new RealmIntegerField<>(TestRecord.class, "integerField");
    public static final RealmLongField<TestRecord> LONG_FIELD = new RealmLongField<>(TestRecord.class, "longField");
    public static final RealmShortField<TestRecord> SHORT_FIELD = new RealmShortField<>(TestRecord.class, "shortField");
    public static final RealmStringField<TestRecord> STRING_FIELD = new RealmStringField<>(TestRecord.class, "stringField");
    public static final RealmIndexedStringField<TestRecord> INDEXED_FIELD = new RealmIndexedStringField<>(TestRecord.class, "indexedField");
    public static final RealmIndexedStringField<TestRecord> PRIMARY_KEY = new RealmIndexedStringField<>(TestRecord.class, "primaryKey");
}
