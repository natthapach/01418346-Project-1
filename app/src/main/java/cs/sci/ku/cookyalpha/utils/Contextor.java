package cs.sci.ku.cookyalpha.utils;

import android.content.Context;

/**
 * Created by MegapiesPT on 31/10/2560.
 */

public class Contextor {
    private static Contextor ourInstance = new Contextor();
    private Context mContext;

    public static Contextor getInstance() {
        return ourInstance;
    }

    private Contextor() {
    }
    public void init(Context context){
        mContext = context;
    }
    public Context getContext(){
        return mContext;
    }
}
