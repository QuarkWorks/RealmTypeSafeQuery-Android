package com.quarkworks.android.realmtypesafequery.fields


import io.realm.RealmModel
import io.realm.RealmQuery

class RealmToManyRelationship<Model : RealmModel, RelationshipModel : RealmModel>
    (override val modelClass: Class<Model>, override val name: String) :
        RealmRelationship<Model, RelationshipModel>,
        RealmEmptyableField<Model>

class RealmToOneRelationship<Model : RealmModel, RelationshipModel : RealmModel>
    (override val modelClass: Class<Model>, override val name: String) :
        RealmRelationship<Model, RelationshipModel>,
        RealmNullableField<Model> {

    override fun never(query: RealmQuery<Model>) {
        throw NotImplementedError("Never should be needed for a to one relationship")
    }
}

interface RealmRelationship<Model : RealmModel, RelationshipModel : RealmModel>: RealmField<Model> {

    private fun linkKeyPath(field: RealmField<*>): String = name + "." + field.name

    /*
        Basic
     */

    fun link(field: RealmBooleanField<RelationshipModel>): RealmBooleanField<Model> =
            RealmBooleanField(modelClass, linkKeyPath(field))

    fun link(field: RealmByteArrayField<RelationshipModel>): RealmByteArrayField<Model> =
            RealmByteArrayField(modelClass, linkKeyPath(field))

    fun link(field: RealmByteField<RelationshipModel>): RealmByteField<Model> =
            RealmByteField(modelClass, linkKeyPath(field))

    fun link(field: RealmDateField<RelationshipModel>): RealmDateField<Model> =
            RealmDateField(modelClass, linkKeyPath(field))

    fun link(field: RealmDoubleField<RelationshipModel>): RealmDoubleField<Model> =
            RealmDoubleField(modelClass, linkKeyPath(field))

    fun link(field: RealmFloatField<RelationshipModel>): RealmFloatField<Model> =
            RealmFloatField(modelClass, linkKeyPath(field))

    fun link(field: RealmIntegerField<RelationshipModel>): RealmIntegerField<Model> =
            RealmIntegerField(modelClass, linkKeyPath(field))

    fun link(field: RealmShortField<RelationshipModel>): RealmShortField<Model> =
            RealmShortField(modelClass, linkKeyPath(field))

    fun link(field: RealmStringField<RelationshipModel>): RealmStringField<Model> =
            RealmStringField(modelClass, linkKeyPath(field))

    /*
        Indexed
     */

    fun link(field: RealmIndexedBooleanField<RelationshipModel>): RealmIndexedBooleanField<Model> =
            RealmIndexedBooleanField(modelClass, linkKeyPath(field))

    fun link(field: RealmIndexedByteField<RelationshipModel>): RealmIndexedByteField<Model> =
            RealmIndexedByteField(modelClass, linkKeyPath(field))

    fun link(field: RealmIndexedDateField<RelationshipModel>): RealmIndexedDateField<Model> =
            RealmIndexedDateField(modelClass, linkKeyPath(field))

    fun link(field: RealmIndexedIntegerField<RelationshipModel>): RealmIndexedIntegerField<Model> =
            RealmIndexedIntegerField(modelClass, linkKeyPath(field))

    fun link(field: RealmIndexedLongField<RelationshipModel>): RealmIndexedLongField<Model> =
            RealmIndexedLongField(modelClass, linkKeyPath(field))

    fun link(field: RealmIndexedShortField<RelationshipModel>): RealmIndexedShortField<Model> =
            RealmIndexedShortField(modelClass, linkKeyPath(field))

    fun link(field: RealmIndexedStringField<RelationshipModel>): RealmIndexedStringField<Model> =
            RealmIndexedStringField(modelClass, linkKeyPath(field))

    /*
        Relationships
     */

    fun <R : RealmModel> link(field: RealmToOneRelationship<RelationshipModel, R>): RealmToOneRelationship<Model, R> =
            RealmToOneRelationship(modelClass, linkKeyPath(field))

    fun <R : RealmModel> link(field: RealmToManyRelationship<RelationshipModel, R>): RealmToManyRelationship<Model, R> =
            RealmToManyRelationship(modelClass, linkKeyPath(field))
}
