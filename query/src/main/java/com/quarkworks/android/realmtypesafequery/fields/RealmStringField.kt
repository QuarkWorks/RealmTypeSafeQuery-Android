package com.quarkworks.android.realmtypesafequery.fields

import io.realm.Case

import io.realm.RealmModel
import io.realm.RealmQuery

open class RealmStringField<Model : RealmModel>(override val modelClass: Class<Model>, override val keyPath: String) :
        RealmField<Model>,
        RealmEquatableField<Model, String>,
        RealmSortableField<Model, String>,
        RealmEmptyableField<Model>,
        RealmInableField<Model, String> {

    override fun equalTo(query: RealmQuery<Model>, value: String?) {
        if (value == null) {
            this.isNull(query)
            return
        }

        query.equalTo(this.keyPath, value)
    }

    override fun notEqualTo(query: RealmQuery<Model>, value: String?) {
        if (value == null) {
            this.isNotNull(query)
            return
        }

        query.notEqualTo(this.keyPath, value)
    }

    override fun never(query: RealmQuery<Model>) {
        query.beginGroup().equalTo(this.keyPath, "Q").equalTo(this.keyPath, "W").endGroup()
    }

    override fun `in`(query: RealmQuery<Model>, values: Array<String>) {
        query.`in`(keyPath, values)
    }

    fun `in`(query: RealmQuery<Model>, values: Array<String>, casing: Case) {
        query.`in`(keyPath, values, casing)
    }

    fun equalTo(query: RealmQuery<Model>, value: String?, casing: Case) {
        if (value == null) {
            this.isNull(query)
            return
        }

        query.equalTo(this.keyPath, value, casing)
    }

    fun notEqualTo(query: RealmQuery<Model>, value: String?, casing: Case) {
        if (value == null) {
            this.isNotNull(query)
            return
        }

        query.notEqualTo(this.keyPath, value, casing)
    }

    fun beginsWith(query: RealmQuery<Model>, value: String?) {
        beginsWith(query, value, Case.SENSITIVE)
    }

    fun beginsWith(query: RealmQuery<Model>, value: String?, casing: Case) {
        if (value == null) {
            isNull(query)
            return
        }

        query.beginsWith(keyPath, value, casing)
    }

    fun endsWith(query: RealmQuery<Model>, value: String?) {
        endsWith(query, value, Case.SENSITIVE)
    }

    fun endsWith(query: RealmQuery<Model>, value: String?, casing: Case) {
        if (value == null) {
            isNull(query)
            return
        }

        query.endsWith(keyPath, value, casing)
    }

    fun contains(query: RealmQuery<Model>, value: String?) {
        contains(query, value, Case.SENSITIVE)
    }

    fun contains(query: RealmQuery<Model>, value: String?, casing: Case) {
        if (value == null) {
            isNull(query)
            return
        }

        query.contains(keyPath, value, casing)
    }

    fun contains(query: RealmQuery<Model>, value: String?, delimiter: String) {
        contains(query, value, delimiter, Case.SENSITIVE)
    }

    fun contains(query: RealmQuery<Model>, value: String?, delimiter: String, casing: Case) {
        if (value == null) {
            isNull(query)
            return
        }

        query.beginGroup()
                .contains(keyPath, delimiter + value + delimiter, casing)
                .beginsWith(keyPath, value + delimiter, casing)
                .endsWith(keyPath, delimiter + value, casing)
                .equalTo(keyPath, value, casing)
                .endGroup()

    }

    fun like(query: RealmQuery<Model>, value: String?) {
        like(query, value, Case.SENSITIVE)
    }

    fun like(query: RealmQuery<Model>, value: String?, casing: Case) {
        if (value == null) {
            isNull(query)
            return
        }

        query.like(keyPath, value, casing)
    }
}