package com.hadisi.meitu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.google.gson.Gson;
import com.hadisi.meitu.R;
import com.hadisi.meitu.adapter.PicTypeDetailItemAdapter;
import com.hadisi.meitu.config.Constant;
import com.hadisi.meitu.entities.PicSearch;
import com.hadisi.meitu.entities.PicType;
import com.hadisi.meitu.widget.divider.HorizontalDividerItemDecoration;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wugang on 2016/6/30.
 */

public class TypeDetailItemActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pic_type_detail_item_rv)
    RecyclerView picTypeDetailItemRv;
    private PicType.ShowapiResBodyBean.ListBean.ItemListBean itemListBean;
    private PicTypeDetailItemAdapter picTypeDetailItemAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_detail_item);
        ButterKnife.bind(this);
        itemListBean = (PicType.ShowapiResBodyBean.ListBean.ItemListBean) getIntent().getSerializableExtra("type_detail_item");
        toolbar.setTitle(itemListBean.getName());
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.back_normal);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getPicURI();
    }

    private void getPicURI() {
        final Parameters para = new Parameters();
        para.put("apikey", "89d9bfd4e5dbdc21a5aa27a1082a17d4");
        para.put("type", itemListBean.getId()+"");
        para.put("page", "1");
        ApiStoreSDK.execute(Constant.SER_PIC_SEARCH_URI, ApiStoreSDK.GET, para, new ApiCallBack() {
            @Override
            public void onSuccess(int status, String responseString) {
                super.onSuccess(status, responseString);
                Log.i("sdkdemo", "onSuccess" + responseString);
                Gson gson = new Gson();
                PicSearch picSearch = gson.fromJson(responseString, PicSearch.class);
                final List<PicSearch.ShowapiResBodyBean.PagebeanBean.ContentlistBean> contentlist = picSearch.getShowapi_res_body().getPagebean().getContentlist();
                picTypeDetailItemAdapter = new PicTypeDetailItemAdapter(TypeDetailItemActivity.this, contentlist);
                picTypeDetailItemRv.setAdapter(picTypeDetailItemAdapter);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(TypeDetailItemActivity.this, LinearLayoutManager.VERTICAL, false);
                picTypeDetailItemRv.setLayoutManager(linearLayoutManager);
                picTypeDetailItemRv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(TypeDetailItemActivity.this).colorResId(R.color.colorPrimary).build());
                picTypeDetailItemAdapter.setOnItemClickListener(new PicTypeDetailItemAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int postion) {
                        Intent intent = new Intent(TypeDetailItemActivity.this,DetailItemAcivity.class);
                        intent.putExtra("detail_item", (Serializable) contentlist.get(postion));
                        startActivity(intent);
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

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
}
