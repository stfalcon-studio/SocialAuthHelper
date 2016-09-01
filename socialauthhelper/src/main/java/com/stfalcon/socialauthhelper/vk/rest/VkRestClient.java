package com.stfalcon.socialauthhelper.vk.rest;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stfalcon.socialauthhelper.vk.data.preferences.VkPreferences;
import com.stfalcon.socialauthhelper.vk.utils.VkUtilities;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by troy379 on 16.01.16.
 */
public class VkRestClient implements Interceptor {

    private Retrofit retrofit;
    private Object service;
    private Context context;

    public VkRestClient(Context context) {
        this.context = context;

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(this)
                .addInterceptor(ERROR_CODE_INTERCEPTOR)
                .build();

        Gson gson = new GsonBuilder()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(VkConfig.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request original = chain.request();

        HttpUrl.Builder urlBuilder = original.url().newBuilder();

        String accessToken =
                VkPreferences.getManager().getVkAccessToken(context);
        urlBuilder.addQueryParameter(
                VkUtilities.VK_FILED_ACCESS_TOKEN,
                accessToken == null ? "null" : accessToken);

        urlBuilder.addQueryParameter(
                "v", VkConfig.API_VERSION);

        Request request = original.newBuilder()
                .url(urlBuilder.build())
                .build();

        return chain.proceed(request);
    }

    private static final Interceptor ERROR_CODE_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            Response response = chain.proceed(request);

            String bodyString = response.body().string();
            int code = bodyString.contains("error_code") ? 401 : response.code(); //add others codes

            return response.newBuilder()
                    .body(ResponseBody.create(response.body().contentType(), bodyString))
                    .code(code)
                    .build();
        }
    };

    @SuppressWarnings("unchecked")
    public <T> T getService(Class<T> serviceClass) {
        if (service == null || !serviceClass.isInstance(service))
            service = retrofit.create(serviceClass);
        return (T) service;
    }
}
