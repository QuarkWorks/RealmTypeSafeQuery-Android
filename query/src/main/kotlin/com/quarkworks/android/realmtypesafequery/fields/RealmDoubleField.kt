package com.quarkworks.android.realmtypesafequery.fields

import io.realm.RealmModel
import io.realm.RealmQuery

class RealmDoubleField<Model : RealmModel>(override val modelClass: Class<Model>, override val name: String) :
        RealmField<Model>,
        RealmEquatableField<Model, Double>,
        RealmComparableField<Model, Double>,
        RealmSortableField<Model>,
        RealmInableField<Model, Double>,
        RealmMinMaxField<Model, Double>,
        RealmAggregatableField<Model, Double> {

    override fun equalTo(query: RealmQuery<Model>, value: Double?) {
        if (value == null) {
            isNull(query)
            return
        }

        query.equalTo(name, value)
    }

    override fun notEqualTo(query: RealmQuery<Model>, value: Double?) {
        if (value == null) {
            isNotNull(query)
            return
        }

        query.notEqualTo(name, value)
    }

    override fun never(query: RealmQuery<Model>) {
        query.beginGroup().equalTo(name, 0.0).equalTo(name, 1.0).endGroup()
    }

    override fun greaterThan(query: RealmQuery<Model>, value: Double?) {
        if (value == null) {
            notEqualTo(query, null)
            return
        }

        query.greaterThan(name, value)
    }

    override fun greaterThanOrEqualTo(query: RealmQuery<Model>, value: Double?) {
        if (value == null) {
            equalTo(query, null)
            return
        }

        query.greaterThanOrEqualTo(name, value)
    }

    override fun lessThan(query: RealmQuery<Model>, value: Double?) {
        if (value == null) {
            notEqualTo(query, null)
            return
        }

        query.lessThan(name, value)
    }

    override fun lessThanOrEqualTo(query: RealmQuery<Model>, value: Double?) {
        if (value == null) {
            equalTo(query, null)
            return
        }

        query.lessThanOrEqualTo(name, value)
    }

    override fun between(query: RealmQuery<Model>, start: Double?, end: Double?) {
        if (start == null || end == null) {
            equalTo(query, null)
        }

        query.between(name, start!!, end!!)
    }

    override fun `in`(query: RealmQuery<Model>, values: Array<Double>) {
        query.`in`(name, values)
    }

    override fun `in`(query: RealmQuery<Model>, values: List<Double>) {
        query.`in`(name, values.toTypedArray())
    }

    override fun min(query: RealmQuery<Model>): Double? = query.min(name)?.toDouble()

    override fun max(query: RealmQuery<Model>): Double? = query.max(name)?.toDouble()

    override fun sum(query: RealmQuery<Model>): Double = query.sum(name).toDouble()
}
