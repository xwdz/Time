package com.xwdz.time.adpater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xwdz.time.DetailActivity;
import com.xwdz.time.ImageLoader;
import com.xwdz.time.R;
import com.xwdz.time.entity.Picture;
import com.xwdz.time.util.Format;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xingwei.huang (xwdz9989@gamil.com)
 * @since 2019/3/26
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.PictureHolder> {

    private static final String TAG      = MainAdapter.class.getSimpleName();
    private static final String BASE_URL = "http://47.106.223.246:80/uploads/";

    private List<Picture> mData;
    private Context       mContext;

    public MainAdapter() {
        mData = new ArrayList<>();
    }


    public void update(List<Picture> data) {
        mData.clear();
        add(data);
    }

    public void add(List<Picture> pictures) {
        mData.addAll(pictures);
        notifyDataSetChanged();
    }

    @Override
    public PictureHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_picture_layout, parent, false);
        return new PictureHolder(view);
    }

    @Override
    public void onBindViewHolder(PictureHolder holder, final int position) {
        final Picture picture = mData.get(position);

        holder.time.setText(Format.format(Long.parseLong(picture.getUploadTime())));
        holder.address.setText(picture.getAddress());
        holder.desc.setText(picture.getDesc());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailActivity.start(mContext, mData.get(position));
            }
        });

        ImageLoader.load(mContext, BASE_URL + picture.getName(), holder.imageView);
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class PictureHolder extends RecyclerView.ViewHolder {

        ImageView    imageView;
        TextView     desc;
        TextView     address;
        TextView     time;
        LinearLayout layout;

        public PictureHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.picture);
            desc = itemView.findViewById(R.id.desc);
            address = itemView.findViewById(R.id.address);
            time = itemView.findViewById(R.id.time);
            layout = itemView.findViewById(R.id.layout);
        }
    }
}
