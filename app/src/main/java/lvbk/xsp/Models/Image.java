package lvbk.xsp.Models;

import java.io.Serializable;

/**
 * Created by lvbk on 14/06/2015.
 */
public class Image implements Serializable{
    private String imageFile;
    private String uname;
    private String key;

    public void setImageFile(String imageFile){
        this.imageFile = imageFile;
    }

    public String getImageFile(){
        return imageFile;
    }

    public void setUname(String uname){ this.uname = uname;}

    public String getUname(){ return uname; }

    public String getKey() { return key; }

    public void setKey(String key) { this.key = key; }

    public Image(String imagaFile,String uname){
        setImageFile(imagaFile);
        setUname(uname);
    }

    public Image(String imageFile, String uname, String key){
        this(imageFile,uname);
        setKey(key);
    }
}
