package com.quarkworks.android.realmtypesafequery.fields

import io.realm.RealmModel
import io.realm.RealmQuery

open class RealmShortField<Model : RealmModel>(override val modelClass: Class<Model>, override val name: String) :
        RealmField<Model>,
        RealmEquatableField<Model, Short>,
        RealmComparableField<Model, Short>,
        RealmSortableField<Model>,
        RealmInableField<Model, Short>,
        RealmMinMaxField<Model, Short>,
        RealmAggregatableField<Model, Long> {

    override fun equalTo(query: RealmQuery<Model>, value: Short?) {
        if (value == null) {
            isNull(query)
            return
        }

        query.equalTo(name, value)
    }

    override fun notEqualTo(query: RealmQuery<Model>, value: Short?) {
        if (value == null) {
            isNotNull(query)
            return
        }

        query.notEqualTo(name, value)
    }

    override fun never(query: RealmQuery<Model>) {
        query.beginGroup().equalTo(name, 0.toShort()).equalTo(name, 1.toShort()).endGroup()
    }

    override fun greaterThan(query: RealmQuery<Model>, value: Short?) {
        if (value == null) {
            notEqualTo(query, null)
            return
        }

        query.greaterThan(name, value.toInt())
    }

    override fun greaterThanOrEqualTo(query: RealmQuery<Model>, value: Short?) {
        if (value == null) {
            equalTo(query, null)
            return
        }

        query.greaterThanOrEqualTo(name, value.toInt())
    }

    override fun lessThan(query: RealmQuery<Model>, value: Short?) {
        if (value == null) {
            notEqualTo(query, null)
            return
        }

        query.lessThan(name, value.toInt())
    }

    override fun lessThanOrEqualTo(query: RealmQuery<Model>, value: Short?) {
        if (value == null) {
            equalTo(query, null)
            return
        }

        query.lessThanOrEqualTo(name, value.toInt())
    }

    override fun between(query: RealmQuery<Model>, start: Short?, end: Short?) {
        if (start == null || end == null) {
            equalTo(query, null)
        }

        query.between(name, start!!.toInt(), end!!.toInt())
    }

    override fun `in`(query: RealmQuery<Model>, values: Array<Short>) {
        query.`in`(name, values)
    }

    override fun `in`(query: RealmQuery<Model>, values: List<Short>) {
        query.`in`(name, values.toTypedArray())
    }

    override fun min(query: RealmQuery<Model>): Short? = query.min(name)?.toShort()

    override fun max(query: RealmQuery<Model>): Short? = query.max(name)?.toShort()

    override fun sum(query: RealmQuery<Model>): Long = query.sum(name).toLong()
}
