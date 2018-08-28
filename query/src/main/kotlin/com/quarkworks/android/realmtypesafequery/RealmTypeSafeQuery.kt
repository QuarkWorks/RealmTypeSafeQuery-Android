package com.quarkworks.android.realmtypesafequery

import com.quarkworks.android.realmtypesafequery.fields.RealmAggregatableField
import com.quarkworks.android.realmtypesafequery.fields.RealmComparableField
import com.quarkworks.android.realmtypesafequery.fields.RealmDistinctableField
import com.quarkworks.android.realmtypesafequery.fields.RealmEmptyableField
import com.quarkworks.android.realmtypesafequery.fields.RealmEquatableField
import com.quarkworks.android.realmtypesafequery.fields.RealmField
import com.quarkworks.android.realmtypesafequery.fields.RealmInableField
import com.quarkworks.android.realmtypesafequery.fields.RealmMinMaxField
import com.quarkworks.android.realmtypesafequery.fields.RealmNullableField
import com.quarkworks.android.realmtypesafequery.fields.RealmSortableField
import com.quarkworks.android.realmtypesafequery.fields.RealmStringField
import io.realm.Case
import io.realm.Realm
import io.realm.RealmCollection
import io.realm.RealmModel
import io.realm.RealmQuery
import io.realm.RealmResults
import io.realm.Sort

/**
 * @author berbschloe@quarkworks.co (Brandon Erbschloe)
 * @author paul@quarkworks.co (Paul Gillis)
 */
@Suppress("unused")
class RealmTypeSafeQuery<Model : RealmModel>(private val realmQuery: RealmQuery<Model>) {
    /*
     * Constructors
     */
    constructor(modelClass: Class<Model>, realm: Realm) : this(realm.where(modelClass))
    constructor(realmCollection: RealmCollection<Model>) : this(realmCollection.where())

    val isValid = realmQuery.isValid

    companion object {

        /*
         * Static Constructors
         */

        @JvmStatic
        fun with(realm: Realm) = Builder(realm)

        @JvmStatic
        fun <M : RealmModel> where(clazz: Class<M>, realm: Realm): RealmTypeSafeQuery<M> {
            return RealmTypeSafeQuery(clazz, realm)
        }

        /*
         * Quick Queries
         */

        @JvmStatic
        fun <M : RealmModel, V> findFirst(realm: Realm, field: RealmEquatableField<M, V>, value: V?): M? {
            return RealmTypeSafeQuery.with(realm).where(field.modelClass)
                .equalTo(field, value).findFirst()
        }

        @JvmStatic
        fun <M, V, F> findFirst(realm: Realm, field: F, value: V?, sortOrder: Sort): M?
            where M : RealmModel, F : RealmSortableField<M>, F : RealmEquatableField<M, V>
        {
            return RealmTypeSafeQuery.with(realm)
                .where(field.modelClass)
                .equalTo(field, value)
                .sort(field, sortOrder)
                .findFirst()
        }

        @JvmStatic
        fun <M : RealmModel, V> findAll(realm: Realm, field: RealmEquatableField<M, V>, value: V?): RealmResults<M> {
            return RealmTypeSafeQuery(field.modelClass, realm)
                .equalTo(field, value).findAll()
        }

        @JvmStatic
        fun <M, V, F> findAll(realm: Realm, field: F, value: V?, sortOrder: Sort): RealmResults<M>
            where M : RealmModel, F : RealmSortableField<M>, F : RealmEquatableField<M, V> {
            return RealmTypeSafeQuery.with(realm)
                .where(field.modelClass)
                .equalTo(field, value)
                .sort(field, sortOrder)
                .findAll()
        }

        /*
         * Results
         */

        @JvmStatic
        private fun fieldNames(fields: Array<RealmField<*>>): Array<String> {
            val fieldNames = Array(fields.size) { "" }
            fields.forEachIndexed { index, realmField ->
                fieldNames[index] = realmField.name
            }

            return fieldNames
        }
    }

    /*
     * Intermediary builder class
     */

    class Builder internal constructor(private val realm: Realm) {
        fun <M : RealmModel> where(clazz: Class<M>): RealmTypeSafeQuery<M> {
            return RealmTypeSafeQuery(clazz, realm)
        }
    }

