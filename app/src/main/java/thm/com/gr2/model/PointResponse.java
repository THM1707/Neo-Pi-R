package thm.com.gr2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PointResponse {
    @Expose
    @SerializedName("message")
    private String mMessage;

    @Expose
    @SerializedName("data")
    private Point mPoint;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Point getPoint() {
        return mPoint;
    }

    public void setPoint(Point point) {
        mPoint = point;
    }
}
