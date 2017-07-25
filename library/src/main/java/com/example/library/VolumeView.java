package com.example.library;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


/**
 * Description: 组合加减输入框
 * date: 2016/10/20 18:05
 */
public class VolumeView extends LinearLayout implements View.OnClickListener, TextWatcher {
    private EditText etVolume;
    private Button btnDecrease, btnIncrease;
    private Context mContext;
    private static final String TAG = "VolumeView";
    private int Volume = 1; //购买数量
    private int max_multipliy = 10; //最大倍数

    private OnVolumeChangeListener mListener;
    private boolean flag;
    private boolean onTextChangedAble;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public VolumeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, attrs);
    }

    public VolumeView(Context context) {
        super(context);
        initView(context, null);
    }

    public VolumeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public VolumeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(final Context context, AttributeSet attrs) {
        this.mContext = context;
        LayoutInflater.from(context).inflate(R.layout.view_volume, this);
        etVolume = (EditText) findViewById(R.id.etAmount);
        btnDecrease = (Button) findViewById(R.id.btnDecrease);//减减
        btnIncrease = (Button) findViewById(R.id.btnIncrease);//加加
        btnDecrease.setOnClickListener(this);
        btnIncrease.setOnClickListener(this);
        etVolume.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && flag) {
                    flag = false;
                    if (etVolume.getText().toString().isEmpty()) {
                        return;
                    }
                    try {
                        Volume = Integer.parseInt(etVolume.getText().toString());
                        if (Volume < 1) {
                            Toast.makeText(mContext,"请输入大于1的整数",Toast.LENGTH_SHORT).show();
                            Volume = 1;
                            setVolume(1);
                        }
                        if (Volume > max_multipliy) {
                            Toast.makeText(mContext,mContext.getString(R.string.betting_ulv_holder, max_multipliy),Toast.LENGTH_SHORT).show();
                            Volume = max_multipliy;
                            setVolume(max_multipliy);
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        Toast.makeText(mContext,mContext.getString(R.string.betting_ulv_holder, max_multipliy),Toast.LENGTH_SHORT).show();
                        Volume = max_multipliy;
                        setVolume(max_multipliy);
                    }
                    if (mListener != null) {
                        mListener.onVolumeChange(VolumeView.this, Volume);
                    }
                }
            }
        });
        etVolume.addTextChangedListener(this);

        //添加自定义属性
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attrs, R.styleable.VolumeView);
        int btnWidth = obtainStyledAttributes.getDimensionPixelSize(R.styleable.VolumeView_btnWidth, DensityUtils.dp2px(context, 25));
        int hight = obtainStyledAttributes.getDimensionPixelSize(R.styleable.VolumeView_viewHeight, DensityUtils.dp2px(context, 25));
        int editWidth = obtainStyledAttributes.getDimensionPixelSize(R.styleable.VolumeView_editWidth, DensityUtils.dp2px(context, 40));
        float editSize = obtainStyledAttributes.getDimension(R.styleable.VolumeView_editSize, DensityUtils.dp2px(context, 13));
        float btnTextSize = obtainStyledAttributes.getDimension(R.styleable.VolumeView_btnTextSize, DensityUtils.dp2px(context, 13));
        int editTextColor = obtainStyledAttributes.getColor(R.styleable.VolumeView_editTColor, Color.parseColor("#000000"));
        onTextChangedAble = obtainStyledAttributes.getBoolean(R.styleable.VolumeView_onTextChanged, false);
        obtainStyledAttributes.recycle();

        LayoutParams params = new LayoutParams(btnWidth, hight);
        params.gravity = Gravity.CENTER;
        btnDecrease.setLayoutParams(params);
        btnIncrease.setLayoutParams(params);
        btnDecrease.setTextSize(TypedValue.COMPLEX_UNIT_PX, btnTextSize);
        btnIncrease.setTextSize(TypedValue.COMPLEX_UNIT_PX, btnTextSize);
        LayoutParams textParams = new LayoutParams(editWidth, hight);
        etVolume.setLayoutParams(textParams);
        etVolume.setTextColor(editTextColor);
        etVolume.setTextSize(TypedValue.COMPLEX_UNIT_PX, editSize);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btnDecrease) {
            if (Volume > 1) {
                Volume --;
                setEtVolume(Volume);
            }

        } else if (i == R.id.btnIncrease) {
            if (Volume < max_multipliy) {
                Volume ++;
                setEtVolume(Volume);
            }

        }
        etVolume.clearFocus();
        if (mListener != null) {
            mListener.onVolumeChange(this, Volume);
        }
    }

    private void setEtVolume(int Volume) {
        etVolume.removeTextChangedListener(this);
        etVolume.setText(Volume + "");
        etVolume.setSelection(etVolume.length());
        etVolume.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        flag = false;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (etVolume.getText().toString().isEmpty()) {
            Volume = 0;
            if (mListener != null && onTextChangedAble) {
                mListener.onVolumeChange(VolumeView.this, Volume);
            }
            return;
        }
        try {
            Volume = Integer.parseInt(etVolume.getText().toString());
            if (Volume < 1) {
                Toast.makeText(mContext,"请输入大于1的整数",Toast.LENGTH_SHORT).show();
                Volume = 1;
                setVolume(1);
            }
            if (Volume > max_multipliy) {
                Toast.makeText(mContext,mContext.getString(R.string.betting_ulv_holder, max_multipliy),Toast.LENGTH_SHORT).show();
                Volume = max_multipliy;
                setVolume(max_multipliy);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Toast.makeText(mContext,mContext.getString(R.string.betting_ulv_holder, max_multipliy),Toast.LENGTH_SHORT).show();
            Volume = max_multipliy;
            setVolume(max_multipliy);
        }
        if (mListener != null && onTextChangedAble) {
            mListener.onVolumeChange(VolumeView.this, Volume);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        flag = true;
    }

    public void setVolume(int Volume) {
        this.Volume = Volume;
        setEtVolume(Volume);
    }

    public void setOnVolumeChangeListener(OnVolumeChangeListener onVolumeChangeListener) {
        this.mListener = onVolumeChangeListener;
    }

    public void clear() {
        etVolume.clearFocus();
    }


    public int getVolume() {
        return Volume;
    }

    public String getVolumeStr() {
        return String.valueOf(Volume);
    }

    /**
     * 封装对输入框的合法性判断
     *
     * @return
     */
    public boolean isCorrectAble() {
        return Volume > 0 && Volume <= max_multipliy;
    }

    public interface OnVolumeChangeListener {
        void onVolumeChange(View view, int Volume);
    }
}
