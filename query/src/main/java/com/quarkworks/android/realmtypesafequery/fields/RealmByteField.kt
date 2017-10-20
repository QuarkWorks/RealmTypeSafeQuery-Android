package com.quarkworks.android.realmtypesafequery.fields

import io.realm.RealmModel
import io.realm.RealmQuery

open class RealmByteField<Model : RealmModel>(override val modelClass: Class<Model>, override val name: String) :
        RealmField<Model>,
        RealmEquatableField<Model, Byte>,
        RealmSortableField<Model, Byte>,
        RealmInableField<Model, Byte> {

    override fun equalTo(query: RealmQuery<Model>, value: Byte?) {
        if (value == null) {
            isNull(query)
            return
        }

        query.equalTo(name, value)
    }

    override fun notEqualTo(query: RealmQuery<Model>, value: Byte?) {
        if (value == null) {
            isNotNull(query)
            return
        }

        query.notEqualTo(name, value)
    }

    override fun never(query: RealmQuery<Model>) {
        query.beginGroup().equalTo(name, 0.toByte()).equalTo(name, 1.toByte()).endGroup()
    }

    override fun `in`(query: RealmQuery<Model>, values: Array<Byte>) {
        query.`in`(name, values)
    }
}
