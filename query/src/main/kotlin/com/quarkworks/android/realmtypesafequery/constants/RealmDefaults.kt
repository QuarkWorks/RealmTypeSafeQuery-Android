package com.quarkworks.android.realmtypesafequery.constants

import java.util.Date

/**
 * @author berbschloe@quarkworks.co (Brandon Erbschloe)
 * @author paul@quarkworks.co (Paul Gillis)
 */
@Suppress("unused")
object RealmDefaults {
    const val BOOLEAN = false
    const val BYTE: Byte = 0x0
    const val SHORT: Short = 0
    const val INTEGER = 0
    const val LONG = 0L

    const val FLOAT = 0.0f
    const val DOUBLE = 0.0

    const val STRING = ""
    @JvmStatic val DATE = Date(0)

    @JvmStatic val BYTE_ARRAY = byteArrayOf()
}