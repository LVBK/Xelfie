package lvbk.xsp.Models;

/**
 * Created by lvbk on 14/06/2015.
 */
public class Comment {
    private String uname;
    private String content;
    private String keyImage;

    public Comment(String uname, String content, String keyImage) {
        setUname(uname);
        setContent(content);
        setKeyImage(keyImage);
    }
    public Comment(String uname, String content) {
        setUname(uname);
        setContent(content);
    }

    public String getKeyImage() {
        return keyImage;
    }

    public void setKeyImage(String keyImage) {
        this.keyImage = keyImage;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
