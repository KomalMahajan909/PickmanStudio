package com.itechvision.ecrobo.pickman.AsyncTask;

public interface MainAsynListener<T> {

    void onPostSuccess(T result, int flag, boolean isSucess);

    void onPostError(int flag);

}

