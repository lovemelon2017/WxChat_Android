package com.winderinfo.wechat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.winderinfo.wechat.adapter.HomePageAdapter;
import com.winderinfo.wechat.api.OnClickDialogBottom2Interface;
import com.winderinfo.wechat.base.BaseActivity;
import com.winderinfo.wechat.bean.WxBean;
import com.winderinfo.wechat.db.DbUtil;
import com.winderinfo.wechat.event.MyEvent;
import com.winderinfo.wechat.fragment.ContactFragment;
import com.winderinfo.wechat.fragment.FindFragment;
import com.winderinfo.wechat.fragment.HomeFragment;
import com.winderinfo.wechat.fragment.MineFragment;
import com.winderinfo.wechat.view.NoAnimationViewPager;
import com.winderinfo.wechat.view.TrendsBottomDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    private String[] permissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };
    private int PERMISSION_REQUEST_CODE = 1001;

    @BindView(R.id.main_vp)
    NoAnimationViewPager mViewPager;
    HomePageAdapter mAdapter;
    @BindView(R.id.home_iv)
    ImageView ivHome;
    @BindView(R.id.home_tv)
    TextView tvHome;
    @BindView(R.id.contact_iv)
    ImageView ivContact;
    @BindView(R.id.contact_tv)
    TextView tvContact;
    @BindView(R.id.find_iv)
    ImageView ivFind;
    @BindView(R.id.find_tv)
    TextView tvFind;
    @BindView(R.id.mine_iv)
    ImageView ivMine;
    @BindView(R.id.mine_tv)
    TextView tvMine;
    @BindView(R.id.home_message_tv)
    TextView tvHomeMessage;

    @BindView(R.id.find_message_tv1)
    TextView tvFindMessage;
    @BindView(R.id.find_message_tv2)
    View tvMessage2;
    @BindView(R.id.find_fl)
    View vFind;

    boolean isShowDt1;
    boolean isShowDt2;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        DbUtil.clear();
        requestPermission();
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new ContactFragment());
        fragments.add(new FindFragment());
        fragments.add(new MineFragment());
        mAdapter = new HomePageAdapter(getSupportFragmentManager(), 0, fragments);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(4);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setCurrentItem(0);
        setTab(0);

        vFind.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showFindDialog();
                return true;
            }
        });

    }

    private void showFindDialog() {
        TrendsBottomDialog dialog = new TrendsBottomDialog();
        dialog.setShowTrend1(isShowDt1);
        dialog.setShowTrend2(isShowDt2);
        dialog.setBottomInterface(new OnClickDialogBottom2Interface() {
            @Override
            public void onCancel() {
                dialog.dismiss();
            }

            @Override
            public void onClickTrendOne(boolean isShow, String num) {
                isShowDt1 = isShow;
                if (isShowDt1) {
                    if (!TextUtils.isEmpty(num)) {
                        tvFindMessage.setText(num);
                    }
                    tvFindMessage.setVisibility(View.VISIBLE);
                    tvMessage2.setVisibility(View.GONE);
                } else {
                    tvFindMessage.setVisibility(View.GONE);
                    tvMessage2.setVisibility(View.GONE);
                }
                dialog.dismiss();

            }

            @Override
            public void onClickTrendTwo(boolean isShow, String num) {
                isShowDt2 = isShow;
                if (isShowDt2) {
                    tvFindMessage.setVisibility(View.GONE);
                    tvMessage2.setVisibility(View.VISIBLE);
                } else {
                    tvMessage2.setVisibility(View.GONE);
                    tvFindMessage.setVisibility(View.GONE);
                }
                dialog.dismiss();

            }
        });
        dialog.show(getSupportFragmentManager(), "dialog");
    }

    private void setTab(int position) {
        switch (position) {
            case 0:
                ivHome.setEnabled(false);
                tvHome.setEnabled(false);
                ivContact.setEnabled(true);
                tvContact.setEnabled(true);
                ivFind.setEnabled(true);
                tvFind.setEnabled(true);
                ivMine.setEnabled(true);
                tvMine.setEnabled(true);

                break;
            case 1:
                ivHome.setEnabled(true);
                tvHome.setEnabled(true);
                ivContact.setEnabled(false);
                tvContact.setEnabled(false);
                ivFind.setEnabled(true);
                tvFind.setEnabled(true);
                ivMine.setEnabled(true);
                tvMine.setEnabled(true);
                break;
            case 2:
                ivHome.setEnabled(true);
                tvHome.setEnabled(true);
                ivContact.setEnabled(true);
                tvContact.setEnabled(true);
                ivFind.setEnabled(false);
                tvFind.setEnabled(false);
                ivMine.setEnabled(true);
                tvMine.setEnabled(true);
                break;
            case 3:
                ivHome.setEnabled(true);
                tvHome.setEnabled(true);
                ivContact.setEnabled(true);
                tvContact.setEnabled(true);
                ivFind.setEnabled(true);
                tvFind.setEnabled(true);
                ivMine.setEnabled(false);
                tvMine.setEnabled(false);
                break;
        }
    }


    @OnClick({R.id.home_fl, R.id.contact_fl, R.id.find_fl, R.id.mine_fl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_fl:
                mViewPager.setCurrentItem(0);
                setTab(0);
                break;
            case R.id.contact_fl:
                mViewPager.setCurrentItem(1);
                setTab(1);
                break;
            case R.id.find_fl:
                mViewPager.setCurrentItem(2);
                setTab(2);
                break;
            case R.id.mine_fl:
                mViewPager.setCurrentItem(3);
                setTab(3);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(MyEvent event) {
        if (event.getType() == 2) {
            int num = 0;
            List<WxBean> beans = DbUtil.queryAll();
            if (beans!=null&&beans.size()>0){
                for (int i = 0; i < beans.size(); i++) {
                    WxBean bean = beans.get(i);
                    boolean isRead = bean.getIsRead();
                    String chatNum = bean.getChatNum();
                    if (!isRead) {
                        num = Integer.valueOf(chatNum) + num;
                    }
                }

                if (num > 0) {
                    tvHomeMessage.setVisibility(View.VISIBLE);
                    if (num > 99) {
                        tvHomeMessage.setText("…");
                    } else {
                        tvHomeMessage.setText(num + "");
                    }
                } else {
                    tvHomeMessage.setVisibility(View.GONE);
                }
            }else {
                tvHomeMessage.setVisibility(View.GONE);
            }

        }
    }

    /**
     * 检查权限
     */
    private void requestPermission() {
        if (!checkPermissionAllGranted(permissions)) {
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
        }
    }

    private boolean checkPermissionAllGranted(String[] permissions) {
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                // 只要有一个权限没有被授予, 则直接返回 false
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            boolean isAllGranted = true;
            // 判断是否所有的权限都已经授予了
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    break;
                }
            }
            if (!isAllGranted) {
                requestPermission();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
