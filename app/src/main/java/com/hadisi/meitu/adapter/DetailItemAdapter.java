package com.hadisi.meitu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.imagepipeline.request.ImageRequest;
import com.hadisi.meitu.R;
import com.hadisi.meitu.entities.PicSearch;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wugang on 2016/6/29.
 */

public class DetailItemAdapter extends RecyclerView.Adapter<DetailItemAdapter.MyViewHolder> {

    private OnItemClickListener mOnItemClickListener;
    private Context mContext;
    private PicSearch.ShowapiResBodyBean.PagebeanBean.ContentlistBean contentlistBean;

    public DetailItemAdapter(Context mContext, PicSearch.ShowapiResBodyBean.PagebeanBean.ContentlistBean contentlistBean) {
        this.mContext = mContext;
        this.contentlistBean = contentlistBean;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pic_search, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
//        Uri uri = Uri.parse(contentlistBean.getList().get(position).getBig());
//        holder.picSearchIv.setImageURI(uri);
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setLowResImageRequest(ImageRequest.fromUri(contentlistBean.getList().get(position).getSmall()))
                .setImageRequest(ImageRequest.fromUri(contentlistBean.getList().get(position).getBig()))
                .setOldController(holder.picSearchIv.getController())
                .build();
        holder.picSearchIv.setController(controller);
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
        return contentlistBean.getList().size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.pic_search_iv)
        com.facebook.drawee.view.SimpleDraweeView picSearchIv;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            Fresco.initialize(mContext);
        }
    }
}
