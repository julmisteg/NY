package com.example.gaetanejulmiste.nytimes.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.gaetanejulmiste.nytimes.Activities.ArticleActivity;
import com.example.gaetanejulmiste.nytimes.Adapters.ArticleArrayAdapter;
import com.example.gaetanejulmiste.nytimes.Fragments.SettingsFragment;
import com.example.gaetanejulmiste.nytimes.Listeners.EndlessScrollListener;
import com.example.gaetanejulmiste.nytimes.Models.Article;
import com.example.gaetanejulmiste.nytimes.R;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity {
    EditText etQuery;
    GridView gvResults;
    Button btnSearch;
    ArrayList<Article> articles;
    ArrayAdapter adapter;
    String search;
    SettingsFragment sdialog;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupViews();


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client2 = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void setupViews() {
        etQuery = (EditText) findViewById(R.id.etQuery);
        gvResults = (GridView) findViewById(R.id.gvResults);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        articles = new ArrayList<>();
        adapter = new ArticleArrayAdapter(this, articles);
        gvResults.setAdapter(adapter);
        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), ArticleActivity.class);
                Article article = articles.get(position);
                //i.putExtra("url",article.getWebUrl());
                i.putExtra("article", article);
                startActivity(i);

            }
        });

        gvResults.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView

                addMoreArticles(page);
                // or customLoadMoreDataFromApi(totalItemsCount);
                return true; // ONLY if more data is actually being loaded; false otherwise.
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_article);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                //searchView.clearFocus();
                //addMoreArticles(0);

                addSearchActionBar(query);
                etQuery.setVisibility(View.INVISIBLE);
                btnSearch.setVisibility(View.INVISIBLE);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);

        //return true;*/
        // return true;
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


    public void addSearchActionBar(String query) {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.nytimes.com/svc/search/v2/articlesearch.json";
        RequestParams params = new RequestParams();
        params.put("api-key", "1567a6e4782c43dea0f707ff17544f66");
        params.put("page", "0");
        params.put("q", query);
        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Log.d("DEBUG", response.toString());
                JSONArray articleJsonResults = null;

                try {
                    articleJsonResults = response.getJSONObject("response").getJSONArray("docs");


                    adapter.addAll(Article.fromJsonArray(articleJsonResults));
                    Log.d("DEBUG", adapter.toString());


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    public void onArticleSearch(View view) {

        adapter.clear();
        addMoreArticles(0);

      /*  if(!isNetworkAvailable()) {
            Toast.makeText(this,"No Network Connectivity, Try Again !", Toast.LENGTH_LONG).show();
            return;
        }
        String query =etQuery.getText().toString();
        //Toast.makeText(getApplicationContext(),"Searching For"+ query,Toast.LENGTH_LONG).show();
        //1567a6e4782c43dea0f707ff17544f66
        //var url = "https://api.nytimes.com/svc/search/v2/articlesearch.json";
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.nytimes.com/svc/search/v2/articlesearch.json";
        RequestParams params = new RequestParams();
        params.put("api-key","1567a6e4782c43dea0f707ff17544f66");
        params.put("page",0);
        params.put("q",query);
        client.get(url,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
               // super.onSuccess(statusCode, headers, response);
                Log.d("DEBUG",response.toString());
                JSONArray articleJsonResults = null;

                    try{
                        articleJsonResults = response.getJSONObject("response").getJSONArray("docs");
                        //Log.d("DEBUG",articleJsonResults.toString());
                        //articles.addAll(Article.fromJsonArray(articleJsonResults));
                        //Log.d("DEBUG",articles.toString());
                        //adapter.notifyDataSetChanged();

                        adapter.addAll(Article.fromJsonArray(articleJsonResults));
                        Log.d("DEBUG",adapter.toString());


                    }catch (JSONException e){
                        e.printStackTrace();
                    }

            }
        });
*/


    }


    public void addMoreArticles(int page) {
        if (!isNetworkAvailable()) {
            Toast.makeText(this, "No Network Connectivity, Try Again !", Toast.LENGTH_LONG).show();
            return;
        }
        String query = etQuery.getText().toString();
        //Toast.makeText(getApplicationContext(),"Searching For"+ query,Toast.LENGTH_LONG).show();
        //1567a6e4782c43dea0f707ff17544f66
        //var url = "https://api.nytimes.com/svc/search/v2/articlesearch.json";
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.nytimes.com/svc/search/v2/articlesearch.json";
        RequestParams params = new RequestParams();
        params.put("api-key", "1567a6e4782c43dea0f707ff17544f66");
        params.put("page", page);
        params.put("q", query);
        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // super.onSuccess(statusCode, headers, response);
                Log.d("DEBUG", response.toString());
                JSONArray articleJsonResults = null;

                try {
                    articleJsonResults = response.getJSONObject("response").getJSONArray("docs");
                    //Log.d("DEBUG",articleJsonResults.toString());
                    //articles.addAll(Article.fromJsonArray(articleJsonResults));
                    //Log.d("DEBUG",articles.toString());
                    //adapter.notifyDataSetChanged();

                    adapter.addAll(Article.fromJsonArray(articleJsonResults));
                    Log.d("DEBUG", adapter.toString());


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


    }

    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }


    public void addFilters(MenuItem item) {
        Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivityForResult(i, 1);


    }

   /*public void addFilters(MenuItem item) {
        FragmentManager fgm = getSupportFragmentManager();
       // sdialog.show(fgm,"advanceSearch");

    }*/


}
