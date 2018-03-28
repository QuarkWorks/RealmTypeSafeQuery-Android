package com.quarkworks.android.realmtypesafequery.example

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button

class MainActivity : Activity() {
    var javaCallMix:Button? = null;
    var kotlinCallMix:Button? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        javaCallMix = findViewById(R.id.javaCallMix) as Button?
        kotlinCallMix = findViewById(R.id.kotlinCallMix) as Button?
        javaCallMix!!.setOnClickListener {
            val i = Intent(this, MainActivityJavaCallMix::class.java)
            startActivity(i)

        }
        kotlinCallMix!!.setOnClickListener {
            val i = Intent(this, MainActivityKotlincallMix::class.java)
            startActivity(i)
        }
    }
}
