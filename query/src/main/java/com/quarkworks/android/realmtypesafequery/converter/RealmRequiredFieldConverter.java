package com.quarkworks.android.realmtypesafequery.converter;

import android.support.annotation.NonNull;

public interface RealmRequiredFieldConverter<RealmValue, Value> extends RealmFieldConverter<RealmValue, Value> {
    @Override
    @NonNull
    Value convertToValue(@NonNull RealmValue realmValue);

    @Override
    @NonNull
    RealmValue convertToRealmValue(@NonNull Value value);
}
