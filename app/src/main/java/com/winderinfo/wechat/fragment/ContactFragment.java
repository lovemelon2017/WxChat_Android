package com.winderinfo.wechat.fragment;


import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.winderinfo.wechat.ChatDetailsActivity;
import com.winderinfo.wechat.R;
import com.winderinfo.wechat.adapter.ContactAdapter;
import com.winderinfo.wechat.api.OnClickDialogBottomInterface;
import com.winderinfo.wechat.api.OnClickDialogInterface;
import com.winderinfo.wechat.base.BaseFragment;
import com.winderinfo.wechat.bean.ContactBean;
import com.winderinfo.wechat.bean.WxBean;
import com.winderinfo.wechat.db.DbUtil;
import com.winderinfo.wechat.util.DialogUtil;
import com.winderinfo.wechat.util.PinyinComparator;
import com.winderinfo.wechat.util.PinyinUtils;
import com.winderinfo.wechat.view.AddFriendBottomDialog;
import com.winderinfo.wechat.view.ContactCenterDialog;
import com.winderinfo.wechat.view.EditUserNameCenterDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 联系人
 */
public class ContactFragment extends BaseFragment {


    public static final String names[] = {"青丝缠梳－O－", "川绿风", "语声", "你如热雪", "回忆不肯熄灭",
            "初生的太阳", "半梦半醒", "梧桐听雨", "年少纵马且歌", "灰色的漩涡",
            "独身迷漾少女", "灰色的漩涡", "迷宫与迷", "风落桃花寂", "太阳不懂月亮的空虚",
            "逾期不候", "烟雨相思醉", "红唇爱上烟", "黑白琴键弹不出的旋律", "北海旧梦碎人心",
            "易烊千玺", "书一封旧长信", "天下皆白唯我独黑", "雨后云初霁", "银海属于我们",
            "寻一个拥抱", "我們蓦然相愛", "试着揣摩心事", "敢倒追的女孩不会太差", "你我相爱未曾告白",
            "下课闹闹", "灰色的漩涡", "迷宫与迷", "风落桃花寂", "太阳不懂月亮的空虚",
            "可爱成性", "恋上", "独占你心", "初心", "太阳不懂月亮的空虚",
            "花房花菇凉", "聆听你的哀伤", "傻吊不是你叫的", "风落桃花寂", "小草莓",
            "望故事予你", "男人本色", "屌丝也有逆袭日", "玩世不恭", "鸣筝",
            "小女子", "老头", "懵懵懂懂拽", "机智", "忘情",
            "是爱养活了心脏", "第一先生", "把自己活得漂亮好么", "勇士", "坠红颜",
            "多情的女人", "不怕黑", "画个圈圈鄙视你", "你太刺眼", "肉多多",
            "温暖给我", "你的城市有我的影子", "我不稀罕", "要坚强", "平淡的心",
            "中国心", "我是男神闪亮亮", "老娘就是喜欢你的坏", "金姐", "相信凡",
            "午夜卑微的眼泪", "三生三世枕上书", "我是偷心女贼", "周杰伦粉丝", "萌怪咖",
            "短发美", "长风为骨", "穿着高跟鞋照样奔跑", "大学一号", "念稚",
            "逍遥", "浪荡王者", "迷宫与迷", "美丽的你", "狱中猫",
            "画个句号给今天", "嘴强王者", "小爷你好贱", "完美的我", "浅兮",
            "刻你名在我心", "高姿态男子", "爱她你滚", "渐渐清晰", "旧街",
            "唱歌的女孩", "旧戏书", "淑女我做不来", "我的心脏", "独栖",
            "下一次微笑", "枕边梦", "再见不如不见", "我的女孩", "回忆",
            "回忆是你", "羌栗乞庇佣", "偿困薄", "桥芍揖", "会不会不是你",
            "微笑、不失礼", "梦想的初衷", "往事风中埋", "御膳天厨™", "✯吉祥如意", "宾至如归㊖",
            "万象更新≍", "Allure Love", "Tenderness", "Flowers", "Koreyoshi", "Jalen杰伦",
            "没人娶的俊小妞", "打灰机的蜡笔小新", "旁观了、幸福", "Yon9 -人自扰", "那伤、倾城", "兔斯基、简单的美好",
            "小热恋", "骑着蜗牛追着伱", "龟兔ゝ赛跑", "有个小小梦想╮", "西瓜说他很爱夏天#", "温柔点的孩子气",
            "再见丶童年哟", "爱里承欢", "好感都给你", "看到你好害羞", "超喜欢你", "穿草裙的少女",
            "狂,是我的个性", "中意无拘无束", "人生失意无南北", "逆天↘浪子", "放肆的年华╰╯",
            "Pitiless", "Memorial", "Hickey", "Miracle", "Belief°", "Ambiguous", "Poppies",
            "BInsomnia", "Prisoner", "rough°名城梦", "Rodma 玩命", "Sehun丶狠狠爱",
            "旧事 Reminiscence°", "暗里着迷 Dreamland", "Assassin°宿妖瞳", "冷瞳 ruin▲", "毒药 |▍Posion",
            "Sham「伪善」", "Distance 距离", "A monologue 独白", "Dream°幻梦症", "Forever`ㄋ`淡墨℡", "閁电flicker",
            "LiKE", "晴初- moment°", "struggle゛遺忘ッ", "安于此生丶Taurus", "Animai°情兽",
            "梦中花O(∩_∩)O", "Sandm °旧颜", "Promise。承诺", "浓情chocolate", "Ambition（野心）", "安于此生丶ˉTaurus",
            "Flower * 刺心", "My Sunshine", "Obsession", "Gentleman", "Dear °心裂", "Summer℡ 念", "执念° follow you",
            "Tiamo-叛逆つ", "Cello琴弦之間", "Despair（绝望）", "Pretext", "远行者，Let’s go",
            "gorgeous丶倾国倾城", "Classic 经典", "ruin °破碎フ", "旧情歌-TRISTE", "Angle、微眸", "洛初≈ hundredth early",
            "Pandoraルo 潘朵拉", "Ponnenult□旧时光", "余存°dsTiny", "ｌōｖé倾城一吻", "Shallow sing丿忧伤", "IthinkIloveyou.ゆ",
            "Minemine无心无痛", "Don’t care.不在乎", "Frustrated.失落", "사랑해요(我爱你)", "가벼운 그리움",
            "굴복하다", "여름향기", "잘자요", "카푸치노의 맛음", "괜찮아요",
            "绣花针针恨", "話語扎心", "你见爱情放过谁", "特别漫长", "愚爱", "孤独障碍", "一言难尽",
            "活着真没意思", "不哭不哭", "爱你到走投无路", "忧伤染指青春", "那年泛黄的情书", "心已倦，泪已干",
            "久伴成了酒伴", "她的剧本我只是观众", "无奈敷衍的沦落", "无处安放的痛", "你已走出我的世界",
            "回忆里没有你", "空景孤扰人心", "没有你的以后", "你给的痛、已满分", "忘记，不代表放弃", "爱情、我们黑名单见",
            "奈何缘浅", "积极废物", "诵一段因果", "时光未老、我们已散", "心岛未晴", "我也有泪有感情", "让伱懂我的好",
            "没有结局的爱、", "曾经有过的坚持", "我的心里没有伱", "抿着嘴，窃笑", "仅剩旳温纯、", "拥抱空气", "丧尽良心",
            "感情喂了狗", "抱住我好不好", "月亮邮递员", "找到快乐啦", "想当你的狐狸", "偏偏坠入你心河",
            "眼中宛如星河", "眉间似有山川", "路边捡回来的小可爱", "喝可乐的小猫妖", "想要一点点甜", "温柔戏命师",
            "萝莉身御姐心", "贩卖可爱", "偶尔善良", "耳根太软", "锁着月亮是窗户", "一身仙女味", "你是人间宝藏", "吹来人间烟火",
            "软的要命", "乖一点就爱你", "梦境贩卖官", "独角兽妹妹", "过分迷人", "今天星期八", "吐个泡泡", "止于娇喘", "我可爱炸了.",
            "天杀的可爱", "少女的朝思暮想", "跟空气撒个娇", "奶味小仙女", "甜甜的梦都给你", "满船清梦压星河",
            "蜡笔小丸子", "小勋家的鹿边摊", "Sunshineづ残阳", "喜欢EXO不悔", "小醜丶獨角戲", "喜歡妳的寵愛", "喜欢你给我的外号",
            "回憶只屬于曾經", "╬═☆杺誶ルo", "あ縌偑ぜ", "奶里奶气", "盖世垃圾", "有趣迷人", "倾世梦回",
            "望你沉睡", "千秋萬代", "晨雨北风", "EnticE. *[诱惑]", "傲娇菇凉(没感情)", "佛说妍妍很渣", "嗷呜!~小乖乖/*",
            "萌萌哒小可爱", "乜づ狠傷ぺ", "时光不老我们不分离", "Rampant~(猖狂)", "哭不代表软弱", "妖精ξ也會哭",
            "爱你等于作孽", "傲气稳全场", "♠一个人的简单", "只想，你陪我看海", "奈何桥上摆地摊ソ", "今天的天气︶真好",
            "无色无味的日子", "早就把心丢了の", "一个人，迷离り", "狂暴旳青春わ", "一枕相思泪♠", "感觉✢没feel", "一身傲骨の",
            "指着心の说她爱我"};


