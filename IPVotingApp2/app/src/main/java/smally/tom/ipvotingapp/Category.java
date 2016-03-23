package smally.tom.ipvotingapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class Category extends MainActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setTitle("Categories");

        loadBillsIntoCategoryPage();
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
        getMenuInflater().inflate(R.menu.category, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
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
        } else if (id == R.id.nav_favourites) {
            Intent intent = new Intent(Category.this, Favourites.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivityForResult(intent, 1);
        } else if (id == R.id.nav_share) {
        } else if (id == R.id.nav_send) {
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onCategoryClick(View view)
    {
        Intent intent = new Intent(Category.this, LoadCategory.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra(EXTRA_MESSAGE, "Defence");
        startActivityForResult(intent, 1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode == Activity.RESULT_OK)
        {
            System.out.println("Ended category list: OK");
        }else if (resultCode == Activity.RESULT_CANCELED) {
            System.out.println("Ended category list: cancelled");
        }
    }

    public void loadBillsIntoCategoryPage()
    {
        for (int i = 0; i < 5; i++)
        {
            ABill newBill = AllBills.get(i);
            String category = newBill.billCategory;

            TextView textView1 = null;
            ImageView imageView3 = null;
            switch (i){
                case 0:
                    textView1 = (TextView) findViewById(R.id.textView15);
                    imageView3 = (ImageView) findViewById(R.id.imageView15);
                    break;
                case 1:
                    textView1 = (TextView) findViewById(R.id.textView16);
                    imageView3 = (ImageView) findViewById(R.id.imageView16);
                    break;
                case 2:
                    textView1 = (TextView) findViewById(R.id.textView17);
                    imageView3 = (ImageView) findViewById(R.id.imageView17);
                    break;
                case 3:
                    textView1 = (TextView) findViewById(R.id.textView18);
                    imageView3 = (ImageView) findViewById(R.id.imageView18);
                    break;
                case 4:
                    textView1 = (TextView) findViewById(R.id.textView19);
                    imageView3 = (ImageView) findViewById(R.id.imageView19);
                    break;
            }
            Drawable drawable = newBill.billImg;
            imageView3.setImageDrawable(drawable);
            textView1.setText(category);
        }
    }
}
