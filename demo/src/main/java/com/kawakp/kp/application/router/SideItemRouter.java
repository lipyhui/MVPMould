package com.kawakp.kp.application.router;

import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;

import com.kawakp.kp.application.R;
import com.kawakp.kp.application.app.KawakpApplication;
import com.kawakp.kp.application.ui.fragment.main.MainFragment;

/**
 * 创建人: penghui.li
 * 创建时间: 2017/9/14
 * 修改人:penghui.li
 * 修改时间:2017/9/14
 * 修改内容:
 * <p>
 * 功能描述:侧边栏fragment功能列表配置
 */

public enum SideItemRouter {
<<<<<<< HEAD
    CHART(1, "折线图"),
    ANIM(2, "动画");

    private int mType = 1;
    private String mSideName = "折线图";

    SideItemRouter(int type, String name) {
        mSideName = name;
        mType = type;
    }

    public String getSideName() {
        return mSideName;
    }

    public Fragment getFragment() {
        return MainFragment.Companion.newInstance(mType);
    }
=======
	CHART(1, R.string.side_char_img, R.string.side_char),
	ANIM(2, R.string.side_anim_img, R.string.side_anim);

	private int mType = 1;
	private String mSideImg;
	private String mSideName = "折线图";

	SideItemRouter(int type, @StringRes int imgId, @StringRes int nameId){
		mSideImg = KawakpApplication.getContext().getResources().getString(imgId);
		mSideName = KawakpApplication.getContext().getResources().getString(nameId);
		mType = type;
	}

	public Fragment getFragment() {
		return MainFragment.Companion.newInstance(mType);
	}

	public String getSideImg() {
		return mSideImg;
	}

	public String getSideName() {
		return mSideName;
	}
>>>>>>> 09d532aaaf49234f5f8e4e780f33402123c37b26
}
