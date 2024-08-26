package com.eoutlet.Eoutlet.api;

import retrofit2.Response;

public interface ApiResponse {
    void onSuccess(Response res, int requestCode);

    void onFailure(Throwable res, int requestCode);

    void onSuccessJson(Object res, int requestCode);
}

