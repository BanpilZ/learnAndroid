package com.fkgpby0329.yxb.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.fkgpby0329.yxb.R;

/**
 * @author：zhanglifan
 */
public class ThreeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);
        TextView text = (TextView) findViewById(R.id.tv_three);
        text.setText("  四川麻将大家都不陌生吧，下面小编来给大家介绍一下四川麻将的技巧吧！\n" +
                "\n" +
                " \n" +
                "\n" +
                "2.保留的牌（比如要保留条），也可以先选一张没有用的打出，让对手迷糊；1.必打牌可以放上一会，不断变幻出牌程序，让对手摸不清路数；\n" +
                "3.条件不成熟千万不做清一色；\n" +
                "4.有三张相同牌，先碰后开杠,多长分；\n" +
                "5.有对子尽量全碰，好下雨刮风；\n" +
                "6.牢记“卡下家，防对家，顶上家“，这样你就可以洗白三家！\n" +
                "小编不知大家看懂没有，如果还是不懂得话就来看一下下面的例子吧！\n" +
                "摸到8万，面临选择。打9万还是3条，或者1万？\n" +
                "小编举这个例子的意义在于告诉小伙伴们如何去评估这几张牌的价值。\n" +
                " \n" +
                "3条是“爆肚子”多张，有人认为它没有价值，直接打出就是了。实际上，3条也可以用来上张，摸到1、2、4、5条都构成新的搭子（都是两口搭子），必要时可以取代1、2万（边张搭子）。\n" +
                " \n" +
                "9万也可视为“多张”，没有用处，但是摸到7万可以构成顺子，因此不能认为它没有价值。实战中笔者打出的是3条。\n" +
                "四川麻将“实战”技巧:\n" +
                "该游戏完全按照成都麻将的现行部分规则执行，麻将牌只有筒、条、万三门;缺门可胡;点炮时点炮的一个人负责;胡牌时有杠加分;最后四张必须能胡必须胡;\n" +
                "　　◆麻将一副牌共108张，筒、条、万各36张，4人同时玩。\n" +
                "　　◆胡牌情况：必须缺一门才能胡牌;任何一对牌都可以做将。规定如下：\n" +
                "　　◆1分：平胡，即缺一门，四坎牌加一对将。\n" +
                "　　◆2分： (1)对对胡，即每坎牌都是三张一样的牌。例: 111万444万222筒999筒66筒(2)抢杠胡，即在他人杠时抢牌胡。");
    }
}
