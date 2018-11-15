package com.example.dylan.contactcardapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String URL_DATA = "https://randomuser.me/api/?results=50";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getData();
    }
    private void getData() {
        getDataOperation getDataOperation = new getDataOperation(this);
        getDataOperation.execute();
//        final ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Loading people...");
//        progressDialog.show();
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET,
//                URL_DATA, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                progressDialog.dismiss();
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    JSONArray array = jsonObject.getJSONArray("results");
//                    for (int i = 0; i < array.length(); i++){
//                        JSONObject jo = array.getJSONObject(i);
//                        String title = jo.getJSONObject("name").getString("title");
//                        String first = jo.getJSONObject("name").getString("first");
//                        String last = jo.getJSONObject("name").getString("last");
//                        String thumbNail = jo.getJSONObject("picture").getString("thumbnail");
//                        String medium = jo.getJSONObject("picture").getString("medium");
//                        Person person = new Person(title, first, last, thumbNail, medium);
//                        people.add(person);
//                    }
//                    adapter = new PersonAdapter(people, getApplicationContext());
//                    recyclerView.setAdapter(adapter);
//                } catch (JSONException e) {
//
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(MainActivity.this, "Error" + error.toString(), Toast.LENGTH_SHORT).show();
//
//            }
//        });
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
    }
    private class getDataOperation extends AsyncTask<Void, Void, Void> {
        private List<Person> asyncPersonList;
        private final WeakReference<Activity> mainActivity;

        public getDataOperation(Activity mainActivity) {
            this.mainActivity = new WeakReference<>(mainActivity);
            this.asyncPersonList = new ArrayList<>();
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getApplicationContext(), "The background thread is going to retrieve data separate from the UI thread", Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getApplicationContext(), "The background thread has retrieved data separate from the UI thread.", Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            Toast.makeText(getApplicationContext(), "The background is retrieving data separate from the UI thread.", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... people) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("results");
                    for (int i = 0; i < array.length(); i++){
                        JSONObject jo = array.getJSONObject(i);
                        String title = jo.getJSONObject("name").getString("title");
                        String first = jo.getJSONObject("name").getString("first");
                        String last = jo.getJSONObject("name").getString("last");
                        String thumbNail = jo.getJSONObject("picture").getString("thumbnail");
                        String medium = jo.getJSONObject("picture").getString("medium");
                        Person person = new Person(title, first, last, thumbNail, medium);
                        asyncPersonList.add(person);
                    }
                    adapter = new PersonAdapter(asyncPersonList, getApplicationContext());
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error" + error.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(mainActivity.get());
        requestQueue.add(stringRequest);
            return null;
        }
    }
}

