package moguzhanun.dailyselfie;

import android.net.Uri;

public class Pictures {

    Uri photoUri;
    String photoName;

    public Pictures(String str, Uri uri){

        photoName = str;
        photoUri = uri;
    }

    public Pictures(){

    }

    public  void setUri (Uri uri){
        photoUri    = uri;
    }

    public void setString (String str){
        photoName = str;
    }

    public Uri getUri() {
        return photoUri;
    }

    public String getString() {
        return photoName;
    }
}
