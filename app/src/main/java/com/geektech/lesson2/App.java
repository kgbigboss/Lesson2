package com.geektech.lesson2;

import android.app.Application;

import com.geektech.lesson2.data.remote.PostApi;
import com.geektech.lesson2.data.remote.RetrofitClient;

public class App extends Application {

    private RetrofitClient client;
    public static PostApi api;

    @Override
    public void onCreate() {
        super.onCreate();
        client = new RetrofitClient();
        api = client.provieApi();
    }
}
