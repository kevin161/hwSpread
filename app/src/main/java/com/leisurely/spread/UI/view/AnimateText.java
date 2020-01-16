package com.leisurely.spread.UI.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.leisurely.spread.R;
import com.leisurely.spread.util.TextUtil;

/**
 * Created by Administrator on 2018/12/17.
 */

public class AnimateText extends AppCompatTextView {

    private Handler handler = new Handler(Looper.getMainLooper());
    private boolean annimaStart = false; // 开始时是否展示动画
    private boolean isNumber = false; // 是否渲染为数字动画
    private int time = 30;// 动画时长
    private int changeCount = 32; // 数字动画数字变化次数
    private int startNumber = 0; // 渲染为数字动画时 动画的开始数字
    private int endNumber = 0; // 渲染为数字动画时 动画的结束数字
    private OnAnimationEndListener listener;// 动画结束时的监听
    private String text;// 文本
    private int type;//0普通 1两位小数 2四位小数

    public AnimateText(Context context) {
        this(context, null);
    }

    public AnimateText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnimateText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AnimateText);
            annimaStart = a.getBoolean(R.styleable.AnimateText_annimaStart, false);
            isNumber = a.getBoolean(R.styleable.AnimateText_isNumber, false);
            time = a.getInteger(R.styleable.AnimateText_time, 20);
            changeCount = a.getInteger(R.styleable.AnimateText_changeCount, 32);
            a.recycle();
        }
    }

    public void start() {
        text = getText().toString();
        if (isNumber) {
            startNumber = text.length() - 1;
            endNumber = (int) Float.parseFloat(text);
        }
        if (annimaStart) {
            startAnimate();
        }
    }

    public void startAnimate() {
        this.startAnimate(null);
    }

    public void startAnimate(final OnAnimationEndListener listener) {
        this.listener = listener;
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (isNumber) {
                    showNumAnimate();
                } else {
                    showNormalAnimate();
                }
            }
        }).start();
    }

    private void showNormalAnimate() {
        char[] cs = text.toCharArray();
        final StringBuffer buffer = new StringBuffer();
        for (char c : cs) {
            buffer.append(c);
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    AnimateText.this.setText(buffer.toString(), BufferType.NORMAL);
                }
            });
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (listener != null) {
                    listener.onEnd();
                }
            }
        });
    }

    private void showNumAnimate() {
        for (int i = 0; i < changeCount; i++) {
            final float t = nextFloat(i);
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    String s = String.valueOf(t);
                    if (type == 0) {
                    } else if (type == 1) {
                        s = TextUtil.parseMoneyTwo(s);
                    } else if (type == 2) {
                        s = TextUtil.parseMoneyFour(s);
                    }
                    setText(s, BufferType.NORMAL);
                }
            });
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                setText(text, BufferType.NORMAL);
            }
        });
    }

    private float nextFloat(int i) {
        return startNumber + ((endNumber - startNumber) / changeCount * (i + 1));
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public boolean isAnnimaStart() {
        return annimaStart;
    }

    public void setAnnimaStart(boolean annimaStart) {
        this.annimaStart = annimaStart;
    }

    public boolean isNumber() {
        return isNumber;
    }

    public void setNumber(boolean number) {
        isNumber = number;
    }

    public int getStartNumber() {
        return startNumber;
    }

    public void setStartNumber(int startNumber) {
        this.startNumber = startNumber;
    }

    public int getChangeCount() {
        return changeCount;
    }

    public void setChangeCount(int changeCount) {
        this.changeCount = changeCount;
    }

    public void setText(String text) {
        this.text = text;
        setText(text, BufferType.NORMAL);
    }

    public OnAnimationEndListener getListener() {
        return listener;
    }

    public void setListener(OnAnimationEndListener listener) {
        this.listener = listener;
    }

    public interface OnAnimationEndListener {
        void onEnd();
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
