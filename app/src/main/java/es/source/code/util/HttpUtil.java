package es.source.code.util;
import android.util.Xml;

import com.google.gson.Gson;

import es.source.code.model.User;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

//okhttp3.Callback callback 是okhttp3自带的回调接口，在enqueue开启子线程，在子线程发生http请求
public class HttpUtil {
    public static  void sendRequestWithOkHttp(String URLOFLOGIN,User user,okhttp3.Callback callback){
        OkHttpClient cilent = new OkHttpClient();
        String params = new Gson().toJson(user);
        MediaType JSON = MediaType.parse("application/json;charset=utf-8");
        RequestBody requestBody = RequestBody.create(JSON,params);
        Request request = new Request.Builder().url(URLOFLOGIN).post(requestBody).build();
        cilent.newCall(request).enqueue(callback);
    }

    public static  void sendRequestWithOkHttpUseJSON(String URLOFLOGIN,okhttp3.Callback callback){
        OkHttpClient cilent = new OkHttpClient();
        Request request = new Request.Builder().url(URLOFLOGIN).build();
        cilent.newCall(request).enqueue(callback);
    }

    public static  void sendRequestWithOkHttpUseXML(String URLOFLOGIN,okhttp3.Callback callback){
        OkHttpClient cilent = new OkHttpClient();
        Request request = new Request.Builder().url(URLOFLOGIN).build();
        cilent.newCall(request).enqueue(callback);
    }
}
