package com.quarkworks.android.tests;

import org.junit.rules.ErrorCollector;

/**
 * Created by james on 12/13/2016.
 * add the obvious
 */
class MyErrCol extends ErrorCollector {
    void checkSucceeds(Runnable runable) {
        try {
            runable.run();
        } catch (Throwable e) {
            addError(e);
        }
    }
}
