package com.commonadapter.common.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.commonadapter.R;
import com.commonadapter.common.anim.ExpandableViewHoldersUtil;
import com.commonadapter.common.bean.RecyBean;

import java.util.List;


public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> implements View.OnClickListener {
    private Context context;
    private List<RecyBean> dataList;
    private List<View> viewList;
    private RecyclePageAdapter recyclePageAdapter;



    public MainAdapter(Context context, List<RecyBean> dataList, List<View> viewList) {
        this.context = context;
        this.dataList = dataList;
        this.viewList = viewList;
        recyclePageAdapter = new RecyclePageAdapter(context, viewList);
    }

    ExpandableViewHoldersUtil.KeepOneH<MainViewHolder> keepOne = new ExpandableViewHoldersUtil.KeepOneH<MainViewHolder>();

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int pos) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item, parent, false);
        MainViewHolder vh = new MainViewHolder((ViewGroup) view);
        //将创建的View注册点击事件
        return vh;

    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            MainViewHolder viewHolder = (MainViewHolder) v.getTag();
            mOnItemClickListener.onItemClick(v, (MainViewHolder) v.getTag(), keepOne, viewHolder.getPosition());
        }
    }


    @Override
    public void onBindViewHolder(final MainViewHolder holder, int pos) {
        RecyBean recyBean = dataList.get(pos);
        holder.tv_item_name.setText(recyBean.getName());
        holder.tv_item_money.setText(recyBean.getMoney());
        keepOne.bind(holder, pos);
        holder.rl_item_context.setTag(holder);
    //    holder.rl_item_context.setOnClickListener(this);
        //viewPage设置适配器
        holder.vp_item_label.setAdapter(recyclePageAdapter);
        holder.vp_item_label.setOffscreenPageLimit(4);
        holder.vp_item_label.setCurrentItem(0);


        //添加下面的小圆点
        ImageView iv;
        View view;
        LinearLayout.LayoutParams params;

       for (int i = 0; i < viewList.size(); i++) {
            // 每循环一次添加一个点到现形布局中
            view = new View(context);
            view.setBackgroundResource(R.drawable.point_background);
            params = new LinearLayout.LayoutParams(10, 10);
            params.leftMargin = 20;
            view.setEnabled(false);
            view.setLayoutParams(params);
            holder.ll_item_point_group.addView(view); // 向线性布局中添加“点”
        }
        holder.ll_item_point_group.getChildAt(0).setEnabled(true);

        //viewPage添加监听
        holder.vp_item_label.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public int preEnablePositon = 0; // 前一个被选中的点的索引位置 默认情况下为0
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                // 取余后的索引
                int newPositon = position % viewList.size();
                // 根据索引设置那个点被选中
                holder.ll_item_point_group.getChildAt(newPositon).setEnabled(true);
                // 把上一个点设置为被选中
                holder.ll_item_point_group.getChildAt(preEnablePositon).setEnabled(false);
                preEnablePositon = newPositon;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    protected int getPosition(RecyclerView.ViewHolder viewHolder) {
        return viewHolder.getAdapterPosition();
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public class MainViewHolder extends RecyclerView.ViewHolder implements ExpandableViewHoldersUtil.Expandable {

        public final RelativeLayout rl_item_context;
        public final ViewPager vp_item_label;
        public final TextView tv_item_name;
        public final TextView tv_item_money;
        public final LinearLayout ll_item_point_group;
        public final LinearLayout ll_item_view_page;

        public MainViewHolder(ViewGroup itemView) {
            super(itemView);
            rl_item_context = ((RelativeLayout) itemView.findViewById(R.id.rl_item_context));
            tv_item_name = (TextView) itemView.findViewById(R.id.tv_item_name);
            vp_item_label = ((ViewPager) itemView.findViewById(R.id.vp_item_label));
            tv_item_money = (TextView) itemView.findViewById(R.id.tv_item_money);
            ll_item_point_group = (LinearLayout) itemView.findViewById(R.id.ll_item_point_group);
            ll_item_view_page= (LinearLayout) itemView.findViewById(R.id.ll_item_view_page);
        }
        @Override
        public View getExpandView() {
            return ll_item_view_page;
        }
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, MainViewHolder mainViewHolder, ExpandableViewHoldersUtil.KeepOneH<MainViewHolder> keepOne, int position);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    public void addItem(RecyBean content, int position) {
        dataList.add(position, content);
       notifyDataSetChanged();
    }
}
