package com.mahmoudmabrok.movieslist;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mahmoudmabrok.movieslist.databinding.ActivityMainBinding;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {

    //String url = "https://api.themoviedb.org/3/movie/550";
    //  String url = "https://api.themoviedb.org/3/discover/movie?api_key=9b99a948f3632e5329d6f62622001602&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1&primary_release_year=2017";
    String ApiKey = "9b99a948f3632e5329d6f62622001602";
    String url = "https://api.themoviedb.org/3/discover/movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        final ActivityMainBinding binder = DataBindingUtil.setContentView(this, R.layout.activity_main);

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("response", response);
                binder.tvShow.setText(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    if (error != null)
                        Log.v("responseError", error.getMessage().toString());
                    binder.tvShow.setText(error.getMessage().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("api_key", ApiKey);
                params.put("page", "1");
                params.put("sort_by", "popularity.desc");
                params.put("primary_release_year", "2017");

                return params;
            }
        };

        Volley.newRequestQueue(this).add(request);


    }
}
