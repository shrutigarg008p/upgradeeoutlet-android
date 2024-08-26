package com.eoutlet.Eoutlet.intrface;

import retrofit2.Response;

/**
 * Created by indianrenters on 12/1/16.
 */

public interface ParentDialogsListener {

    public void showProgressDialog();
    public void hideProgressDialog();
    public <T> void showMessageDialog(Response<T> response, Throwable t, String message);
    public boolean isProgressBarRunning();
    public void showMissingPermissionDialog();
}
