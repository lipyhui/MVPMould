package com.kawakp.kp.application.ui.fragment.anim;

import com.bumptech.glide.Glide;
import com.kawakp.kp.application.R;
import com.kawakp.kp.application.base.BaseFragment;
import com.kawakp.kp.application.databinding.FragmentNetImgBinding;
import com.kawakp.kp.kernel.base.defaults.EmptyPresenter;

/**
 * 创建人: penghui.li
 * 创建时间: 2017/9/14
 * 修改人:penghui.li
 * 修改时间:2017/9/14
 * 修改内容:
 * <p>
 * 功能描述:加载网络图片支持
 */

public class NetImgFragment extends BaseFragment<EmptyPresenter, FragmentNetImgBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_net_img;
    }


    @Override
    protected void onFirstUserVisible() {
        String url = "http://a.hiphotos.baidu.com/image/pic/item/91ef76c6a7efce1b0db20ed9a551f3deb48f650c.jpg";
        Glide.with(this).load(url).into(mBinding.glideImg);
    }
}
