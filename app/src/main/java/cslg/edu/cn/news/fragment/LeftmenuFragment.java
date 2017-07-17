package cslg.edu.cn.news.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import cslg.edu.cn.news.R;
import cslg.edu.cn.news.activity.MainActivity;
import cslg.edu.cn.news.base.BaseFragment;
import cslg.edu.cn.news.page.NewsCenterPager;
import cslg.edu.cn.news.pojo.NewsCats;
import cslg.edu.cn.news.utils.DensityUtil;
import cslg.edu.cn.news.utils.LogUtil;

/**
 * Created by 18049 on 2017/6/13.
 *左侧菜单的fragment
 */

public class LeftmenuFragment extends BaseFragment {

    private ListView listView;
    private LeftmenuFragmentAdapt adapt;
    private List<NewsCats.CatsBean> data;
    /**
     * 点击的位置
     */
    private int prePosition;

    @Override
    public View initView() {
        LogUtil.e("左侧菜单页面初始化");
        listView = new ListView(context);
        listView.setPadding(0, DensityUtil.dip2px(context,40),0,0);
        listView.setDividerHeight(0);   //设置分割线高度为0
        listView.setCacheColorHint(Color.TRANSPARENT);
        //设置按下listview的item不变色
        listView.setSelector(android.R.color.transparent);

        //设置item的点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //1.记录点击的位置，变成红色
                prePosition =position;
                adapt.notifyDataSetChanged();//getCount()-->getView;
                //2.把左侧菜单关闭
                MainActivity mainactivity = (MainActivity) context;
                mainactivity.getSlidingMenu().toggle();   //关->开;开->关
                //3.切换到对应的详情页面
                switchPager(prePosition);

            }
        });

        return listView;
    }

    /**
     * 根据位置切换不同的详情页面
     * @param position
     */
    private void switchPager(int position) {
        MainActivity mainactivity = (MainActivity) context;
        ContentFragment contentFragment = mainactivity.getContentFragment();
        NewsCenterPager newsCenterPager = contentFragment.getNewsCenterPager();
        newsCenterPager.switchPager(position);
    }

    @Override
    public void initDate() {
        super.initDate();
        LogUtil.e("左侧菜单数据初始化");
    }

    /**
     * 接收数据
     * @param data
     */
    public void setData(List<NewsCats.CatsBean> data) {
        this.data = data;
        for (int i=0;i<data.size();i++){
            LogUtil.e("catName=="+data.get(i).getCatName());
        }

        //设置适配器
        adapt = new LeftmenuFragmentAdapt();
        listView.setAdapter(adapt);
        //设置默认页面
        switchPager(prePosition);
    }
    class LeftmenuFragmentAdapt extends BaseAdapter{

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = (TextView)View.inflate(context,R.layout.item_leftmenu,null);
            textView.setText(data.get(position).getCatName());
            textView.setEnabled(position==prePosition);
            return textView;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
    }
}
