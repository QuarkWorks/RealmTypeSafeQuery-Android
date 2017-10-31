package com.quarkworks.android.realmtypesafequery.fields

import io.realm.RealmModel
import io.realm.RealmQuery

open class RealmLongField<Model : RealmModel>(override val modelClass: Class<Model>, override val name: String) :
        RealmField<Model>,
        RealmEquatableField<Model, Long>,
        RealmComparableField<Model, Long>,
        RealmSortableField<Model>,
        RealmInableField<Model, Long>,
        RealmMinMaxField<Model, Long>,
        RealmAggregatableField<Model, Long> {

    override fun equalTo(query: RealmQuery<Model>, value: Long?) {
        if (value == null) {
            isNull(query)
            return
        }

        query.equalTo(name, value)
    }

    override fun notEqualTo(query: RealmQuery<Model>, value: Long?) {
        if (value == null) {
            isNotNull(query)
            return
        }

        query.notEqualTo(name, value)
    }

    override fun never(query: RealmQuery<Model>) {
        query.beginGroup().equalTo(name, 0L).equalTo(name, 1L).endGroup()
    }

    override fun greaterThan(query: RealmQuery<Model>, value: Long?) {
        if (value == null) {
            notEqualTo(query, null)
            return
        }

        query.greaterThan(name, value)
    }

    override fun greaterThanOrEqualTo(query: RealmQuery<Model>, value: Long?) {
        if (value == null) {
            equalTo(query, null)
            return
        }

        query.greaterThanOrEqualTo(name, value)
    }

    override fun lessThan(query: RealmQuery<Model>, value: Long?) {
        if (value == null) {
            notEqualTo(query, null)
            return
        }

        query.lessThan(name, value)
    }

    override fun lessThanOrEqualTo(query: RealmQuery<Model>, value: Long?) {
        if (value == null) {
            equalTo(query, null)
            return
        }

        query.lessThanOrEqualTo(name, value)
    }

    override fun between(query: RealmQuery<Model>, start: Long?, end: Long?) {
        if (start == null || end == null) {
            equalTo(query, null)
        }

        query.between(name, start!!, end!!)
    }

    override fun `in`(query: RealmQuery<Model>, values: Array<Long>) {
        query.`in`(name, values)
    }

    override fun `in`(query: RealmQuery<Model>, values: List<Long>) {
        query.`in`(name, values.toTypedArray())
    }

    override fun min(query: RealmQuery<Model>): Long? = query.min(name)?.toLong()

    override fun max(query: RealmQuery<Model>): Long? = query.max(name)?.toLong()

    override fun sum(query: RealmQuery<Model>): Long = query.sum(name).toLong()
}