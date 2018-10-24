package thm.com.gr2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Quiz implements Serializable {
    @Expose
    @SerializedName("content")
    private String mContent;

    @Expose
    @SerializedName("quiz_type")
    private String mQuizType;

    @Expose
    @SerializedName("mode")
    private int mMode;

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public String getQuizType() {
        return mQuizType;
    }

    public void setQuizType(String quizType) {
        mQuizType = quizType;
    }

    public int getMode() {
        return mMode;
    }

    public void setMode(int mode) {
        mMode = mode;
    }
}
