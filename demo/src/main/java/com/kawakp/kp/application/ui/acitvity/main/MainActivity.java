package com.kawakp.kp.application.ui.acitvity.main;

import com.kawakp.kp.application.Cat;
import com.kawakp.kp.application.R;
import com.kawakp.kp.application.User;
import com.kawakp.kp.application.base.BaseActivity;
import com.kawakp.kp.application.databinding.ActivityMainBinding;
import com.kawakp.kp.kernel.KpApplication;
import com.kawakp.kp.kernel.utils.RealmManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity<MainPresenter, ActivityMainBinding> implements MainAble {

	@Override
	public int getLayoutId() {
		return R.layout.activity_main;
	}

	@Override
	public void init() {
		saveJava();
//		savekotlinCat();
	}

	private void saveJava(){
		List<User> list = new ArrayList<>();
		for (int i = 10; i < 1000; i++) {
			User user = new User();
			user.setAge(i);
			user.setName("name is " + i);
			list.add(user);
		}

		RealmManager.getInstance(KpApplication.getRealmInstance()).add(list);
	}

	private void savekotlinCat(){
		List<Cat> list = new ArrayList<Cat>();
		for (int i = 0; i < 10; i++) {
			Cat cat = new Cat();
			cat.setName("2017-04-07 03:00:0" + i);
			list.add(cat);
		}
		for (int i = 10; i < 20; i++) {
			Cat cat = new Cat();
			cat.setName("2017-04-07 03:00:" + i);
			list.add(cat);
		}

		RealmManager.getInstance(KpApplication.getRealmInstance()).add(list);
	}

	@Override
	public void setData(String data) {
		mBinding.setHello(data);
	}

	@Override
	public void onStateViewRetryListener() {
		showContent();
	}
}
