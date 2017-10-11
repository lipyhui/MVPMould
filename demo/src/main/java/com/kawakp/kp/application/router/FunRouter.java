package com.kawakp.kp.application.router;

import android.support.v4.app.Fragment;

/**
 * 创建人: penghui.li
 * 创建时间: 2017/9/15
 * 修改人:penghui.li
 * 修改时间:2017/9/15
 * 修改内容:
 *
 * 功能描述:主列表路由配置必须实现方法。
 * 		   添加侧边栏功能块，必须对于实现一个路由配置枚举类
 * 		   和{@link com.kawakp.kp.application.ui.fragment.main.MainPresenter}中对应列表解析
 */
public interface FunRouter {
	/**
	 * 获取该方法来获取主页子项名
	 */
	String getItemName();
	/**
	 * 获取相应子项对应的Fragment
	 */
	Fragment getTarget();
}
