package com.jhy.myspaceshopping.myspaceshopping.fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.jhy.myspaceshopping.myspaceshopping.R;
import com.jhy.myspaceshopping.myspaceshopping.activity.HomeSearchActivity;
import com.jhy.myspaceshopping.myspaceshopping.activity.MapBaiduActivity;
import com.jhy.myspaceshopping.myspaceshopping.adapter.FirstClassAdapter;
import com.jhy.myspaceshopping.myspaceshopping.adapter.PagerAdapter;
import com.jhy.myspaceshopping.myspaceshopping.adapter.SecondClassAdapter;
import com.jhy.myspaceshopping.myspaceshopping.objectmode.FirstClassItemModel;
import com.jhy.myspaceshopping.myspaceshopping.objectmode.SecondClassItemModel;
import com.jhy.myspaceshopping.myspaceshopping.util.ScreenUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TOSHIBA on 2016/4/28.
 */
public class BusinessFragment extends Fragment {
    View v;
    View upButtonView;
    ViewPager vpagerStore;//ViewPager容器
    FragmentManager fragmentManager;//Fragment(自定义控件)管理器
    List<Fragment> list;//存放Fragment(自定义控件)的集合
    RadioGroup grpBusiness;
    RadioButton rbtnBusinessAll;//全部商家
    RadioButton rbtnBusinessCheap;//优惠商家
    RadioButton rbtnMap;//定位
    RadioButton rbtnSearch;//搜索
    int whichButton;
    //popupWindow
    Button btnType;//全部分类
    Button btnCity;//全城
    Button btnOrder;//智能排序
    /**
     * 左侧一级分类的数据
     */
    private List<FirstClassItemModel> firstList;
    /**
     * 右侧二级分类的数据
     */
    private List<SecondClassItemModel> secondList;
   //排序order的数据
    private List<FirstClassItemModel> FirstListOrder;
    /**
     * 使用PopupWindow显示一级分类和二级分类
     */
    private PopupWindow popupWindow;

    /**
     * 左侧和右侧两个ListView--popupWindow
     * 只有一个ListView --popupWindow
     */
    private ListView leftLV, rightLV;
    private ListView orderLV;

    //弹出PopupWindow时，背景变暗的动画
    private Animation animIn, animOut;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //将布局文件转化成view对象
        v = inflater.inflate(R.layout.fragment_business, container, false);
        upButtonView = inflater.inflate(R.layout.upbuttonset_layout,null);
        //控件初始化
        vpagerStore = (ViewPager) v.findViewById(R.id.vpager_storeall);
        grpBusiness = (RadioGroup) v.findViewById(R.id.grp_business);
        rbtnBusinessAll = (RadioButton) v.findViewById(R.id.rbtn_businessall);
        rbtnBusinessCheap = (RadioButton) v.findViewById(R.id.rbtn_businesscheap);
        rbtnMap = (RadioButton) v.findViewById(R.id.rbtn_map);
        rbtnSearch = (RadioButton) v.findViewById(R.id.rbtn_search);

