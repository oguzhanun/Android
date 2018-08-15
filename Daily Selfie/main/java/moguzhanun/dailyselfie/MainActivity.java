package moguzhanun.dailyselfie;

import android.app.AlarmManager;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends ListActivity {

    private static final int REQUEST_CODE = 1234;
    Context context;

    private Pictures picture;
    private PictureAdapter pictureAdapter;

    Uri photoUri;
    String photoName;

    View largePicture ;
    RelativeLayout relativeLayout ;
    LinearLayout linearLayout ;
    boolean bool;
    ListView pictureList;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getApplicationContext();

        alarmManager =(AlarmManager) getSystemService(ALARM_SERVICE);
        intent = new Intent (MainActivity.this, DailySelfieAlarm.class);
        pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),0,intent,0);

        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime()+(1000*60*60*24),
                (1000*60*60*24), pendingIntent);

        pictureAdapter = new PictureAdapter(context);

        pictureList = getListView();

        pictureList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                bool = false;

                Pictures mPicture = (Pictures) pictureAdapter.getItem(position);
                Uri mUri = mPicture.photoUri;

                pictureList.setEnabled(false);

                largePicture = getLayoutInflater().inflate(R.layout.list_layout,null);
                relativeLayout = (RelativeLayout) findViewById(R.id.relative);
                linearLayout = (LinearLayout) findViewById(R.id.linear_child);

                linearLayout.setVisibility(View.INVISIBLE);

                relativeLayout.addView(largePicture);

                ImageView image = (ImageView) largePicture.findViewById(R.id.image_db);
                image.setImageURI(mUri);
            }
        });

        setListAdapter(pictureAdapter);

        File mFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File[] fileArray = mFile.listFiles();

        if(fileArray != null){
            for(int i = 0; i<fileArray.length; i++){

                photoUri = Uri.parse(fileArray[i].getAbsolutePath());
                photoName = fileArray[i].getName();

                picture = new Pictures(photoName,photoUri);

                pictureAdapter.addPicture(picture);
            }
        }
    }

    @Override
    public void onBackPressed(){

        if(bool == false) {

            relativeLayout.removeView(largePicture);
            linearLayout.setVisibility(View.VISIBLE);

            pictureList.setEnabled(true);
            bool = true;

        } else finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.photo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.camera:

                Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                photoName = getTimeStamp();
                File photoFile = new File(file, photoName);
                photoUri= Uri.fromFile(photoFile);
                photoIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);

                if(photoIntent.resolveActivity(getPackageManager())!=null){
                    startActivityForResult(photoIntent, REQUEST_CODE);
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onActivityResult(int request_code, int result_code, Intent data){
        if(request_code == REQUEST_CODE){
            if(result_code == RESULT_OK){

                picture = new Pictures(photoName,photoUri);
                pictureAdapter.addPicture(picture);
            }
        }
    }

    private String getTimeStamp() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timeStamp = simpleDateFormat.format(new Date(System.currentTimeMillis()));
        return "IMG_"+ timeStamp + ".png";
    }
}