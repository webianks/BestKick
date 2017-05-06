package com.webianks.test.bestkick;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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


            } catch (JSONException e) {
                e.printStackTrace();
            }

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
    public void itemClicked() {

        Intent intent = new Intent(this, DetailedActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }
}
