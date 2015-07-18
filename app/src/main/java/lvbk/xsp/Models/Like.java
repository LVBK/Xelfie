package lvbk.xsp.Models;

/**
 * Created by lvbk on 14/06/2015.
 */
public class Like {
    private String uname;
    private String keyImage;

    public Like(String uname, String keyImage) {
        setUname(uname);
        setKeyImage(keyImage);
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getKeyImage() {
        return keyImage;
    }

    public void setKeyImage(String keyImage) {
        this.keyImage = keyImage;
    }
}
