package com.quarkworks.android.realmtypesafequery.converter;

import android.support.annotation.NonNull;

public interface RealmFieldConvertable<RealmValue, Value> {
    @NonNull RealmFieldConverter<RealmValue, Value> getConverter();
}
