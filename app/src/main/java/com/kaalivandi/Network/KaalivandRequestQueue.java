package com.kaalivandi.Network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by user on 21-08-2016.
 */
public class KaalivandRequestQueue {

    private Context mContext;
    protected com.android.volley.RequestQueue mRequestQueue;


    private static KaalivandRequestQueue mRequestQ;

    public KaalivandRequestQueue(Context mContext) {
        this.mContext = mContext;
    }

//    get the single object here..with that singleton get getRequest queue

    public static synchronized KaalivandRequestQueue getInstance(Context context) {
        if (mRequestQ == null) {
            mRequestQ = new KaalivandRequestQueue(context);
        }
        return mRequestQ;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToElfREquestQue(Request<T> req) {
        getRequestQueue().add(req);
    }


    public void cancelElfReques(String netTag) {
        if (mRequestQueue!=null){
            getRequestQueue().cancelAll(netTag);
        }
    }
}

