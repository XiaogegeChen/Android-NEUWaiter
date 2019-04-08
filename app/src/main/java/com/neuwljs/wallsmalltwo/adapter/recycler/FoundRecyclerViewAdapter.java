package com.neuwljs.wallsmalltwo.adapter.recycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.neuwljs.wallsmalltwo.R;
import com.neuwljs.wallsmalltwo.common.LoadMoreRecyclerViewAdapter;
import com.neuwljs.wallsmalltwo.model.gson.Found;
import com.neuwljs.wallsmalltwo.util.StringUtil;
import com.neuwljs.wallsmalltwo.util.ViewUtil;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.neuwljs.wallsmalltwo.model.Constant.PUBLISHER_NAME_DEFAULT;

public class FoundRecyclerViewAdapter
        extends LoadMoreRecyclerViewAdapter<FoundRecyclerViewAdapter.ItemViewHolder, Found> {

    private Context mContext;
    private List<Found> mFoundList;

    public FoundRecyclerViewAdapter(List<Found> list, Context context) {
        super (list, context);
        mFoundList = list;
        mContext = context;
    }

    @Override
    public int getItemViewLayout() {
        return R.layout.fragment_d_a_a_property_recycler_view_item;
    }

    @Override
    public ItemViewHolder getItemViewHolder(View itemView) {
        return new ItemViewHolder (itemView);
    }

    @Override
    public void onItemBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        // 为子项设置内容
        ItemViewHolder itemHolder = (ItemViewHolder) viewHolder;

        Found found = mFoundList.get (position);

        // 没名字就默认"墙小二"
        if(StringUtil.isEmpty (found.getPublisherName ())){
            itemHolder.mPublisherName.setText (PUBLISHER_NAME_DEFAULT);
        }

        ViewUtil.handleView (itemHolder.mInformation, found.getInformation ());
        ViewUtil.handleView (itemHolder.mPublishTime, found.getPublishTime ());
        ViewUtil.handleView (itemHolder.mOwnerName, found.getOwnerName ());
        ViewUtil.handleView (itemHolder.mOwnerId, found.getOwnerId ());
        ViewUtil.handleView (itemHolder.mOwnerCollege, found.getOwnerCollege ());

        // 头像
        String url = found.getPublisherHeadIamge ();
        if(url == null || url.equals ("") || url.equals ("null")){
            itemHolder.mPublisherHeadImage.setImageDrawable (
                    mContext.getResources ().getDrawable (R.drawable.ic_default));
        }else{
            Glide.with (mContext)
                    .load (url)
                    .error (mContext.getResources ().getDrawable (R.drawable.ic_default))
                    .into (itemHolder.mPublisherHeadImage);
        }
    }

    /**
     * 普通项的viewHolder
     */
    static class ItemViewHolder extends RecyclerView.ViewHolder{
        /**
         * 发布者头像
         */
        CircleImageView mPublisherHeadImage;

        /**
         * 发布者姓名
         */
        TextView mPublisherName;

        /**
         * 发布时间
         */
        TextView mPublishTime;

        /**
         * 发布者说的话
         */
        TextView mInformation;

        /**
         * 失主姓名
         */
        TextView mOwnerName;

        /**
         * 失主学号
         */
        TextView mOwnerId;

        /**
         * 失主学院
         */
        TextView mOwnerCollege;

        ItemViewHolder(@NonNull View itemView) {
            super (itemView);

            mPublisherHeadImage = itemView.findViewById (R.id.fragment_d_a_a_publisher_head_image);
            mPublisherName = itemView.findViewById (R.id.fragment_d_a_a_publisher_name);
            mPublishTime = itemView.findViewById (R.id.fragment_d_a_a_publish_time);
            mInformation = itemView.findViewById (R.id.fragment_d_a_a_publisher_information);
            mOwnerCollege = itemView.findViewById (R.id.fragment_d_a_a_owner_college);
            mOwnerId = itemView.findViewById (R.id.fragment_d_a_a_owner_id);
            mOwnerName = itemView.findViewById (R.id.fragment_d_a_a_owner_name);
        }
    }
}