package com.fkgpby0329.yxb.activitys;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.fkgpby0329.yxb.R;

/**
 * @author：zhanglifan
 */
public class TwoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        TextView text = (TextView) findViewById(R.id.tv_two);
        text.setText("说起麻将，现在网络上的麻将游戏真的是应有尽有，随便搜索下麻将两个字，也会出来一大串关于麻将的一些游戏软件，让人看得眼花缭乱，不知道做何选择，也不知道是不是自己经常玩的那种，又能不能玩得了那些名字各异的麻将游戏。今天呢，精品游戏网就带大家去了解下四川麻将玩法。想要了解更多玩法，敬请持续关注精品游戏网~\n" +
                "\n" +
                "      麻将游戏是脑力劳动。刚学打麻将的时候，本人每次轮都被玩得汗流浃背，不是因为紧张输赢，而是每一轮牌都玩法太多，不同的出牌方式会有不同的结果，所以非常纠结，后来慢慢打得比较娴熟了。准备麻将游戏前，必须睡眠充足，养精蓄锐，以做到精力充沛，否则，后果很严重。\n" +
                "      ◆麻将一副牌共108张，筒、条、万各36张， 4人同时玩。  \n" +
                "      ◆胡牌情况：必须缺一门才能胡牌；任何一对牌都可以做将。规定如下：  ◆1分：平胡，即缺一门，四坎牌加一对将。   \n" +
                "      ◆2分：   \n" +
                "      （1）对对胡，即每坎牌都是三张一样的牌。  例: 111万 444万 222筒 999筒 66筒  （2）抢杠胡，即在他人杠时抢牌胡   \n" +
                "      ◆3分：  \n" +
                "      （1）清一色，即全副牌是一种花色  例:123条 567条 234 条 888条 99条  （2）幺九牌，即每坎牌都有一或九。  例:123条 123万 789条 789万 99万  （3）七对，即胡牌的时候是七对牌。  例:11万 22万 99万 44筒 66筒 88筒 99筒   \n" +
                "      ◆4分：  \n" +
                "      （1）清七对，即在七对的基础上，有两对牌是四张一样的。注意:此四张牌并不是杠的牌  例:11万 11万 99万 44筒 66筒 88筒 99筒  （2）清对，即一种花色的大对子。  例: 111万 444万 222万 999万 66万  （3）将对：即二、五、八的大对子。  例: 222万 555万 888万 222条 55筒   \n" +
                "      ◆8分：  \n" +
                "      （1）天胡，即刚码好牌就自然成胡  \n" +
                "      （2）地胡，即刚打第一张牌就成胡  \n" +
                "      （3）清七对，即在七对的基础上，有两对牌是四张一样的。注意:此四张牌并不是杠的牌\n" +
                "      以上就是精品游戏网为大家带来的关于四川麻将玩法的一些简介。通过上面的阅读，大家肯定能清楚地了解到四川麻将的一些基本玩法了~是不是觉得，也没有自己以前想象中的那么复杂难以理解呢？想要了解更多麻将玩法和技巧");
    }
}