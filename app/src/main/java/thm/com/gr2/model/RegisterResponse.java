package thm.com.gr2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterResponse {
    @Expose
    @SerializedName("auth_token")
    private String mAuthToken;
    @Expose
    @SerializedName("message")
    private String mMessage;

    public String getAuthToken() {
        return mAuthToken;
    }

    public void setAuthToken(String authToken) {
        mAuthToken = authToken;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }
}
