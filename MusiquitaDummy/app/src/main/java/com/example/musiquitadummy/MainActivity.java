package com.example.musiquitadummy;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.musiquitadummy.databinding.ActivityMainBinding;
import com.facebook.network.connectionclass.ConnectionClassManager;
import com.facebook.network.connectionclass.ConnectionQuality;
import com.facebook.network.connectionclass.DeviceBandwidthSampler;
import com.google.gson.GsonBuilder;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YtFile;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.mancj.materialsearchbar.MaterialSearchBar;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MaterialSearchBar.OnSearchActionListener {

    private YouTubeVideo videoItem;
    private ActivityMainBinding binding;
    private List<String> lastSearches;
    MaterialSearchBar searchBar;
    private DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((binding = ActivityMainBinding.inflate(getLayoutInflater())).getRoot());

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        searchBar = findViewById(R.id.searchBar);
        searchBar.setOnSearchActionListener(this);
        searchBar.inflateMenu(R.menu.main);
        searchBar.setText("Hello World!");
        Log.d("LOG_TAG", getClass().getSimpleName() + ": text " + searchBar.getText());
        searchBar.setCardViewElevation(10);
        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("LOG_TAG", getClass().getSimpleName() + " text changed " + searchBar.getText());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });

        final FloatingActionButton searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchBar.openSearch();
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                        Request.Method.GET,
                        Config.YOUTUBE_QUERY1 + searchBar.getText() + Config.YOUTUBE_QUERY2 + Config.YOUTUBE_API_KEY,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                String num_results = response.optString("resultsPerPage");


                                JSONArray jsonArray = response.optJSONArray("items");
                                List<Items> results = Arrays.asList(new GsonBuilder().create().fromJson(jsonArray.toString(), Items[].class));

                                MyAdapter myAdapter = new MyAdapter(results, getApplicationContext());
                                binding.appbarmain.layoutmain.reciclajevista.setAdapter(myAdapter);
                                binding.appbarmain.layoutmain.reciclajevista.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("tag", "onErrorResponse: " + error.getMessage());
                            }
                        }
                );
                queue.add(jsonObjectRequest);

            }
        });
        //Más pruebas del ser
        /*videoItem = new YouTubeVideo();
        videoItem.setId("Mp8spJn40BA");
        videoItem.setDuration("3:23");
        videoItem.setTitle("La Santa");
        videoItem.setViewCount("2");
        videoItem.setThumbnailURL("https://i3.ytimg.com/vi/JUxITamPWrY/maxresdefault.jpg");

        Intent serviceIntent = new Intent(this, BackgroundAudioService.class);

        serviceIntent.setAction(BackgroundAudioService.ACTION_PLAY);
        serviceIntent.putExtra(Config.YOUTUBE_TYPE, ItemType.YOUTUBE_MEDIA_TYPE_VIDEO);
        serviceIntent.putExtra(Config.YOUTUBE_TYPE_VIDEO, videoItem);
        startService(serviceIntent);*/
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
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onSearchStateChanged(boolean enabled) {
    }

    @Override
    public void onSearchConfirmed(CharSequence text) {

    }

    @Override
    public void onButtonClicked(int buttonCode) {
        switch (buttonCode) {
            case MaterialSearchBar.BUTTON_NAVIGATION:
                drawer.openDrawer(GravityCompat.START);
                break;
            case MaterialSearchBar.BUTTON_SPEECH:
                break;
            case MaterialSearchBar.BUTTON_BACK:
                searchBar.closeSearch();
                break;
        }
    }



}