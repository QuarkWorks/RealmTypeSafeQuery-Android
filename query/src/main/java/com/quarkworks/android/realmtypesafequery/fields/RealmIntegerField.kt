package com.quarkworks.android.realmtypesafequery.fields

import io.realm.RealmModel
import io.realm.RealmQuery

open class RealmIntegerField<Model : RealmModel>(override val modelClass: Class<Model>, override val name: String) :
        RealmField<Model>,
        RealmEquatableField<Model, Int>,
        RealmComparableField<Model, Int>,
        RealmSortableField<Model, Int>,
        RealmInableField<Model, Int>,
        RealmMinMaxField<Model, Int>,
        RealmAggregatableField<Model, Long> {

    override fun equalTo(query: RealmQuery<Model>, value: Int?) {
        if (value == null) {
            isNull(query)
            return
        }

        query.equalTo(name, value)
    }

    override fun notEqualTo(query: RealmQuery<Model>, value: Int?) {
        if (value == null) {
            isNotNull(query)
            return
        }

        query.notEqualTo(name, value)
    }

    override fun never(query: RealmQuery<Model>) {
        val zero = 0
        val one = 1
        query.beginGroup().equalTo(name, zero).equalTo(name, one).endGroup()
    }

    override fun greaterThan(query: RealmQuery<Model>, value: Int?) {
        if (value == null) {
            notEqualTo(query, null)
            return
        }

        query.greaterThan(name, value)
    }

    override fun greaterThanOrEqualTo(query: RealmQuery<Model>, value: Int?) {
        if (value == null) {
            equalTo(query, null)
            return
        }

        query.greaterThanOrEqualTo(name, value)
    }

    override fun lessThan(query: RealmQuery<Model>, value: Int?) {
        if (value == null) {
            notEqualTo(query, null)
            return
        }

        query.lessThan(name, value)
    }

    override fun lessThanOrEqualTo(query: RealmQuery<Model>, value: Int?) {
        if (value == null) {
            equalTo(query, null)
            return
        }

        query.lessThanOrEqualTo(name, value)
    }

    override fun between(query: RealmQuery<Model>, start: Int?, end: Int?) {
        if (start == null || end == null) {
            equalTo(query, null)
        }

        query.between(name, start!!, end!!)
    }

    override fun `in`(query: RealmQuery<Model>, values: Array<Int>) {
        query.`in`(name, values)
    }

    override fun min(query: RealmQuery<Model>): Int? = query.min(name)?.toInt()

    override fun max(query: RealmQuery<Model>): Int? = query.max(name)?.toInt()

    override fun sum(query: RealmQuery<Model>): Long = query.sum(name).toLong()
}
