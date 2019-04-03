package com.fkgpby0329.yxb.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fkgpby0329.yxb.R;
import com.fkgpby0329.yxb.back.TabOneRecyclerHotInterface;
import com.fkgpby0329.yxb.bean.TabOneHotNewBean;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/**
 * TabOne热点聚焦的适配器
 * @author Frank
 * @date 2018/1/16 12:01
 */

public class TabOneRecyclerAdapter extends RecyclerView.Adapter<TabOneRecyclerAdapter.TabOneRecyclerViewHolder> {
    private LayoutInflater inflater;
    private List<TabOneHotNewBean> newBeanList;
    private ImageOptions options;
    private RecyclerView recyclerView;
    private TabOneRecyclerHotInterface hotInterface;

    public TabOneRecyclerAdapter(Context context,List<TabOneHotNewBean> newBeanList,RecyclerView recyclerView,TabOneRecyclerHotInterface hotInterface) {
        this.newBeanList = newBeanList;
        this.recyclerView = recyclerView;
        this.hotInterface = hotInterface;
        inflater = LayoutInflater.from(context);
        options = new ImageOptions.Builder()
                .setUseMemCache(true)
                .setConfig(Bitmap.Config.RGB_565)
                .build();
    }

    @Override
    public TabOneRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = inflater.inflate(R.layout.tab_one_recycler_hot_layout, parent, false);
        return new TabOneRecyclerViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(TabOneRecyclerViewHolder holder, int position) {
        List<TabOneHotNewBean.DongtaiBean> dongtai = newBeanList.get(0).getDongtai();
        if(dongtai!=null&&dongtai.size()>position){
        TabOneHotNewBean.DongtaiBean dongtaiBean =dongtai.get(position);
        if(dongtaiBean!=null){
            holder.tabOneRecyclerHotTitle.setText(dongtaiBean.getTitle());
            x.image().bind(holder.tabOneRecyclerHotImg,dongtaiBean.getPhotos().get(0).getF(),options);
            if(dongtaiBean.getTc()==null){
                    String hour = dongtaiBean.getUpdate_time().substring(0, 2);
                    String minute = dongtaiBean.getUpdate_time().substring(2, 4);
                    holder.tabOneRecyclerHotTime.setText(hour+":"+minute);
            }else {
                holder.tabOneRecyclerHotTime.setText(dongtaiBean.getTc().getJc_r());
            }
        }
        }
    }

    @Override
    public int getItemCount() {
        return newBeanList==null?0:newBeanList.size();
    }

    class TabOneRecyclerViewHolder extends  RecyclerView.ViewHolder{
        ImageView tabOneRecyclerHotImg;
        TextView tabOneRecyclerHotTitle,tabOneRecyclerHotTime;
        public TabOneRecyclerViewHolder(final View itemView) {
            super(itemView);
            tabOneRecyclerHotImg = (ImageView) itemView.findViewById(R.id.tab_one_recycler_hot_img);
            tabOneRecyclerHotTitle = (TextView) itemView.findViewById(R.id.tab_one_recycler_hot_title);
            tabOneRecyclerHotTime = (TextView) itemView.findViewById(R.id.tab_one_recycler_hot_time);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int childAdapterPosition = recyclerView.getChildAdapterPosition(itemView);
                    hotInterface.onItemClickListener(childAdapterPosition);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int childAdapterPosition = recyclerView.getChildAdapterPosition(itemView);
                    hotInterface.onItemLongClickListener(childAdapterPosition);
                    return true;
                }
            });
        }
    }
}
