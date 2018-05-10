package com.mahmoudmabrok.movieslist;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mahmoudmabrok.movieslist.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {

    //String url = "https://api.themoviedb.org/3/movie/550";
    //  String url = "https://api.themoviedb.org/3/discover/movie?api_key=9b99a948f3632e5329d6f62622001602&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1&primary_release_year=2017";
    String ApiKey = "9b99a948f3632e5329d6f62622001602";
    String url = "https://api.themoviedb.org/3/discover/movie";

    List<HashMap<String, String>> _headers;
    HashMap<HashMap<String, String>, String> _childs;
    ExpandleAdapter expandleAdapter;

    ArrayList<Movie> listMovies;


    ActivityMainBinding mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mainLayout = DataBindingUtil.setContentView(this, R.layout.activity_main);

        _headers = new ArrayList<>();
        _childs = new HashMap<>();
        listMovies = new ArrayList<>();

        expandleAdapter = new ExpandleAdapter(this, _headers, _childs);
        mainLayout.expandedList.setAdapter(expandleAdapter);

        mainLayout.btnShowAll.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        _headers.clear();
                        _childs.clear();
                        Log.v("response", response);
                        //   mainLayout.tvShow.setText(response);
                        try {

                            JSONObject root = new JSONObject(response);
                            JSONArray results = root.getJSONArray("results");
                            Movie movie;
                            JSONObject jsonObject;
                            for (int i = 0; i < results.length(); i++) {
                                movie = new Movie();
                                jsonObject = results.getJSONObject(i);
                                movie.setmOverview(jsonObject.getString("overview"));
                                movie.setmTitle(jsonObject.getString("title"));
                                movie.setmVote_count(jsonObject.getInt("vote_count"));
                                movie.setRelease_date(jsonObject.getString("release_date"));

                                _headers.add(movie.getMovieHashMap());
                                _childs.put(_headers.get(i), movie.getmOverview());
                            }
                            updateAdapter();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try {
                            if (error != null)
                                Log.v("responseError", error.getMessage().toString());
                            //     mainLayout.tvShow.setText(error.getMessage().toString());
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

                Volley.newRequestQueue(MainActivity.this).add(request);

            }
        });

    }

    public void updateAdapter() {

        expandleAdapter.notifyDataSetChanged();
    }
}
