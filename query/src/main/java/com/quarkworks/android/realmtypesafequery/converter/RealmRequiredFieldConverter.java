package com.quarkworks.android.realmtypesafequery.converter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public interface RealmRequiredFieldConverter<RealmValue, Value> extends RealmFieldConverter<RealmValue, Value> {
    @Override
    @NonNull
    Value convertToValue(@Nullable RealmValue realmValue);

    @Override
    @NonNull
    RealmValue convertToRealmValue(@Nullable Value value);
}
