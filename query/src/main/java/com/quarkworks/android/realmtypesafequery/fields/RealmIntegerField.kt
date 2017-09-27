package com.quarkworks.android.realmtypesafequery.fields

import io.realm.RealmModel
import io.realm.RealmQuery

open class RealmIntegerField<Model : RealmModel>(override val modelClass: Class<Model>, override val keyPath: String) :
        RealmField<Model>,
        RealmEquatableField<Model, Int>,
        RealmComparableField<Model, Int>,
        RealmSortableField<Model, Int>,
        RealmInableField<Model, Int>,
        RealmMinMaxField<Model, Int>,
        RealmAggregatableField<Model, Long> {

    override fun equalTo(query: RealmQuery<Model>, value: Int?) {
        if (value == null) {
            this.isNull(query)
            return
        }

        query.equalTo(this.keyPath, value)
    }

    override fun notEqualTo(query: RealmQuery<Model>, value: Int?) {
        if (value == null) {
            this.isNotNull(query)
            return
        }

        query.notEqualTo(this.keyPath, value)
    }

    override fun never(query: RealmQuery<Model>) {
        val zero = 0
        val one = 1
        query.beginGroup().equalTo(this.keyPath, zero).equalTo(this.keyPath, one).endGroup()
    }

    override fun greaterThan(query: RealmQuery<Model>, value: Int?) {
        if (value == null) {
            this.notEqualTo(query, null)
            return
        }

        query.greaterThan(this.keyPath, value)
    }

    override fun greaterThanOrEqualTo(query: RealmQuery<Model>, value: Int?) {
        if (value == null) {
            this.equalTo(query, null)
            return
        }

        query.greaterThanOrEqualTo(this.keyPath, value)
    }

    override fun lessThan(query: RealmQuery<Model>, value: Int?) {
        if (value == null) {
            this.notEqualTo(query, null)
            return
        }

        query.lessThan(this.keyPath, value)
    }

    override fun lessThanOrEqualTo(query: RealmQuery<Model>, value: Int?) {
        if (value == null) {
            this.equalTo(query, null)
            return
        }

        query.lessThanOrEqualTo(this.keyPath, value)
    }

    override fun between(query: RealmQuery<Model>, start: Int?, end: Int?) {
        if (start == null || end == null) {
            this.equalTo(query, null)
        }

        query.between(this.keyPath, start!!, end!!)
    }

    override fun `in`(query: RealmQuery<Model>, values: Array<Int>) {
        query.`in`(keyPath, values)
    }

    override fun min(query: RealmQuery<Model>): Int? = query.min(keyPath)?.toInt()

    override fun max(query: RealmQuery<Model>): Int? = query.max(keyPath)?.toInt()

    override fun sum(query: RealmQuery<Model>): Long = query.sum(keyPath).toLong()
}
