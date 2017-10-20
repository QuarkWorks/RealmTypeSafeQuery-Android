package com.quarkworks.android.tests.models;

import com.quarkworks.android.realmtypesafequery.annotations.GenerateRealmFieldNames;
import com.quarkworks.android.realmtypesafequery.annotations.GenerateRealmFields;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

@RealmClass
@GenerateRealmFieldNames
@GenerateRealmFields
public class StatData implements RealmModel {
    public static final int SUM_DATA_INDEX;
    public static final int AVG_DATA_INDEX;
    public static final int MAX_DATA_INDEX;
    public static final int MIN_DATA_INDEX;
    public static final Object [] SUM_DATA = {13057, 130.57f};
    public static final Object [] AVG_DATA = {395, 3.95666666666667d};
    public static final Object [] MAX_DATA = {1089, 10.89f, "1089,"};
    public static final Object [] MIN_DATA = {33, 0.33f, "0033,"};



    public static final Object[][] DATA = new Object[][] {
        {215, 2.15f  , "0215,"},
        {189, 1.89f  , "0189,"},
        {243, 2.43f  , "0243,"},
        {845, 8.45f  , "0845,"},
        {453, 4.53f  , "0453,"},
        {963, 9.63f  , "0963,"},
        {1025, 10.25f, "1025,"},
        {413, 4.13f  , "0413,"},
        {105, 1.05f  , "0105,"},
        {89, 0.89f   , "0089,"},
        {585, 5.85f  , "0585,"},
        {33, 0.33f   , "0033,"},
        {633, 6.33f  , "0633,"},
        {1089, 10.89f, "1089,"},
        {75, 0.75f   , "0075,"},
        {495, 4.95f  , "0495,"},
        {305, 3.05f  , "0305,"},
        {375, 3.75f  , "0375,"},
        {165, 1.65f  , "0165,"},
        {39, 0.39f   , "0039,"},
        {63, 0.63f   , "0063,"},
        {683, 6.83f  , "0683,"},
        {339, 3.39f  , "0339,"},
        {539, 5.39f  , "0539,"},
        {903, 9.03f  , "0903,"},
        {273, 2.73f  , "0273,"},
        {123, 1.23f  , "0123,"},
        {143, 1.43f  , "0143,"},
        {45, 0.45f   , "0045,"},
        {35, 0.35f   , "0035,"},
        {53, 0.53f   , "0053,"},
        {735, 7.35f  , "0735,"},
        {789, 7.89f  , "0789,"},


        {395, 3.95666666666667d},
        {13057, 130.57f},
        {1089, 10.89f},
        {33, 0.33f},
    };

    static {
        AVG_DATA_INDEX = DATA.length - 4;
        SUM_DATA_INDEX = DATA.length - 3;
        MAX_DATA_INDEX = DATA.length - 2;
        MIN_DATA_INDEX = DATA.length - 1;
    }

//    @PrimaryKey
//    public String primaryKey;

    public Integer integerField;
    public Float floatField;
    public String stringField;
}