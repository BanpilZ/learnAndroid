package com.fkgpby0329.yxb.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fkgpby0329.yxb.activitys.HotNewActivity;
import com.fkgpby0329.yxb.adapter.TabOneRecyclerAdapter;
import com.fkgpby0329.yxb.back.TabOneRecyclerHotInterface;
import com.fkgpby0329.yxb.bean.TabOneHotNewBean;
import com.fkgpby0329.yxb.bean.TabOneMakerBean;
import com.fkgpby0329.yxb.utils.ParseHotNewData;
import com.fkgpby0329.yxb.utils.Tools;

import com.fkgpby0329.yxb.R;

import com.fkgpby0329.yxb.back.TabOneHotNewDataInterface;
import com.fkgpby0329.yxb.factory.TabOneMakerManager;
import com.fkgpby0329.yxb.utils.TaskUtil;
import com.fkgpby0329.yxb.utils.Httpaddress;

import java.util.ArrayList;
import java.util.List;


/**
 * Create by Frank 0n 2018/1/15 14:18
 *
 * @author Frank
 */
public class Tab1 extends Fragment implements TabOneRecyclerHotInterface {
    ProgressDialog pd;
    private SwipeRefreshLayout fragment_tab_swipeRefreshLayout;
    private ViewPager fragment_tab_one_viewPager;
    private LinearLayout fragment_tab_one_linearLayout_maker;
    private RecyclerView fragment_tab_one_recyclerView;
    /**
     * 指示器的容器
     */
    private List<View> makerList;
    private ImageView[] imgsMaker;
    private MakerViewPagerAdapter makerViewPagerAdapter;
    /**
     * 刷新的次数
     */
    private int conutRefresh = 1;
    /**
     * 需要展示的数据源集合
     */
    private List<TabOneHotNewBean> newBeanList = new ArrayList<>();

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                fragment_tab_one_viewPager.setCurrentItem(fragment_tab_one_viewPager.getCurrentItem() + 1);
                if (mHandler != null) {
                    //无限轮询
                    mHandler.sendEmptyMessageDelayed(1, 3000);
                }
            }
        }
    };
    private TabOneRecyclerAdapter tabOneRecyclerAdapter;

    public Tab1() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_one, container, false);
        init(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        FragmentActivity activity = getActivity();
        if (activity != null) {
            int statusBarHeight = Tools.getStatusBarHeight(getContext());
            Log.e("Frank", "==Tab1.onResume==状态栏高度:" + statusBarHeight);
            Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
            if (toolbar != null) {
                int height = toolbar.getHeight();
                if (height == 0) {
                    fragment_tab_swipeRefreshLayout.setPadding(0, statusBarHeight, 0, 0);
                } else {
                    fragment_tab_swipeRefreshLayout.setPadding(0, height / 2, 0, 0);
                }
                Log.e("Frank", "==Tab1.onResume==头布局的高度height:" + height);
            }
        }
        showIndicator();
        showHotNew();
        fragment_tab_swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (tabOneRecyclerAdapter != null) {
                    //每刷新一次重新加载一次
                    newBeanList.clear();
                    refreshData();
                    tabOneRecyclerAdapter.notifyDataSetChanged();
                    fragment_tab_swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    /**
     * 展示热点新闻数据
     */
    private void showHotNew() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        fragment_tab_one_recyclerView.setLayoutManager(linearLayoutManager);
        tabOneRecyclerAdapter = new TabOneRecyclerAdapter(getActivity(), newBeanList, fragment_tab_one_recyclerView, Tab1.this);
        refreshData();
    }

    /**
     * 下载并刷新数据
     */
    private void refreshData() {
        TaskUtil.setProgressDialog(new ProgressDialog(getActivity()));
        TaskUtil.downJsonData(getActivity(), Httpaddress.TAB_ONE_HOT_NEW + conutRefresh + "&per_page=10", 0, new TabOneHotNewDataInterface() {
            @Override
            public void getHotNewData(String strData) {
                if (strData != null) {
                    //刷新前先清空集合数据
                    newBeanList.clear();
                    conutRefresh++;
                    List<TabOneHotNewBean.DongtaiBean> beanList = ParseHotNewData.parseDongTai(strData);
                    for (int i = 0; i < beanList.size(); i++) {
                        TabOneHotNewBean tabOneHotNewBean = new TabOneHotNewBean();
                        tabOneHotNewBean.setDongtai(beanList);
                        newBeanList.add(tabOneHotNewBean);
                        fragment_tab_one_recyclerView.setAdapter(tabOneRecyclerAdapter);
                    }
                }
            }
        });
    }

    /**
     * 展示指示器的数据
     */
    private void showIndicator() {
        makerList = new ArrayList<>();
        TabOneMakerView invoke = new TabOneMakerView().invoke(getActivity());
        View layout1 = invoke.getLayout1();
        View layout2 = invoke.getLayout2();
        View layout3 = invoke.getLayout3();
        View layout4 = invoke.getLayout4();
        makerList.add(layout1);
        makerList.add(layout2);
        makerList.add(layout3);
        makerList.add(layout4);
        //设置指示器
        initIcon();
        //给ViewPager设置适配器
        makerViewPagerAdapter = new MakerViewPagerAdapter();
        fragment_tab_one_viewPager.setAdapter(makerViewPagerAdapter);
        //默认展示第一条数据
        fragment_tab_one_viewPager.setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % makerList.size());
        //设置轮询时间,2s钟轮询一次
        if (mHandler != null) {
            mHandler.sendEmptyMessageDelayed(1, 3000);
        }
        //轮询改变监听
        fragment_tab_one_viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //设置滚动到选中的位置
                fragment_tab_one_viewPager.setCurrentItem(position);
                for (int i = 0; i < makerList.size(); i++) {
                    imgsMaker[i].setImageResource(R.mipmap.dot_normal);
                }
                imgsMaker[position % makerList.size()].setImageResource(R.mipmap.dot_enable);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 设置指示器
     */
    private void initIcon() {
        imgsMaker = new ImageView[makerList.size()];
        for (int i = 0; i < makerList.size(); i++) {
            imgsMaker[i] = new ImageView(getActivity());
            //设置指示器的宽高
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(20, 20);
            imgsMaker[i].setLayoutParams(layoutParams);
            //添加到父容器中
            fragment_tab_one_linearLayout_maker.addView(imgsMaker[i]);
            //给指示器设置图片
            imgsMaker[i].setImageResource(R.mipmap.dot_normal);
            //给当前位置设置tag
            imgsMaker[i].setTag(i);
            //设置点击事件，将指示器和轮询图绑定
            imgsMaker[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragment_tab_one_viewPager.setCurrentItem((Integer) v.getTag(), false);
                }
            });
        }
        //设置默认的第一个图片为选中状态
        imgsMaker[0].setImageResource(R.mipmap.dot_enable);
    }

    private void init(View layout) {
        pd = new ProgressDialog(getActivity());
        pd.setMessage("正在加載，請稍候‧‧‧");
        fragment_tab_swipeRefreshLayout = ((SwipeRefreshLayout) layout.findViewById(R.id.fragment_tab_swipeRefreshLayout));
        fragment_tab_one_viewPager = ((ViewPager) layout.findViewById(R.id.fragment_tab_one_viewPager));
        fragment_tab_one_linearLayout_maker = ((LinearLayout) layout.findViewById(R.id.fragment_tab_one_linearLayout_maker));
        fragment_tab_one_recyclerView = ((RecyclerView) layout.findViewById(R.id.fragment_tab_one_recyclerView));
    }


    /**
     * 解析并展示数据在顶部轮询
     *
     * @param img  顶部轮询图片
     * @param text 轮询文字
     * @param i    轮询的位置
     */
    private void showMakerImage(ImageView img, TextView text, int i) {
        TabOneMakerManager makerManager = TabOneMakerManager.getMakerManager();
        List<TabOneMakerBean> makerBeanList = makerManager.getMakerBeanList(getActivity());
        text.setText(makerBeanList.get(i).getText());
    }

    /**
     * 热点新闻的Item点击事件
     *
     * @param position
     */
    @Override
    public void onItemClickListener(int position) {
        List<TabOneHotNewBean.DongtaiBean> dongtai = newBeanList.get(0).getDongtai();
        if (dongtai != null && dongtai.size() > position) {
            String id = dongtai.get(position).getId();
            Intent intent = new Intent(getActivity(), HotNewActivity.class);
            intent.putExtra("id", id);
//            startActivityForResult(intent, Options.REQUESTCODE_HOT_NEWS);
            if (mHandler != null) {
                mHandler.removeCallbacksAndMessages(null);
                Log.e("Frank", "==Tab1.onItemClickListener==消息滞空");
            }
            conutRefresh = 1;
        }
    }

    /**
     * 长点击事件
     *
     * @param position
     */
    @Override
    public void onItemLongClickListener(int position) {

    }

    /**
     * TabOne顶部指示器
     *
     * @author Frank
     * @date 2018/1/15 17:20
     */
    private class TabOneMakerView {
        private View layout1;
        private View layout2;
        private View layout3;
        private View layout4;
        private ImageView tabOnePagerImg1;
        private TextView tabOnePagerTv1;
        private ImageView tabOnePagerImg2;
        private TextView tabOnePagerTv2;
        private ImageView tabOnePagerImg3;
        private TextView tabOnePagerTv3;
        private ImageView tabOnePagerImg4;
        private TextView tabOnePagerTv4;

        public View getLayout1() {
            return layout1;
        }

        public View getLayout2() {
            return layout2;
        }

        public View getLayout3() {
            return layout3;
        }

        public View getLayout4() {
            return layout4;
        }

        public TabOneMakerView invoke(Context context) {
            layout1 = LayoutInflater.from(context).inflate(R.layout.tab_one_maker_item, null);
            tabOnePagerImg1 = ((ImageView) layout1.findViewById(R.id.tab_one_pager_img1));
            tabOnePagerTv1 = ((TextView) layout1.findViewById(R.id.tab_one_pager_tv1));
            showMakerImage(tabOnePagerImg1, tabOnePagerTv1, 0);

            layout2 = LayoutInflater.from(context).inflate(R.layout.tab_one_maker_item2, null);
            tabOnePagerImg2 = ((ImageView) layout2.findViewById(R.id.tab_one_pager_img2));
            tabOnePagerTv2 = ((TextView) layout2.findViewById(R.id.tab_one_pager_tv2));
            showMakerImage(tabOnePagerImg2, tabOnePagerTv2, 1);

            layout3 = LayoutInflater.from(context).inflate(R.layout.tab_one_maker_item3, null);
            tabOnePagerImg3 = ((ImageView) layout3.findViewById(R.id.tab_one_pager_img3));
            tabOnePagerTv3 = ((TextView) layout3.findViewById(R.id.tab_one_pager_tv3));
            showMakerImage(tabOnePagerImg3, tabOnePagerTv3, 2);

            layout4 = LayoutInflater.from(context).inflate(R.layout.tab_one_maker_item4, null);
            tabOnePagerImg4 = ((ImageView) layout4.findViewById(R.id.tab_one_pager_img4));
            tabOnePagerTv4 = ((TextView) layout4.findViewById(R.id.tab_one_pager_tv4));
            showMakerImage(tabOnePagerImg4, tabOnePagerTv4, 3);
            return this;
        }
    }

    /**
     * 顶部轮播图的适配器
     */
    private class MakerViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(makerList.get(position % makerList.size()));
            return makerList.get(position % makerList.size());
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(makerList.get(position % makerList.size()));
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    public boolean onKeyDown(int keyCode) {
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
        }
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
            Log.e("Frank", "==Tab1.onDestroy==销毁");
        }
        conutRefresh = 1;
        Log.e("Frank", "==Tab1.onDestroy==销毁后");
    }
}