    /*
     * Null
     */

    fun isNull(field: RealmNullableField<Model>): RealmTypeSafeQuery<Model> {
        field.isNull(realmQuery)
        return this
    }

    fun isNotNull(field: RealmNullableField<Model>): RealmTypeSafeQuery<Model> {
        field.isNotNull(realmQuery)
        return this
    }

    /*
     * EqualTo
     */

    fun <V> equalTo(field: RealmEquatableField<Model, V>, value: V?): RealmTypeSafeQuery<Model> {
        field.equalTo(realmQuery, value)
        return this
    }

    fun <V> notEqualTo(field: RealmEquatableField<Model, V>, value: V?): RealmTypeSafeQuery<Model> {
        field.notEqualTo(realmQuery, value)
        return this
    }

    fun <V> `in`(field: RealmInableField<Model, V>, values: Array<V>): RealmTypeSafeQuery<Model> {
        field.`in`(realmQuery, values)
        return this
    }

    fun <V> `in`(field: RealmInableField<Model, V>, values: List<V>): RealmTypeSafeQuery<Model> {
        field.`in`(realmQuery, values)
        return this
    }

    /*
     * String
     */

    fun equalTo(field: RealmStringField<Model>, value: String?, casing: Case): RealmTypeSafeQuery<Model> {
        field.equalTo(realmQuery, value, casing)
        return this
    }

    fun notEqualTo(field: RealmStringField<Model>, value: String?, casing: Case): RealmTypeSafeQuery<Model> {
        field.notEqualTo(realmQuery, value, casing)
        return this
    }

    fun beginsWith(field: RealmStringField<Model>, value: String?): RealmTypeSafeQuery<Model> {
        field.beginsWith(realmQuery, value)
        return this
    }

    fun beginsWith(field: RealmStringField<Model>, value: String?, casing: Case): RealmTypeSafeQuery<Model> {
        field.beginsWith(realmQuery, value, casing)
        return this
    }

    fun endsWith(field: RealmStringField<Model>, value: String?): RealmTypeSafeQuery<Model> {
        field.endsWith(realmQuery, value)
        return this
    }

    fun endsWith(field: RealmStringField<Model>, value: String?, casing: Case): RealmTypeSafeQuery<Model> {
        field.endsWith(realmQuery, value, casing)
        return this
    }

    fun contains(field: RealmStringField<Model>, value: String?): RealmTypeSafeQuery<Model> {
        field.contains(realmQuery, value)
        return this
    }

    fun contains(field: RealmStringField<Model>, value: String?, casing: Case): RealmTypeSafeQuery<Model> {
        field.contains(realmQuery, value, casing)
        return this
    }

    fun contains(field: RealmStringField<Model>, value: String?, delimiter: String): RealmTypeSafeQuery<Model> {
        field.contains(realmQuery, value, delimiter)
        return this
    }

    fun contains(field: RealmStringField<Model>, value: String?, delimiter: String, casing: Case): RealmTypeSafeQuery<Model> {
        field.contains(realmQuery, value, delimiter, casing)
        return this
    }

    fun like(field: RealmStringField<Model>, value: String?): RealmTypeSafeQuery<Model> {
        field.like(realmQuery, value)
        return this
    }

    fun like(field: RealmStringField<Model>, value: String?, casing: Case): RealmTypeSafeQuery<Model> {
        field.like(realmQuery, value, casing)
        return this
    }

    /*
     * Comparison
     */

    fun <V> greaterThan(field: RealmComparableField<Model, V>, value: V?): RealmTypeSafeQuery<Model> {
        field.greaterThan(realmQuery, value)
        return this
    }

    fun <V> greaterThanOrEqualTo(field: RealmComparableField<Model, V>, value: V?): RealmTypeSafeQuery<Model> {
        field.greaterThanOrEqualTo(realmQuery, value)
        return this
    }

    fun <V> lessThan(field: RealmComparableField<Model, V>, value: V?): RealmTypeSafeQuery<Model> {
        field.lessThan(realmQuery, value)
        return this
    }

