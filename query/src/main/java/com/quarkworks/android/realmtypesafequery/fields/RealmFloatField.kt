package com.quarkworks.android.realmtypesafequery.fields

import io.realm.RealmModel
import io.realm.RealmQuery

class RealmFloatField<Model : RealmModel>(override val modelClass: Class<Model>, override val name: String) :
        RealmField<Model>,
        RealmEquatableField<Model, Float>,
        RealmComparableField<Model, Float>,
        RealmSortableField<Model>,
        RealmInableField<Model, Float>,
        RealmMinMaxField<Model, Float>,
        RealmAggregatableField<Model, Double> {

    override fun equalTo(query: RealmQuery<Model>, value: Float?) {
        if (value == null) {
            isNull(query)
            return
        }

        query.equalTo(name, value)
    }

    override fun notEqualTo(query: RealmQuery<Model>, value: Float?) {
        if (value == null) {
            isNotNull(query)
            return
        }

        query.notEqualTo(name, value)
    }

    override fun never(query: RealmQuery<Model>) {
        query.beginGroup().equalTo(name, 0.0f).equalTo(name, 1.0f).endGroup()
    }

    override fun greaterThan(query: RealmQuery<Model>, value: Float?) {
        if (value == null) {
            notEqualTo(query, null)
            return
        }

        query.greaterThan(name, value)
    }

    override fun greaterThanOrEqualTo(query: RealmQuery<Model>, value: Float?) {
        if (value == null) {
            equalTo(query, null)
            return
        }

        query.greaterThanOrEqualTo(name, value)
    }

    override fun lessThan(query: RealmQuery<Model>, value: Float?) {
        if (value == null) {
            notEqualTo(query, null)
            return
        }

        query.lessThan(name, value)
    }

    override fun lessThanOrEqualTo(query: RealmQuery<Model>, value: Float?) {
        if (value == null) {
            equalTo(query, null)
            return
        }

        query.lessThanOrEqualTo(name, value)
    }

    override fun between(query: RealmQuery<Model>, start: Float?, end: Float?) {
        if (start == null || end == null) {
            equalTo(query, null)
        }

        query.between(name, start!!, end!!)
    }

    override fun `in`(query: RealmQuery<Model>, values: Array<Float>) {
        query.`in`(name, values)
    }

    override fun min(query: RealmQuery<Model>): Float? = query.min(name)?.toFloat()

    override fun max(query: RealmQuery<Model>): Float? = query.max(name)?.toFloat()

    override fun sum(query: RealmQuery<Model>): Double = query.sum(name).toDouble()
}
