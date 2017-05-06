package com.webianks.test.bestkick;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {

    static VolleySingleton instance = null;
    private RequestQueue requestQueue;


    private VolleySingleton() {
        requestQueue = Volley.newRequestQueue(MyApplication.getContext());
    }


    public static VolleySingleton getInstance() {

        if (instance == null)
            instance = new VolleySingleton();

        return instance;
    }


    public RequestQueue getRequestQueue() {
        return requestQueue;
    }
}
