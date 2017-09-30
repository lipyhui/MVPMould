package com.kawakp.kp.kernel.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.kawakp.kp.kernel.R;


/**
 * 阿里图标字体支持
 *
 * Created by lipy01 on 2016/6/2.
 */
public class IconFontTextView extends AppCompatTextView {
    public IconFontTextView(Context context) {
        super(context);
    }

    public IconFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        IconFontSet(attrs, context);
    }

    public IconFontTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        IconFontSet(attrs, context);
    }

    private void IconFontSet(AttributeSet attrs, Context context){
        try {
            TypedArray typeArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.IconFontTextView, 0, 0);
            String path = typeArray.getString(R.styleable.IconFontTextView_fontAssetsPath).trim();

            typeArray.recycle();

            if (null != path && path.length() > 0) {
                Typeface iconfont = Typeface.createFromAsset(context.getAssets(), path);
                setTypeface(iconfont);
            }
        } catch (Throwable e) {

        }
    }
}
