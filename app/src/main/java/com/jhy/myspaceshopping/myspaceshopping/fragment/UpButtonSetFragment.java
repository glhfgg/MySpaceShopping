package com.jhy.myspaceshopping.myspaceshopping.fragment;

import android.content.Context;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;
import com.jhy.myspaceshopping.myspaceshopping.R;
import com.jhy.myspaceshopping.myspaceshopping.adapter.FirstClassAdapter;
import com.jhy.myspaceshopping.myspaceshopping.adapter.SecondClassAdapter;

import com.jhy.myspaceshopping.myspaceshopping.objectmode.FirstClassItemModel;
import com.jhy.myspaceshopping.myspaceshopping.objectmode.SecondClassItemModel;
import com.jhy.myspaceshopping.myspaceshopping.util.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TOSHIBA on 2016/4/28.
 */
public class UpButtonSetFragment extends Fragment {
    View v;
    Context context=this.getActivity();
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

    /**
     * 使用PopupWindow显示一级分类和二级分类
     */
    private PopupWindow popupWindow;

    /**
     * 左侧和右侧两个ListView
     */
    private ListView leftLV, rightLV;

    //弹出PopupWindow时，背景变暗的动画
    private Animation animIn, animOut;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.upbuttonset_layout,container,false);
        btnType = (Button)v. findViewById(R.id.btn_type);
        btnCity = (Button)v. findViewById(R.id.btn_city);
        btnOrder = (Button)v. findViewById(R.id.btn_order);
        //Button监听
        btnType.setOnClickListener(btnListener);
        btnCity.setOnClickListener(btnListener);
        btnOrder.setOnClickListener(btnListener);

        animIn = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in_anim);
        animOut = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out_anim);

        return v;
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
        ArrayList<SecondClassItemModel> secondList1 = new ArrayList<SecondClassItemModel>();
        secondList1.add(new SecondClassItemModel(101, "智能排序"));
        secondList1.add(new SecondClassItemModel(102, "好评优先"));
        secondList1.add(new SecondClassItemModel(103, "离我最近"));
    }
    //PopupWindow
    private void initPopup() {
        popupWindow = new PopupWindow(getActivity());
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
        final FirstClassAdapter firstAdapter = new FirstClassAdapter(context, firstList);
        leftLV.setAdapter(firstAdapter);

        //加载左侧第一行对应右侧二级分类
        secondList = new ArrayList<SecondClassItemModel>();
        secondList.addAll(firstList.get(0).getSecondList());
        final SecondClassAdapter secondAdapter = new SecondClassAdapter(context, secondList);
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

    //Button控件的点击事件
    private void btnOnClick() {
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            popupWindow.showAsDropDown(v.findViewById(R.id.main_div_downline));
            popupWindow.setFocusable(true);
        }
    }

    //Button监听事件
    View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_type:
                    typeData();
                    initPopup();
                    btnOnClick();

                    break;
                case R.id.btn_city:
                    cityData();
                    initPopup();
                    btnOnClick();
                    break;
                case R.id.btn_order:
                    orderData();
                    initPopup();
                    btnOnClick();
                    break;
                default:
                    break;
            }
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
       if(btnType.isFocusableInTouchMode()){
           btnType.setText(selectedName);
       }
        if(btnCity.isFocusableInTouchMode()){
            btnCity.setText(selectedName);
        }
        if(btnOrder.isFocusableInTouchMode()){
            btnOrder.setText(selectedName);
        }
    }
}

