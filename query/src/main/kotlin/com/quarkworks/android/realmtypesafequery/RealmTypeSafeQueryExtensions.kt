package com.quarkworks.android.realmtypesafequery

import io.realm.Realm
import io.realm.RealmModel

/**
 * @author paul@quarkworks.co (Paul Gillis)
 */

inline fun <reified T : RealmModel> RealmTypeSafeQuery.Builder.where(): RealmTypeSafeQuery<T> {
    return where(T::class.java)
}

inline fun <reified T : RealmModel> RealmTypeSafeQuery.Companion.where(realm: Realm): RealmTypeSafeQuery<T> {
    return RealmTypeSafeQuery.where(T::class.java, realm)
}