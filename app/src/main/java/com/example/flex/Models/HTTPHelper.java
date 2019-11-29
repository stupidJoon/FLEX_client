package com.example.flex.Models;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HTTPHelper {
    static String setCookie;
    static OkHttpClient okHttpClient = new OkHttpClient.Builder().addNetworkInterceptor(new Interceptor() {
        @NotNull
        @Override
        public Response intercept(@NotNull Chain chain) throws IOException {
            Request request = chain.request();
            if (setCookie != null) {
                request = chain.request().newBuilder().addHeader("Cookie", setCookie).build();
            }
            Response response = chain.proceed(request);
            if (response.header("set-cookie") != null) {
                setCookie = response.header("set-cookie");
            }
            return response;
        }
    }).build();

    public static void signIn(String id, String pw, Callback callback) {
        RequestBody formBody = new FormBody.Builder().add("id", id).add("pw", pw).build();
        Request request = new Request.Builder().url("http://54.180.57.73:3000/signin").post(formBody).build();
        okHttpClient.newCall(request).enqueue(callback);
    }
    public static void signUp(String id, String pw, Callback callback) {
        RequestBody formBody = new FormBody.Builder().add("id", id).add("pw", pw).build();
        Request request = new Request.Builder().url("http://54.180.57.73:3000/signup").post(formBody).build();
        okHttpClient.newCall(request).enqueue(callback);
    }
    public static void status() {
        Request request = new Request.Builder().url("http://54.180.57.73:3000/status").build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e("HTTP", "Request Status Failed Error: " + e.toString());
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.e("HTTP", "Request Status Response Body: " + response.body().string());
            }
        });
    }
    public static void addEstate(Estate estate, Callback callback) {
        RequestBody formBody = new FormBody.Builder().add("type", estate.type).add("title", estate.title).add("money", estate.money).build();
        Request request = new Request.Builder().url("http://54.180.57.73:3000/assets").post(formBody).build();
        okHttpClient.newCall(request).enqueue(callback);
    }
}
