package com.example.volleypicassorecycler;

public class ImagesModel {
    public int numLike , numView , numFav;
    public String resultImageUrl , userName , userImageUrl;

    public ImagesModel(int numLike, int numView, int numFav, String resultImageUrl, String userName, String userImageUrl) {
        this.numLike = numLike;
        this.numView = numView;
        this.numFav = numFav;
        this.resultImageUrl = resultImageUrl;
        this.userName = userName;
        this.userImageUrl = userImageUrl;
    }
}
