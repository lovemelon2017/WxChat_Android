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
import com.winderinfo.wechat.view.EditSubCenterDialog;
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
            "郑州大学交流群", "交流群", "小吃群", "快递外卖群"};


    public static final String subContents[] = {"AI前线,对话阿里巴巴:如何成为一个优秀的架构师",
            "你的男友VS别人家的男友", "好消息!,电话、网络、微信...河南看病有了新平台!", "微信公众号开始测试收费功能",
            "好消息,假期期间免费领取", "她来了", "不是所有的爱是你能拥有的", "新的政策是否符合你的需求",
            "你的推荐就是我的喜欢", "快来体验", "每日一看"
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
                String chatName = bean.getChatName();
                if ("微信运动".equals(chatName)) {
                    return;
                }
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
                dialog.dismiss();
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

            @Override
            public void onEditContent(WxBean wxBean) {

                showEditSub(wxBean);
                dialog.dismiss();

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

    private void showEditSub(WxBean bean) {
        EditSubCenterDialog subDialog = new EditSubCenterDialog();
        subDialog.setDialogInterface(new OnClickDialogInterface() {
            @Override
            public void onCancel() {
                subDialog.dismiss();
            }

            @Override
            public void onSure(String content) {

                bean.setChatContent(content);
                DbUtil.insert(bean);
                subDialog.dismiss();
                mAdapter.notifyDataSetChanged();
            }
        });
        subDialog.show(getFragmentManager(), "sub");
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
        bean3.setChatTime(System.currentTimeMillis() - 1000 * 60 * 60 * mRandom.nextInt(50));
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
        bean6.setChatTime(System.currentTimeMillis() - 1000 * 60 * mRandom.nextInt(300));
        beans.add(bean6);

        WxBean bean7 = new WxBean();
        bean7.setChatHead(3);
        bean7.setId(beans.size() + 1l);
        bean7.setType(6);
        bean7.setIsRead(true);
        bean7.setChatName("360借条");
        bean7.setChatContent(subContents[(int) ((Math.random() * subContents.length))]);
        bean7.setChatTime(System.currentTimeMillis() - 1000 * 60 * mRandom.nextInt(600));
        beans.add(bean7);

        WxBean bean8 = new WxBean();
        bean8.setChatHead(4);
        bean8.setId(beans.size() + 1l);
        bean8.setType(6);
        bean8.setIsRead(true);
        bean8.setChatName("CoCo都可");
        bean8.setChatContent(subContents[(int) ((Math.random() * subContents.length))]);
        bean8.setChatTime(System.currentTimeMillis() - 1000 * 60 * mRandom.nextInt(600));
        beans.add(bean8);

        WxBean bean9 = new WxBean();
        bean9.setChatHead(5);
        bean9.setId(beans.size() + 1l);
        bean9.setType(6);
        bean9.setIsRead(true);
        bean9.setChatName("HM官方");
        bean9.setChatContent(subContents[(int) ((Math.random() * subContents.length))]);
        bean9.setChatTime(System.currentTimeMillis() - 1000 * 60 * mRandom.nextInt(600));
        beans.add(bean9);

        WxBean bean10 = new WxBean();
        bean10.setChatHead(6);
        bean10.setId(beans.size() + 1l);
        bean10.setType(6);
        bean10.setIsRead(true);
        bean10.setChatName("K米");
        bean10.setChatContent(subContents[(int) ((Math.random() * subContents.length))]);
        bean10.setChatTime(System.currentTimeMillis() - 1000 * 60 * mRandom.nextInt(600));
        beans.add(bean10);

        WxBean bean11 = new WxBean();
        bean11.setChatHead(7);
        bean11.setId(beans.size() + 1l);
        bean11.setType(6);
        bean11.setIsRead(true);
        bean11.setChatName("TODAY便利店南宁");
        bean11.setChatContent(subContents[(int) ((Math.random() * subContents.length))]);
        bean11.setChatTime(System.currentTimeMillis() - 1000 * 60 * mRandom.nextInt(600));
        beans.add(bean11);

        WxBean bean12 = new WxBean();
        bean12.setChatHead(8);
        bean12.setId(beans.size() + 1l);
        bean12.setType(6);
        bean12.setIsRead(true);
        bean12.setChatName("滴滴出行服务号");
        bean12.setChatContent(subContents[(int) ((Math.random() * subContents.length))]);
        bean12.setChatTime(System.currentTimeMillis() - 1000 * 60 * mRandom.nextInt(600));
        beans.add(bean12);

        WxBean bean13 = new WxBean();
        bean13.setChatHead(9);
        bean13.setId(beans.size() + 1l);
        bean13.setType(6);
        bean13.setIsRead(true);
        bean13.setChatName("服务通知");
        bean13.setChatContent("租界归还通知");
        bean13.setChatTime(System.currentTimeMillis() - 1000 * 60 * mRandom.nextInt(600));
        beans.add(bean13);

        WxBean bean14 = new WxBean();
        bean14.setChatHead(10);
        bean14.setId(beans.size() + 1l);
        bean14.setType(6);
        bean14.setIsRead(true);
        bean14.setChatName("海底捞火锅");
        bean14.setChatContent(subContents[(int) ((Math.random() * subContents.length))]);
        bean14.setChatTime(System.currentTimeMillis() - 1000 * 60 * mRandom.nextInt(600));
        beans.add(bean14);

        WxBean bean15 = new WxBean();
        bean15.setChatHead(11);
        bean15.setId(beans.size() + 1l);
        bean15.setType(6);
        bean15.setIsRead(true);
        bean15.setChatName("海澜之家");
        bean15.setChatContent(subContents[(int) ((Math.random() * subContents.length))]);
        bean15.setChatTime(System.currentTimeMillis() - 1000 * 60 * mRandom.nextInt(600));
        beans.add(bean15);

        WxBean bean16 = new WxBean();
        bean16.setChatHead(12);
        bean16.setId(beans.size() + 1l);
        bean16.setType(6);
        bean16.setIsRead(true);
        bean16.setChatName("捷停车");
        bean16.setChatContent(subContents[(int) ((Math.random() * subContents.length))]);
        bean16.setChatTime(System.currentTimeMillis() - 1000 * 60 * mRandom.nextInt(600));
        beans.add(bean16);

        WxBean bean17 = new WxBean();
        bean17.setChatHead(13);
        bean17.setId(beans.size() + 1l);
        bean17.setType(6);
        bean17.setIsRead(true);
        bean17.setChatName("京东JD.COM");
        bean17.setChatContent(subContents[(int) ((Math.random() * subContents.length))]);
        bean17.setChatTime(System.currentTimeMillis() - 1000 * 60 * mRandom.nextInt(600));
        beans.add(bean17);

        WxBean bean18 = new WxBean();
        bean18.setChatHead(14);
        bean18.setId(beans.size() + 1l);
        bean18.setType(6);
        bean18.setIsRead(true);
        bean18.setChatName("京喜商家信息平台");
        bean18.setChatContent(subContents[(int) ((Math.random() * subContents.length))]);
        bean18.setChatTime(System.currentTimeMillis() - 1000 * 60 * mRandom.nextInt(600));
        beans.add(bean18);

        WxBean bean19 = new WxBean();
        bean19.setChatHead(15);
        bean19.setId(beans.size() + 1l);
        bean19.setType(6);
        bean19.setIsRead(true);
        bean19.setChatName("美的鹭湖");
        bean19.setChatContent(subContents[(int) ((Math.random() * subContents.length))]);
        bean19.setChatTime(System.currentTimeMillis() - 1000 * 60 * mRandom.nextInt(600));
        beans.add(bean19);

        WxBean bean20 = new WxBean();
        bean20.setChatHead(16);
        bean20.setId(beans.size() + 1l);
        bean20.setType(6);
        bean20.setIsRead(true);
        bean20.setChatName("去哪儿网");
        bean20.setChatContent(subContents[(int) ((Math.random() * subContents.length))]);
        bean20.setChatTime(System.currentTimeMillis() - 1000 * 60 * mRandom.nextInt(600));
        beans.add(bean20);

        WxBean bean21 = new WxBean();
        bean21.setChatHead(17);
        bean21.setId(beans.size() + 1l);
        bean21.setType(6);
        bean21.setIsRead(true);
        bean21.setChatName("微派谁是卧底游戏助手");
        bean21.setChatContent(subContents[(int) ((Math.random() * subContents.length))]);
        bean21.setChatTime(System.currentTimeMillis() - 1000 * 60 * mRandom.nextInt(600));
        beans.add(bean21);

        WxBean bean22 = new WxBean();
        bean22.setChatHead(18);
        bean22.setId(beans.size() + 1l);
        bean22.setType(6);
        bean22.setIsRead(true);
        bean22.setChatName("微信游戏");
        bean22.setChatContent(subContents[(int) ((Math.random() * subContents.length))]);
        bean22.setChatTime(System.currentTimeMillis() - 1000 * 60 * mRandom.nextInt(600));
        beans.add(bean22);

        WxBean bean23 = new WxBean();
        bean23.setChatHead(19);
        bean23.setId(beans.size() + 1l);
        bean23.setType(1);
        bean23.setChatType(1);
        bean23.setChatNum("1");
        bean23.setIsRead(true);
        bean23.setChatName("微信运动");
        bean23.setChatContent("[应用消息]");
        bean23.setChatTime(System.currentTimeMillis() - 1000 * 60 * mRandom.nextInt(600));
        beans.add(bean23);

        WxBean bean24 = new WxBean();
        bean24.setChatHead(20);
        bean24.setId(beans.size() + 1l);
        bean24.setType(6);
        bean24.setIsRead(true);
        bean24.setChatName("星巴克中国");
        bean24.setChatContent(subContents[(int) ((Math.random() * subContents.length))]);
        bean24.setChatTime(System.currentTimeMillis() - 1000 * 60 * mRandom.nextInt(600));
        beans.add(bean24);

        WxBean bean25 = new WxBean();
        bean25.setChatHead(21);
        bean25.setId(beans.size() + 1l);
        bean25.setType(6);
        bean25.setIsRead(true);
        bean25.setChatName("众嗨联盟");
        bean25.setChatContent(subContents[(int) ((Math.random() * subContents.length))]);
        bean25.setChatTime(System.currentTimeMillis() - 1000 * 60 * mRandom.nextInt(600));
        beans.add(bean25);

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
