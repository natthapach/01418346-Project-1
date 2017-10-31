package cs.sci.ku.cookyalpha;

import android.app.Application;

import cs.sci.ku.cookyalpha.utils.Contextor;

/**
 * Created by MegapiesPT on 31/10/2560.
 */

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Contextor.getInstance().init(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}

