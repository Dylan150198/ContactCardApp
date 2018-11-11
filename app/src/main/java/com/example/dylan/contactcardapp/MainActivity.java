package com.example.dylan.contactcardapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String URL_DATA = "https://randomuser.me/api/?results=50";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Person> people;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        people = new ArrayList<>();
        getData();
    }

    private void getData() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading people...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
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
                        people.add(person);
                    }
                    adapter = new PersonAdapter(people, getApplicationContext());
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
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}