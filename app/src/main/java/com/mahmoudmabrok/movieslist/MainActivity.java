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

    String url = "http://www.themoviedb.org/discover/movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        final ActivityMainBinding binder = DataBindingUtil.setContentView(this, R.layout.activity_main);

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("response", response);
                binder.tvShow.setText(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("responseError", error.getMessage());
                binder.tvShow.setText(error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("sort_by", "Popularity.desc");
                params.put("primary_release_year", "2017");
                params.put("with_genres", "18");

                return params;
            }
        };

        Volley.newRequestQueue(this).add(request);


    }
}
