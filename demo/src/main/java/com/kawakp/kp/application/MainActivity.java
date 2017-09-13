package com.kawakp.kp.application;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.kawakp.kp.application.databinding.ActivityMainBinding;
import com.kawakp.kp.kernel.KernelJNI;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);

		ActivityMainBinding mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

		// Example of a call to a native method
//		TextView tv = (TextView) findViewById(R.id.sample_text);
		mBinding.testText.setText(KernelJNI.stringFromJNI());
	}
}
