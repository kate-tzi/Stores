package com.one.example.katerina.stores;


import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/*** Created by cathr on 24/6/2017. ***/


public class FetchStoresTask extends AsyncTask<String,Void,ArrayList<Store>> {

    private final String LOG_TAG = FetchStoresTask.class.getSimpleName();
    private StoresAdapter storesAdapter;
    public static final String YUMMY_BASE_DOMAIN = "https://www.yummywallet.com/";

    public FetchStoresTask( StoresAdapter storesAdapter){
        this.storesAdapter = storesAdapter;
    }

    private ArrayList<Store> getStoresFromJson(String storeJsonStr) throws JSONException {
        ArrayList<Store> stores = new ArrayList<>();
        try{
            JSONArray storesArray = new JSONArray(storeJsonStr);
            for(int i=0; i<storesArray.length(); i++){
                JSONObject jsonStore = storesArray.getJSONObject(i);

                int storeId = jsonStore.getInt("id");
                String storeName = jsonStore.getString("legalName");

                JSONObject jsonCategory = jsonStore.getJSONObject("merchantCategory");
                String storeCategory = jsonCategory.getString("alternateName");

                JSONObject jsonContactPoint = jsonStore.getJSONObject("contactPoint");
                String storeAddress = jsonContactPoint.getString("streetAddress");

                JSONObject jsonAggregateRating = jsonStore.getJSONObject("aggregateRating");
                int storeRate = jsonAggregateRating.getInt("ratingValue");



                stores.add(new Store(storeId, storeName, storeCategory, storeAddress, storeRate));
            }
            Log.d(LOG_TAG, "Store Fetching Complete. " + stores.size() + "stores inserted");
            return  stores;
        }catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
            return  stores;
        }
    }

    @Override
    protected ArrayList<Store> doInBackground(String... params) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String storesJsonStr = null;

        try {
            final String YUMMY_MERCHANTS_URL = "/Merchant/index.json?%24orderby=id";

            Uri builtUri = Uri.parse(YUMMY_BASE_DOMAIN+YUMMY_MERCHANTS_URL);

            URL url = new URL(builtUri.toString());

            // Create the request to Yummy Wallet server, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            storesJsonStr = buffer.toString();
            return  getStoresFromJson(storesJsonStr);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attempting
            // to parse it.
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Store> stores) {
        if(stores.size() > 0){
            this.storesAdapter.clear();
            for(Store s : stores){
                this.storesAdapter.add(s);
            }
        }
    }
}