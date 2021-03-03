package com.example.volleypicassorecycler.UI;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.volleypicassorecycler.Adapter.PixabayRecyclerAdapter;
import com.example.volleypicassorecycler.Data.API;
import com.example.volleypicassorecycler.Data.ImagesModel;
import com.example.volleypicassorecycler.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PixabayRecyclerAdapter.onItemClickListener, ViewPresenter, View.OnClickListener {

    public static final String TAG = "MainActivity";
    private String url = "https://pixabay.com/api/?key=19999666-dcd12856d7a383c52280a0783&q=";
    private String query = "";
    private RecyclerView recyclerView;
    private PixabayRecyclerAdapter adapter;
    private ArrayList<ImagesModel> data;
    private ProgressBar progressBar;
    private Button searchBtn;
    private EditText editTextKeyword;
    private Presenter presenter;
    private API api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        api = new API(getApplicationContext());

        progressBar = findViewById(R.id.progressBar);

        data = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view);

        searchBtn = findViewById(R.id.btnSearch);
        editTextKeyword = findViewById(R.id.searchTxt);

        presenter = new Presenter(this::onGetDataFromApi, getApplicationContext());

        mainData();

        searchBtn.setOnClickListener(this);
    }

    @Override
    public void onItemClick(int pos) {
        Uri webpage = Uri.parse(data.get(pos).pageUrl);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);
        else
            Toast.makeText(this, "Sorry", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onItemLongClick(int pos) {
        data.remove(pos);
        adapter.notifyItemRemoved(pos);
    }

    private void mainData() {
        progressBar.setVisibility(View.VISIBLE);
        presenter.getDataFrom("https://pixabay.com/api/?key=19999666-dcd12856d7a383c52280a0783");
        progressBar.setVisibility(View.INVISIBLE);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (API.getInstance(this.getApplicationContext()).getRequestQueue() != null) {
            API.getInstance(this.getApplicationContext()).getRequestQueue().cancelAll(TAG);
        }

    }

    @Override
    public void onGetDataFromApi(ArrayList<ImagesModel> models) {
        //TODO after getting data
        data.clear();
        data.addAll(models);
        Log.v(TAG , data.size() + " " + models.size());
        adapter = new PixabayRecyclerAdapter();
        adapter.setInfoList(data);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(MainActivity.this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSearch:

                query = editTextKeyword.getText().toString().trim();
                if (query.isEmpty() || query == null)
                    Toast.makeText(MainActivity.this, "Empty KeyWord", Toast.LENGTH_SHORT).show();
                else {
                    progressBar.setVisibility(View.VISIBLE);
                    StringBuilder finalURl = new StringBuilder();
                    finalURl.append(url).append(query);
                    presenter.getDataFrom(finalURl.toString());
                    progressBar.setVisibility(View.INVISIBLE);
                }
                break;
        }
    }

    public ArrayList<ImagesModel> getDdata(String Url)
    {
        ArrayList<ImagesModel> data = new ArrayList<>();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("hits");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject hit = jsonArray.getJSONObject(i);
                        int likes = hit.getInt("likes");
                        int views = hit.getInt("views");
                        int fav = hit.getInt("favorites");

                        String imgUrl = hit.getString("webformatURL");
                        String UserName = hit.getString("user");
                        String UserImageUrl = hit.getString("userImageURL");
                        String pageUrl = hit.getString("pageURL");

                        data.add(new ImagesModel(likes, views, fav, imgUrl, UserName, UserImageUrl, pageUrl));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        request.setTag(MainActivity.TAG);
        API.getInstance(getApplicationContext()).getRequestQueue().add(request);
        Log.v("Api" , data.size() + " ");
        return data;
    }
}