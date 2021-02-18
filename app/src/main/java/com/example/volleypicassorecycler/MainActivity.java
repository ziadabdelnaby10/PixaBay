package com.example.volleypicassorecycler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PixabayRecyclerAdapter.onItemClickListener{

    public static final String TAG = "MainActivity";
    public static final String url = "https://pixabay.com/api/?key=19999666-dcd12856d7a383c52280a0783&q=cat";
    private RecyclerView recyclerView;
    private PixabayRecyclerAdapter adapter;
    private ArrayList<ImagesModel> data;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        data = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view);

        loadApiData();
    }

    @Override
    public void onItemClick(int pos) {
        data.remove(pos);
        adapter.notifyItemRemoved(pos);
    }

    private void loadApiData()
    {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("hits");
                    for(int i =0 ; i<jsonArray.length() ; i++)
                    {
                        JSONObject hit = jsonArray.getJSONObject(i);
                        int likes = hit.getInt("likes");
                        int views = hit.getInt("views");
                        int fav = hit.getInt("favorites");

                        String imgUrl = hit.getString("webformatURL");
                        String UserName = hit.getString("user");
                        String UserImageUrl = hit.getString("userImageURL");

                        data.add(new ImagesModel( likes, views , fav, imgUrl , UserName , UserImageUrl));
                    }


                    progressBar.setVisibility(View.INVISIBLE);
                    adapter = new PixabayRecyclerAdapter();
                    adapter.setInfoList(data);
                    recyclerView.setAdapter(adapter);
                    adapter.setOnItemClickListener(MainActivity.this::onItemClick);
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
        request.setTag(TAG);
        API.getInstance(this.getApplicationContext()).getRequestQueue().add(request);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (API.getInstance(this.getApplicationContext()).getRequestQueue() != null) {
            API.getInstance(this.getApplicationContext()).getRequestQueue().cancelAll(TAG);
        }

    }
}