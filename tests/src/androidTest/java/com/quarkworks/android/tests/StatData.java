package com.quarkworks.android.tests;

import com.quarkworks.android.realmtypesafequery.annotations.GenerateRealmFieldNames;
import com.quarkworks.android.realmtypesafequery.annotations.GenerateRealmFields;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

@RealmClass
@GenerateRealmFieldNames
@GenerateRealmFields
public class StatData implements RealmModel {
    static final int SUM_DATA_INDEX;
    static final int AVG_DATA_INDEX;
    static final int MAX_DATA_INDEX;
    static final int MIN_DATA_INDEX;
    static Object [] SUM_DATA = {13057, 130.57f};
    static Object [] AVG_DATA = {395, 3.95666666666667d};
    static Object [] MAX_DATA = {1089, 10.89f, "1089,"};
    static Object [] MIN_DATA = {33, 0.33f, "33,  "};



    static Object[][] DATA = new Object[][]
            {
                    {215, 2.15f  , "215, "},
                    {189, 1.89f  , "189, "},
                    {243, 2.43f  , "243, "},
                    {845, 8.45f  , "845, "},
                    {453, 4.53f  , "453, "},
                    {963, 9.63f  , "963, "},
                    {1025, 10.25f, "1025,"},
                    {413, 4.13f  , "413, "},
                    {105, 1.05f  , "105, "},
                    {89, 0.89f   , "89,  "},
                    {585, 5.85f  , "585, "},
                    {33, 0.33f   , "33,  "},
                    {633, 6.33f  , "633, "},
                    {1089, 10.89f, "1089,"},
                    {75, 0.75f   , "75,  "},
                    {495, 4.95f  , "495, "},
                    {305, 3.05f  , "305, "},
                    {375, 3.75f  , "375, "},
                    {165, 1.65f  , "165, "},
                    {39, 0.39f   , "39,  "},
                    {63, 0.63f   , "63,  "},
                    {683, 6.83f  , "683, "},
                    {339, 3.39f  , "339, "},
                    {539, 5.39f  , "539, "},
                    {903, 9.03f  , "903, "},
                    {273, 2.73f  , "273, "},
                    {123, 1.23f  , "123, "},
                    {143, 1.43f  , "143, "},
                    {45, 0.45f   , "45,  "},
                    {35, 0.35f   , "35,  "},
                    {53, 0.53f   , "53,  "},
                    {735, 7.35f  , "735, "},
                    {789, 7.89f  , "789, "},


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
}