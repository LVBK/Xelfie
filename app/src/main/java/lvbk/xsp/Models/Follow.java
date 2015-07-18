package lvbk.xsp.Models;

import java.io.Serializable;

/**
 * Created by lvbk on 14/06/2015.
 */
public class Follow implements Serializable {
    private String uname;
    private String unameFollow;
    private String key;
    public Follow(String uname, String unameFollow){
        setUname(uname);
        setUnameFollow(unameFollow);
    }

    public Follow(String uname, String unameFollow, String key){
        this(uname,unameFollow);
        setKey(key);
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUnameFollow() {
        return unameFollow;
    }

    public void setUnameFollow(String unameFollow) {
        this.unameFollow = unameFollow;
    }

    public String getKey() { return key; }

    public void setKey(String key) { this.key = key; }
}
