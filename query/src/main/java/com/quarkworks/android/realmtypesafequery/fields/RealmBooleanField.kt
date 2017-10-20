package com.quarkworks.android.realmtypesafequery.fields

import io.realm.RealmModel
import io.realm.RealmQuery

open class RealmBooleanField<Model : RealmModel>(override val modelClass: Class<Model>, override val keyPath: String) :
        RealmField<Model>,
        RealmEquatableField<Model, Boolean>,
        RealmSortableField<Model, Boolean>,
        RealmInableField<Model, Boolean> {

    override fun equalTo(query: RealmQuery<Model>, value: Boolean?) {
        if (value == null) {
            this.isNull(query)
            return
        }

        query.equalTo(this.keyPath, value)
    }

    override fun notEqualTo(query: RealmQuery<Model>, value: Boolean?) {
        if (value == null) {
            this.isNotNull(query)
            return
        }

        query.notEqualTo(this.keyPath, value)
    }

    override fun never(query: RealmQuery<Model>) {
        query.beginGroup().equalTo(this.keyPath, true).equalTo(this.keyPath, false).endGroup()
    }

    override fun `in`(query: RealmQuery<Model>, values: Array<Boolean>) {
        query.`in`(keyPath, values)
    }
}