        btnType = (Button)v. findViewById(R.id.btn_type);
        btnCity = (Button)v. findViewById(R.id.btn_city);
        btnOrder = (Button)v. findViewById(R.id.btn_order);
        //Button监听  --全部分类、全城、智能排序
        btnType.setOnClickListener(btnListener);
        btnCity.setOnClickListener(btnListener);
        btnOrder.setOnClickListener(btnListener);
        //弹出PopupWindow时，背景变暗的动画
        animIn = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in_anim);
        animOut = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out_anim);

        //添加监听事件
        //ViewPager监听
        vpagerStore.setOnPageChangeListener(vpagerListener);
        //RadioButton监听  --全部商家、优惠商家 /定位、搜索
        rbtnBusinessAll.setOnClickListener(rbtnListener);
        rbtnBusinessCheap.setOnClickListener(rbtnListener);
        rbtnMap.setOnClickListener(rbtnListener);
        rbtnSearch.setOnClickListener(rbtnListener);
        //support.v4  低版本获得fragmentmanager
        fragmentManager = getFragmentManager();
        //fragmentMtanager管理的fragment
        data();
        //自定义的PagerAdapter，将ViewPager与fragment建立连接
        PagerAdapter pagerAdap = new PagerAdapter(fragmentManager, list);
        vpagerStore.setAdapter(pagerAdap);
        Log.i("gl","666666666666666"+"BusinessFragment Create了");
        return v;

    }

    //ViewPager监听
    //当选择某一Fragment则切换到对应位置的RadioButton
    ViewPager.OnPageChangeListener vpagerListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //当选择某一Fragment则切换到对应位置的RadioButton
            RadioButton rb = (RadioButton) grpBusiness.getChildAt(position);
            rb.setChecked(true);
            rb.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    //RadioButton监听 （全部商家、优惠商家）
    //当选择某一按钮则切换到对应位置的ViewPager中的Fragment
    View.OnClickListener rbtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.rbtn_businessall:
                    vpagerStore.setCurrentItem(0);//当选择某一按钮则切换到对应位置的ViewPager中的Fragment
                    break;
                case R.id.rbtn_businesscheap:
                    vpagerStore.setCurrentItem(1);
                    break;
                case R.id.rbtn_map:
                    Intent intentMap = new Intent(getActivity(), MapBaiduActivity.class);
                    startActivity(intentMap);
                    break;
                case R.id.rbtn_search:
                    Intent intentSearch = new Intent(getActivity(), HomeSearchActivity.class);
                    startActivity(intentSearch);
                    break;
                default:
                    break;
            }
        }
    };

    //往FragmentManager中加入两个Fragment
    private void data() {
        list = new ArrayList<Fragment>();
        list.add(new StoreAllFragment());
        list.add(new StoreCheapFragment());
        Log.i("gl","DDDDDDDDDDDDDDDDD"+"data()执行了");
    }

    //PopupWindow中ListView中存进的数据
    //全部分类--data
    private void typeData() {
        firstList = new ArrayList<FirstClassItemModel>();
        //1
        ArrayList<SecondClassItemModel> secondList1 = new ArrayList<SecondClassItemModel>();
        secondList1.add(new SecondClassItemModel(101, "自助餐"));
        secondList1.add(new SecondClassItemModel(102, "西餐"));
        secondList1.add(new SecondClassItemModel(103, "川菜"));
        firstList.add(new FirstClassItemModel(1, "美食", secondList1));
        //2
        ArrayList<SecondClassItemModel> secondList2 = new ArrayList<SecondClassItemModel>();
        secondList2.add(new SecondClassItemModel(201, "天津"));
        secondList2.add(new SecondClassItemModel(202, "北京"));
        secondList2.add(new SecondClassItemModel(203, "秦皇岛"));
        secondList2.add(new SecondClassItemModel(204, "沈阳"));
        secondList2.add(new SecondClassItemModel(205, "大连"));
        secondList2.add(new SecondClassItemModel(206, "哈尔滨"));
        secondList2.add(new SecondClassItemModel(207, "锦州"));
        secondList2.add(new SecondClassItemModel(208, "上海"));
        secondList2.add(new SecondClassItemModel(209, "杭州"));
        secondList2.add(new SecondClassItemModel(210, "南京"));
        secondList2.add(new SecondClassItemModel(211, "嘉兴"));
        secondList2.add(new SecondClassItemModel(212, "苏州"));
        firstList.add(new FirstClassItemModel(2, "旅游", secondList2));
        //3
        ArrayList<SecondClassItemModel> secondList3 = new ArrayList<SecondClassItemModel>();
        secondList3.add(new SecondClassItemModel(301, "南开区"));
        secondList3.add(new SecondClassItemModel(302, "和平区"));
        secondList3.add(new SecondClassItemModel(303, "河西区"));
        secondList3.add(new SecondClassItemModel(304, "河东区"));
        secondList3.add(new SecondClassItemModel(305, "滨海新区"));
        firstList.add(new FirstClassItemModel(3, "电影", secondList3));
        //4
        firstList.add(new FirstClassItemModel(4, "手机", new ArrayList<SecondClassItemModel>()));
        //5
        firstList.add(new FirstClassItemModel(5, "娱乐", null));
        //6
        firstList.add(new FirstClassItemModel(6, "全部分类", null));
        //copy
        firstList.addAll(firstList);
    }
    //全城--data
    private void cityData() {
        firstList = new ArrayList<FirstClassItemModel>();
        //1
        ArrayList<SecondClassItemModel> secondList1 = new ArrayList<SecondClassItemModel>();
        secondList1.add(new SecondClassItemModel(101, "1km"));
        secondList1.add(new SecondClassItemModel(102, "3km"));
        secondList1.add(new SecondClassItemModel(103, "5km"));
        secondList1.add(new SecondClassItemModel(104, "全城"));
        firstList.add(new FirstClassItemModel(1, "附近", secondList1));
        //2
        ArrayList<SecondClassItemModel> secondList2 = new ArrayList<SecondClassItemModel>();
        secondList2.add(new SecondClassItemModel(201, "阅马场"));
        secondList2.add(new SecondClassItemModel(202, "武昌火车站"));
        secondList2.add(new SecondClassItemModel(203, "鲁巷"));
        secondList2.add(new SecondClassItemModel(204, "江汉路步行街"));
        firstList.add(new FirstClassItemModel(2, "推荐商圈", secondList2));
        //3
        ArrayList<SecondClassItemModel> secondList3 = new ArrayList<SecondClassItemModel>();
        secondList3.add(new SecondClassItemModel(301, "全部"));
        secondList3.add(new SecondClassItemModel(302, "邾城街"));
        firstList.add(new FirstClassItemModel(3, "新洲区", secondList3));
        //4
        firstList.add(new FirstClassItemModel(4, "汉南区", new ArrayList<SecondClassItemModel>()));
        //5
        firstList.add(new FirstClassItemModel(5, "江夏区", null));
        //6
        firstList.add(new FirstClassItemModel(6, "青山区", null));
        //copy
        firstList.addAll(firstList);
    }

    // 智能排序--data
    private void orderData() {
        FirstListOrder = new ArrayList<FirstClassItemModel>();
        FirstListOrder.add(new FirstClassItemModel(1, "智能排序",null));
        FirstListOrder.add(new FirstClassItemModel(2, "好评优先",null));
        FirstListOrder.add(new FirstClassItemModel(3, "离我最近",null));
    }

    //PopupWindow
    private void initPopup() {
        popupWindow = new PopupWindow(this.getActivity());
        //存放popupWindow中的空间的view
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.popuplevel_layout, null);
        leftLV = (ListView) view.findViewById(R.id.pop_listview_left);
        rightLV = (ListView) view.findViewById(R.id.pop_listview_right);
        popupWindow.setContentView(view);
        popupWindow.setBackgroundDrawable(new PaintDrawable());
        popupWindow.setFocusable(true);
        //LayoutParams 属性参数
        popupWindow.setHeight(ScreenUtils.getScreenH(this.getActivity()) * 2 / 3);
        popupWindow.setWidth(ScreenUtils.getScreenW(this.getActivity()));

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                leftLV.setSelection(0);
                rightLV.setSelection(0);
            }
        });

        //为了方便扩展，这里的两个ListView均使用BaseAdapter.如果分类名称只显示一个字符串，建议改为ArrayAdapter.
        //加载一级分类
        final FirstClassAdapter firstAdapter = new FirstClassAdapter(this.getActivity(), firstList);
        leftLV.setAdapter(firstAdapter);

        //加载左侧第一行对应右侧二级分类
        secondList = new ArrayList<SecondClassItemModel>();
        secondList.addAll(firstList.get(0).getSecondList());
        final SecondClassAdapter secondAdapter = new SecondClassAdapter(this.getActivity(), secondList);
        rightLV.setAdapter(secondAdapter);

        //左侧ListView点击事件
        leftLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //二级数据
                List<SecondClassItemModel> listSecond = firstList.get(position).getSecondList();
                //如果没有二级类，则直接跳转
                if (listSecond == null || listSecond.size() == 0) {
                    popupWindow.dismiss();

                    int firstId = firstList.get(position).getId();
                    String selectedName = firstList.get(position).getName();
                    result(selectedName);
                    return;
                }

                FirstClassAdapter adapter = (FirstClassAdapter) (parent.getAdapter());
                //如果上次点击的就是这一个item，则不进行任何操作
                if (adapter.getSelectPosition() == position) {
                    return;
                }

                //根据左侧一级分类选中情况，更新背景色
                adapter.setSelectPosition(position);
                adapter.notifyDataSetChanged();

                //显示右侧二级分类
                updateSecondListView(listSecond, secondAdapter);
            }
        });

        //右侧ListView点击事件
        rightLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //关闭popupWindow，显示用户选择的分类
                popupWindow.dismiss();

                int firstPosition = firstAdapter.getSelectPosition();
                int firstId = firstList.get(firstPosition).getId();
                int secondId = firstList.get(firstPosition).getSecondList().get(position).getId();
                String selectedName = firstList.get(firstPosition).getSecondList().get(position)
                        .getName();
                result(selectedName);
            }
        });
    }

    private void initOrderPop(){
        popupWindow = new PopupWindow(this.getActivity());
        //存放popupWindow中的空间的view
        View viewOrder = LayoutInflater.from(getActivity()).inflate(R.layout.popuplevelorder_layout, null);
        orderLV = (ListView) viewOrder.findViewById(R.id.pop_listview_order);
        popupWindow.setContentView(viewOrder);
        popupWindow.setFocusable(true);
        //LayoutParams 属性参数
        popupWindow.setHeight(ViewPager.LayoutParams.WRAP_CONTENT);
        popupWindow.setWidth(ViewPager.LayoutParams.MATCH_PARENT);
         orderData();
        final FirstClassAdapter secondOrderAdapter = new FirstClassAdapter(this.getActivity(),FirstListOrder);
        orderLV.setAdapter(secondOrderAdapter);

        orderLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //关闭popupWindow，显示用户选择的分类
                popupWindow.dismiss();
                String selectedName=FirstListOrder.get(position).getName();
                result(selectedName);
            }
        });
    }
    //Button控件的点击事件
    private void btnOnClick() {
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            popupWindow.showAsDropDown(v.findViewById(R.id.include_upbuttonset));
            popupWindow.setFocusable(true);
        }
    }

    //Button监听事件
    View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_type:
                    whichButton=1;
                    typeData();
                    initPopup();
                   break;
                case R.id.btn_city:
                    whichButton=2;
                    cityData();
                    initPopup();
                   break;
                case R.id.btn_order:
                    whichButton=3;
                    orderData();
                    initOrderPop();
                    break;
                default:
                    break;
            }
             btnOnClick();
        }
    };

    //刷新右侧ListView
    private void updateSecondListView(List<SecondClassItemModel> listSecond, SecondClassAdapter secondAdapter) {
        secondList.clear();
        secondList.addAll(listSecond);
        secondAdapter.notifyDataSetChanged();
    }


    //处理点击结果
    private void  result(String selectedName) {
        Toast.makeText(this.getActivity(), selectedName, Toast.LENGTH_SHORT).show();
        switch (whichButton){
            case 1:
                btnType.setText(selectedName);
                //Log.i("qff","1111111111111");
                break;
            case 2:
                btnCity.setText(selectedName);
                break;
            case 3:
                btnOrder.setText(selectedName);
                break;
            default:
                break;
        }


    }
}
