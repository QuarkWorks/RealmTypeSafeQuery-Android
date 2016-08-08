package com.quarkworks.android.realmtypesafequery.converter;

public interface RealmFieldConverter<RealmValue, Value> {
    RealmValue convertToValue(Value value);
    Value convertToRealmValue(RealmValue realmValue);
}
