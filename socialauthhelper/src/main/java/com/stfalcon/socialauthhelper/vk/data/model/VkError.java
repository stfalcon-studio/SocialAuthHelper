package com.stfalcon.socialauthhelper.vk.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by troy379 on 17.01.16.
 */
public class VkError {

    @SerializedName("response")
    private Error error;

    class Error {

        @SerializedName("error_code")
        private int code;

        @SerializedName("error_msg")
        private String message;
    }

    public int getCode() {
        return error.code;
    }

    public String getMessage() {
        return error.message;
    }

    public static VkError parse(Response<?> response, Retrofit retrofit) {
        Converter<ResponseBody, VkError> converter =
                retrofit.responseBodyConverter(VkError.class, new Annotation[0]);

        try { return converter.convert(response.errorBody());
        } catch (IOException e) { return new VkError(); }
    }
}
