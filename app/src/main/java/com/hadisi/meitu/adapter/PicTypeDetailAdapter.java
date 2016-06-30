package com.hadisi.meitu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hadisi.meitu.R;
import com.hadisi.meitu.entities.PicType;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wugang on 2016/6/29.
 */

public class PicTypeDetailAdapter extends RecyclerView.Adapter<PicTypeDetailAdapter.MyViewHolder> {

    private OnItemClickListener mOnItemClickListener;
    private Context mContext;
    private ArrayList<PicType.ShowapiResBodyBean.ListBean.ItemListBean> mTypeDetailNameList;

    public PicTypeDetailAdapter(Context mContext, ArrayList<PicType.ShowapiResBodyBean.ListBean.ItemListBean> mTypeName) {
        this.mContext = mContext;
        this.mTypeDetailNameList = mTypeName;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int postion);

        void onItemLongClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pic_type, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.picTypeName.setText(mTypeDetailNameList.get(position).getName());
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int layoutPostition = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(v, layoutPostition);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int layoutPostition = holder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(v, layoutPostition);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mTypeDetailNameList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.pic_type_name)
        TextView picTypeName;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
