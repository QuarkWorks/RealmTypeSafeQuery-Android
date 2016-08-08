package com.quarkworks.android.realmtypesafequery.converter;

import android.support.annotation.Nullable;

public interface RealmOptionalFieldConverter<RealmValue, Value> extends RealmFieldConverter<RealmValue, Value> {
    @Override
    @Nullable
    RealmValue convertToValue(@Nullable Value attribute);

    @Override
    @Nullable
    Value convertToRealmValue(@Nullable RealmValue value);
}
