@file:Suppress("unused")

package com.quarkworks.android.realmtypesafequery

import com.quarkworks.android.realmtypesafequery.fields.*
import io.realm.*

/*
    Null
 */

fun <T : RealmModel> RealmQuery<T>.isNull(field: RealmNullableField<T>) : RealmQuery<T> {
    field.isNull(this)
    return this
}

fun <T : RealmModel> RealmQuery<T>.isNotNull(field: RealmNullableField<T>) : RealmQuery<T> {
    field.isNotNull(this)
    return this
}

/*
    EqualTo
 */

fun <T : RealmModel, V> RealmQuery<T>.equalTo(field: RealmEquatableField<T, V>, value: V?) : RealmQuery<T> {
    field.equalTo(this, value)
    return this
}

fun <T : RealmModel, V> RealmQuery<T>.notEqualTo(field: RealmEquatableField<T, V>, value: V?) : RealmQuery<T> {
    field.notEqualTo(this, value)
    return this
}

fun <T : RealmModel, V> RealmQuery<T>.`in`(field: RealmInableField<T, V>, value: Array<V>) : RealmQuery<T> {
    field.`in`(this, value)
    return this
}

fun <T : RealmModel, V> RealmQuery<T>.`in`(field: RealmInableField<T, V>, value: List<V>) : RealmQuery<T> {
    field.`in`(this, value)
    return this
}

/*
    String
*/

fun <T : RealmModel> RealmQuery<T>.equalTo(field: RealmStringField<T>, value: String?, casing: Case) : RealmQuery<T> {
    field.equalTo(this, value, casing)
    return this
}

fun <T : RealmModel> RealmQuery<T>.notEqualTo(field: RealmStringField<T>, value: String?, casing: Case) : RealmQuery<T> {
    field.notEqualTo(this, value, casing)
    return this
}

fun <T : RealmModel> RealmQuery<T>.beginsWith(field: RealmStringField<T>, value: String?, casing: Case = Case.SENSITIVE) : RealmQuery<T> {
    field.beginsWith(this, value, casing)
    return this
}

fun <T : RealmModel> RealmQuery<T>.endsWith(field: RealmStringField<T>, value: String?, casing: Case = Case.SENSITIVE) : RealmQuery<T> {
    field.endsWith(this, value, casing)
    return this
}

fun <T : RealmModel> RealmQuery<T>.contains(field: RealmStringField<T>, value: String?, casing: Case = Case.SENSITIVE) : RealmQuery<T> {
    field.contains(this, value, casing)
    return this
}

fun <T : RealmModel> RealmQuery<T>.contains(field: RealmStringField<T>, value: String?, delimiter: String, casing: Case = Case.SENSITIVE) : RealmQuery<T> {
    field.contains(this, value, delimiter, casing)
    return this
}

fun <T : RealmModel> RealmQuery<T>.like(field: RealmStringField<T>, value: String?, casing: Case = Case.SENSITIVE) : RealmQuery<T> {
    field.like(this, value, casing)
    return this
}

/*
    Comparison
*/

fun <T : RealmModel, V> RealmQuery<T>.greaterThan(field: RealmComparableField<T, V>, value: V?) : RealmQuery<T> {
    field.greaterThan(this, value)
    return this
}

fun <T : RealmModel, V> RealmQuery<T>.greaterThanOrEqualTo(field: RealmComparableField<T, V>, value: V?) : RealmQuery<T> {
    field.greaterThanOrEqualTo(this, value)
    return this
}

fun <T : RealmModel, V> RealmQuery<T>.lessThan(field: RealmComparableField<T, V>, value: V?) : RealmQuery<T> {
    field.lessThan(this, value)
    return this
}

fun <T : RealmModel, V> RealmQuery<T>.lessThanOrEqualTo(field: RealmComparableField<T, V>, value: V?) : RealmQuery<T> {
    field.lessThanOrEqualTo(this, value)
    return this
}

fun <T : RealmModel, V> RealmQuery<T>.between(field: RealmComparableField<T, V>, start: V?, end: V?) : RealmQuery<T> {
    field.between(this, start, end)
    return this
}

/*
    Group
 */

fun <T : RealmModel> RealmQuery<T>.group(body: (RealmQuery<T>) -> Void) : RealmQuery<T> {
    beginGroup()
    body(this)
    endGroup()
    return this
}

/*
    Or
 */

fun <T : RealmModel> RealmQuery<T>.or(body: (RealmQuery<T>) -> Void) : RealmQuery<T> = or().group(body)

/*
    Not
 */

fun <T : RealmModel> RealmQuery<T>.not(body: (RealmQuery<T>) -> Void) : RealmQuery<T> = not().group(body)

/*
    Empty
 */

fun <T : RealmModel> RealmQuery<T>.isEmpty(field: RealmEmptyableField<T>) : RealmQuery<T> {
    field.isEmpty(this)
    return this
}

fun <T : RealmModel> RealmQuery<T>.isNotEmpty(field: RealmEmptyableField<T>) : RealmQuery<T> {
    field.isNotEmpty(this)
    return this
}

/*
    Distinct
 */

fun <T : RealmModel> RealmQuery<T>.distinct(field: RealmDistinctableField<T>) : RealmQuery<T> =
        field.distinct(this)


fun <T : RealmModel> RealmQuery<T>.distinct(firstField: RealmDistinctableField<T>, vararg remainingFields: RealmDistinctableField<T>) : RealmQuery<T> =
        firstField.distinct(this, *remainingFields)

/*
    Aggregate
 */

fun <T : RealmModel, V> RealmQuery<T>.sum(field: RealmAggregatableField<T, V>) : V = field.sum(this)

fun <T : RealmModel> RealmQuery<T>.average(field: RealmAggregatableField<T, *>) : Double = field.average(this)

/*
    Min Max
 */

fun <T : RealmModel, V> RealmQuery<T>.min(field: RealmMinMaxField<T, V>) : V? = field.min(this)

fun <T : RealmModel, V> RealmQuery<T>.max(field: RealmMinMaxField<T, V>) : V? = field.max(this)

/*
    Sorting
*/

fun <T : RealmModel> RealmQuery<T>.sort(field: RealmSortableField<T>, sortOrder: Sort = Sort.ASCENDING) : RealmQuery<T> =
        field.sort(this, sortOrder)

fun <T : RealmModel> RealmQuery<T>.sort(field1: RealmSortableField<T>, sortOrder1: Sort, field2: RealmSortableField<T>, sortOrder2: Sort) : RealmQuery<T> =
        field1.sort(this, sortOrder1, field2, sortOrder2)

fun <T : RealmModel> RealmQuery<T>.sort(fields: Array<RealmSortableField<T>>, sortOrders: Array<Sort>) : RealmQuery<T> =
        RealmSortableField.sort(this, fields, sortOrders);

