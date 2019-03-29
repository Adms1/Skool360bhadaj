package com.anandniketanbhadaj.skool360.skool360.Utility;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Url;

public interface webServices {

    @retrofit2.http.GET()
    Call<JsonObject> getBaseUrl(@Url String url);

}
