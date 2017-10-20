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

fun <T : RealmModel> RealmQuery<T>.distinct(field: RealmDistinctableField<T>) : RealmResults<T> =
        field.distinct(this)

fun <T : RealmModel> RealmQuery<T>.distinctAsync(field: RealmDistinctableField<T>) : RealmResults<T> =
        field.distinctAsync(this)

fun <T : RealmModel> RealmQuery<T>.distinct(firstField: RealmDistinctableField<T>, vararg remainingFields: RealmDistinctableField<T>) : RealmResults<T> =
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
    Results
 */
fun <T : RealmModel> RealmQuery<T>.findAllSorted(field: RealmSortableField<T, *>, sort: Sort) : RealmResults<T> =
        findAllSorted(field.name, sort)

fun <T : RealmModel> RealmQuery<T>.findAllSorted(field1: RealmSortableField<T, *>, sort1: Sort, field2: RealmSortableField<T, *>, sort2: Sort) : RealmResults<T> =
        findAllSorted(field1.name, sort1, field2.name, sort2)

fun <T : RealmModel> RealmQuery<T>.findAllSorted(fields: Array<RealmSortableField<T, *>>, sorts: Array<Sort>) : RealmResults<T> =
        findAllSorted(fields.map { it.name }.toTypedArray(), sorts)

/*
    Find First
 */

fun <T : RealmModel> RealmQuery<T>.findFirstSorted(field: RealmSortableField<T, *>, sort: Sort) : T? =
        findAllSorted(field, sort).first(null)

fun <T : RealmModel> RealmQuery<T>.findFirstSorted(field1: RealmSortableField<T, *>, sort1: Sort, field2: RealmSortableField<T, *>, sort2: Sort) : T? =
        findAllSorted(field1, sort1, field2, sort2).first(null)

fun <T : RealmModel> RealmQuery<T>.findFirstSorted(fields: Array<RealmSortableField<T, *>>, sorts: Array<Sort>) : T? =
        findAllSorted(fields, sorts).first(null)

/*
    Async
 */

fun <T : RealmModel> RealmQuery<T>.findAllSortedAsync(field: RealmSortableField<T, *>, sort: Sort) : RealmResults<T> =
        findAllSortedAsync(field.name, sort)

fun <T : RealmModel> RealmQuery<T>.findAllSortedAsync(field1: RealmSortableField<T, *>, sort1: Sort, field2: RealmSortableField<T, *>, sort2: Sort) : RealmResults<T> =
        findAllSortedAsync(field1.name, sort1, field2.name, sort2)

fun <T : RealmModel> RealmQuery<T>.findAllSortedAsync(fields: Array<RealmSortableField<T, *>>, sorts: Array<Sort>) : RealmResults<T> =
        findAllSortedAsync(fields.map { it.name }.toTypedArray(), sorts)
