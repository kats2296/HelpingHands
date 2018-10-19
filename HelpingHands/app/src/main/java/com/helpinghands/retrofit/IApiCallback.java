package com.helpinghands.retrofit;

/**
 * Interface is used for common purpose in Application.
 *
 * @author and15031989
 */
public interface IApiCallback {
    /**
     * Method for getting the type and data.
     *
     * @param data Actual data
     */
    void onSuccess(Object type, Object data);

    /**
     * Failure Reason
     * @param data
     */
    void onFailure(Object data);

}
