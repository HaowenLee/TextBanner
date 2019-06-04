# TextBanner

[![License](https://img.shields.io/badge/License%20-Apache%202-337ab7.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![JCenter](https://img.shields.io/badge/%20JCenter%20-1.0.4-5bc0de.svg)](https://bintray.com/haowen/maven/textbanner/_latestVersion)
[![JitPack](https://jitpack.io/v/HaowenLee/TextBanner.svg)](https://jitpack.io/#HaowenLee/TextBanner)
[![MinSdk](https://img.shields.io/badge/%20MinSdk%20-%2014+%20-f0ad4e.svg)](https://android-arsenal.com/api?level=14)

文字轮播切换控件

## 应用截图

<img src="https://raw.githubusercontent.com/HaowenLee/TextBanner/master/arts/text_banner.gif" width="300" alt="TextBanner效果图"/>

#### 步骤 1. 在module的build.gradle添加依赖项

```
dependencies {
        implementation 'com.haowen:textbanner:1.0.4'
}
```

#### 步骤 2. 在布局文件中添加TextBanner，可以设置自定义属性

```
<me.haowen.textbanner.TextBanner
    android:id="@+id/textBanner"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:layout_marginLeft="12dp"
    android:layout_marginRight="12dp"
    app:delayTime="5000"
    app:duration="800" />
```

#### 步骤 3. 在Activity或者Fragment中配置Adapter

1. 简单布局SimpleTextBannerAdapter

```
/**
 * 简单的TextBanner适配器（只含有一个TextView）
 *
 * @param context     上下文
 * @param layoutResId 布局资源ID
 * @param data        字符串列表数据源
 */
SimpleTextBannerAdapter simpleAdapter = new SimpleTextBannerAdapter(this,
        R.layout.item_text_banner_simple, Arrays.asList(hotWordArray));
textBanner.setAdapter(simpleAdapter);
```

2. 自定义布局

继承BaseAdapter实现自己的Adapter

#### 步骤 4. 增加体验（可选）

```
/**
 * 体验优化
 */
@Override
protected void onStart() {
    super.onStart();
    textBanner.startAutoPlay();
    customTextBanner.startAutoPlay();
}

@Override
protected void onStop() {
    super.onStop();
    textBanner.stopAutoPlay();
    customTextBanner.stopAutoPlay();
}
```

### 属性说明

| 名称 | 格式 | 描述 | 示例 |
| ------ | ------ | ------ | ------ |
| duration | integer | 切换动画的时长（单位毫秒） | 800 |
| delayTime | integer | 停留时长（单位毫秒） | 5000 |
| animIn | reference | View进入动画资源ID | @anim/anim_in |
| animOut | reference | View消失动画资源ID | @anim/anim_out |