    @BindView(R.id.contact_rv)
    RecyclerView mRv;
    ContactAdapter mAdapter;
    @BindView(R.id.contact_footer_tv)
    TextView tvFootContactNum;

    @BindView(R.id.contact_head_add_ll)
    LinearLayout llAddFriend;

    int randomAllNum = 30;//随机生成数
    int headAll = 130;

    PinyinComparator pinyinComparator;
    List<ContactBean> beanList = new ArrayList<>();

    @Override
    public int getViewId() {
        return R.layout.fragment_contact;
    }

    @Override
    public void initView() {
        // StatusBarUtil.setColor(getActivity(), getResources().getColor(R.color.cl_background),1);
        initRv();

    }

    private void initRv() {
        pinyinComparator = new PinyinComparator();
        getDefaultData();

        mRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ContactAdapter(R.layout.contact_item_lay, beanList);

        mRv.setAdapter(mAdapter);


        llAddFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddFriend();
            }
        });

        tvFootContactNum.setText(mAdapter.getData().size() + "位联系人");

        //编辑好友数量
        tvFootContactNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditFriendDialog();
            }
        });

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<ContactBean> data = adapter.getData();
                ContactBean bean = data.get(position);
                long id = bean.getId();

                int headNum = bean.getHead();
                String name = bean.getName();
                String letter = bean.getLetter();
                WxBean wxBean = new WxBean();
                wxBean.setType(WxBean.TYPE_CHAT);
                wxBean.setChatHead(headNum);
                wxBean.setChatName(name);
                wxBean.setId(id);

                Intent intent = new Intent(getActivity(), ChatDetailsActivity.class);
                intent.putExtra("bean", wxBean);
                startActivity(intent);


            }
        });

        mAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                List<ContactBean> data = adapter.getData();
                ContactBean bean = data.get(position);
                showEditName(bean);
                return true;
            }
        });

    }

    /**
     * 修改联系人名字
     *
     * @param
     */
    private void showEditName(ContactBean mBean) {
        EditUserNameCenterDialog centerDialog = new EditUserNameCenterDialog();
        centerDialog.setDialogInterface(new OnClickDialogInterface() {
            @Override
            public void onCancel() {
                centerDialog.dismiss();
            }

            @Override
            public void onSure(String content) {
                mBean.setLetter(PinyinUtils.getFirstLetter(content));
                mBean.setName(content);
                DbUtil.insertContact(mBean);
                centerDialog.dismiss();
                List<ContactBean> datas = mAdapter.getData();
                Collections.sort(datas, pinyinComparator);
                mAdapter.setNewData(datas);

            }
        });
        centerDialog.show(getFragmentManager(), "dialog");
    }

    @OnClick({R.id.contact_add_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.contact_add_iv:
                showAddFriend();
                break;
        }
    }

    /**
     * 自动生成好友
     */
    private void showAddFriend() {
        AddFriendBottomDialog bottomDialog = new AddFriendBottomDialog();
        bottomDialog.setBottomInterface(new OnClickDialogBottomInterface() {
            @Override
            public void onCancel() {
                bottomDialog.dismiss();
            }

            @Override
            public void onSure(int num) {


                randomAllNum = num;
                getAddData();
                bottomDialog.dismiss();
                ToastUtils.showShort("添加成功");

            }
        });
        bottomDialog.show(getFragmentManager(), "dialog2");
    }

    private void showEditFriendDialog() {
        ContactCenterDialog dialog = new ContactCenterDialog();
        dialog.setDialogInterface(new OnClickDialogInterface() {
            @Override
            public void onCancel() {
                dialog.dismiss();
            }

            @Override
            public void onSure(String content) {
                tvFootContactNum.setText(content + "位联系人");
                dialog.dismiss();
            }
        });
        dialog.show(getFragmentManager(), "dialog");
    }

    private List<ContactBean> getDefaultData() {

        ArrayList<Integer> numList = new ArrayList<Integer>();
        ArrayList<String> nameList = new ArrayList<String>();


        Random rand = new Random();
        while (numList.size() < randomAllNum) {
            int Num = rand.nextInt(headAll);
            if (!numList.contains(Num))
                numList.add(Num);
        }


        while (nameList.size() < randomAllNum) {
            int ranNum = rand.nextInt(names.length);
            String name = names[ranNum];
            if (!nameList.contains(name))
                nameList.add(name);
        }


        List<ContactBean> list = new ArrayList<>();
        for (int i = 0; i < randomAllNum; i++) {
            ContactBean bean = new ContactBean();
            bean.setId(i + 1);
            bean.setHead(numList.get(i));
            bean.setName(nameList.get(i));
            bean.setLetter(PinyinUtils.getFirstLetter(nameList.get(i)));
            list.add(bean);
        }
        // 根据a-z进行排序源数据
        Collections.sort(list, pinyinComparator);
        beanList.addAll(list);

        DbUtil.insertContact(list);
        return list;

    }

    private List<ContactBean> getAddData() {
        pinyinComparator = new PinyinComparator();
        List<ContactBean> datas = new ArrayList<>();
        new Handler().post(new Runnable() {
            @Override
            public void run() {

                ArrayList<Integer> numList = new ArrayList<Integer>();
                ArrayList<String> nameList = new ArrayList<String>();

                if (randomAllNum <= 100) {
                    while (numList.size() < randomAllNum) {
                        int Num = (int) ((Math.random() * headAll));
                        if (!numList.contains(Num)) {
                            numList.add(Num);
                        }

                    }
                } else {
                    while (numList.size() < randomAllNum) {
                        int Num = (int) ((Math.random() * headAll));
                        numList.add(Num);
                    }
                }

                if (randomAllNum <= 100) {
                    ArrayList<Integer> nameTag = new ArrayList<Integer>();
                    while (nameTag.size() < randomAllNum) {
                        int ranNum = (int) ((Math.random() * names.length));
                        if (!nameTag.contains(ranNum)) {
                            nameTag.add(ranNum);
                        }
                    }

                    for (int i = 0; i < nameTag.size(); i++) {
                        String name = names[nameTag.get(i)];
                        Log.e("han", name + "==" + name);
                        nameList.add(name);
                    }


                } else {
                    while (nameList.size() < randomAllNum) {
                        int ranNum = (int) ((Math.random() * names.length));
                        Log.e("han", ranNum + "==name");
                        String name = names[ranNum];
                        nameList.add(name);
                    }
                }


                // 根据a-z进行排序源数据
                List<ContactBean> data = mAdapter.getData();

                List<ContactBean> list = new ArrayList<>();
                for (int i = 0; i < randomAllNum; i++) {
                    ContactBean bean = new ContactBean();
                    bean.setId(data.size() + i + 1);
                    bean.setHead(numList.get(i));
                    bean.setName(nameList.get(i));
                    bean.setLetter(PinyinUtils.getFirstLetter(nameList.get(i)));
                    list.add(bean);
                }
                datas.addAll(data);
                datas.addAll(list);

                Collections.sort(datas, pinyinComparator);
                DbUtil.insertContact(datas);

                mAdapter.setNewData(datas);
                tvFootContactNum.setText(datas.size() + "位联系人");

            }
        });

        return datas;

    }
}
