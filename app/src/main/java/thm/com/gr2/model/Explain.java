package thm.com.gr2.model;

public class Explain {
    private String mName;
    private String mContent;

    public Explain() {
    }

    public Explain(String name, String content) {
        mName = name;
        mContent = content;
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
