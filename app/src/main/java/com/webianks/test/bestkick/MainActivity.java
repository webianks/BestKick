package com.webianks.test.bestkick;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.webianks.test.bestkick.database.KickContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class MainActivity extends AppCompatActivity implements KickStarterAdapter.ItemClickListener {


    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        getSetProjects();
    }

    private void getSetProjects() {

        VolleySingleton volleySingleton = VolleySingleton.getInstance();
        RequestQueue requestQueue = volleySingleton.getRequestQueue();

        String BASE_URL = "http://starlord.hackerearth.com/kickstarter";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, BASE_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        processResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }


    private void processResponse(JSONArray response) {

        List<KickProject> projectList = new ArrayList<>();
        Vector<ContentValues> cVVector = new Vector<ContentValues>(response.length());

        for (int i = 0; i < response.length(); i++) {

            try {

                JSONObject jsonProject = response.getJSONObject(i);

                String title = jsonProject.getString("title");
                String backers = jsonProject.getString("num.backers");
                String endTime = jsonProject.getString("end.time");
                int pledged = jsonProject.getInt("amt.pledged");

                String blurb = jsonProject.getString("blurb");
                String by = jsonProject.getString("by");
                String country = jsonProject.getString("country");
                String currency = jsonProject.getString("currency");
                String location = jsonProject.getString("location");
                String state = jsonProject.getString("state");
                String type = jsonProject.getString("type");
                String url = jsonProject.getString("url");
                int percentageFunded = jsonProject.getInt("percentage.funded");
                int serialNumber = jsonProject.getInt("s.no");

                KickProject kickProject = new KickProject();

                kickProject.setTitle(title);
                kickProject.setBackers(backers);
                kickProject.setEnd_time(endTime);
                kickProject.setPledge(String.valueOf(pledged));

                kickProject.setBlurb(blurb);
                kickProject.setBy(by);
                kickProject.setCountry(country);
                kickProject.setCurrency(currency);
                kickProject.setLocation(location);
                kickProject.setState(state);
                kickProject.setType(type);
                kickProject.setUrl(url);
                kickProject.setPercentageFunded(percentageFunded);
                kickProject.setSerialNumber(serialNumber);

                projectList.add(kickProject);

                ContentValues kickValues = new ContentValues();

                kickValues.put(KickContract.KickEntry.KICK_SL_NUMBER, serialNumber);
                kickValues.put(KickContract.KickEntry.KICK_AMT_PLEDGED, pledged);
                kickValues.put(KickContract.KickEntry.KICK_BLURB, blurb);
                kickValues.put(KickContract.KickEntry.KICK_BY, by);
                kickValues.put(KickContract.KickEntry.KICK_COUNTRY, country);
                kickValues.put(KickContract.KickEntry.KICK_CURRENCY, currency);
                kickValues.put(KickContract.KickEntry.KICK_END_TIME, endTime);
                kickValues.put(KickContract.KickEntry.KICK_LOCATION, location);
                kickValues.put(KickContract.KickEntry.KICK_PERCENTAGE_FUNDED, percentageFunded);
                kickValues.put(KickContract.KickEntry.KICK_BACKERS, backers);
                kickValues.put(KickContract.KickEntry.KICK_STATE, state);
                kickValues.put(KickContract.KickEntry.KICK_TITLE, title);
                kickValues.put(KickContract.KickEntry.KICK_TYPE, type);
                kickValues.put(KickContract.KickEntry.KICK_URL, url);

                cVVector.add(kickValues);


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        int inserted = 0;
        // add to database
        if (cVVector.size() > 0) {
            ContentValues[] cvArray = new ContentValues[cVVector.size()];
            cVVector.toArray(cvArray);
            getContentResolver().delete(KickContract.KickEntry.CONTENT_URI, null, null);
            getContentResolver().bulkInsert(KickContract.KickEntry.CONTENT_URI, cvArray);
        }

        showResponse(projectList);
    }

    private void showResponse(List<KickProject> projectList) {
        KickStarterAdapter kickStarterAdapter = new KickStarterAdapter(this, projectList);
        recyclerView.setAdapter(kickStarterAdapter);
        kickStarterAdapter.setItemClickListener(this);
        progressBar.setVisibility(View.GONE);
    }


    private void initView() {

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(null);

        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void itemClicked() {

        Intent intent = new Intent(this, DetailedActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }
}
