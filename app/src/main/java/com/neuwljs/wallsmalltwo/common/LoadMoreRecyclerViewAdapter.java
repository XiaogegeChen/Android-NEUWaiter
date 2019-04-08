package com.neuwljs.wallsmalltwo.common;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.neuwljs.wallsmalltwo.R;

import java.util.List;

/**
 * 含有加载项的recyclerView的adapter
 * @param <VH> 子项的viewHolder
 * @param <T> 子项的数据类型
 */
public abstract class LoadMoreRecyclerViewAdapter<VH extends RecyclerView.ViewHolder,T>
        extends BaseRecyclerViewAdapter<RecyclerView.ViewHolder, T> {

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

    // 当前状态
    private int state = STATE_LOAD;

    // 数据源
    private List<T> mList;

    // 上下文对象
    private Context mContext;

    public LoadMoreRecyclerViewAdapter(List<T> list, Context context) {
        super (list);
        mList = list;
        mContext = context;
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
                View FootView = LayoutInflater.from (mContext).inflate (R.layout.recycler_view_load_more_foot_view,
                        viewGroup, false);
                return new FootViewHolder (FootView);

            case TYPE_ITEM:
                View itemView = LayoutInflater.from (mContext).inflate (getItemViewLayout (),
                        viewGroup, false);
                return getItemViewHolder (itemView);
            default:
                View errorView = LayoutInflater.from (mContext).inflate (getItemViewLayout (),
                        viewGroup, false);
                return getItemViewHolder (errorView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if(viewHolder instanceof FootViewHolder){
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
        }else{
            onItemBindViewHolder (viewHolder, i);
        }
    }

    @Override
    public int getItemCount() {

        // 因为有底部,所以要加1
        return mList.size () + 1;
    }

    // 拿到子项布局的id
    public abstract int getItemViewLayout();

    // 拿到子项的viewHolder
    public abstract VH getItemViewHolder(View itemView);

    // 子项onBindViewHolder的具体实现
    public abstract void onItemBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position);

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
}
