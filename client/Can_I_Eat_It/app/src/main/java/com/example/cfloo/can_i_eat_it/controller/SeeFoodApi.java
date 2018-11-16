package com.example.cfloo.can_i_eat_it.controller;

import android.graphics.Bitmap;

import com.example.cfloo.can_i_eat_it.model.UploadedImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SeeFoodApi {
    // Doesnt work yet
    // private static final String serverUrl = "http://18.224.175.218/";
    //private static final String serverUrl = "http://10.17.60.22/";
    private static final String serverUrl = "https://2f945ccc.ngrok.io/";

    public UploadedImage detectImage(Bitmap bitmap) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);

        OkHttpClient client = new OkHttpClient();
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
            return jsonToImage(new JSONObject(response.body().string()));
        } catch (IOException|JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public UploadedImage jsonToImage(JSONObject o) {
        try {
            return new UploadedImage(o.getInt("id"), o.getLong("date"), (float)o.getDouble("result"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
