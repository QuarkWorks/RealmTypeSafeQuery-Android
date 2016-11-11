package com.quarkworks.android.realmtypesafequery.annotationprocessor;


import com.squareup.javapoet.ClassName;

import java.util.HashMap;

/**
 * Created by james on 9/12/2016.
 */

class Maps {
    public static final HashMap<String, ClassName> BaseMap;
    public static final HashMap<ClassName, ClassName> IndexMap;

    public static final ClassName realmbooleanfield    = ClassName.get("com.quarkworks.android.realmtypesafequery.fields", "RealmBooleanField")  ;
    public static final ClassName realmbytefield       = ClassName.get("com.quarkworks.android.realmtypesafequery.fields", "RealmByteField")     ;
    public static final ClassName realmshortfield      = ClassName.get("com.quarkworks.android.realmtypesafequery.fields", "RealmShortField")    ;
    public static final ClassName realmintegerfield    = ClassName.get("com.quarkworks.android.realmtypesafequery.fields", "RealmIntegerField")  ;
    public static final ClassName realmlongfield       = ClassName.get("com.quarkworks.android.realmtypesafequery.fields", "RealmLongField")     ;
    public static final ClassName realmfloatfield      = ClassName.get("com.quarkworks.android.realmtypesafequery.fields", "RealmFloatField")    ;
    public static final ClassName realmdoublefield     = ClassName.get("com.quarkworks.android.realmtypesafequery.fields", "RealmDoubleField")   ;
    public static final ClassName realmstringfield     = ClassName.get("com.quarkworks.android.realmtypesafequery.fields", "RealmStringField")   ;
    public static final ClassName realmbytearrayfield  = ClassName.get("com.quarkworks.android.realmtypesafequery.fields", "RealmByteArrayField");
    public static final ClassName realmdatefield       = ClassName.get("com.quarkworks.android.realmtypesafequery.fields", "RealmDateField")     ;

    public static final ClassName realmindexedbooleanfield    = ClassName.get("com.quarkworks.android.realmtypesafequery.fields.indexed", "RealmIndexedBooleanField")  ;
    public static final ClassName realmindexedbytefield       = ClassName.get("com.quarkworks.android.realmtypesafequery.fields.indexed", "RealmIndexedByteField")     ;
    public static final ClassName realmindexedshortfield      = ClassName.get("com.quarkworks.android.realmtypesafequery.fields.indexed", "RealmIndexedShortField")    ;
    public static final ClassName realmindexedintegerfield    = ClassName.get("com.quarkworks.android.realmtypesafequery.fields.indexed", "RealmIndexedIntegerField")  ;
    public static final ClassName realmindexedlongfield       = ClassName.get("com.quarkworks.android.realmtypesafequery.fields.indexed", "RealmIndexedLongField")     ;
    public static final ClassName realmindexedstringfield     = ClassName.get("com.quarkworks.android.realmtypesafequery.fields.indexed", "RealmIndexedStringField")   ;
    public static final ClassName realmindexeddatefield       = ClassName.get("com.quarkworks.android.realmtypesafequery.fields.indexed", "RealmIndexedDateField")     ;

    public static final ClassName realmtoonerelationship       = ClassName.get("com.quarkworks.android.realmtypesafequery.relationships", "RealmToOneRelationship")    ;
    public static final ClassName realmtomanyrelationship      = ClassName.get("com.quarkworks.android.realmtypesafequery.relationships", "RealmToManyRelationship")   ;

    static {
        BaseMap = new HashMap<>();

        BaseMap.put("java.lang.Boolean",  realmbooleanfield   );
        BaseMap.put("java.lang.Byte",     realmbytefield      );
        BaseMap.put("java.lang.Short",    realmshortfield     );
        BaseMap.put("java.lang.Integer",  realmintegerfield   );
        BaseMap.put("java.lang.Long",     realmlongfield      );
        BaseMap.put("java.lang.Float",    realmfloatfield     );
        BaseMap.put("java.lang.Double",   realmdoublefield    );
        BaseMap.put("java.lang.String",   realmstringfield    );
        BaseMap.put("byte[]",             realmbytearrayfield );
        BaseMap.put("java.util.Date",     realmdatefield      );

        BaseMap.put("boolean",            realmbooleanfield   );
        BaseMap.put("byte",               realmbytefield      );
        BaseMap.put("short",              realmshortfield     );
        BaseMap.put("int",                realmintegerfield   );
        BaseMap.put("long",               realmlongfield      );
        BaseMap.put("float",              realmfloatfield     );
        BaseMap.put("double",             realmdoublefield    );


        IndexMap = new HashMap<>();

        IndexMap.put(  realmbooleanfield  , realmindexedbooleanfield );
        IndexMap.put(  realmbytefield     , realmindexedbytefield    );
        IndexMap.put(  realmshortfield    , realmindexedshortfield   );
        IndexMap.put(  realmintegerfield  , realmindexedintegerfield );
        IndexMap.put(  realmlongfield     , realmindexedlongfield    );
        IndexMap.put(  realmstringfield   , realmindexedstringfield  );
        IndexMap.put(  realmdatefield     , realmindexeddatefield    );
    }
}
