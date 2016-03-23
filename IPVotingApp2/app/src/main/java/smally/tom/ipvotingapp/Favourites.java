package smally.tom.ipvotingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Favourites extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        loadFavouriteBills();
        refreshRadioGroups();
        setTitle("Favourites");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.favourites, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            super.onBackPressed();
        } else if (id == R.id.nav_categories) {
            Intent intent = new Intent(Favourites.this, Category.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivityForResult(intent, 1);
        } else if (id == R.id.nav_favourites) {
        //} else if (id == R.id.nav_recommended) {
        } else if (id == R.id.nav_share) {
        } else if (id == R.id.nav_send) {
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void loadFavouriteBills()
    {
        for(int x = 0; x < AllBills.size(); x++)
        {
            ABill newBill = null;
            newBill = AllBills.get(x);//Bills should be referenced by refNum - 1

            TextView textView1 = null;
            ImageView imageView3 = (ImageView) findViewById(R.id.imageView3);
            RelativeLayout layout = (RelativeLayout) findViewById(R.id.relativeLayout3);

            /** THIS LOADS ONLY FAV BILLS INTO REQUIRED SPACES **/
            if(newBill.billFavourited == Boolean.FALSE){
                continue;
            }
            switch (x) {
                case 0:
                    textView1 = (TextView) findViewById(R.id.textView3);
                    imageView3 = (ImageView) findViewById(R.id.imageView3);
                    layout = (RelativeLayout) findViewById(R.id.relativeLayout3);
                    layout.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    textView1 = (TextView) findViewById(R.id.textView7);
                    imageView3 = (ImageView) findViewById(R.id.imageView7);
                    layout = (RelativeLayout) findViewById(R.id.relativeLayout7);
                    layout.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    textView1 = (TextView) findViewById(R.id.textView6);
                    imageView3 = (ImageView) findViewById(R.id.imageView6);
                    layout = (RelativeLayout) findViewById(R.id.relativeLayout6);
                    layout.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    textView1 = (TextView) findViewById(R.id.textView8);
                    imageView3 = (ImageView) findViewById(R.id.imageView8);
                    layout = (RelativeLayout) findViewById(R.id.relativeLayout8);
                    layout.setVisibility(View.VISIBLE);
                    break;
                case 4:
                    textView1 = (TextView) findViewById(R.id.textView5);
                    imageView3 = (ImageView) findViewById(R.id.imageView5);
                    layout = (RelativeLayout) findViewById(R.id.relativeLayout5);
                    layout.setVisibility(View.VISIBLE);
                    break;
            }
                imageView3.setImageDrawable(newBill.billImg);
                textView1.setText(newBill.billName);
        }
    }
    public void onRadioButtonClicked(View view)
    {
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.favouriteRelative);
        switch(view.getId()) {
            case R.id.radio_behind:
                Snackbar.make(layout, "Added to behind", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                AllBills.get(0).billStance = "behind";
                break;
            case R.id.radio_none:
                Snackbar.make(layout, "Remove bill", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                AllBills.get(0).billStance = "none";
                break;
            case R.id.radio_against:
                Snackbar.make(layout, "Added to against", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                AllBills.get(0).billStance = "against";
                break;
        }
    }
    public void refreshRadioGroups()
    {
        ABill newBill = AllBills.get(0);
        if(newBill.billStance.equals("behind")){
            RadioButton radioButton = (RadioButton) findViewById(R.id.radio_behind);
            radioButton.setChecked(true);
        }else if(newBill.billStance.equals("none")){
            RadioButton radioButton = (RadioButton) findViewById(R.id.radio_none);
            radioButton.setChecked(true);
        }else if(newBill.billStance.equals("against")){
            RadioButton radioButton = (RadioButton) findViewById(R.id.radio_against);
            radioButton.setChecked(true);
        }
    }
}
