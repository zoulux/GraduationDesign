package com.lyy.hitogether.activity.fragment.first_fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lyy.hitogether.R;
import com.lyy.hitogether.view.MyViewPager;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment {

	private MyViewPager viewPager;
	private List<Fragment> mTabs = new ArrayList<Fragment>();
	private FragmentPagerAdapter mAdapter;
	//private View v;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_first, null);

	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		System.out.println("onViewCreated");
		//v = view;
		initView(view);
		initData();
		viewPager.setAdapter(mAdapter);
		viewPager.setScanScroll(false);
	}

	private FirstFragmentDestination firstFragmentDestination = new FirstFragmentDestination();

	private void initData() {

		mTabs.add(firstFragmentDestination);
		mTabs.add(new FirstFragmentOfFriend());
		mAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {

			@Override
			public int getCount() {
				return mTabs.size();
			}

			@Override
			public Fragment getItem(int position) {
				return mTabs.get(position);
			}
		};
	}

	public void setItem(int pos) {
		viewPager.setCurrentItem(pos, true);

	}

	private void initView(View v) {
		viewPager = (MyViewPager) v
				.findViewById(R.id.id_viewpager_fragment_first);

	}

	@Override
	public void onDestroyView() {
		System.err.println("onDestroyViewMax");

		super.onDestroy();
	}

	@Override
	public void onResume() {
		System.out.println("onResume");
		System.out.println(mTabs.size() + ">>>>>>>>>>>>>>>>>>>>");
		super.onResume();
	}

	@Override
	public void onAttach(Activity activity) {
		System.out.println("onAttach");
		super.onAttach(activity);
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		System.out.println("onCreate");
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		System.out.println("onActivityCreated");
	}
	
	public void clearTab(){
		mTabs.clear();
	}

	@Override
	public void onPause() {
		//mTabs.clear();
		super.onPause();
		System.out.println("onPause");
	}

	public void setMin() {

	}
	@Override
	public void onStop() {
		super.onStop();
		System.out.println("onStop");
	}

}
