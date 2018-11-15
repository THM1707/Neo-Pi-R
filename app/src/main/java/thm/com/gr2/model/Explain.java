package thm.com.gr2.model;

import java.io.Serializable;

public class Explain implements Serializable {
    private String mName;
    private String mContent;
    private String mRate;

    public Explain() {
    }

    public Explain(String name, String content, String rate) {
        mName = name;
        mContent = content;
        mRate = rate;
    }

    public String getRate() {
        return mRate;
    }

    public void setRate(String rate) {
        mRate = rate;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }
}
