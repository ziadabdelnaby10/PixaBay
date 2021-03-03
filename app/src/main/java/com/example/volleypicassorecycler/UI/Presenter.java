package com.example.volleypicassorecycler.UI;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.example.volleypicassorecycler.Data.API;
import com.example.volleypicassorecycler.Data.ImagesModel;

import java.util.ArrayList;

public class Presenter {
    private ViewPresenter view;
    private API api;
    private Context context;

    public Presenter(ViewPresenter view, Context context) {
        this.view = view;
        api = new API(context.getApplicationContext());
        this.context = context;
    }

    public void getDataFrom(String url)
    {
        ArrayList<ImagesModel> data;
        data = api.getData(url);
        ArrayList<ImagesModel> finalData = data;
        API.getInstance(context).getRequestQueue().addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {

            @Override
            public void onRequestFinished(Request<Object> request) {
                view.onGetDataFromApi(finalData);
            }
        });

    }
}
