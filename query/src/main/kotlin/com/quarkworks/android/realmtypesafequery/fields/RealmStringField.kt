package com.quarkworks.android.realmtypesafequery.fields

import io.realm.Case

import io.realm.RealmModel
import io.realm.RealmQuery

open class RealmStringField<Model : RealmModel>(override val modelClass: Class<Model>, override val name: String) :
        RealmField<Model>,
        RealmEquatableField<Model, String>,
        RealmSortableField<Model>,
        RealmEmptyableField<Model>,
        RealmInableField<Model, String> {

    override fun equalTo(query: RealmQuery<Model>, value: String?) {
        if (value == null) {
            isNull(query)
            return
        }

        query.equalTo(name, value)
    }

    override fun notEqualTo(query: RealmQuery<Model>, value: String?) {
        if (value == null) {
            isNotNull(query)
            return
        }

        query.notEqualTo(name, value)
    }

    override fun never(query: RealmQuery<Model>) {
        query.beginGroup().equalTo(name, "Q").equalTo(name, "W").endGroup()
    }

    override fun `in`(query: RealmQuery<Model>, values: Array<String>) {
        query.`in`(name, values)
    }

    override fun `in`(query: RealmQuery<Model>, values: List<String>) {
        query.`in`(name, values.toTypedArray())
    }

    fun `in`(query: RealmQuery<Model>, values: Array<String>, casing: Case) {
        query.`in`(name, values, casing)
    }

    fun `in`(query: RealmQuery<Model>, values: List<String>, casing: Case) {
        query.`in`(name, values.toTypedArray(), casing)
    }

    fun equalTo(query: RealmQuery<Model>, value: String?, casing: Case) {
        if (value == null) {
            isNull(query)
            return
        }

        query.equalTo(name, value, casing)
    }

    fun notEqualTo(query: RealmQuery<Model>, value: String?, casing: Case) {
        if (value == null) {
            isNotNull(query)
            return
        }

        query.notEqualTo(name, value, casing)
    }

    fun beginsWith(query: RealmQuery<Model>, value: String?) {
        beginsWith(query, value, Case.SENSITIVE)
    }

    fun beginsWith(query: RealmQuery<Model>, value: String?, casing: Case) {
        if (value == null) {
            isNull(query)
            return
        }

        query.beginsWith(name, value, casing)
    }

    fun endsWith(query: RealmQuery<Model>, value: String?) {
        endsWith(query, value, Case.SENSITIVE)
    }

    fun endsWith(query: RealmQuery<Model>, value: String?, casing: Case) {
        if (value == null) {
            isNull(query)
            return
        }

        query.endsWith(name, value, casing)
    }

    fun contains(query: RealmQuery<Model>, value: String?) {
        contains(query, value, Case.SENSITIVE)
    }

    fun contains(query: RealmQuery<Model>, value: String?, casing: Case) {
        if (value == null) {
            isNull(query)
            return
        }

        query.contains(name, value, casing)
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
                .contains(name, delimiter + value + delimiter, casing)
                .or().beginsWith(name, value + delimiter, casing)
                .or().endsWith(name, delimiter + value, casing)
                .or().equalTo(name, value, casing)
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

        query.like(name, value, casing)
    }
}