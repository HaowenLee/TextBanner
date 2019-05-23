package com.sample.textbanner;

import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sample.textbanner.adapter.CustomAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    private TextBanner textBanner;
    private TextBanner customTextBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        textBanner = findViewById(R.id.textBanner);
        customTextBanner = findViewById(R.id.customTextBanner);

        initSimpleAdapter();
        initCustomAdapter();
    }

    /**
     * 简单的Adapter
     */
    private void initSimpleAdapter() {
        final SimpleTextBannerAdapter simpleAdapter = new SimpleTextBannerAdapter(this, Arrays.asList(hotWordArray));
        textBanner.setAdapter(simpleAdapter);

        textBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleAdapter.setData(Arrays.asList(updateHotWordArray));
                Toast.makeText(HomeActivity.this, "数据刷新", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 自定义的Adapter
     */
    private void initCustomAdapter() {
        final List<Pair<String, String>> data = new ArrayList<>();
        data.add(new Pair<>("精华", "初入汉服坑的小仙女看过来"));
        data.add(new Pair<>("精华", "国产马自达3，黑科技，3.3L"));
        data.add(new Pair<>("超赞", "最惨855手机，发货至今"));
        data.add(new Pair<>("热文", "自动挡下坡为什么不能D档"));
        data.add(new Pair<>("精华", "蓝鲫和九一八咋搭配才好用"));
        data.add(new Pair<>("超赞", "五一去哪？大数据告诉你"));

        CustomAdapter adapter = new CustomAdapter(this, data);
        customTextBanner.setAdapter(adapter);
        // 设置监听事件
        adapter.setItemClickListener(new CustomAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(HomeActivity.this, data.get(position).second, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 增加体验
     */
    @Override
    protected void onStart() {
        super.onStart();
        textBanner.startAutoPlay();
        customTextBanner.startAutoPlay();
    }

    /**
     * 增加体验
     */
    @Override
    protected void onStop() {
        super.onStop();
        textBanner.stopAutoPlay();
        customTextBanner.stopAutoPlay();
    }
}