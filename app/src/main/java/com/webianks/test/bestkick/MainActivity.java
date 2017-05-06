package com.webianks.test.bestkick;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

import java.util.Vector;

public class MainActivity extends AppCompatActivity implements KickStarterAdapter.ItemClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    private static final int BEST_KICK_PROJECTS_LOADER = 200;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private KickStarterAdapter kickStarterAdapter;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        getProjects();
        showResponse(null);

        //for first 20 items
        Bundle args = new Bundle();
        args.putInt("start", 0);
        args.putInt("end", 19);

        getSupportLoaderManager().initLoader(BEST_KICK_PROJECTS_LOADER, args, this);
    }

    private void getProjects() {

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

        //List<KickProject> projectList = new ArrayList<>();
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

        //showResponse(projectList);

    }

    private void showResponse(Cursor cursor) {
        kickStarterAdapter = new KickStarterAdapter(this, cursor);
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
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = linearLayoutManager.getChildCount();
                    totalItemCount = linearLayoutManager.getItemCount();
                    pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            Log.v("...", "Last Item Wow !" + totalItemCount);

                            //restart loader to fetch more values
                            //for first 20 items
                            Bundle args = new Bundle();
                            args.putInt("start", 0);
                            args.putInt("end", totalItemCount + 19);
                            getSupportLoaderManager().restartLoader(BEST_KICK_PROJECTS_LOADER, args, MainActivity.this);

                        }
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void itemClicked(Cursor dataCursor) {

        int serial_number_index = dataCursor.getColumnIndex(KickContract.KickEntry.KICK_SL_NUMBER);
        int serial_number = dataCursor.getInt(serial_number_index);

        Intent intent = new Intent(this, DetailedActivity.class);
        intent.putExtra("sl_number", serial_number);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        int start = args.getInt("start");
        int end = args.getInt("end");

        loading = true;

        Uri projects_uri = KickContract.KickEntry.CONTENT_URI;
        String selection = KickContract.KickEntry.TABLE_NAME + "." + KickContract.KickEntry.KICK_SL_NUMBER + " >= ? AND " +
                KickContract.KickEntry.TABLE_NAME + "." + KickContract.KickEntry.KICK_SL_NUMBER + " <= ?";

        String[] selectionArgs = new String[]{String.valueOf(start), String.valueOf(end)};

        return new CursorLoader(this,
                projects_uri,
                null,
                selection,
                selectionArgs,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        if (cursor != null && cursor.getCount() > 0) {
            kickStarterAdapter.swapCursor(cursor);
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        kickStarterAdapter.swapCursor(null);
    }
}
