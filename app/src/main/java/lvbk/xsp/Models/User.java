package lvbk.xsp.Models;

import java.io.Serializable;

/**
 * Created by lvbk on 12/06/2015.
 */
public class User implements Serializable {
    private String uname;
    private String password;

    public User(String uname, String password){
        setUname(uname);
        setPassword(password);
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
