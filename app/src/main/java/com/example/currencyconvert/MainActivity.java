package com.example.currencyconvert;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView tryText;
    TextView cadText;
    TextView usdText;
    TextView btcText;
    TextView gbpText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tryText = findViewById(R.id.tryText);
        cadText = findViewById(R.id.cadText);
        usdText = findViewById(R.id.usdText);
        btcText = findViewById(R.id.btcText);
        gbpText = findViewById(R.id.gbpText);

    }

    public void getRates(View view){

        DownloadData downloadData = new DownloadData();

        try {

            String url = "http://data.fixer.io/api/latest?access_key=870d1a23af17e06b45c45f68b73c7828&format=1";

            downloadData.execute(url);

        }catch (Exception e){

        }

    }

    private class DownloadData extends AsyncTask<String, Void, String>{


        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            URL url;
            HttpURLConnection httpURLConnection;

            try{

                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                int data = inputStreamReader.read();

                while (data > 0){

                    char character = (char) data;
                    result += character;

                    data = inputStreamReader.read();

                }

                return result;

            }catch (Exception e){
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //System.out.println("alınan data: " + s);

            try {

                JSONObject jsonObject = new JSONObject(s);
                String base = jsonObject.getString("base");
                //System.out.println("base: " + base);

                String rates = jsonObject.getString("rates");
                //System.out.println("rates: " + rates);

                JSONObject jsonObject1 = new JSONObject(rates);

                String turkishlira = jsonObject1.getString("TRY");
                tryText.setText("TRY: " + turkishlira);

                String usd = jsonObject1.getString("USD");
                usdText.setText("USD: " + usd);

                String cad = jsonObject1.getString("CAD");
                cadText.setText("CAD: " + cad);

                String gbp = jsonObject1.getString("GBP");
                gbpText.setText("GBP: " + gbp);

                String btc = jsonObject1.getString("BTC");
                btcText.setText("BTC: " + btc);


            }catch (Exception e){

            }



        }
    }
}
