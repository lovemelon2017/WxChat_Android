package com.winderinfo.wechat.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.winderinfo.wechat.ChatDetailsActivity;
import com.winderinfo.wechat.HelpSendActivity;
import com.winderinfo.wechat.R;
import com.winderinfo.wechat.adapter.HomeRvAdapter;
import com.winderinfo.wechat.api.OnClickDialogEditInterface;
import com.winderinfo.wechat.api.OnClickDialogInterface;
import com.winderinfo.wechat.base.BaseFragment;
import com.winderinfo.wechat.bean.WxBean;
import com.winderinfo.wechat.db.DbUtil;
import com.winderinfo.wechat.event.GroupEvent;
import com.winderinfo.wechat.event.MyEvent;
import com.winderinfo.wechat.view.EditMessageCenterDialog;
import com.winderinfo.wechat.view.HomEditDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;

import butterknife.BindView;

/**
 * 微信首页
 */
public class HomeFragment extends BaseFragment {

    public static final String chatContents[] = {"今天下雪了", "快来啊", "你怎么了", "哎，没钱了", "我太难了", "车到了",
            "正在忙，稍等", "OK"};

    public static final String chatHeads[] = {"咚咚打次", "瓦乌李小区群", "512宿舍", "公司群",
            "郑州大学交流群", "薪资交流群", "小吃群", "快递外卖群"};


    public static final String subContents[] = {"AI前线,对话阿里巴巴:如何成为一个优秀的架构师",
            "你的男友VS别人家的男友", "好消息!,电话、网络、微信...河南看病有了新平台!", "微信公众号开始测试收费功能"
    };

    public static final String txNews[] = {"电商年货节大战:鼠元素商品火了,鼠年内裤了解下", "中国经济成绩单今揭晓这些看点需关注", "纪检“内鬼”犯案细节曝光", "万达电影复苏了？", "开车走高速免费路段为何扣了两毛一？ETC系统正优化"};

    public static final String fcNews[] = {"春节到了", "丰巢给你带来了福利", "丰巢智能柜或将迎来新改革"};

    @BindView(R.id.home_rv)
    RecyclerView mRv;
    HomeRvAdapter mAdapter;

    TimePickerView timePickerView;//时间选择器

    @BindView(R.id.home_title_tv)
    TextView tvHomeTitle;


    @Override
    public int getViewId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        //StatusBarUtil.setColor(getActivity(), getResources().getColor(R.color.cl_background),1);
        getDefaultData();

        mRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new HomeRvAdapter(getDefaultData());
        mRv.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //点击
                List<WxBean> data = adapter.getData();
                WxBean bean = data.get(position);
                if (bean.getType() == WxBean.TYPE_CHAT || bean.getType() == WxBean.TYPE_GROUP) {
                    Intent intent = new Intent(getActivity(), ChatDetailsActivity.class);
                    intent.putExtra("bean", bean);
                    startActivity(intent);
                } else if (bean.getType() == WxBean.TYPE_CHAT_GROUP_SEND) {
                    Intent intent = new Intent(getActivity(), HelpSendActivity.class);
                    startActivity(intent);
                }

            }
        });

        mAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                List<WxBean> data = adapter.getData();
                for (int i = 0; i < data.size(); i++) {
                    data.get(i).setLongCheck(false);
                }
                data.get(position).setLongCheck(true);
                mAdapter.notifyDataSetChanged();

                WxBean wxBean = data.get(position);
                showDialog(wxBean);


                return true;
            }
        });

    }

    private void initTimePicker(WxBean bean) {

        timePickerView = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                long selectTime = date.getTime();
                long currentTimeMillis = System.currentTimeMillis();

                if (selectTime > currentTimeMillis) {
                    ToastUtils.showShort("时间不能超过当前时间");
                    return;
                }
                bean.setChatTime(selectTime);
                DbUtil.insert(bean);
                //刷新数据
                List<WxBean> beans = DbUtil.queryAll();
                Collections.sort(beans, new Comparator<WxBean>() {
                    @Override
                    public int compare(WxBean wxBean, WxBean t1) {
                        long time = t1.getChatTime() - wxBean.getChatTime();
                        if (time > 0) {
                            return 1;
                        } else if (time == 0) {
                            return 0;
                        } else {
                            return -1;
                        }
                    }
                });

                mAdapter.setNewData(beans);
                resetList();


            }
        })
                .setType(new boolean[]{true, true, true, true, true, true})// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setContentTextSize(18)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleText("修改时间")//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.parseColor("#08C261"))//确定按钮文字颜色
                .setCancelColor(Color.parseColor("#808080"))//取消按钮文字颜色
                .setBgColor(getResources().getColor(R.color.cl_fff))//滚轮背景颜色 Night mode

                .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(true)//是否显示为对话框样式
                .build();

        timePickerView.show();
    }

    private void showDialog(WxBean wxBean) {
        HomEditDialog dialog = new HomEditDialog();
        dialog.setWxBean(wxBean);
        boolean isRead = wxBean.getIsRead();
        dialog.setRead(isRead);

        dialog.setClick(new OnClickDialogEditInterface() {
            @Override
            public void onCancel() {

            }

            @Override
            public void onStateRead(boolean state) {

                wxBean.setIsRead(state);
                DbUtil.insert(wxBean);
                dialog.dismiss();
                EventBus.getDefault().post(new MyEvent(2));


            }

            @Override
            public void onEditNum(WxBean wxBean) {

                if (wxBean.getType() == 1 || wxBean.getType() == 2) {
                    showEditNumDialog(wxBean);
                    dialog.dismiss();
                } else {
                    ToastUtils.showShort("该消息不支持修改数量");
                }


            }

            @Override
            public void onEditTime(WxBean wxBean) {
                initTimePicker(wxBean);
                dialog.dismiss();

            }

            @Override
            public void onDelete(WxBean wxBean) {
                DbUtil.delete(wxBean);
                getNewList();
                dialog.dismiss();
                EventBus.getDefault().post(new MyEvent(2));
            }
        });

        dialog.setmOnClickListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                resetList();
            }
        });

        dialog.show(getFragmentManager(), "dialog");
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * 数量编辑
     *
     * @param mBean
     */
    private void showEditNumDialog(WxBean mBean) {
        EditMessageCenterDialog editDialog = new EditMessageCenterDialog();
        editDialog.setDialogInterface(new OnClickDialogInterface() {
            @Override
            public void onCancel() {
                editDialog.dismiss();
            }

            @Override
            public void onSure(String num) {
                hideSoftKeyboard();
                mBean.setIsRead(false);
                mBean.setChatNum(num);
                DbUtil.insert(mBean);
                EventBus.getDefault().post(new MyEvent(2));
                editDialog.dismiss();
                getNewList();
                resetList();

            }
        });
        editDialog.show(getFragmentManager(), "editDialog");

    }

    //恢复
    private void resetList() {

        int numAll = 0;


        List<WxBean> data = mAdapter.getData();
        for (int i = 0; i < data.size(); i++) {
            WxBean wxBean = data.get(i);
            if (wxBean.getType() == 1 || wxBean.getType() == 2) {
                if (!wxBean.isRead()) {
                    String chatNum = wxBean.getChatNum();
                    int item = Integer.valueOf(chatNum);
                    numAll = numAll + item;
                }
            }

            wxBean.setLongCheck(false);
        }
        mAdapter.notifyDataSetChanged();

        if (numAll > 0) {
            tvHomeTitle.setText("微信(" + numAll + ")");
        } else {
            tvHomeTitle.setText("微信");
        }


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(MyEvent event) {
        if (event.getType() == 1) {
            getNewList();
            EventBus.getDefault().post(new MyEvent(2));

            int numAll = 0;
            List<WxBean> data = mAdapter.getData();
            for (int i = 0; i < data.size(); i++) {
                WxBean wxBean = data.get(i);
                if (wxBean.getType() == 1 || wxBean.getType() == 2) {
                    if (!wxBean.isRead()) {
                        String chatNum = wxBean.getChatNum();
                        numAll = numAll + Integer.valueOf(chatNum);
                    }
                }

            }
            if (numAll > 0) {
                tvHomeTitle.setText("微信(" + numAll + ")");
            } else {
                tvHomeTitle.setText("微信");
            }


        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(GroupEvent event) {

        long time = System.currentTimeMillis();
        if (event.isText()) {
            String text = event.getText();
            List<WxBean> data = mAdapter.getData();
            for (int i = 0; i < data.size(); i++) {
                WxBean bean = data.get(i);
                int type = bean.getType();
                if (type == WxBean.TYPE_CHAT_GROUP_SEND) {
                    bean.setChatContent(text);
                    bean.setChatTime(time);
                }
            }
            Collections.sort(data, new Comparator<WxBean>() {
                @Override
                public int compare(WxBean wxBean, WxBean t1) {
                    long time = t1.getChatTime() - wxBean.getChatTime();
                    if (time > 0) {
                        return 1;
                    } else if (time == 0) {
                        return 0;
                    } else {
                        return -1;
                    }
                }
            });
            mAdapter.notifyDataSetChanged();
        } else {
            List<WxBean> data = mAdapter.getData();
            for (int i = 0; i < data.size(); i++) {
                WxBean bean = data.get(i);
                int type = bean.getType();
                if (type == WxBean.TYPE_CHAT_GROUP_SEND) {
                    bean.setChatContent("图片");
                    bean.setChatTime(time);
                }
            }
            Collections.sort(data, new Comparator<WxBean>() {
                @Override
                public int compare(WxBean wxBean, WxBean t1) {
                    long time = t1.getChatTime() - wxBean.getChatTime();
                    if (time > 0) {
                        return 1;
                    } else if (time == 0) {
                        return 0;
                    } else {
                        return -1;
                    }
                }
            });
            mAdapter.notifyDataSetChanged();
        }

        int numAll = 0;
        List<WxBean> data = mAdapter.getData();
        for (int i = 0; i < data.size(); i++) {
            WxBean wxBean = data.get(i);
            if (wxBean.getType() == 1 || wxBean.getType() == 2) {
                if (!wxBean.isRead()) {
                    String chatNum = wxBean.getChatNum();
                    numAll = numAll + Integer.valueOf(chatNum);
                }
            }

        }
        if (numAll > 0) {
            tvHomeTitle.setText("微信(" + numAll + ")");
        } else {
            tvHomeTitle.setText("微信");
        }

    }

    private void getNewList() {

        List<WxBean> wxBeans = DbUtil.queryAll();
        Collections.sort(wxBeans, new Comparator<WxBean>() {
            @Override
            public int compare(WxBean wxBean, WxBean t1) {
                long time = t1.getChatTime() - wxBean.getChatTime();
                if (time > 0) {
                    return 1;
                } else if (time == 0) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });


        mAdapter.setNewData(wxBeans);
    }


    /**
     * 获取默认数据
     */


    private List<WxBean> getDefaultData() {
        Random mRandom = new Random();
        List<WxBean> beans = new ArrayList<>();


        List<Integer> numList = new ArrayList<>();
        Random rand = new Random();
        while (numList.size() < 8) {
            int Num = rand.nextInt(23) + 1;
            if (!numList.contains(Num)) {
                numList.add(Num);
            }
        }

        /**
         * 创建8个群聊
         */

        for (int i = 0; i < numList.size(); i++) {
            int numId = numList.get(i);


            WxBean beanGroup = new WxBean();
            beanGroup.setId(beans.size() + 1l);
            beanGroup.setChatHead(numId);
            beanGroup.setType(2);
            beanGroup.setChatTime(System.currentTimeMillis() - 1000 * 60 * mRandom.nextInt(500));
            beanGroup.setRead(true);
            beanGroup.setChatContent(chatContents[i]);
            beanGroup.setChatName(chatHeads[i]);
            beanGroup.setChatType(1);
            beanGroup.setChatNum("1");
            beans.add(beanGroup);


        }

        //微信支付 第一个
        WxBean bean1 = new WxBean();
        bean1.setId(beans.size() + 1l);
        bean1.setIsRead(true);
        bean1.setType(4);
        bean1.setChatTime(System.currentTimeMillis() - 1000 * 60 * 60 * mRandom.nextInt(100));
        beans.add(bean1);

        //公众号
        WxBean bean2 = new WxBean();
        bean2.setId(beans.size() + 1l);
        bean2.setType(3);
        bean2.setIsRead(true);
        bean2.setChatTime(System.currentTimeMillis() - 1000 * 60 * 60 * mRandom.nextInt(100));
        bean2.setChatContent(subContents[(int) ((Math.random() * subContents.length))]);
        beans.add(bean2);

        //群组

        WxBean bean3 = new WxBean();
        bean3.setId(beans.size() + 1l);
        bean3.setType(5);
        bean3.setIsRead(true);
        bean3.setChatContent("生日快乐!(●ˇ∀ˇ●)");
        bean3.setChatTime(System.currentTimeMillis() - 1000 * 60 * 60 * mRandom.nextInt(100));
        beans.add(bean3);


        WxBean bean4 = new WxBean();
        bean4.setChatHead(0);
        bean4.setId(beans.size() + 1l);
        bean4.setType(6);
        bean4.setIsRead(true);
        bean4.setChatName("腾讯新闻");
        bean4.setChatContent(txNews[(int) ((Math.random() * txNews.length))]);
        bean4.setChatTime(System.currentTimeMillis() - 1000 * 60 * 60 * mRandom.nextInt(100));
        beans.add(bean4);

        WxBean bean5 = new WxBean();
        bean5.setChatHead(1);
        bean5.setId(beans.size() + 1l);
        bean5.setType(6);
        bean5.setIsRead(true);
        bean5.setChatName("丰巢智能柜");
        bean5.setChatContent(fcNews[(int) ((Math.random() * fcNews.length))]);
        bean5.setChatTime(System.currentTimeMillis() - 1000 * 60 * mRandom.nextInt(500));
        beans.add(bean5);

        WxBean bean6 = new WxBean();
        bean6.setChatHead(2);
        bean6.setId(beans.size() + 1l);
        bean6.setType(6);
        bean6.setIsRead(true);
        bean6.setChatName("婚礼纪服务号");
        bean6.setChatContent(subContents[(int) ((Math.random() * subContents.length))]);
        bean6.setChatTime(System.currentTimeMillis() - 1000 * 60 * mRandom.nextInt(200));
        beans.add(bean6);

        Collections.sort(beans, new Comparator<WxBean>() {
            @Override
            public int compare(WxBean wxBean, WxBean t1) {
                long time = t1.getChatTime() - wxBean.getChatTime();
                if (time > 0) {
                    return 1;
                } else if (time == 0) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });

        DbUtil.insertWxList(beans);

        return beans;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
