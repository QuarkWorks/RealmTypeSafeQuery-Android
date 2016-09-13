package com.quarkworks.android.realmtypesafequery;


import java.util.HashMap;

/**
 * Created by james on 9/12/2016.
 */

class Maps {
    private static final HashMap<String, String> BaseMap;
    private static final HashMap<String, String> IndexMap;

    static {
        BaseMap = new HashMap<>();

        BaseMap.put("Boolean", "RealmBooleanField"    );
        BaseMap.put("Byte",    "RealmByteField"       );
        BaseMap.put("Short",   "RealmShortField"      );
        BaseMap.put("Integer", "RealmIntegerField"    );
        BaseMap.put("Long",    "RealmLongField"       );
        BaseMap.put("Float",   "RealmFloatField"      );
        BaseMap.put("Double",  "RealmDoubleField"     );
        BaseMap.put("String",  "RealmStringField"     );
        BaseMap.put("byte[]",  "RealmByteArrayField"  );
        BaseMap.put("Date",    "RealmDateField"       );
        //BaseMap.put("RealmModel",     );
        //BaseMap.put("RealmList" , );

        IndexMap = new HashMap<>();

        IndexMap.put(  "RealmBooleanField"  , "RealmIndexedBooleanField" );
        IndexMap.put(  "RealmByteField"     , "RealmIndexedByteField"    );
        IndexMap.put(  "RealmShortField"    , "RealmIndexedShortField"   );
        IndexMap.put(  "RealmIntegerField"  , "RealmIndexedIntegerField" );
        IndexMap.put(  "RealmLongField"     , "RealmIndexedLongField"    );
        IndexMap.put(  "RealmStringField"   , "RealmIndexedStringField"  );
        IndexMap.put(  "RealmDateField"     , "RealmIndexedDateField"    );
    }

}
