package thm.com.gr2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ResultResponse {
    @Expose
    @SerializedName("message")
    private String mMessage;

    @Expose
    @SerializedName("data")
    private List<Result> mResults;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public List<Result> getResults() {
        return mResults;
    }

    public void setResults(List<Result> results) {
        mResults = results;
    }
}
