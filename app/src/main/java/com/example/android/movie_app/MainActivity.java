package com.example.android.movie_app;

import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    GridView mGridView;
    private ProgressBar mProgressBar;
    ArrayList<String> images;
    GridViewAdapter gridViewAdapter;
    //ArrayAdapter<String>adapter;
    //83a3250794f3258e62cae1d7f5cca379
    //d04160312987af22a80ba27b59cd080c old
    public  String FEED_URL = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=83a3250794f3258e62cae1d7f5cca379";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGridView=(GridView)findViewById(R.id.grid_id);
        images=new ArrayList<>();
       Log.d(FEED_URL,"errorrrr");
        RequestQueue queue= Volley.newRequestQueue(this);
        StringRequest request=new StringRequest(Request.Method.GET, FEED_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject ob=new JSONObject(response);
                    JSONArray A=ob.getJSONArray("results");
                    for (int i=0;i<A.length();i++)
                    {
                        JSONObject object=A.getJSONObject(i);
                        String name=object.getString("poster_path");
                        String poster="http://image.tmdb.org/t/p/w500"+name;
                        images.add(poster);
                    }
                    gridViewAdapter=new GridViewAdapter(MainActivity.this,images);
                    //  ArrayAdapter<String>adapter=new ArrayAdapter<String>(MainActivity.this,R.layout.image,R.id.image_id,images);
                    // Picasso.get().load(FEED_URL).into((Target) mGridView);
                    mGridView.setAdapter(gridViewAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_LONG).show();

            }
        });
        queue.add(request);

    }
}
