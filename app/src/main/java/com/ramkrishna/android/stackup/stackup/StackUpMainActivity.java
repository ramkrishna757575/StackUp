package com.ramkrishna.android.stackup.stackup;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ramkr on 17-Apr-16.
 *
 * Launcher Activity of the App.
 */
public class StackUpMainActivity extends AppCompatActivity {

    private List<Item> itemsInList = new ArrayList<Item>();
    private SearchView searchQuestions;
    private String url = Constants.API_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stack_up_main);

        searchQuestions = (SearchView) findViewById(R.id.search_questions);

        //*** setOnQueryTextFocusChangeListener ***
        searchQuestions.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                // TODO Auto-generated method stub
                //Toast.makeText(getBaseContext(), String.valueOf(hasFocus), Toast.LENGTH_SHORT).show();
            }
        });

        //*** setOnQueryTextListener ***
        searchQuestions.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query)
            {
                // TODO Auto-generated method stub
                //Toast.makeText(getBaseContext(), query, Toast.LENGTH_SHORT).show();
                url = Constants.API_URL;
                if (!query.isEmpty())
                {
                    String tags[] = query.split(" ");
                    for (int i = 0; i < tags.length; i++)
                    {
                        url += tags[i] + ";";
                    }
                }
                GetData gt = new GetData();
                gt.execute();
                searchQuestions.setQuery("",true);
                searchQuestions.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                // TODO Auto-generated method stub
                //	Toast.makeText(getBaseContext(), newText,Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        GetData gt = new GetData();
        gt.execute();
    }

    public void loadDefaultList(View v)
    {
        url = Constants.API_URL;
        GetData gt = new GetData();
        gt.execute();
    }

    //Inner class to get data from Network using the API
    private class GetData extends AsyncTask {

        NetworkDataManager ndm = new NetworkDataManager(getApplicationContext());
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            ndm.setUrl(url);
            ndm.initNetwork();
            progressDialog = ProgressDialog.show(StackUpMainActivity.this, "Loading Questions List", "Please wait...");
        }

        @Override
        protected Object doInBackground(Object[] params)
        {
            ndm.fetchRawString();
            return null;
        }

        @Override
        protected void onPostExecute(Object o)
        {
            super.onPostExecute(o);
            String rawData = ndm.getRawData();
            JSONParser jsp = new JSONParser(rawData);
            jsp.parseObject();
            itemsInList = jsp.getItemList();
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list_recycler_view);
            RecyclerViewAdapter adapter = new RecyclerViewAdapter(itemsInList, getApplication());
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            TextView api_quota = (TextView) findViewById(R.id.api_quota);
            api_quota.setText("API Quota "+ Integer.toString(jsp.getAPI_Quota()) + "%");
            progressDialog.dismiss();
        }
    }
}
