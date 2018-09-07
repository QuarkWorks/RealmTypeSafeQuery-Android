package com.quarkworks.android.realmtypesafequery.fields

import io.realm.Sort
import io.realm.RealmQuery
import io.realm.RealmModel
import java.util.LinkedList


/**
 * @author berbschloe@quarkworks.co (Brandon Erbschloe)
 * @author paul@quarkworks.co (Paul Gillis)
 */
class RealmSortedQuery<Model : RealmModel> internal constructor(private val originalRealmQuery: RealmQuery<Model>) {

    val realmQuery by lazy {
        val sortParams = getSortParams()
        originalRealmQuery.sort(sortParams.fieldNames, sortParams.sortOrders)
    }

    private val sortParams = LinkedList<Pair<String, Sort>>()

    private class SortParams(val fieldNames: Array<String>, val sortOrders: Array<Sort>)

    /*
     * Sorts
     */
    internal fun sort(field: RealmSortableField<Model>, sortOrder: Sort): RealmSortedQuery<Model> {
        sortParams.add(Pair(field.name, sortOrder))
        return this
    }

    internal fun sort(fields: Array<RealmSortableField<Model>>, sortOrders: Array<Sort>): RealmSortedQuery<Model> {
        if (fields.size != sortOrders.size) {
            throw IllegalArgumentException("fields length(" + fields.size + ") must equal sortOrders length (" + sortOrders.size + ")")
        }

        for (i in fields.indices) {
            sort(fields[i], sortOrders[i])
        }
        return this
    }

    /*
     * Results
     */
    fun findAll() = realmQuery.findAll()

    // findAll() is needed because Realm's findFirst() ignores sort orders.
    fun findFirst() = findAll().first(null)

    fun findAllAsync() = realmQuery.findAllAsync()

    private fun getSortParams(): SortParams {
        val fieldNames = emptyArray<String>()
        val sortOrders = emptyArray<Sort>()

        sortParams.forEachIndexed { index, pair ->
            fieldNames[index] = pair.first
            sortOrders[index] = pair.second
        }

        return SortParams(fieldNames, sortOrders)
    }
}