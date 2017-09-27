package com.quarkworks.android.realmtypesafequery.fields

import java.util.Date

import io.realm.RealmModel
import io.realm.RealmQuery

open class RealmDateField<Model : RealmModel>(override val modelClass: Class<Model>, override val keyPath: String) :
        RealmField<Model>,
        RealmEquatableField<Model, Date>,
        RealmComparableField<Model, Date>,
        RealmSortableField<Model, Date>,
        RealmInableField<Model, Date>,
        RealmMinMaxField<Model, Date> {

    override fun equalTo(query: RealmQuery<Model>, value: Date?) {
        if (value == null) {
            this.isNull(query)
            return
        }

        query.equalTo(this.keyPath, value)
    }

    override fun notEqualTo(query: RealmQuery<Model>, value: Date?) {
        if (value == null) {
            this.isNotNull(query)
            return
        }

        query.notEqualTo(this.keyPath, value)
    }

    override fun never(query: RealmQuery<Model>) {
        query.beginGroup().equalTo(this.keyPath, Date(0)).equalTo(this.keyPath, Date(1)).endGroup()
    }

    override fun greaterThan(query: RealmQuery<Model>, value: Date?) {
        if (value == null) {
            this.notEqualTo(query, null)
            return
        }

        query.greaterThan(this.keyPath, value)
    }

    override fun greaterThanOrEqualTo(query: RealmQuery<Model>, value: Date?) {
        if (value == null) {
            this.equalTo(query, null)
            return
        }

        query.greaterThanOrEqualTo(this.keyPath, value)
    }

    override fun lessThan(query: RealmQuery<Model>, value: Date?) {
        if (value == null) {
            this.notEqualTo(query, null)
            return
        }

        query.lessThan(this.keyPath, value)
    }

    override fun lessThanOrEqualTo(query: RealmQuery<Model>, value: Date?) {
        if (value == null) {
            this.equalTo(query, null)
            return
        }

        query.lessThanOrEqualTo(this.keyPath, value)
    }

    override fun between(query: RealmQuery<Model>, start: Date?, end: Date?) {
        if (start == null || end == null) {
            this.equalTo(query, null)
        }

        query.between(this.keyPath, start!!, end!!)
    }

    override fun `in`(query: RealmQuery<Model>, values: Array<Date>) {
        query.`in`(keyPath, values)
    }

    override fun min(query: RealmQuery<Model>): Date? = query.minimumDate(keyPath)

    override fun max(query: RealmQuery<Model>): Date? = query.maximumDate(keyPath)
}