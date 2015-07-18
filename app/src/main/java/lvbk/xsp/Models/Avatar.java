package lvbk.xsp.Models;

/**
 * Created by lvbk on 14/06/2015.
 */
public class Avatar {
    private String uname;
    private String avatarFile;

    public Avatar(String avatarFile, String uname){
        setAvatarFile(avatarFile);
        setUname(uname);
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getAvatarFile() {
        return avatarFile;
    }

    public void setAvatarFile(String avatarFile) {
        this.avatarFile = avatarFile;
    }
}
