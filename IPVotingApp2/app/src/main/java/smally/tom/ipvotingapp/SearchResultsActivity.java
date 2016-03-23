package smally.tom.ipvotingapp;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.widget.RelativeLayout;

/**
 * Created by Thomas on 21/03/2016.
 */
public class SearchResultsActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent)
    {
        //if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            RelativeLayout layout = (RelativeLayout) findViewById(R.id.mainRelative);
            Snackbar.make(layout, "Search for: " + query, Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
            //use the query to search your data somehow
        //}
    }
}
