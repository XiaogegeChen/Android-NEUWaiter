package com.neuwljs.wallsmalltwo.adapter.recycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.neuwljs.wallsmalltwo.R;
import com.neuwljs.wallsmalltwo.common.LoadMoreRecyclerViewAdapter;
import com.neuwljs.wallsmalltwo.model.gson.Lost;
import com.neuwljs.wallsmalltwo.util.StringUtil;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.neuwljs.wallsmalltwo.model.Constant.PUBLISHER_NAME_DEFAULT;

public class LostRecyclerViewAdapter
        extends LoadMoreRecyclerViewAdapter<LostRecyclerViewAdapter.ItemViewHolder, Lost> {

    private Context mContext;
    private List<Lost> mLostList;

    public LostRecyclerViewAdapter(List<Lost> list, Context context) {
        super (list, context);
        mContext = context;
        mLostList = list;
    }

    @Override
    public int getItemViewLayout() {
        return R.layout.fragment_d_a_b_property_recycler_view_item;
    }

    @Override
    public ItemViewHolder getItemViewHolder(View itemView) {
        return new ItemViewHolder (itemView);
    }

    @Override
    public void onItemBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        // 为子项设置内容
        ItemViewHolder itemHolder = (ItemViewHolder) viewHolder;

        Lost lost = mLostList.get (position);

        // 没名字就默认"墙小二"
        if(StringUtil.isEmpty (lost.getOwnerName ())){
            itemHolder.mOwnerName.setText (PUBLISHER_NAME_DEFAULT);
        }

        String time = StringUtil.foramtTime (lost.getOwnerPublishTime ());

        itemHolder.mOwnerInformation.setText (lost.getOwnerInfo ());
        itemHolder.mOwnerTime.setText (time);
        itemHolder.mOwnerNameCard.setText (lost.getOwnerName ());
        itemHolder.mOwnerIdCard.setText (lost.getOwnerId ());
        itemHolder.mOwnerCollegeCard.setText (lost.getOwnerCollege ());

        // 头像
        String url = lost.getOwnerHeadImage ();
        if(url == null || url.equals ("") || url.equals ("null")){
            itemHolder.mOwnerHeadImage.setImageDrawable (
                    mContext.getResources ().getDrawable (R.drawable.ic_default));
        }else{
            Glide.with (mContext)
                    .load (url)
                    .error (mContext.getResources ().getDrawable (R.drawable.ic_default))
                    .into (itemHolder.mOwnerHeadImage);
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder{

        /**
         * 失主头像
         */
        CircleImageView mOwnerHeadImage;

        /**
         * 失主姓名
         */
        TextView mOwnerName;

        /**
         * 失主发布时间
         */
        TextView mOwnerTime;

        /**
         * 失主说的话
         */
        TextView mOwnerInformation;

        /**
         * 卡片上的失主姓名
         */
        TextView mOwnerNameCard;

        /**
         * 卡片上的失主学号
         */
        TextView mOwnerIdCard;

        /**
         * 卡片上的失主学院
         */
        TextView mOwnerCollegeCard;

        ItemViewHolder(@NonNull View itemView) {
            super (itemView);

            mOwnerHeadImage = itemView.findViewById (R.id.fragment_d_a_b_owner_head_image);
            mOwnerName = itemView.findViewById (R.id.fragment_d_a_b_owner_name);
            mOwnerTime = itemView.findViewById (R.id.fragment_d_a_b_owner_time);
            mOwnerInformation = itemView.findViewById (R.id.fragment_d_a_b_owner_information);
            mOwnerCollegeCard = itemView.findViewById (R.id.fragment_d_a_b_owner_college_card);
            mOwnerIdCard = itemView.findViewById (R.id.fragment_d_a_b_owner_id_card);
            mOwnerNameCard = itemView.findViewById (R.id.fragment_d_a_b_owner_name_card);
        }
    }
}
