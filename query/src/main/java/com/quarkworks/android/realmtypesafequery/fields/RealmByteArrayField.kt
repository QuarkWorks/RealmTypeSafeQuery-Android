package com.quarkworks.android.realmtypesafequery.fields

import io.realm.RealmModel
import io.realm.RealmQuery

class RealmByteArrayField<Model : RealmModel>(override val modelClass: Class<Model>, override val name: String) :
        RealmField<Model>,
        RealmEquatableField<Model, ByteArray>,
        RealmEmptyableField<Model> {

    override fun equalTo(query: RealmQuery<Model>, value: ByteArray?) {
        if (value == null) {
            isNull(query)
            return
        }

        query.equalTo(name, value)
    }

    override fun notEqualTo(query: RealmQuery<Model>, value: ByteArray?) {
        if (value == null) {
            isNotNull(query)
            return
        }

        query.notEqualTo(name, value)
    }

    override fun never(query: RealmQuery<Model>) {
        query.beginGroup().equalTo(name, byteArrayOf()).equalTo(name, byteArrayOf(0)).endGroup()
    }
}
