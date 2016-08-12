package com.quarkworks.android.realmtypesafequery.converter;

import android.support.annotation.Nullable;

public interface RealmOptionalFieldConverter<RealmValue, Value> extends RealmFieldConverter<RealmValue, Value> {
    @Override
    @Nullable
    Value convertToValue(@Nullable RealmValue realmValue);

    @Override
    @Nullable
    RealmValue convertToRealmValue(@Nullable Value value);
}
