package cn.neuwljs.module_d.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import cn.neuwljs.common.adapter.LoadMoreRecyclerViewAdapter;
import cn.neuwljs.common.util.ImageUtil;
import cn.neuwljs.common.util.StringUtil;
import cn.neuwljs.module_d.R;
import cn.neuwljs.module_d.model.gson.Lost;
import de.hdodenhof.circleimageview.CircleImageView;

import static cn.neuwljs.module_d.Constants.PUBLISHER_NAME_DEFAULT;

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
        return R.layout.module_d_fragment_d_a_b_property_recycler_view_item;
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
                    mContext.getResources ().getDrawable (R.drawable.module_d_head_image_default));
        }else{
            ImageUtil.displayImage (mContext,
                    url,
                    mContext.getResources ().getDrawable (R.drawable.module_d_head_image_default),
                    itemHolder.mOwnerHeadImage
            );
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

            mOwnerHeadImage = itemView.findViewById (R.id.module_d_fragment_d_a_b_owner_head_image);
            mOwnerName = itemView.findViewById (R.id.module_d_fragment_d_a_b_owner_name);
            mOwnerTime = itemView.findViewById (R.id.module_d_fragment_d_a_b_owner_time);
            mOwnerInformation = itemView.findViewById (R.id.module_d_fragment_d_a_b_owner_information);
            mOwnerCollegeCard = itemView.findViewById (R.id.module_d_fragment_d_a_b_owner_college_card);
            mOwnerIdCard = itemView.findViewById (R.id.module_d_fragment_d_a_b_owner_id_card);
            mOwnerNameCard = itemView.findViewById (R.id.module_d_fragment_d_a_b_owner_name_card);
        }
    }
}
