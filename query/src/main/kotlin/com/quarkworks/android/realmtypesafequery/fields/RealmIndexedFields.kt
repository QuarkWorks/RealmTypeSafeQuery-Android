package com.quarkworks.android.realmtypesafequery.fields

import io.realm.RealmModel

class RealmIndexedBooleanField<Model : RealmModel>(modelClass: Class<Model>, keyPath: String)
    : RealmBooleanField<Model>(modelClass, keyPath), RealmIndexedField<Model>

class RealmIndexedByteField<Model : RealmModel>(modelClass: Class<Model>, keyPath: String)
    : RealmByteField<Model>(modelClass, keyPath), RealmIndexedField<Model>

class RealmIndexedDateField<Model : RealmModel>(modelClass: Class<Model>, keyPath: String)
    : RealmDateField<Model>(modelClass, keyPath), RealmIndexedField<Model>

class RealmIndexedIntegerField<Model : RealmModel>(modelClass: Class<Model>, keyPath: String)
    : RealmIntegerField<Model>(modelClass, keyPath), RealmIndexedField<Model>

class RealmIndexedLongField<Model : RealmModel>(modelClass: Class<Model>, keyPath: String)
    : RealmLongField<Model>(modelClass, keyPath), RealmIndexedField<Model>

class RealmIndexedShortField<Model : RealmModel>(modelClass: Class<Model>, keyPath: String)
    : RealmShortField<Model>(modelClass, keyPath), RealmIndexedField<Model>

class RealmIndexedStringField<Model : RealmModel>(modelClass: Class<Model>, keyPath: String)
    : RealmStringField<Model>(modelClass, keyPath), RealmIndexedField<Model>