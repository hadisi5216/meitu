package com.hadisi.meitu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hadisi.meitu.R;
import com.hadisi.meitu.entities.PicSearch;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wugang on 2016/6/29.
 */

public class PicTypeDetailItemAdapter extends RecyclerView.Adapter<PicTypeDetailItemAdapter.MyViewHolder> {

    private OnItemClickListener mOnItemClickListener;
    private Context mContext;
    private List<PicSearch.ShowapiResBodyBean.PagebeanBean.ContentlistBean> mContentlistBeen;

    public PicTypeDetailItemAdapter(Context mContext, List<PicSearch.ShowapiResBodyBean.PagebeanBean.ContentlistBean> contentlistBeen) {
        this.mContext = mContext;
        this.mContentlistBeen = contentlistBeen;
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
        holder.picTypeName.setText(mContentlistBeen.get(position).getTitle());
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
        return mContentlistBeen.size();
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
