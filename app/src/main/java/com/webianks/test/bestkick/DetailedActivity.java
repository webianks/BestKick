package com.webianks.test.bestkick;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.webianks.test.bestkick.database.KickContract;

/**
 * Created by R Ankit on 06-05-2017.
 */

public class DetailedActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {


    private static final int PROJECT_DETAILS_LOADER = 123;
    private TextView titleTV;
    private TextView byTV;
    private TextView blurbTV;
    private TextView backersTV;
    private TextView pledgedTV;
    private TextView countryTV;
    private TextView locationTV;
    private TextView percentageFundedTV;
    private Button moreButton;
    int sl_number;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_layout);
        initView();
    }


    private void initView() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(null);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        titleTV = (TextView) findViewById(R.id.title);
        byTV = (TextView) findViewById(R.id.by);
        blurbTV = (TextView) findViewById(R.id.blurb);
        backersTV = (TextView) findViewById(R.id.backersValue);
        pledgedTV = (TextView) findViewById(R.id.amountValue);
        countryTV = (TextView) findViewById(R.id.countryValue);
        locationTV = (TextView) findViewById(R.id.locationValue);
        percentageFundedTV = (TextView) findViewById(R.id.fundedValue);
        moreButton = (Button) findViewById(R.id.moreButton);

        sl_number = getIntent().getIntExtra("sl_number", 0);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.second_in, R.anim.second_out);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.second_in, R.anim.second_out);
    }


    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().initLoader(PROJECT_DETAILS_LOADER, null, this);
    }

    public void readMore(View view) {

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return new CursorLoader(this,
                KickContract.KickEntry.buildProjectWithSerial(sl_number),
                null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if (data != null && data.moveToFirst()) {

            int title_index = data.getColumnIndex(KickContract.KickEntry.KICK_TITLE);
            int backers_index = data.getColumnIndex(KickContract.KickEntry.KICK_BACKERS);
            int endtime_index = data.getColumnIndex(KickContract.KickEntry.KICK_END_TIME);
            int pleaged_index = data.getColumnIndex(KickContract.KickEntry.KICK_AMT_PLEDGED);
            int blurb_index = data.getColumnIndex(KickContract.KickEntry.KICK_BLURB);
            int by_index = data.getColumnIndex(KickContract.KickEntry.KICK_BY);
            int country_index = data.getColumnIndex(KickContract.KickEntry.KICK_COUNTRY);
            int currency_index = data.getColumnIndex(KickContract.KickEntry.KICK_CURRENCY);
            int location_index = data.getColumnIndex(KickContract.KickEntry.KICK_LOCATION);
            int state_index = data.getColumnIndex(KickContract.KickEntry.KICK_STATE);
            int type_index = data.getColumnIndex(KickContract.KickEntry.KICK_TYPE);
            int url_index = data.getColumnIndex(KickContract.KickEntry.KICK_URL);
            int percentage_funded_index = data.getColumnIndex(KickContract.KickEntry.KICK_PERCENTAGE_FUNDED);

            String title = data.getString(title_index);
            String backers = data.getString(backers_index);
            String endTime = data.getString(endtime_index);
            int pledged = data.getInt(pleaged_index);

            String blurb = data.getString(blurb_index);
            String by = data.getString(by_index);
            String country = data.getString(country_index);
            String currency = data.getString(currency_index);
            String location = data.getString(location_index);
            String state = data.getString(state_index);
            String type = data.getString(type_index);
            String url = data.getString(url_index);
            int percentageFunded = data.getInt(percentage_funded_index);

            titleTV.setText(title);
            byTV.setText(by);
            blurbTV.setText(blurb);
            backersTV.setText(backers);
            pledgedTV.setText(String.valueOf(pledged));
            countryTV.setText(country);
            locationTV.setText(location);
            percentageFundedTV.setText(String.valueOf(percentageFunded));

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

}
