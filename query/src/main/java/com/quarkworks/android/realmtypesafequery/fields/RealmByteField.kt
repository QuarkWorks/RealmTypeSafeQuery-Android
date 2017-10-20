package com.quarkworks.android.realmtypesafequery.fields

import io.realm.RealmModel
import io.realm.RealmQuery

open class RealmByteField<Model : RealmModel>(override val modelClass: Class<Model>, override val keyPath: String) :
        RealmField<Model>,
        RealmEquatableField<Model, Byte>,
        RealmSortableField<Model, Byte>,
        RealmInableField<Model, Byte> {

    override fun equalTo(query: RealmQuery<Model>, value: Byte?) {
        if (value == null) {
            this.isNull(query)
            return
        }

        query.equalTo(this.keyPath, value)
    }

    override fun notEqualTo(query: RealmQuery<Model>, value: Byte?) {
        if (value == null) {
            this.isNotNull(query)
            return
        }

        query.notEqualTo(this.keyPath, value)
    }

    override fun never(query: RealmQuery<Model>) {
        query.beginGroup().equalTo(this.keyPath, 0.toByte()).equalTo(this.keyPath, 1.toByte()).endGroup()
    }

    override fun `in`(query: RealmQuery<Model>, values: Array<Byte>) {
        query.`in`(keyPath, values)
    }
}
