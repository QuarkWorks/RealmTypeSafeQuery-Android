package com.quarkworks.android.realmtypesafequery.fields

import io.realm.RealmModel
import io.realm.RealmQuery

class RealmDoubleField<Model : RealmModel>(override val modelClass: Class<Model>, override val keyPath: String) :
        RealmField<Model>,
        RealmEquatableField<Model, Double>,
        RealmComparableField<Model, Double>,
        RealmSortableField<Model, Double>,
        RealmInableField<Model, Double>,
        RealmMinMaxField<Model, Double>,
        RealmAggregatableField<Model, Double> {

    override fun equalTo(query: RealmQuery<Model>, value: Double?) {
        if (value == null) {
            this.isNull(query)
            return
        }

        query.equalTo(this.keyPath, value)
    }

    override fun notEqualTo(query: RealmQuery<Model>, value: Double?) {
        if (value == null) {
            this.isNotNull(query)
            return
        }

        query.notEqualTo(this.keyPath, value)
    }

    override fun never(query: RealmQuery<Model>) {
        query.beginGroup().equalTo(this.keyPath, 0.0).equalTo(this.keyPath, 1.0).endGroup()
    }

    override fun greaterThan(query: RealmQuery<Model>, value: Double?) {
        if (value == null) {
            this.notEqualTo(query, null)
            return
        }

        query.greaterThan(this.keyPath, value)
    }

    override fun greaterThanOrEqualTo(query: RealmQuery<Model>, value: Double?) {
        if (value == null) {
            this.equalTo(query, null)
            return
        }

        query.greaterThanOrEqualTo(this.keyPath, value)
    }

    override fun lessThan(query: RealmQuery<Model>, value: Double?) {
        if (value == null) {
            this.notEqualTo(query, null)
            return
        }

        query.lessThan(this.keyPath, value)
    }

    override fun lessThanOrEqualTo(query: RealmQuery<Model>, value: Double?) {
        if (value == null) {
            this.equalTo(query, null)
            return
        }

        query.lessThanOrEqualTo(this.keyPath, value)
    }

    override fun between(query: RealmQuery<Model>, start: Double?, end: Double?) {
        if (start == null || end == null) {
            this.equalTo(query, null)
        }

        query.between(this.keyPath, start!!, end!!)
    }

    override fun `in`(query: RealmQuery<Model>, values: Array<Double>) {
        query.`in`(keyPath, values)
    }

    override fun min(query: RealmQuery<Model>): Double? = query.min(keyPath)?.toDouble()

    override fun max(query: RealmQuery<Model>): Double? = query.max(keyPath)?.toDouble()

    override fun sum(query: RealmQuery<Model>): Double = query.sum(keyPath).toDouble()
}
