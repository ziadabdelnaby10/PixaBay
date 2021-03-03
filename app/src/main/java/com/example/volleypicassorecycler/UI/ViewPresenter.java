package com.example.volleypicassorecycler.UI;

import com.example.volleypicassorecycler.Data.ImagesModel;

import java.util.ArrayList;

public interface ViewPresenter {
    public void onGetDataFromApi(ArrayList<ImagesModel> models);
}
