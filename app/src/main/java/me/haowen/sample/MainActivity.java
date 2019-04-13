package me.haowen.sample;

import android.os.Bundle;

import java.util.Arrays;

import androidx.appcompat.app.AppCompatActivity;
import me.haowen.sample.adapter.SimpleAdapter;
import me.haowen.textbanner.TextBanner;

public class MainActivity extends AppCompatActivity {

    private String[] hotWordArray = new String[]{
            "肖申克的救赎",
            "这个杀手不太冷",
            "流浪地球",
            "盗梦空间",
            "我不是药神",
            "千与千寻"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextBanner textBanner = findViewById(R.id.textBanner);
        textBanner.setAdapter(new SimpleAdapter(this, Arrays.asList(hotWordArray)));
    }
}