    fun <V> lessThanOrEqualTo(field: RealmComparableField<Model, V>, value: V?): RealmTypeSafeQuery<Model> {
        field.lessThanOrEqualTo(realmQuery, value)
        return this
    }

    fun <V> between(field: RealmComparableField<Model, V>, start: V?, end: V?): RealmTypeSafeQuery<Model> {
        field.between(realmQuery, start, end)
        return this
    }

    /*
     * Groups
     */

    interface Group<Model : RealmModel> {
        fun group(query: RealmTypeSafeQuery<Model>)
    }

    fun group(group: (RealmTypeSafeQuery<Model>) -> Unit): RealmTypeSafeQuery<Model> {
        realmQuery.beginGroup()
        group(this)
        realmQuery.endGroup()
        return this
    }

    fun group(group: RealmTypeSafeQuery.Group<Model>): RealmTypeSafeQuery<Model> = group(group::group)

    fun beginGroup(): RealmTypeSafeQuery<Model> {
        realmQuery.beginGroup()
        return this
    }

    fun endGroup(): RealmTypeSafeQuery<Model> {
        realmQuery.endGroup()
        return this
    }

    /*
     * Or
     */

    fun or(): RealmTypeSafeQuery<Model> {
        realmQuery.or()
        return this
    }

    fun or(group: Group<Model>): RealmTypeSafeQuery<Model> {
        or().group(group)
        return this
    }

    /*
     * Not
     */

    operator fun not(): RealmTypeSafeQuery<Model> {
        realmQuery.not()
        return this
    }

    fun not(group: Group<Model>): RealmTypeSafeQuery<Model> {
        not().group(group)
        return this
    }

    /*
     * Empty
     */

    fun isEmpty(field: RealmEmptyableField<Model>): RealmTypeSafeQuery<Model> {
        field.isEmpty(realmQuery)
        return this
    }

    fun isNotEmpty(field: RealmEmptyableField<Model>): RealmTypeSafeQuery<Model> {
        field.isNotEmpty(realmQuery)
        return this
    }

    /*
     * Distinct
     */

    fun distinct(field: RealmDistinctableField<Model>): RealmQuery<Model> = field.distinct(realmQuery)

    @SafeVarargs
    fun distinct(firstField: RealmDistinctableField<Model>, vararg remainingFields: RealmDistinctableField<Model>): RealmQuery<Model> {
        return firstField.distinct(realmQuery, *remainingFields)
    }

    /*
     * Aggregate Functions
     */

    fun <T> sum(field: RealmAggregatableField<Model, T>): T = field.sum(realmQuery)

    fun average(field: RealmAggregatableField<Model, *>): Double = field.average(realmQuery)

    /*
     * Min Max
     */

    fun <T> min(field: RealmMinMaxField<Model, T>): T? = field.min(realmQuery)

    fun <T> max(field: RealmMinMaxField<Model, T>): T? = field.max(realmQuery)

    fun count(): Long = realmQuery.count()

    fun findAll(): RealmResults<Model> = realmQuery.findAll()


    /*
     * Find First
     */

    fun findFirst(): Model? = realmQuery.findFirst()

    /*
     * Async
     */

    fun findAllAsync(): RealmResults<Model> = realmQuery.findAllAsync()

    fun findFirstAsync(): Model = realmQuery.findFirstAsync()


    /*
     * Sort
     */

    fun sort(field: RealmSortableField<Model>): RealmTypeSafeQuery<Model> {
        field.sort(realmQuery)
        return this
    }

    fun sort(field: RealmSortableField<Model>, sortOrder: Sort): RealmTypeSafeQuery<Model> {
        field.sort(realmQuery, sortOrder)
        return this
    }

    fun sort(field1: RealmSortableField<Model>, sortOrder1: Sort, field2: RealmSortableField<Model>, sortOrder2: Sort): RealmTypeSafeQuery<Model> {
        field1.sort(realmQuery, sortOrder1, field2, sortOrder2)
        return this
    }

    fun sort(fields: Array<RealmSortableField<Model>>, sortOrders: Array<Sort>): RealmTypeSafeQuery<Model> {
        RealmSortableField.sort(realmQuery, fields, sortOrders)
        return this
    }
}