package com.webianks.test.bestkick;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by R Ankit on 06-05-2017.
 */

public class DetailedActivity extends AppCompatActivity {


    private TextView titleTV;
    private TextView byTV;
    private TextView blurbTV;
    private TextView backersTV;
    private TextView pledgedTV;
    private TextView countryTV;
    private TextView locationTV;
    private TextView percentageFunded;
    private Button moreButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_layout);

        initView();
        setValues();
    }


    private void setValues() {

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
        percentageFunded = (TextView) findViewById(R.id.fundedValue);
        moreButton = (Button) findViewById(R.id.moreButton);

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


    public void readMore(View view) {

    }
}
