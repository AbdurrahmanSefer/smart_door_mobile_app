package com.seferapp.animals_and_pepol_app;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.SSLCertificateSocketFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.request.target.Target;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import javax.net.ssl.HttpsURLConnection;
public class MainActivity extends AppCompatActivity {
    //2 person in the door is wating
    ProgressDialog dialog;
    ImageView person_Pohto;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();



                    }
                });

        dialog = ProgressDialog.show(MainActivity.this, "", "Loading. Please wait...", true);
        dialog.show();
        person_Pohto = findViewById(R.id.person_iv);
        linearLayout = findViewById(R.id.person_in_the_door);
        new arkaplan().execute("");
    }
    class arkaplan extends AsyncTask<String, String, String> {
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader br = null;
            try {
                URL url = new URL(getString(R.string.server_adress) + "api/fotoCheck/Door_person_photo");
                connection = (HttpURLConnection) url.openConnection();
                if (connection instanceof HttpsURLConnection) {
                    HttpsURLConnection httpsConn = (HttpsURLConnection) connection;
                    httpsConn.setSSLSocketFactory(SSLCertificateSocketFactory.getInsecure(0, null));
                    httpsConn.setHostnameVerifier(new AllowAllHostnameVerifier());
                }
                connection.connect();
                InputStream is = connection.getInputStream();
                br = new BufferedReader(new InputStreamReader(is));
                String satir;
                String dosya = "";
                while ((satir = br.readLine()) != null) {
                    dosya += satir;
                }
                return dosya;
            } catch (Exception ex) {
                return ex.toString();
            }
        }

        protected void onPostExecute(String s) {

            try {
              s=  s.replace("\"","");
                if (s.length() > 20) {

                    Glide.with(getApplicationContext()).load(s).listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }
                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                            .dontAnimate().into(person_Pohto);
                    linearLayout.setVisibility(View.VISIBLE);

                } else {
                    linearLayout.setVisibility(View.GONE);
                }
            } catch (Exception ex) {

                String hata = ex.toString();
            } finally {
                dialog.dismiss();
            }
        }
    }
    public void open(View v) {
        new door().execute("1");
    }
    public void close(View v) {
        new door().execute("0");
    }
    class door extends AsyncTask<String, String, String> {
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader br = null;
            try {
                URL url = new URL(getString(R.string.server_adress) + "api/fotoCheck/manager_request");
                connection = (HttpURLConnection) url.openConnection();
               /* if (connection instanceof HttpsURLConnection) {
                    HttpsURLConnection httpsConn = (HttpsURLConnection) connection;
                    httpsConn.setSSLSocketFactory(SSLCertificateSocketFactory.getInsecure(0, null));
                    httpsConn.setHostnameVerifier(new AllowAllHostnameVerifier());
                }*/
                connection.setReadTimeout(50000);
                connection.setConnectTimeout(50000);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                connection.setDoInput(true);
                connection.setDoOutput(true);
                kapi_durumu model = new kapi_durumu(3,0,params[0]);
                DataOutputStream localDataOutputStream = new DataOutputStream(connection.getOutputStream());
                Gson gson = new Gson();
                String json = gson.toJson(model);
                localDataOutputStream.writeBytes(json);
                localDataOutputStream.flush();
                localDataOutputStream.close();
                connection.connect();
                InputStream is = connection.getInputStream();
                br = new BufferedReader(new InputStreamReader(is));
                String satir;
                String dosya = "";
                while ((satir = br.readLine()) != null) {
                    dosya += satir;
                }
                return dosya;
            } catch (Exception ex){
                ex.printStackTrace();
                return ex.toString();
            }
        }
        protected void onPostExecute(String s) {
            try {
                linearLayout.setVisibility(View.GONE);
            } catch (Exception ex) {


            } finally {
                dialog.dismiss();
            }
        }
    }



    public void refresh_button(View v)
    {
        try{
            dialog.show();
            new arkaplan().execute("");
        }catch (Exception ex)
        {

        }
    }

    public void history_button(View v)
    {
        try{
            Intent intent=new Intent(getApplicationContext(),history_page.class);
            startActivity(intent);
        }catch (Exception ex)
        {

        }

    }


}
