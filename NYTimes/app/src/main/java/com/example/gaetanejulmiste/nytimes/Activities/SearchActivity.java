package com.example.gaetanejulmiste.nytimes.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import com.example.gaetanejulmiste.nytimes.Activities.ArticleActivity;
import com.example.gaetanejulmiste.nytimes.Adapters.ArticleArrayAdapter;
import com.example.gaetanejulmiste.nytimes.Models.Article;
import com.example.gaetanejulmiste.nytimes.R;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupViews();


    }

    public void setupViews(){
        etQuery = (EditText)findViewById(R.id.etQuery);
        gvResults =(GridView) findViewById(R.id.gvResults);
        btnSearch =(Button)findViewById(R.id.btnSearch);
        articles = new ArrayList<>();
        adapter = new ArticleArrayAdapter(this,articles);
        gvResults.setAdapter(adapter);
        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), ArticleActivity.class);
                Article article =articles.get(position);
                //i.putExtra("url",article.getWebUrl());
                i.putExtra("article",article);
                startActivity(i);

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
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

    public void onArticleSearch(View view) {
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


    }
}
