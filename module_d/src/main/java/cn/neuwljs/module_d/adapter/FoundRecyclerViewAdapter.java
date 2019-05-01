package cn.neuwljs.module_d.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.neuwljs.common.adapter.LoadMoreRecyclerViewAdapter;
import cn.neuwljs.common.util.LogUtil;
import cn.neuwljs.common.util.StringUtil;
import cn.neuwljs.module_d.R;
import cn.neuwljs.module_d.model.gson.Found;
import de.hdodenhof.circleimageview.CircleImageView;

import static cn.neuwljs.module_d.Constants.PUBLISHER_NAME_DEFAULT;

public class FoundRecyclerViewAdapter
        extends LoadMoreRecyclerViewAdapter<FoundRecyclerViewAdapter.ItemViewHolder, Found> {

    private static final String TAG = "FoundRecyclerViewAdapter";

    private Context mContext;
    private List<Found> mFoundList;

    public FoundRecyclerViewAdapter(List<Found> list, Context context) {
        super (list, context);
        mFoundList = list;
        mContext = context;
    }

    @Override
    public int getItemViewLayout() {
        return R.layout.module_d_fragment_d_a_a_property_recycler_view_item;
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

        LogUtil.d (TAG, "position: "+position+" found: "+found);

        // 没名字就默认"墙小二"
        if(StringUtil.isEmpty (found.getPublisherName ())){
            itemHolder.mPublisherName.setText (PUBLISHER_NAME_DEFAULT);
        }

        String time = StringUtil.foramtTime (found.getPublishTime ());

        itemHolder.mInformation.setText (found.getInformation ());
        itemHolder.mPublishTime.setText (time);
        itemHolder.mOwnerName.setText (found.getOwnerName ());
        itemHolder.mOwnerId.setText (found.getOwnerId ());
        itemHolder.mOwnerCollege.setText (found.getOwnerCollege ());

        // 头像
        String url = found.getPublisherHeadIamge ();
        if(url == null || url.equals ("") || url.equals ("null")){
            itemHolder.mPublisherHeadImage.setImageDrawable (
                    mContext.getResources ().getDrawable (R.drawable.module_d_head_image_default));
        }else{
            Glide.with (mContext)
                    .load (url)
                    .error (mContext.getResources ().getDrawable (R.drawable.module_d_head_image_default))
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

            mPublisherHeadImage = itemView.findViewById (R.id.module_d_fragment_d_a_a_publisher_head_image);
            mPublisherName = itemView.findViewById (R.id.module_d_fragment_d_a_a_publisher_name);
            mPublishTime = itemView.findViewById (R.id.module_d_fragment_d_a_a_publish_time);
            mInformation = itemView.findViewById (R.id.module_d_fragment_d_a_a_publisher_information);
            mOwnerCollege = itemView.findViewById (R.id.module_d_fragment_d_a_a_owner_college);
            mOwnerId = itemView.findViewById (R.id.module_d_fragment_d_a_a_owner_id);
            mOwnerName = itemView.findViewById (R.id.module_d_fragment_d_a_a_owner_name);
        }
    }
}