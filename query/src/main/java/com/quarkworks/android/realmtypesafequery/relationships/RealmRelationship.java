package com.quarkworks.android.realmtypesafequery.relationships;


import android.support.annotation.NonNull;

import com.quarkworks.android.realmtypesafequery.fields.RealmBooleanField;
import com.quarkworks.android.realmtypesafequery.fields.RealmByteArrayField;
import com.quarkworks.android.realmtypesafequery.fields.RealmByteField;
import com.quarkworks.android.realmtypesafequery.fields.RealmDateField;
import com.quarkworks.android.realmtypesafequery.fields.RealmDoubleField;
import com.quarkworks.android.realmtypesafequery.fields.RealmFloatField;
import com.quarkworks.android.realmtypesafequery.fields.RealmIntegerField;
import com.quarkworks.android.realmtypesafequery.fields.RealmShortField;
import com.quarkworks.android.realmtypesafequery.fields.RealmStringField;
import com.quarkworks.android.realmtypesafequery.fields.indexed.RealmIndexedBooleanField;
import com.quarkworks.android.realmtypesafequery.fields.indexed.RealmIndexedByteField;
import com.quarkworks.android.realmtypesafequery.fields.indexed.RealmIndexedDateField;
import com.quarkworks.android.realmtypesafequery.fields.indexed.RealmIndexedIntegerField;
import com.quarkworks.android.realmtypesafequery.fields.indexed.RealmIndexedLongField;
import com.quarkworks.android.realmtypesafequery.fields.indexed.RealmIndexedShortField;
import com.quarkworks.android.realmtypesafequery.fields.indexed.RealmIndexedStringField;

import io.realm.RealmModel;

public abstract class RealmRelationship<Model extends RealmModel, RelationshipModel extends RealmModel> {

    @NonNull
    private final Class<Model> modelClass;

    @NonNull
    private final String keyPath;

    public RealmRelationship(@NonNull Class<Model> modelClass, @NonNull String keyPath) {
        this.modelClass = modelClass;
        this.keyPath = keyPath;
    }

    @NonNull
    public Class<Model> getModelClass() {
        return modelClass;
    }

    @NonNull
    public String getKeyPath() {
        return keyPath;
    }

    /*
        Basic
     */

    @NonNull
    public RealmBooleanField<Model> link(@NonNull RealmBooleanField<RelationshipModel> field) {
        return new RealmBooleanField<>(this.getModelClass(), this.getKeyPath() + "." + field.getKeyPath());
    }

    @NonNull
    public RealmByteArrayField<Model> link(@NonNull RealmByteArrayField<RelationshipModel> field) {
        return new RealmByteArrayField<>(this.getModelClass(), this.getKeyPath() + "." + field.getKeyPath());
    }

    @NonNull
    public RealmByteField<Model> link(@NonNull RealmByteField<RelationshipModel> field) {
        return new RealmByteField<>(this.getModelClass(), this.getKeyPath() + "." + field.getKeyPath());
    }

    @NonNull
    public RealmDateField<Model> link(@NonNull RealmDateField<RelationshipModel> field) {
        return new RealmDateField<>(this.getModelClass(), this.getKeyPath() + "." + field.getKeyPath());
    }

    @NonNull
    public RealmDoubleField<Model> link(@NonNull RealmDoubleField<RelationshipModel> field) {
        return new RealmDoubleField<>(this.getModelClass(), this.getKeyPath() + "." + field.getKeyPath());
    }

    @NonNull
    public RealmFloatField<Model> link(@NonNull RealmFloatField<RelationshipModel> field) {
        return new RealmFloatField<>(this.getModelClass(), this.getKeyPath() + "." + field.getKeyPath());
    }

    @NonNull
    public RealmIntegerField<Model> link(@NonNull RealmIntegerField<RelationshipModel> field) {
        return new RealmIntegerField<>(this.getModelClass(), this.getKeyPath() + "." + field.getKeyPath());
    }

    @NonNull
    public RealmShortField<Model> link(@NonNull RealmShortField<RelationshipModel> field) {
        return new RealmShortField<>(this.getModelClass(), this.getKeyPath() + "." + field.getKeyPath());
    }

    @NonNull
    public RealmStringField<Model> link(@NonNull RealmStringField<RelationshipModel> field) {
        return new RealmStringField<>(this.getModelClass(), this.getKeyPath() + "." + field.getKeyPath());
    }

    /*
        Indexed
     */

    @NonNull
    public RealmIndexedBooleanField<Model> link(@NonNull RealmIndexedBooleanField<RelationshipModel> field) {
        return new RealmIndexedBooleanField<>(this.getModelClass(), this.getKeyPath() + "." + field.getKeyPath());
    }

    @NonNull
    public RealmIndexedByteField<Model> link(@NonNull RealmIndexedByteField<RelationshipModel> field) {
        return new RealmIndexedByteField<>(this.getModelClass(), this.getKeyPath() + "." + field.getKeyPath());
    }

    @NonNull
    public RealmIndexedDateField<Model> link(@NonNull RealmIndexedDateField<RelationshipModel> field) {
        return new RealmIndexedDateField<>(this.getModelClass(), this.getKeyPath() + "." + field.getKeyPath());
    }

    @NonNull
    public RealmIndexedIntegerField<Model> link(@NonNull RealmIndexedIntegerField<RelationshipModel> field) {
        return new RealmIndexedIntegerField<>(this.getModelClass(), this.getKeyPath() + "." + field.getKeyPath());
    }

    @NonNull
    public RealmIndexedLongField<Model> link(@NonNull RealmIndexedLongField<RelationshipModel> field) {
        return new RealmIndexedLongField<>(this.getModelClass(), this.getKeyPath() + "." + field.getKeyPath());
    }

    @NonNull
    public RealmIndexedShortField<Model> link(@NonNull RealmIndexedShortField<RelationshipModel> field) {
        return new RealmIndexedShortField<>(this.getModelClass(), this.getKeyPath() + "." + field.getKeyPath());
    }

    @NonNull
    public RealmIndexedStringField<Model> link(@NonNull RealmIndexedStringField<RelationshipModel> field) {
        return new RealmIndexedStringField<>(this.getModelClass(), this.getKeyPath() + "." + field.getKeyPath());
    }

    /*
        Relationships
     */

    @NonNull
    public <R extends RealmModel> RealmToOneRelationship<Model, R> link(@NonNull RealmToOneRelationship<RelationshipModel, R> field) {
        return new RealmToOneRelationship<>(this.getModelClass(), this.getKeyPath() + "." + field.getKeyPath());
    }

    @NonNull
    public <R extends RealmModel> RealmToManyRelationship<Model, R> link(@NonNull RealmToManyRelationship<RelationshipModel, R> field) {
        return new RealmToManyRelationship<>(this.getModelClass(), this.getKeyPath() + "." + field.getKeyPath());
    }
}
