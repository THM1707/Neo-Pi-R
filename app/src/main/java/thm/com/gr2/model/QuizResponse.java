package thm.com.gr2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class QuizResponse {
    @Expose
    @SerializedName("message")
    private String mMessage;

    @Expose
    @SerializedName("data")
    private List<Quiz> mQuizzes;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public List<Quiz> getQuizzes() {
        return mQuizzes;
    }

    public void setQuizzes(List<Quiz> quizzes) {
        mQuizzes = quizzes;
    }
}
