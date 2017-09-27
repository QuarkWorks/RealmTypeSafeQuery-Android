package com.quarkworks.android.realmtypesafequery.fields

import io.realm.RealmModel
import io.realm.RealmQuery

class RealmByteArrayField<Model : RealmModel>(override val modelClass: Class<Model>, override val keyPath: String) :
        RealmField<Model>,
        RealmEquatableField<Model, ByteArray>,
        RealmEmptyableField<Model> {

    override fun equalTo(query: RealmQuery<Model>, value: ByteArray?) {
        if (value == null) {
            this.isNull(query)
            return
        }

        query.equalTo(this.keyPath, value)
    }

    override fun notEqualTo(query: RealmQuery<Model>, value: ByteArray?) {
        if (value == null) {
            this.isNotNull(query)
            return
        }

        query.notEqualTo(this.keyPath, value)
    }

    override fun never(query: RealmQuery<Model>) {
        query.beginGroup().equalTo(this.keyPath, byteArrayOf()).equalTo(this.keyPath, byteArrayOf(0)).endGroup()
    }
}
