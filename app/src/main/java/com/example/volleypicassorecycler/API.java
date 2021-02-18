package com.example.volleypicassorecycler;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class API {
    private static API instance;
    private RequestQueue requestQueue;
    private static Context ctx;

    public API(Context context) {
        ctx = context;
        this.requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue() {
        if(requestQueue == null)
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        return requestQueue;
    }

    public synchronized static API getInstance(Context context)
    {
        if(instance == null)
            instance = new API(context);
        return instance;
    }

}
/*
public class MySingleton {
    private static MySingleton instance;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
    private static Context ctx;

    private MySingleton(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized MySingleton getInstance(Context context) {
        if (instance == null) {
            instance = new MySingleton(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }
}
*/