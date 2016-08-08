package com.quarkworks.android.realmtypesafequeryexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.quarkworks.android.realmtypesafequery.generated.TestRecordStringFields;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Object f1 = TestRecordStringFields.DATE_FIELD;
        Object f2 = TestRecordStringFields.STRING_FIELD;
    }
}
