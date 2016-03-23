package smally.tom.ipvotingapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

public class Bill extends MainActivity
{
    //Boolean favourited = false;
    ABill newBill;
    Integer refNum;
    String[] comment;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent addComment = new Intent(Bill.this, AddComment.class);
                addComment.putExtra(EXTRA_MESSAGE, String.valueOf(refNum));
                startActivityForResult(addComment, 1);

            }
        });

        String [] sortArray = new String[2];
        sortArray[0] = "Popularity";
        sortArray[1] = "Newest";
        Spinner spin1 = (Spinner) findViewById(R.id.sortSpinner);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
                Bill.this, android.R.layout.simple_spinner_item, sortArray);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin1.setAdapter(adapter1);
        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position == 0)
                    //sort comments by popularity
                    if (position == 1) ;//sort comments by age
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        refNum = Integer.valueOf(intent.getStringExtra(MainActivity.EXTRA_MESSAGE));

        Button lessButton = (Button) findViewById(R.id.lessButton);
        lessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Bill.this, Description.class);
                intent.putExtra(EXTRA_MESSAGE, String.valueOf(refNum));
                startActivity(intent);
            }
        });

        loadBillIntoBillPage();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode == Activity.RESULT_OK)
        {
            RelativeLayout layout = (RelativeLayout) findViewById(R.id.billRelativeLayout);
            Snackbar.make(layout, "Added comment", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            comment = data.getStringExtra("result").split("/");
            newBill.billComments.setUserName(comment[0]);
            newBill.billComments.setCommentText(comment[1]);
            newBill.billComments.setDateTimeString(comment[2]);
            AllBills.set(refNum-1,newBill);

            refreshComments();
        }else if (resultCode == Activity.RESULT_CANCELED) {
            //Don't refresh.
        }
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
        getMenuInflater().inflate(R.menu.bill, menu);
        this.menu = menu;
        try {
            setFavToggle();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }if (id == R.id.action_favourite)
        {
            RelativeLayout layout = (RelativeLayout) findViewById(R.id.billRelativeLayout);
            if(newBill.billFavourited) {
                item.setIcon(R.drawable.favouriteimg);
                Snackbar.make(layout, "Removed from favourites", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                newBill.billFavourited = Boolean.FALSE;
            }else {
                item.setIcon(R.drawable.favouriteimg_orange);
                Snackbar.make(layout, "Added to favourites", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                newBill.billFavourited = Boolean.TRUE;
            }
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
            super.onBackPressed();
        } else if (id == R.id.nav_categories) {
            Intent intent = new Intent(Bill.this, Category.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivityForResult(intent, 1);
        } else if (id == R.id.nav_favourites) {
            Intent intent = new Intent(Bill.this, Favourites.class);
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

    public void loadBillIntoBillPage()
    {
        try {
            newBill = AllBills.get(refNum - 1);//Bills should be referenced by refNum - 1
        }catch (IndexOutOfBoundsException e) {
            System.out.println("failed to load all bills");
        }
            ImageView imageView3 = (ImageView) findViewById(R.id.imageView10);
            ImageView imageView = (ImageView) findViewById(R.id.imageView);

            imageView3.setImageDrawable(newBill.billImg);
            imageView.setBackground(newBill.billBackImg);

            refreshComments();

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

            /*CHECKS IF TITLE IS TOO LARGE TO BE DISPLAYED - NOT IDEAL*/
            String name = newBill.billName;
            TextView textView1 = (TextView) findViewById(R.id.textView10);
            if(name.length() <= 24) {
                setTitle(name); //set title of page to the category of the bill
                textView1.setText("(" + newBill.billCategory + ")");
            }else{
                String[] nameSplit = name.split(" ");
                setTitle(nameSplit[0]+" "+nameSplit[1]+" ...");
                textView1.setText(nameSplit[2]+" "+nameSplit[3]+", (" + newBill.billCategory + ")");
            }
    }

    private void refreshComments()
    {
        try {
            RelativeLayout comment = (RelativeLayout) findViewById(R.id.userComment);
            if(newBill.billComments.getUserName() == "")
            {
                comment.setVisibility(View.GONE);
            }else
            {
                comment.setVisibility(View.VISIBLE);
                TextView commentUsername = (TextView) findViewById(R.id.commentUsername);
                commentUsername.setText(newBill.billComments.getUserName());
                TextView commentText = (TextView) findViewById(R.id.commentText);
                commentText.setText(newBill.billComments.getCommentText());
                TextView commentTime = (TextView) findViewById(R.id.commentTime);
                commentTime.setText(newBill.billComments.getDateTimeString());
            }
        }catch(Exception e){
            System.out.println("not loaded Bill.Java yet");
        }
    }
    public void setFavToggle() throws Exception
    {
        MenuItem favToggle = menu.findItem(R.id.action_favourite);
        //RelativeLayout layout = (RelativeLayout) findViewById(R.id.billRelativeLayout);
        if(newBill.billFavourited == Boolean.FALSE) {
            favToggle.setIcon(R.drawable.favouriteimg);
        }else {
            favToggle.setIcon(R.drawable.favouriteimg_orange);
        }
    }
    public void onRadioButtonClicked(View view)
    {
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.billRelativeLayout);
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
}
