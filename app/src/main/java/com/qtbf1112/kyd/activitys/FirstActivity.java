package com.fkgpby0329.yxb.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.fkgpby0329.yxb.R;

/**
 * @author：zhanglifan
 */
public class FirstActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        TextView text = (TextView) findViewById(R.id.tv_one);
        text.setText("好不夸张的说没有麻将桌的成都茶馆不是真正的茶馆，四川人喜欢打麻将所以四川麻将的玩法可以说是非常有趣而且多种多样的，在各地特色玩法的麻将中四川麻将有着非常独特的魅力" +
                "四川麻将游戏是一款麻将游戏。让众多棋牌玩家都可以尽情的体验牌类游戏带来的乐趣，你也可以与身在远方的朋友一起开设房间打麻将!随时随时体验四川麻将的无限精彩。\n" +
                "\n" +
                "熊猫四川麻将是一款休闲类棋牌游戏，时尚简约的棋牌画面，真人实时对战玩法，陪伴您快乐每一天。");
    }
}
