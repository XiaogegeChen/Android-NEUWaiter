package cn.neuwljs.widget.dialog;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import cn.neuwljs.widget.R;

public class Widget_MessageListDialog extends LinearLayout {

    private OnMessageItemClickListener mOnMessageItemClickListener;
    private MyRecyclerViewAdapter mMyRecyclerViewAdapter;

    public Widget_MessageListDialog(Context context) {
        this (context, null);
    }

    public Widget_MessageListDialog(Context context, AttributeSet attrs) {
        this (context, attrs, 0);
    }

    public Widget_MessageListDialog(Context context, AttributeSet attrs, int defStyleAttr) {
        super (context, attrs, defStyleAttr);

        //加载布局
        LayoutInflater.from (context).inflate (R.layout.widget_message_list_dialog, this);

        // 拿到控件
        TextView cancel = findViewById (R.id.widget_message_list_dialog_cancel_button);
        RecyclerView recyclerView = findViewById (R.id.widget_message_list_dialog_recycler_view);

        // 拿到资源
        TypedArray typedArray = context.obtainStyledAttributes (attrs, R.styleable.Widget_MessageListDialog);

        // 拿到属性
        CharSequence[] messageList = typedArray.getTextArray (R.styleable.Widget_MessageListDialog_widget_message_list);

        // 设置到布局
        mMyRecyclerViewAdapter = new MyRecyclerViewAdapter (Arrays.asList (messageList), context, mOnMessageItemClickListener);
        recyclerView.setAdapter (mMyRecyclerViewAdapter);
        recyclerView.setLayoutManager (new LinearLayoutManager (context));

        cancel.setOnClickListener (v -> mOnMessageItemClickListener.cancel ());

        // 回收资源
        typedArray.recycle ();
    }

    public void setOnMessageItemClickListener(OnMessageItemClickListener onMessageItemClickListener) {
        mOnMessageItemClickListener = onMessageItemClickListener;
        mMyRecyclerViewAdapter.setOnMessageItemClickListener (mOnMessageItemClickListener);
    }

    /**
     * 条目点击监听器
     */
    public interface OnMessageItemClickListener{
        void messageItemClick(CharSequence message);
        void cancel();
    }

    private static class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>{

        private List<CharSequence> mList;
        private Context mContext;
        private OnMessageItemClickListener mOnMessageItemClickListener;

        MyRecyclerViewAdapter(List<CharSequence> list, Context context, OnMessageItemClickListener onMessageItemClickListener) {
            mList = list;
            mContext = context;
            mOnMessageItemClickListener = onMessageItemClickListener;
        }

        void setOnMessageItemClickListener(OnMessageItemClickListener onMessageItemClickListener) {
            mOnMessageItemClickListener = onMessageItemClickListener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from (mContext)
                    .inflate (R.layout.widget_message_list_dialog_recycler_item, viewGroup, false);
            return new ViewHolder (view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            CharSequence message = mList.get (i);
            viewHolder.mTextView.setText (message);

            if (mOnMessageItemClickListener != null) {
                viewHolder.mTextView.setOnClickListener (v -> mOnMessageItemClickListener.messageItemClick (message));
            }
        }

        @Override
        public int getItemCount() {
            return mList.size ();
        }

        static class ViewHolder extends RecyclerView.ViewHolder{

            TextView mTextView;

            ViewHolder(@NonNull View itemView) {
                super (itemView);
                mTextView = itemView.findViewById (R.id.widget_message_list_dialog_recycler_item_text);
            }
        }
    }
}
