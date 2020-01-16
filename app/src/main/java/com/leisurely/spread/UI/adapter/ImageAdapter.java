package com.leisurely.spread.UI.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.donkingliang.imageselector.utils.ImageSelector;

import java.io.File;
import java.util.ArrayList;

import com.leisurely.spread.R;
import com.leisurely.spread.UI.activity.money.ChongbiActivity;
import com.leisurely.spread.util.StringUtil;
import com.leisurely.spread.util.ToastUtil;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private static final int REQUEST_CODE = 0x00000011;

    private Context mContext;
    private ArrayList<String> mImages;
    private LayoutInflater mInflater;

    public ImageAdapter(Context context) {
        mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
    }

    public ArrayList<String> getImages() {
        return mImages;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adapter_image, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final String image = mImages.get(position);
        if (StringUtil.isNotNull(image)) {
            Glide.with(mContext).load(new File(image)).into(holder.ivImage);
            holder.ivImage.setVisibility(View.VISIBLE);
            holder.delete.setVisibility(View.VISIBLE);
            if (mContext instanceof ChongbiActivity) {
                holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (((ChongbiActivity) mContext).getImageUrlsCount() == (mImages.size() == 5 ? 5 : mImages.size() - 1)) {
                            if (mImages.size() == (5 - ((ChongbiActivity) mContext).getCameraUrlsCount())
                                    && StringUtil.isNotNull(mImages.get(4 - ((ChongbiActivity) mContext).getCameraUrlsCount()))) {
                                mImages.add("");
                            }
                            if (mContext instanceof ChongbiActivity) {
                                ((ChongbiActivity) mContext).reomove(position, mImages.get(position));
                            }
                            mImages.remove(position);
                            notifyDataSetChanged();
                        } else {
                            ToastUtil.showToast("图片正在上传中,请稍后进行操作");
                        }


                    }
                });
                holder.ivImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (((ChongbiActivity) mContext).getImageUrlsCount() == (mImages.size() == 5 ? 5 : mImages.size() - 1)) {
                            ((ChongbiActivity) mContext).openPhotoView(position);
                        } else {
                            ToastUtil.showToast("图片正在上传中,请稍后进行操作");
                        }
                    }
                });
            }

        } else {
            holder.ivImage.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.find_add_button));
            holder.delete.setVisibility(View.GONE);
            holder.ivImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageSelector.ImageSelectorBuilder builder = ImageSelector.builder();
                    builder.useCamera(true); // 设置是否使用拍照
                    builder.setSingle(false);  //设置是否单选
                    if (mContext instanceof ChongbiActivity) {
                        builder.setMaxSelectCount(5 - ((ChongbiActivity) mContext).getCameraUrlsCount()); // 图片的最大选择数量，小于等于0时，不限数量。
                    }
                    mImages.remove(mImages.size() - 1);
                    if (mImages.size() > 0) {
                        builder.setSelected(mImages);
                    }
                    if (mContext instanceof ChongbiActivity) {
                        builder.start((ChongbiActivity) mContext, REQUEST_CODE); // 打开相册
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mImages == null ? 0 : mImages.size();
    }

    public void refresh(ArrayList<String> images) {
        mImages = images;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivImage;
        TextView delete;

        public ViewHolder(View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.iv_image);
            delete = itemView.findViewById(R.id.delete);
        }
    }
}
