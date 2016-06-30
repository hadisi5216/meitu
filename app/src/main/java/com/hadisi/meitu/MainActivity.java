package com.hadisi.meitu;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.google.gson.Gson;
import com.hadisi.meitu.activity.TypeDetailActivity;
import com.hadisi.meitu.adapter.PicTypeAdapter;
import com.hadisi.meitu.config.Constant;
import com.hadisi.meitu.entities.PicType;
import com.hadisi.meitu.widget.divider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView picTypeRecyclerView;
    private PicTypeAdapter picTypeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        requestData();

        picTypeRecyclerView = (RecyclerView) findViewById(R.id.pic_type);


    }

    private void requestData() {
        final Parameters para = new Parameters();
        para.put("apikey", "89d9bfd4e5dbdc21a5aa27a1082a17d4");
        ApiStoreSDK.execute(Constant.SER_PIC_TYPE_URI, ApiStoreSDK.GET, para, new ApiCallBack() {
            @Override
            public void onSuccess(int status, String responseString) {
                Log.i("sdkdemo", "onSuccess" + responseString);
                Gson gson = new Gson();
                PicType picType = gson.fromJson(responseString, PicType.class);
                ArrayList<String> typeNames = new ArrayList<String>();
                final ArrayList<PicType.ShowapiResBodyBean.ListBean> listBeen = new ArrayList<PicType.ShowapiResBodyBean.ListBean>();
                for (int i = 0; i < picType.getShowapi_res_body().getList().size(); i++) {
                    typeNames.add(picType.getShowapi_res_body().getList().get(i).getName());
                    listBeen.add(picType.getShowapi_res_body().getList().get(i));
                }
                picTypeAdapter = new PicTypeAdapter(MainActivity.this, typeNames);
                picTypeRecyclerView.setAdapter(picTypeAdapter);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
                picTypeRecyclerView.setLayoutManager(linearLayoutManager);
                picTypeRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(MainActivity.this).colorResId(R.color.colorPrimary).build());
                picTypeAdapter.setOnItemClickListener(new PicTypeAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int postion) {
                        Intent intent = new Intent(MainActivity.this, TypeDetailActivity.class);
                        intent.putExtra("listBeen", listBeen.get(postion));
                        startActivity(intent);
                    }

                    @Override
                    public void onItemLongClick(View view, final int position) {
                    }

                });

            }

            @Override
            public void onComplete() {
                Log.i("sdkdemo", "onComplete");
            }

            @Override
            public void onError(int status, String responseString, Exception e) {
                Log.i("sdkdemo", "onError, status: " + status);
                Log.i("sdkdemo", "errMsg: " + (e == null ? "" : e.getMessage()));
            }
        });
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
}
