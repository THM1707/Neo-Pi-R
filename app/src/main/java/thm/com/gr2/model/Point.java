package thm.com.gr2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Point {
    @Expose
    @SerializedName("p_a")
    int mPointA;
    @Expose
    @SerializedName("p_c")
    int mPointC;
    @Expose
    @SerializedName("p_o")
    int mPointO;
    @Expose
    @SerializedName("p_n")
    int mPointN;
    @Expose
    @SerializedName("p_e")
    int mPointE;

    public int getPointA() {
        return mPointA;
    }

    public void setPointA(int pointA) {
        mPointA = pointA;
    }

    public int getPointC() {
        return mPointC;
    }

    public void setPointC(int pointC) {
        mPointC = pointC;
    }

    public int getPointO() {
        return mPointO;
    }

    public void setPointO(int pointO) {
        mPointO = pointO;
    }

    public int getPointN() {
        return mPointN;
    }

    public void setPointN(int pointN) {
        mPointN = pointN;
    }

    public int getPointE() {
        return mPointE;
    }

    public void setPointE(int pointE) {
        mPointE = pointE;
    }
}
