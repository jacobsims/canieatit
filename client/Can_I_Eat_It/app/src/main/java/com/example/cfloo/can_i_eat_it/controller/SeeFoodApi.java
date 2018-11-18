package com.example.cfloo.can_i_eat_it.controller;

import android.graphics.Bitmap;

import com.example.cfloo.can_i_eat_it.model.UploadedImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SeeFoodApi {
    private static final String serverUrl = "http://18.224.175.218:2000/";

    private OkHttpClient client;

    public SeeFoodApi() {
        client = new OkHttpClient();
    }

    public UploadedImage detectImage(Bitmap bitmap) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);

        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", "image.png", RequestBody.create(MediaType.parse("image/png"), os.toByteArray()))
                .build();
        Request request = new Request.Builder()
                .url(serverUrl + "upload")
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.body() == null) throw new IOException("Empty response");
            return jsonToImage(new JSONObject(response.body().string()));
        } catch (IOException|JSONException e) {
            e.printStackTrace();
            throw new IOException("could not use API", e);
        }
    }

    public UploadedImage jsonToImage(JSONObject o) throws JSONException {
        return new UploadedImage(o.getInt("id"), o.getLong("date"), (float)o.getDouble("result"));
    }

    public List<UploadedImage> getHistoryList() throws IOException {
        Request request = new Request.Builder()
                .url(serverUrl + "list")
                .get()
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.body() == null) throw new IOException("Empty response");
            JSONArray arr = new JSONArray(response.body().string());
            List<UploadedImage> data = new ArrayList<>(arr.length());
            for (int i=0; i<arr.length(); i++) {
                data.add(jsonToImage(arr.getJSONObject(i)));
            }
            return data;
        } catch (IOException|JSONException e) {
            e.printStackTrace();
            throw new IOException("could not use API", e);
        }
    }


    public static String imageUrl(int imageId) {
        return serverUrl + "image/" + imageId + ".png";
    }

}
