package thm.com.gr2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("message")
    @Expose
    private String mMessage;

    @SerializedName("auth_token")
    @Expose
    private String mAuthToken;

    @SerializedName("user")
    @Expose
    private User mUser;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public String getAuthToken() {
        return mAuthToken;
    }

    public void setAuthToken(String authToken) {
        mAuthToken = authToken;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public class User {

        @SerializedName("gender")
        @Expose
        private int mGender;

        @SerializedName("name")
        @Expose
        private String mName;

        public int getGender() {
            return mGender;
        }

        public void setGender(int gender) {
            mGender = gender;
        }

        public String getName() {
            return mName;
        }

        public void setName(String name) {
            mName = name;
        }
    }
}
