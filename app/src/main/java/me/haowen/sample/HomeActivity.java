package me.haowen.sample;

import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

import me.haowen.sample.adapter.CustomAdapter;
import me.haowen.textbanner.TextBanner;
import me.haowen.textbanner.adapter.SimpleTextBannerAdapter;

public class HomeActivity extends AppCompatActivity {

    /**
     * 热搜词数据
     */
    private String[] hotWordArray = new String[]{
            "肖申克的救赎",
            "这个杀手不太冷",
            "流浪地球",
            "盗梦空间",
            "我不是药神",
            "千与千寻"
    };

    /**
     * 更新后的数据
     */
    private String[] updateHotWordArray = new String[]{
            "蝙蝠侠",
            "复仇者联盟4",
            "仙剑3",
            "小猪佩奇",
            "猫和老鼠",
            "哆啦A梦"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initSimpleAdapter();
        initCustomAdapter();
    }

    /**
     * 简单的Adapter
     */
    private void initSimpleAdapter() {
        TextBanner textBanner = findViewById(R.id.textBanner);
        final SimpleTextBannerAdapter simpleAdapter = new SimpleTextBannerAdapter(this, Arrays.asList(hotWordArray));
        textBanner.setAdapter(simpleAdapter);

        textBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleAdapter.setData(Arrays.asList(updateHotWordArray));
            }
        });
    }

    /**
     * 自定义的Custom
     */
    private void initCustomAdapter() {
        List<Pair<String, String>> data = new ArrayList<>();
        data.add(new Pair<>("精华", "初入汉服坑的小仙女看过来"));
        data.add(new Pair<>("精华", "国产马自达3，黑科技，3.3L"));
        data.add(new Pair<>("超赞", "最惨855手机，发货至今"));
        data.add(new Pair<>("热文", "自动挡下坡为什么不能D档"));
        data.add(new Pair<>("精华", "蓝鲫和九一八咋搭配才好用"));
        data.add(new Pair<>("超赞", "五一去哪？大数据告诉你"));

        TextBanner textBanner = findViewById(R.id.customTextBanner);
        textBanner.setAdapter(new CustomAdapter(this, data));

        textBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "详细列表页", Toast.LENGTH_SHORT).show();
            }
        });
    }
}