package com.quarkworks.android.realmtypesafequery.fields

import io.realm.RealmModel
import io.realm.RealmQuery

open class RealmShortField<Model : RealmModel>(override val modelClass: Class<Model>, override val keyPath: String) :
        RealmField<Model>,
        RealmEquatableField<Model, Short>,
        RealmComparableField<Model, Short>,
        RealmSortableField<Model, Short>,
        RealmInableField<Model, Short>,
        RealmMinMaxField<Model, Short>,
        RealmAggregatableField<Model, Long> {

    override fun equalTo(query: RealmQuery<Model>, value: Short?) {
        if (value == null) {
            this.isNull(query)
            return
        }

        query.equalTo(this.keyPath, value)
    }

    override fun notEqualTo(query: RealmQuery<Model>, value: Short?) {
        if (value == null) {
            this.isNotNull(query)
            return
        }

        query.notEqualTo(this.keyPath, value)
    }

    override fun never(query: RealmQuery<Model>) {
        query.beginGroup().equalTo(this.keyPath, 0.toShort()).equalTo(this.keyPath, 1.toShort()).endGroup()
    }

    override fun greaterThan(query: RealmQuery<Model>, value: Short?) {
        if (value == null) {
            this.notEqualTo(query, null)
            return
        }

        query.greaterThan(this.keyPath, value.toInt())
    }

    override fun greaterThanOrEqualTo(query: RealmQuery<Model>, value: Short?) {
        if (value == null) {
            this.equalTo(query, null)
            return
        }

        query.greaterThanOrEqualTo(this.keyPath, value.toInt())
    }

    override fun lessThan(query: RealmQuery<Model>, value: Short?) {
        if (value == null) {
            this.notEqualTo(query, null)
            return
        }

        query.lessThan(this.keyPath, value.toInt())
    }

    override fun lessThanOrEqualTo(query: RealmQuery<Model>, value: Short?) {
        if (value == null) {
            this.equalTo(query, null)
            return
        }

        query.lessThanOrEqualTo(this.keyPath, value.toInt())
    }

    override fun between(query: RealmQuery<Model>, start: Short?, end: Short?) {
        if (start == null || end == null) {
            this.equalTo(query, null)
        }

        query.between(this.keyPath, start!!.toInt(), end!!.toInt())
    }

    override fun `in`(query: RealmQuery<Model>, values: Array<Short>) {
        query.`in`(keyPath, values)
    }

    override fun min(query: RealmQuery<Model>): Short? = query.min(keyPath)?.toShort()

    override fun max(query: RealmQuery<Model>): Short? = query.max(keyPath)?.toShort()

    override fun sum(query: RealmQuery<Model>): Long = query.sum(keyPath).toLong()
}
