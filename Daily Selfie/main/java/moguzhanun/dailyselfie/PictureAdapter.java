package moguzhanun.dailyselfie;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;


public class PictureAdapter extends BaseAdapter {

    private ArrayList<Pictures> list = new ArrayList<Pictures>();
    private static LayoutInflater inflater = null;
    private Context mContext;
    private Uri photoUri;
    private String photoName;
    View newView ;
    ViewHolder holder;

    public PictureAdapter(Context context){
        mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    public void addPicture(Pictures picture){

        list.add(picture);
        notifyDataSetChanged();
    }

    static class ViewHolder {
            ImageView mImageView;
            TextView mTextView;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public  void setUri (Uri uri){
        photoUri    = uri;
    }

    public void setString (String str){
        photoName = str;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        newView = convertView;
        Pictures curr = list.get(position);

        holder = new ViewHolder();
        if(null == convertView) {

            newView = inflater.inflate(R.layout.camera_layout, parent, false);

            holder.mImageView = (ImageView) newView.findViewById(R.id.image1);
            holder.mImageView.setImageURI(curr.getUri());
            holder.mTextView = (TextView) newView.findViewById(R.id.textView_name);
            holder.mTextView.setText(curr.getString());
        }
        return newView;
    }
}
