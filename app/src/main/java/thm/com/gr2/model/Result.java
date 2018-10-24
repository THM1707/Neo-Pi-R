package thm.com.gr2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Result implements Serializable {
    @SerializedName("name")
    @Expose
    private String mName;

    @SerializedName("low")
    @Expose
    private String mLow;

    @SerializedName("medium")
    @Expose
    private String mMedium;

    @SerializedName("high")
    @Expose
    private String mHigh;

    @SerializedName("advice")
    @Expose
    private String mAdvice;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getLow() {
        return mLow;
    }

    public void setLow(String low) {
        mLow = low;
    }

    public String getMedium() {
        return mMedium;
    }

    public void setMedium(String medium) {
        mMedium = medium;
    }

    public String getHigh() {
        return mHigh;
    }

    public void setHigh(String high) {
        mHigh = high;
    }

    public String getAdvice() {
        return mAdvice;
    }

    public void setAdvice(String advice) {
        mAdvice = advice;
    }
}
