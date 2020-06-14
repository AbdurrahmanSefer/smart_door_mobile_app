package com.seferapp.animals_and_pepol_app;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.net.SSLCertificateSocketFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import com.google.gson.Gson;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.json.JSONArray;
import org.json.JSONObject;
import static java.util.Objects.requireNonNull;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class history_page extends AppCompatActivity {
    ProgressDialog dialog;
    ListView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_page);
        dialog = ProgressDialog.show(history_page.this, "", "Loading. Please wait...", true);
        dialog.show();
        tv=findViewById(R.id.listview);
        new arkaplan().execute("");
    }

    class arkaplan extends AsyncTask<String, String, String> {
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader br = null;
            try {
                URL url = new URL(getString(R.string.server_adress) + "api/fotoCheck/Door_history");
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
                if(s.equals("\"eror\""))
                {

                }else{

                    JSONArray jsonarray = new JSONArray(s);
                    Gson gson = new Gson();
                    List<door_hestory> liste = new ArrayList<>();



                    for (int i = 0; i < jsonarray.length(); i++) {
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        door_hestory gsonResponse = gson.fromJson(jsonobject.toString(), door_hestory.class);

                        liste.add(gsonResponse);
                    }


                    history_adapter custumAdapter = new history_adapter(requireNonNull(history_page.this), liste);
                    tv.setAdapter(custumAdapter);
                }

            } catch (Exception ex) {

                String hata = ex.toString();
            } finally {
                dialog.dismiss();
            }
        }
    }
}
