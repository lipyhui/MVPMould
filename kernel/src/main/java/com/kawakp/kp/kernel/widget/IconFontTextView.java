package com.kawakp.kp.kernel.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.kawakp.kp.kernel.R;

/**
 * 创建人: penghui.li
 * 创建时间: 2017/8/29
 * 修改人:penghui.li
 * 修改时间:2017/8/29
 * 修改内容:
 *
 * 功能描述:	支持ttf系列文字图标，支持配置ttf路径(只支持将ttf文字图库放在assets文件夹里面)
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

    /**
     * ttf格式文字图标支持实现
     * 支持配置ttf路径(只支持将ttf文字图库放在assets文件夹里面)
     *
     * @param attrs
     * @param context
     */
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
