package com.prathamesh.mywellness;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class APISingleton {
        private static APISingleton apiSingleton;
        private RequestQueue requestQueue;
        private static Context myContext;

        private APISingleton(Context context){
            myContext = context;
            requestQueue = getRequestQueue();
        }

        private RequestQueue getRequestQueue() {
                if (requestQueue == null){
                        requestQueue = Volley.newRequestQueue(myContext.getApplicationContext());
                }
                return requestQueue;
        }

        public <T> void addToRequestQueue(Request<T> request){
                getRequestQueue().add(request);
        }

        public static synchronized APISingleton getInstance(Context context){
                if (apiSingleton == null)
                        apiSingleton = new APISingleton(context);

                return apiSingleton;
        }


}
