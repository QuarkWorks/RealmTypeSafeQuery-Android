package com.quarkworks.android.realmtypesafequery.converter;

public interface RealmFieldConverter<RealmValue, Value> {
    Value convertToValue(RealmValue realmValue);
    RealmValue convertToRealmValue(Value value);
}
