package com.quarkworks.android.realmtypesafequery.fields

import io.realm.RealmModel
import io.realm.RealmQuery

class RealmFloatField<Model : RealmModel>(override val modelClass: Class<Model>, override val keyPath: String) :
        RealmField<Model>,
        RealmEquatableField<Model, Float>,
        RealmComparableField<Model, Float>,
        RealmSortableField<Model, Float>,
        RealmInableField<Model, Float>,
        RealmMinMaxField<Model, Float>,
        RealmAggregatableField<Model, Double> {

    override fun equalTo(query: RealmQuery<Model>, value: Float?) {
        if (value == null) {
            this.isNull(query)
            return
        }

        query.equalTo(this.keyPath, value)
    }

    override fun notEqualTo(query: RealmQuery<Model>, value: Float?) {
        if (value == null) {
            this.isNotNull(query)
            return
        }

        query.notEqualTo(this.keyPath, value)
    }

    override fun never(query: RealmQuery<Model>) {
        query.beginGroup().equalTo(this.keyPath, 0.0f).equalTo(this.keyPath, 1.0f).endGroup()
    }

    override fun greaterThan(query: RealmQuery<Model>, value: Float?) {
        if (value == null) {
            this.notEqualTo(query, null)
            return
        }

        query.greaterThan(this.keyPath, value)
    }

    override fun greaterThanOrEqualTo(query: RealmQuery<Model>, value: Float?) {
        if (value == null) {
            this.equalTo(query, null)
            return
        }

        query.greaterThanOrEqualTo(this.keyPath, value)
    }

    override fun lessThan(query: RealmQuery<Model>, value: Float?) {
        if (value == null) {
            this.notEqualTo(query, null)
            return
        }

        query.lessThan(this.keyPath, value)
    }

    override fun lessThanOrEqualTo(query: RealmQuery<Model>, value: Float?) {
        if (value == null) {
            this.equalTo(query, null)
            return
        }

        query.lessThanOrEqualTo(this.keyPath, value)
    }

    override fun between(query: RealmQuery<Model>, start: Float?, end: Float?) {
        if (start == null || end == null) {
            this.equalTo(query, null)
        }

        query.between(this.keyPath, start!!, end!!)
    }

    override fun `in`(query: RealmQuery<Model>, values: Array<Float>) {
        query.`in`(keyPath, values)
    }

    override fun min(query: RealmQuery<Model>): Float? = query.min(keyPath)?.toFloat()

    override fun max(query: RealmQuery<Model>): Float? = query.max(keyPath)?.toFloat()

    override fun sum(query: RealmQuery<Model>): Double = query.sum(keyPath).toDouble()
}
