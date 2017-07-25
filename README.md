# AmountEditView

##### 自定义扩展 LinearLayout 控件实现的一款可增减的购物按钮

效果如下

![](https://github.com/MoveToNext/AmountEditView/blob/master/WechatIMG10.jpeg)

| 参考自定义属性 | 对应用途 |
|------|:---:|
| btnTextSize| 加减按钮文字大小|
| btnWidth | 加减按钮宽度|
| editWidth | 输入框宽度|
| viewHeight | 整体高度|
| editSize | 输入框文字大小|
| editTColor | 输入框文字颜色|
| onTextChanged | 是否需要开启 onTextChanged 模式|

### 回调方法
```
volumeView.setOnVolumeChangeListener(new VolumeView.OnVolumeChangeListener() {
            @Override
            public void onVolumeChange(View view, int Volume) {
                Log.d("MainActivity", "Volume:" + Volume);
            }
        });
```