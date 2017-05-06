package com.webianks.test.bestkick;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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

public class MainActivity extends AppCompatActivity {


    private RecyclerView recyclerView;

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
                String noOfDaysToGo = jsonProject.getString("end.time");
                int pleadged = jsonProject.getInt("amt.pledged");

                KickProject kickProject = new KickProject();
                kickProject.setTitle(title);
                kickProject.setBackers(backers);
                kickProject.setNoOfDaysToGo(noOfDaysToGo);
                kickProject.setPleadge(String.valueOf(pleadged));

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
    }


    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }
}
