package com.quarkworks.android.realmtypesafequery.fields

import io.realm.RealmModel
import io.realm.RealmQuery

open class RealmBooleanField<Model : RealmModel>(override val modelClass: Class<Model>, override val name: String) :
        RealmField<Model>,
        RealmEquatableField<Model, Boolean>,
        RealmSortableField<Model>,
        RealmInableField<Model, Boolean> {

    override fun equalTo(query: RealmQuery<Model>, value: Boolean?) {
        if (value == null) {
            isNull(query)
            return
        }

        query.equalTo(name, value)
    }

    override fun notEqualTo(query: RealmQuery<Model>, value: Boolean?) {
        if (value == null) {
            isNotNull(query)
            return
        }

        query.notEqualTo(name, value)
    }

    override fun never(query: RealmQuery<Model>) {
        query.beginGroup().equalTo(name, true).equalTo(name, false).endGroup()
    }

    override fun `in`(query: RealmQuery<Model>, values: Array<Boolean>) {
        query.`in`(name, values)
    }
}
