package com.quarkworks.android.realmtypesafequery.converter;

import android.support.annotation.Nullable;

public interface RealmFieldConverter<RealmValue, Value> {
    Value convertToValue(@Nullable  RealmValue realmValue);
    RealmValue convertToRealmValue(@Nullable Value value);
}
