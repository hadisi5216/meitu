package com.hadisi.meitu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.hadisi.meitu.R;
import com.hadisi.meitu.adapter.PicTypeDetailAdapter;
import com.hadisi.meitu.entities.PicType;
import com.hadisi.meitu.widget.divider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wugang on 2016/6/30.
 */

public class TypeDetailActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pic_type_detail_rv)
    RecyclerView picTypeDetailRv;
    private PicType.ShowapiResBodyBean.ListBean listBeen;
    private PicTypeDetailAdapter picTypeDetailAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_detail);
        ButterKnife.bind(this);
        listBeen = (PicType.ShowapiResBodyBean.ListBean) getIntent().getSerializableExtra("listBeen");
        toolbar.setTitle(listBeen.getName());
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.back_normal);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        picTypeDetailAdapter = new PicTypeDetailAdapter(this, (ArrayList<PicType.ShowapiResBodyBean.ListBean.ItemListBean>) listBeen.getList());
        picTypeDetailRv.setAdapter(picTypeDetailAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        picTypeDetailRv.setLayoutManager(linearLayoutManager);
        picTypeDetailRv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).colorResId(R.color.colorPrimary).build());
        picTypeDetailAdapter.setOnItemClickListener(new PicTypeDetailAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                Intent intent = new Intent(TypeDetailActivity.this,TypeDetailItemActivity.class);
                intent.putExtra("type_detail_item",listBeen.getList().get(postion));
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }
}
