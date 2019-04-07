package com.neuwljs.wallsmalltwo.adapter.recycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.neuwljs.wallsmalltwo.R;
import com.neuwljs.wallsmalltwo.common.BaseRecyclerViewAdapter;
import com.neuwljs.wallsmalltwo.model.gson.Found;
import com.neuwljs.wallsmalltwo.util.StringUtil;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.neuwljs.wallsmalltwo.model.Constant.PUBLISHER_NAME_DEFAULT;

public class FoundRecyclerViewAdapter
        extends BaseRecyclerViewAdapter<RecyclerView.ViewHolder, Found> {
    /**
     * 子项
     */
    private static final int TYPE_ITEM = 1;

    /**
     * 最后一项,加载页
     */
    private static final int TYPE_FOOT = 2;

    /**
     * 加载完成
     */
    public static final int STATE_DONE = 0;

    /**
     * 正在加载
     */
    public static final int STATE_LOAD = 1;

    /**
     * 加载出错
     */
    public static final int STATE_ERROR = 2;

    // 状态，初始化为加载中
    private int state = STATE_LOAD;

    // 上下文对象
    private Context mContext;

    // 数据源
    private List<Found> mFoundList;

    public FoundRecyclerViewAdapter(List<Found> list, Context context) {
        super (list);
        mContext = context;
        mFoundList = list;
    }

    public void setState(int state) {
        this.state = state;
        notifyDataSetChanged ();
    }

    @Override
    public int getItemViewType(int position) {

        if(position + 1 == getItemCount ()){

            // 最后一项是加载页面
            return TYPE_FOOT;
        }else{
            return TYPE_ITEM;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        switch (viewType){
            case TYPE_FOOT:
                View footView = LayoutInflater.from (mContext).inflate (R.layout.recycler_view_load_more_foot_view,
                        viewGroup, false);
                return new FootViewHolder (footView);
            case TYPE_ITEM:
                View itemView = LayoutInflater.from (mContext).inflate (R.layout.fragment_d_a_a_property_recycler_view_item,
                        viewGroup, false);
                return new ItemViewHolder (itemView);
            default:
                View errorView = LayoutInflater.from (mContext).inflate (R.layout.fragment_d_a_a_property_recycler_view_item,
                        viewGroup, false);
                return new ItemViewHolder (errorView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if(viewHolder instanceof ItemViewHolder){

            // 为子项设置内容
            ItemViewHolder itemHolder = (ItemViewHolder) viewHolder;

            Found found = mFoundList.get (i);

            // 没名字就默认"墙小二"
            if(StringUtil.isEmpty (found.getPublisherName ())){
                itemHolder.mPublisherName.setText (PUBLISHER_NAME_DEFAULT);
            }

            handleTextView (itemHolder.mInformation, found.getInformation ());
            handleTextView (itemHolder.mPublishTime, found.getPublishTime ());
            handleTextView (itemHolder.mOwnerName, found.getOwnerName ());
            handleTextView (itemHolder.mOwnerId, found.getOwnerId ());
            handleTextView (itemHolder.mOwnerCollege, found.getOwnerCollege ());

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
        }else if(viewHolder instanceof FootViewHolder){

            // 为加载项设置内容
            FootViewHolder footHolder = (FootViewHolder) viewHolder;

            switch (state){
                case STATE_LOAD:
                    footHolder.mProgressBar.setVisibility (View.VISIBLE);
                    footHolder.mDoneText.setVisibility (View.INVISIBLE);
                    footHolder.mErrorText.setVisibility (View.INVISIBLE);
                    break;

                case STATE_DONE:
                    footHolder.mProgressBar.setVisibility (View.INVISIBLE);
                    footHolder.mDoneText.setVisibility (View.VISIBLE);
                    footHolder.mErrorText.setVisibility (View.INVISIBLE);
                    break;

                case STATE_ERROR:
                    footHolder.mProgressBar.setVisibility (View.INVISIBLE);
                    footHolder.mDoneText.setVisibility (View.INVISIBLE);
                    footHolder.mErrorText.setVisibility (View.VISIBLE);
                    break;

                default:
                    break;
            }
        }

    }

    @Override
    public int getItemCount() {

        // 因为有底部,所以要加1
        return mFoundList.size () + 1;
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

    /**
     * 加载项的viewHolder
     */
    static class FootViewHolder extends RecyclerView.ViewHolder{
        /**
         * 进度条
         */
        ProgressBar mProgressBar;

        /**
         * 加载错误
         */
        TextView mErrorText;

        /**
         * 加载完毕，不再有数据可以加载了
         */
        TextView mDoneText;

        FootViewHolder(@NonNull View itemView) {
            super (itemView);

            mProgressBar = itemView.findViewById (R.id.load_more_progress);
            mErrorText = itemView.findViewById (R.id.load_more_error);
            mDoneText = itemView.findViewById (R.id.load_more_done);
        }
    }

    /**
     * 微调textView的显示，有数据就显示，没数据直接删除这个textView
     */
    private void handleTextView(TextView view, String text){
        if(StringUtil.isEmpty (text)){
            view.setVisibility (View.GONE);
        }else{
            view.setText (text);
        }
    }

}