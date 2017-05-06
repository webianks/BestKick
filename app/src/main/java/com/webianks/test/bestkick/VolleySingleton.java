package com.webianks.test.bestkick;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

class VolleySingleton {

    static VolleySingleton instance = null;
    private RequestQueue requestQueue;


    private VolleySingleton() {
        requestQueue = Volley.newRequestQueue(MyApplication.getContext());
    }


    static VolleySingleton getInstance() {

        if (instance == null)
            instance = new VolleySingleton();

        return instance;
    }


    RequestQueue getRequestQueue() {
        return requestQueue;
    }
}
