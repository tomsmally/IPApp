package smally.tom.ipvotingapp;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.media.Image;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.Display;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{
    public final static String EXTRA_MESSAGE = "smally.tom.ipvotingapp.MESSAGE";
    //public ArrayList<Integer> favBills = new ArrayList<Integer>();
    //public final ArrayList<ABill> AllBills = new ArrayList<ABill>();
    public static final ArrayList<ABill> AllBills = new ArrayList<ABill>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        /**
         * THIS IS DONE TO STOP RE-DRAWING MAIN AND DELETING THE ALLBILLS ARRAYLIST    ------
         */
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(prefs.getBoolean("firstTime", false)) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
            if (AllBills.size() == 0) {
                loadBillsIntoArrayList();
            }
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            refreshRadioGroups();
            loadBillsIntoHomePage();
            setTitle("Home");

        }
        /**
         * ------------------    END    ---------------------------
         */

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        String [] sortArray = new String[2];
        sortArray[0] = "Popularity";
        sortArray[1] = "Newest";
        Spinner spin1 = (Spinner) findViewById(R.id.sortSpinner);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
                MainActivity.this, R.layout.custom_spinner, sortArray);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin1.setAdapter(adapter1);
        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position == 0)
                    //sort comments by popularity
                    if (position == 1) ;
                //sort comments by age
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode == Activity.RESULT_OK)
        {
            String [] comment = data.getStringExtra("result").split("/");
            ABill newBill = AllBills.get(requestCode - 1);
            newBill.billComments.setUserName(comment[0]);
            newBill.billComments.setCommentText(comment[1]);
            newBill.billComments.setDateTimeString(comment[2]);
            AllBills.set(requestCode - 1,newBill);

        }else if (resultCode == Activity.RESULT_CANCELED) {
            //Don't refresh.
        }
        refreshRadioGroups();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

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
        }else if (id == R.id.action_search) {
            /*RelativeLayout layout = (RelativeLayout) findViewById(R.id.mainRelative);
            Snackbar.make(layout, "Add search function", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();*/
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_home) {
        } else if (id == R.id.nav_categories) {
            Intent intent = new Intent(MainActivity.this, Category.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivityForResult(intent, 1);
        } else if (id == R.id.nav_favourites) {
            Intent intent = new Intent(MainActivity.this, Favourites.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivityForResult(intent, 1);
        //} else if (id == R.id.nav_recommended) {
        } else if (id == R.id.nav_share) {
        } else if (id == R.id.nav_send) {
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onImageClick(View view){
        Intent intent = new Intent(getApplicationContext(), Bill.class);
        switch (view.getId()) {
            case R.id.imageView3:
                intent.putExtra(EXTRA_MESSAGE, "1");
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                break;
            case R.id.imageView7:
                intent.putExtra(EXTRA_MESSAGE, "2");
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                break;
            case R.id.imageView6:
                intent.putExtra(EXTRA_MESSAGE, "3");
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                break;
            case R.id.imageView8:
                intent.putExtra(EXTRA_MESSAGE, "4");
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                break;
            case R.id.imageView5:
                intent.putExtra(EXTRA_MESSAGE, "5");
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                break;
        }
        /*Bundle bndlanimation = ActivityOptions.
                makeCustomAnimation(getApplicationContext(), R.anim.animation, R.anim.animation2).toBundle();*/
        startActivityForResult(intent, 1);
    }

    private void onRadioButtonClicked(View view)
    {
        //boolean checked = ((RadioButton) view).isChecked();
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.mainRelative);
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

    private void refreshRadioGroups()
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

    public void loadBillsIntoArrayList()
    {
        ArrayList<ArrayList> billArrayList = getFile();
        for (int i = 0; i < billArrayList.size(); i++)
        {
            //refnum goes from 1 to 4, so loop must use -1.
            ArrayList inputLine = billArrayList.get(i);
            //Integer billRefNum = Integer.parseInt(String.valueOf(inputLine.get(0)));
            String title = (String) inputLine.get(1);
            String category = (String) inputLine.get(2);

            ABill abill = new ABill();
            abill.billRefNum = i;//billRefNum
            abill.billName = title;
            abill.billCategory = category;
            abill.billFavourited = Boolean.FALSE;
            abill.billImg = getResources().getDrawable(R.drawable.galleryimage);
            abill.billBackImg = getResources().getDrawable(R.drawable.galleryimage);

            switch (i) {
                case 0:
                    abill.billImg = getResources().getDrawable(R.drawable.armedforces);
                    abill.billBackImg = getResources().getDrawable(R.drawable.armedforces);
                    break;
                case 1:
                    abill.billImg = getResources().getDrawable(R.drawable.tradeunion);
                    abill.billBackImg = getResources().getDrawable(R.drawable.tradeunion);
                    break;
                case 2:
                    abill.billImg = getResources().getDrawable(R.drawable.childcare);
                    abill.billBackImg = getResources().getDrawable(R.drawable.childcare);
                    break;
                case 3:
                    abill.billImg = getResources().getDrawable(R.drawable.energy);
                    abill.billBackImg = getResources().getDrawable(R.drawable.energy);
                    break;
                case 4:
                    abill.billImg = getResources().getDrawable(R.drawable.insuranceimg);
                    abill.billBackImg = getResources().getDrawable(R.drawable.insuranceimg);
                    break;
            }
            AllBills.add(abill);
        }
    }

    public void loadBillsIntoHomePage()
    {
        ArrayList<ArrayList> billArrayList = getFile();//Size = 5
        for (int i = 0; i < billArrayList.size(); i++)
        {
            //refnum goes from 1 to 4, so loop must use -1.
            ArrayList inputLine = billArrayList.get(i);
            //Integer billRefNum = Integer.parseInt(String.valueOf(inputLine.get(0)));
            String title = (String) inputLine.get(1);
            String category = (String) inputLine.get(2);

            TextView textView1 = null;
            ImageView imageView3 = (ImageView) findViewById(R.id.imageView3);

            switch (i) {
                case 0:
                    textView1 = (TextView) findViewById(R.id.textView3);
                    imageView3 = (ImageView) findViewById(R.id.imageView3);
                    break;
                case 1:
                    textView1 = (TextView) findViewById(R.id.textView7);
                    imageView3 = (ImageView) findViewById(R.id.imageView7);
                    break;
                case 2:
                    textView1 = (TextView) findViewById(R.id.textView6);
                    imageView3 = (ImageView) findViewById(R.id.imageView6);
                    break;
                case 3:
                    textView1 = (TextView) findViewById(R.id.textView8);
                    imageView3 = (ImageView) findViewById(R.id.imageView8);
                    break;
                case 4:
                    textView1 = (TextView) findViewById(R.id.textView5);
                    imageView3 = (ImageView) findViewById(R.id.imageView5);
                    break;
            }
            try {
                imageView3.setImageDrawable(AllBills.get(i).billImg);
                setTitle(category);//set title of page to the category of the bill
                textView1.setText(title);
            }catch (Exception e){
                System.out.println("Main activity no longer being viewed...");
            }
        }
    }

    public ArrayList<ArrayList> getFile()
    {
        InputStream is = this.getResources().openRawResource(
                getResources().getIdentifier("raw/" + "bill", //first18times
                        "raw", getPackageName()));
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        ArrayList<ArrayList> bills = new ArrayList<ArrayList>();
        String data;
        try {
            while ((data = reader.readLine()) != null)
            {
                String[] splitArray = data.split(",");
                ArrayList sublist = new ArrayList();

                for (int i = 0; i < splitArray.length; i++) {
                    sublist.add(splitArray[i]);
                }
                bills.add(sublist);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bills;
    }
}
