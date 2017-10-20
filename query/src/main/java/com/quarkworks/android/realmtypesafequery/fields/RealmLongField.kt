package com.quarkworks.android.realmtypesafequery.fields

import io.realm.RealmModel
import io.realm.RealmQuery

open class RealmLongField<Model : RealmModel>(override val modelClass: Class<Model>, override val keyPath: String) :
        RealmField<Model>,
        RealmEquatableField<Model, Long>,
        RealmComparableField<Model, Long>,
        RealmSortableField<Model, Long>,
        RealmInableField<Model, Long>,
        RealmMinMaxField<Model, Long>,
        RealmAggregatableField<Model, Long> {

    override fun equalTo(query: RealmQuery<Model>, value: Long?) {
        if (value == null) {
            this.isNull(query)
            return
        }

        query.equalTo(this.keyPath, value)
    }

    override fun notEqualTo(query: RealmQuery<Model>, value: Long?) {
        if (value == null) {
            this.isNotNull(query)
            return
        }

        query.notEqualTo(this.keyPath, value)
    }

    override fun never(query: RealmQuery<Model>) {
        query.beginGroup().equalTo(this.keyPath, 0L).equalTo(this.keyPath, 1L).endGroup()
    }

    override fun greaterThan(query: RealmQuery<Model>, value: Long?) {
        if (value == null) {
            this.notEqualTo(query, null)
            return
        }

        query.greaterThan(this.keyPath, value)
    }

    override fun greaterThanOrEqualTo(query: RealmQuery<Model>, value: Long?) {
        if (value == null) {
            this.equalTo(query, null)
            return
        }

        query.greaterThanOrEqualTo(this.keyPath, value)
    }

    override fun lessThan(query: RealmQuery<Model>, value: Long?) {
        if (value == null) {
            this.notEqualTo(query, null)
            return
        }

        query.lessThan(this.keyPath, value)
    }

    override fun lessThanOrEqualTo(query: RealmQuery<Model>, value: Long?) {
        if (value == null) {
            this.equalTo(query, null)
            return
        }

        query.lessThanOrEqualTo(this.keyPath, value)
    }

    override fun between(query: RealmQuery<Model>, start: Long?, end: Long?) {
        if (start == null || end == null) {
            this.equalTo(query, null)
        }

        query.between(this.keyPath, start!!, end!!)
    }

    override fun `in`(query: RealmQuery<Model>, values: Array<Long>) {
        query.`in`(keyPath, values)
    }

    override fun min(query: RealmQuery<Model>): Long? = query.min(keyPath)?.toLong()

    override fun max(query: RealmQuery<Model>): Long? = query.max(keyPath)?.toLong()

    override fun sum(query: RealmQuery<Model>): Long = query.sum(keyPath).toLong()
}