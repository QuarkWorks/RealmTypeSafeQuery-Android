package com.quarkworks.android.realmtypesafequery.converter;

import android.support.annotation.NonNull;

public interface RealmRequiredFieldConverter<RealmValue, Value> extends RealmFieldConverter<RealmValue, Value> {
    @Override
    @NonNull
    RealmValue convertToValue(@NonNull Value attribute);

    @Override
    @NonNull
    Value convertToRealmValue(@NonNull RealmValue value);
}
