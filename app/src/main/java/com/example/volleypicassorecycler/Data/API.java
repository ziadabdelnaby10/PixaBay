package com.example.volleypicassorecycler.Data;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.volleypicassorecycler.Adapter.PixabayRecyclerAdapter;
import com.example.volleypicassorecycler.UI.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class API {

    private static API instance;
    private RequestQueue requestQueue;
    private static Context ctx;

    public API(Context context) {
        ctx = context;
        this.requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue() {
        if(requestQueue == null)
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        return requestQueue;
    }

    public synchronized static API getInstance(Context context)
    {
        if(instance == null)
            instance = new API(context);
        return instance;
    }

    public ArrayList<ImagesModel> getData(String Url)
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
                Toast.makeText(ctx.getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        request.setTag(MainActivity.TAG);
        API.getInstance(ctx.getApplicationContext()).getRequestQueue().add(request);
        return data;
    }

}