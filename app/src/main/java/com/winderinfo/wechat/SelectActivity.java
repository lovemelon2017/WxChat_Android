package com.winderinfo.wechat;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.winderinfo.wechat.adapter.SelectContactAdapter;
import com.winderinfo.wechat.adapter.SelectTopAdapter;
import com.winderinfo.wechat.base.BaseActivity;
import com.winderinfo.wechat.bean.ContactBean;
import com.winderinfo.wechat.db.DbUtil;
import com.winderinfo.wechat.util.PinyinComparator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 选择收件人
 */
public class SelectActivity extends BaseActivity {

    @BindView(R.id.select_rv)
    RecyclerView rv;
    SelectContactAdapter mAdapter;
    SelectTopAdapter mTopAdapter;

    @BindView(R.id.select_top_rv)
    RecyclerView rvTop;
    @BindView(R.id.select_iv)
    ImageView ivSelect;
    @BindView(R.id.select_state_bt)
    TextView tvState;
    @BindView(R.id.select_next_tv)
    TextView tvNext;
    List<ContactBean> beanList = new ArrayList<>();
    PinyinComparator pinyinComparator;

    String letterTag = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_select;
    }

    @Override
    public void initView() {


        List<ContactBean> contactBeans = DbUtil.queryContactAll();


        pinyinComparator = new PinyinComparator();
        Collections.sort(contactBeans, pinyinComparator);

        for (int i = 0; i < contactBeans.size(); i++) {
            ContactBean bean = contactBeans.get(i);
            String letter = bean.getLetter();
            if (letter.equals(letterTag)) {
                bean.setShowLetter(false);
            } else {
                letterTag = letter;
                bean.setShowLetter(true);
            }
        }
        mAdapter = new SelectContactAdapter(R.layout.select_contact_item_lay, contactBeans);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(mAdapter);



        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                List<ContactBean> data = adapter.getData();
                ContactBean bean = data.get(position);
                boolean isCheck = bean.getIsCheck();
                if (isCheck) {
                    bean.setCheck(false);
                    beanList.remove(bean);
                } else {
                    bean.setCheck(true);
                    beanList.add(bean);
                }

                mAdapter.notifyDataSetChanged();
                mTopAdapter.setNewData(beanList);


                changeNextBt();
            }
        });

        initTopRv();


    }

    private void changeNextBt() {
        if (beanList.size() > 0) {
            tvNext.setEnabled(true);
        } else {
            tvNext.setEnabled(false);
        }
    }

    private void initTopRv() {

        mTopAdapter = new SelectTopAdapter(R.layout.item_select_top_lay);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvTop.setLayoutManager(manager);
        rvTop.setAdapter(mTopAdapter);

    }

    @OnClick({R.id.back_iv, R.id.select_state_bt, R.id.select_next_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.select_state_bt:

                changeState();

                break;
            case R.id.select_next_tv:
                if (beanList.size() > 0) {
                    Intent intent = new Intent(SelectActivity.this, SendGroupActivity.class);
                    intent.putExtra("list", (Serializable) beanList);
                    startActivity(intent);
                }

                break;
        }
    }

    private void changeState() {
        String state = tvState.getText().toString();
        if ("全选".equals(state)) {
            List<ContactBean> data = mAdapter.getData();
            for (int i = 0; i < data.size(); i++) {
                data.get(i).setCheck(true);
            }
            tvState.setText("不选");
            mAdapter.setNewData(data);
            mTopAdapter.setNewData(data);
            beanList.clear();
            beanList.addAll(data);
        } else if ("不选".equals(state)) {
            List<ContactBean> data = mAdapter.getData();
            for (int i = 0; i < data.size(); i++) {
                data.get(i).setCheck(false);
            }
            tvState.setText("全选");
            mAdapter.setNewData(data);
            beanList.clear();
            mTopAdapter.setNewData(beanList);
        }
        changeNextBt();
    }
}
