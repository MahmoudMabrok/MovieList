package com.mahmoudmabrok.movieslist;

import android.app.Activity;
import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

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

    List<HashMap<String, String>> mHeaderList;
    HashMap<HashMap<String, String>, String> mChildList;
    ExpandleAdapter expandleAdapter;

    ArrayList<Movie> listMovies;
    ActivityMainBinding mainLayout;  //use binding instead of findViewById()

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mainLayout = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mHeaderList = new ArrayList<>();
        mChildList = new HashMap<>();
        listMovies = new ArrayList<>();

        expandleAdapter = new ExpandleAdapter(this, mHeaderList, mChildList);
        mainLayout.expandedList.setAdapter(expandleAdapter);

        mainLayout.btnShowAll.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Gey Movies ^_^ ");
                progressDialog.setCancelable(false);
                progressDialog.show();

                final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.hearder);
                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        linearLayout.setVisibility(View.VISIBLE);
                        mainLayout.btnShowAll.setVisibility(View.GONE);
                        mHeaderList.clear();
                        mChildList.clear();
                        Log.v("response", response);
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

                                mHeaderList.add(movie.getMovieHashMap());
                                mChildList.put(mHeaderList.get(i), movie.getmOverview());
                            }

                            updateAdapter();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "error ,try again", Toast.LENGTH_SHORT).show();
                        try {
                            if (error != null)
                                Log.v("responseError", error.getMessage().toString());
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
