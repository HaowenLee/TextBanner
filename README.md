# TextBanner

[![JitPack](https://jitpack.io/v/HaowenLee/TextBanner.svg)](https://jitpack.io/#HaowenLee/TextBanner)

文字轮播滚动切换

## 应用截图

<img src="https://github.com/HaowenLee/TextBanner/blob/master/screenshot/text_banner.gif" width="360" alt="TextBanner效果图"/>

#### 步骤 1. 将JitPack存储库添加到构建文件中

将其添加到存储库末尾的根build.gradle中：

```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

#### 步骤 2. 在module的build.gradle添加依赖项

```
dependencies {
        implementation 'com.github.HaowenLee:TextBanner:1.0.0'
}

```

#### 步骤 3. 在布局文件中添加BannerViewPager和IndicatorLayout，可以设置自定义属性

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

#### 步骤 4. 在Activity或者Fragment中配置Adapter
```
textBanner.setAdapter(new SimpleTextBannerAdapter(this, Arrays.asList(hotWordArray)));
```

### 属性说明

| 名称 | 格式 | 描述 |
| ------ | ------ | ------ |
| duration | int | 切换动画的时长（单位毫秒） |
| delayTIme | int | 停留时长（单位毫秒） |