package com.hadisi.meitu.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.hadisi.meitu.R;
import com.hadisi.meitu.adapter.DetailItemAdapter;
import com.hadisi.meitu.entities.PicSearch;
import com.hadisi.meitu.widget.divider.HorizontalDividerItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wugang on 2016/6/30.
 */

public class DetailItemAcivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pic_detail_item_rv)
    RecyclerView picDetailItemRv;
    private PicSearch.ShowapiResBodyBean.PagebeanBean.ContentlistBean contentlistBean;
    private DetailItemAdapter detailItemAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_detail_item);
        ButterKnife.bind(this);
        contentlistBean = (PicSearch.ShowapiResBodyBean.PagebeanBean.ContentlistBean) getIntent().getSerializableExtra("detail_item");
        toolbar.setTitle(contentlistBean.getTitle());
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.back_normal);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        detailItemAdapter = new DetailItemAdapter(this,contentlistBean);
        picDetailItemRv.setAdapter(detailItemAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        picDetailItemRv.setLayoutManager(linearLayoutManager);
        picDetailItemRv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).colorResId(R.color.colorPrimary).build());
    }
}
