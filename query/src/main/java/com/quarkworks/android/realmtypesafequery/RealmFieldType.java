package com.quarkworks.android.realmtypesafequery;

public enum RealmFieldType {
    BOOLEAN(true, false, true),
    BYTE(true, true, true),
    SHORT(true, true, true),
    INTEGER(true, true, true),
    LONG(true, true, true),
    FLOAT(false, false, true),
    DOUBLE(false, false, true),
    STRING(false, false, true),
    DATE(true, false, true),
    BYTE_ARRAY(false, false, true),
    TO_ONE(false, false, true),
    TO_MANY(false, false, false);

    private final boolean canBeIndex;
    private final boolean canBePrimaryKey;
    private final boolean canBeOptional;

    RealmFieldType(boolean canBeIndex, boolean canBePrimaryKey, boolean canBeOptional) {
        this.canBeIndex = canBeIndex;
        this.canBePrimaryKey = canBePrimaryKey;
        this.canBeOptional = canBeOptional;
    }

    public boolean canBeIndex() {
        return canBeIndex;
    }

    public boolean canBePrimaryKey() {
        return canBePrimaryKey;
    }

    public boolean canBeOptional() {
        return this.canBeOptional;
    }
}
