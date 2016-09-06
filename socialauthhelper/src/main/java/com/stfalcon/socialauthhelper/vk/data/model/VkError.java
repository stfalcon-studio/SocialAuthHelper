/*
 * Copyright 2016 stfalcon.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Created by Anton Bevza
 *
 */

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
