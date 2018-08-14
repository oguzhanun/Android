package org.bahriyeenstitusu.denizcilikterimleri10;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.LinkedHashMap;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private EditText terimTR;

    private TextView aciklama ;

    private Button araTr;

    private String aramaSozcugu,json;

    private ScrollView skrol;

    private LinearLayout ly;

    private JSONObject jsonObject, object;

    private JSONArray jsonArray, denizcilik_terimleri;

    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowHomeEnabled(true);

        actionBar.setIcon(R.mipmap.logo);

        terimTR = (EditText) findViewById(R.id.trgiris);

        aciklama = (TextView) findViewById(R.id.editTextTerimAciklama);

        araTr = (Button) findViewById(R.id.buttontrara);

        skrol = (ScrollView) findViewById(R.id.skrol);

        ly = (LinearLayout) findViewById(R.id.skrol_layout);

        donat();

        reklamiyukle();

     }

    private void reklamiyukle() {

        adView = new AdView(this);

        adView.setAdSize(AdSize.BANNER);

        adView.setAdUnitId(getString(R.string.reklam_kimligi));

        LinearLayout layout;

        layout = (LinearLayout) findViewById(R.id.reklam);

        layout.addView(adView);

        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();

        adView.loadAd(adRequest);
    }

    public void donat () {

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        try {

            InputStream is = getResources().openRawResource(R.raw.denizcilik_terimleri);

            int n = is.available();

            byte[] buffer = new byte[n];

            while (is.read(buffer) != -1) {
            }

            is.close();

            json = new String(buffer, "UTF-8");

            jsonObject = new JSONObject(json);

            denizcilik_terimleri = jsonObject.getJSONArray("denizcilikterimleri");

            for (int i = 0; i < 1172; i++) {

                final int z = i;

                object = denizcilik_terimleri.getJSONObject(i);

                final String str = object.names().getString(0);

                LinearLayout layout2 = new LinearLayout(this);

                layout2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                        LinearLayoutCompat.LayoutParams.WRAP_CONTENT));

                View item = inflater.inflate(R.layout.sozcuk, null, false);

                final TextView tx = item.findViewById(R.id.sozcukX);

                tx.setText(str);

                layout2.addView(item);

                ly.addView(layout2);

                tx.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        try {
                            aciklama.setText(denizcilik_terimleri.getJSONObject(z).getString(str));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        terimTR.setText(str);
                    }
                });
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onClickTRTerimAra(View view) throws IOException {

        aramaSozcugu = ((EditText) findViewById(R.id.trgiris)).getText().toString();

        newSyncTask task = new newSyncTask();

        task.execute(aramaSozcugu);

    }

    public class newSyncTask extends AsyncTask <String, Void, Integer > {

        @Override
        protected Integer doInBackground(String... aramaSozcugu) {

            try {

                for (int i = 0; i<1172; i++) {

                    if (aramaSozcugu[0].equals(denizcilik_terimleri.getJSONObject(i).names().getString(0)))
                    {
                        return i;
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return -1;
        }

        @Override
        protected void onPostExecute(Integer i){

            if(i==-1) {

                aciklama.setText("");

                Toast.makeText(getApplicationContext(), "Malesef Aradığınız Terim Bulunamamıştır!", Toast.LENGTH_SHORT).show();

            } else {

                try {

                    aciklama.setText(denizcilik_terimleri.getJSONObject(i).getString(denizcilik_terimleri.getJSONObject(i).names().getString(0)));

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        }
    }

}
