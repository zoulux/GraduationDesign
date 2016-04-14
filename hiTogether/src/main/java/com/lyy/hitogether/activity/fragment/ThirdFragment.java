package com.lyy.hitogether.activity.fragment;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import cn.bmob.push.a.in;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.lyy.hitogether.R;
import com.lyy.hitogether.activity.ShowSceneDetailsActivity;
import com.lyy.hitogether.activity.fragment.first_fragment.FirstFragmentDestination;
import com.lyy.hitogether.adapter.PictureAndTextAdapter;
import com.lyy.hitogether.adapter.ThirdFragmentAdapter;
import com.lyy.hitogether.adapter.ThirdFragmentAdapter.OnThirdFragmentBtListener;
import com.lyy.hitogether.bean.HotScenic;
import com.lyy.hitogether.bean.MyUser;
import com.lyy.hitogether.bean.Service;
import com.lyy.hitogether.bean.ThirdFragmentBean;
import com.lyy.hitogether.global.App;
import com.lyy.hitogether.mydialog.SweetAlertDialog;

public class ThirdFragment extends BaseFragment {
	// private PullToRefreshGridView mGridView;
	// private ThirdFragmentAdapter thirdFragmentAdapter;

	private boolean isPrepared;
	// private List<Service> list;
	private SweetAlertDialog sweetAlertDialog;

	private List<HotScenic> hotScenicList;

	private PullToRefreshGridView gridView;
	// private SweetAlertDialog sweetAlertDialog;
	private String[] scen = new String[] { "风景1", "风景2", "风景3", "风景4", "风景5",
			"风景6", "风景7", "风景8", "风景9", "风景10" };
	private int[] pics = new int[] { R.drawable.p1, R.drawable.p2,
			R.drawable.p3, R.drawable.p4, R.drawable.p1, R.drawable.p2,
			R.drawable.p3, R.drawable.p4, R.drawable.p1, R.drawable.p2 };

	// private SweetAlertDialog alertDialog;

	// private boolean mHasLoadOnce=false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i("ThirdFragment", "onCreateView");
		View view = inflater.inflate(R.layout.fragment_third, container, false);
		init(view);
		isPrepared = true;

		Log.i("TAG", "2");
		lazyLoad();

		return view;
	}

	private void init(View view) {
		initView(view);

		initEvent();

	}

	private void initEvent() {
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(ThirdFragment.this.getActivity(),
						ShowSceneDetailsActivity.class);
				if (hotScenicList != null) {
					HotScenic hotScenic = hotScenicList.get(position);
					intent.putExtra(HotScenic.TAG, hotScenic);
				}

				startActivity(intent);
			}
		});
	}

	private void initView(View view) {
		gridView = (PullToRefreshGridView) view
				.findViewById(R.id.id_third_fragment_grideview);
		sweetAlertDialog = new SweetAlertDialog(getActivity(), 5);
		sweetAlertDialog.setTitleText("加载中...");
		sweetAlertDialog.showCancelButton(false);
		
		initIndicator();

	}

	private void initIndicator() {
		ILoadingLayout startLabels = gridView
				.getLoadingLayoutProxy(true, false);
		startLabels.setPullLabel("下拉刷新");// 刚下拉时，显示的提示
		startLabels.setRefreshingLabel("正在刷新...");// 刷新时
		startLabels.setReleaseLabel("松开刷新数据");// 下来达到一定距离时，显示的提示
	}

	private class GetDataTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			try {
				// Thread.sleep(2000);
				postAsync("getAllHotScenic", null);
			} catch (Exception e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			gridView.onRefreshComplete();
		}
	}

	// private List<ThirdFragmentBean> getdatas() {
	//
	// List<ThirdFragmentBean> bean = new ArrayList<ThirdFragmentBean>();
	// for (int i = 0; i < 30; i++) {
	// bean.add(new ThirdFragmentBean(R.drawable.p1,
	// R.drawable.default_avarter, "名字" + i, " 中国好景点" + i, " 景点"
	// + i, 2.5f));
	// }
	// return bean;
	// }

	/**
	 * 返回正确结果，接下来去写适配器
	 * 
	 * @param json
	 *            json数据
	 */
	protected void handleSuccess(String json) {
		Log.i("ThirdFragment", "handleSuccess");
		sweetAlertDialog.dismiss();
		Gson gson = new Gson();
		if (hotScenicList == null) {
			hotScenicList = gson.fromJson(json,
					new TypeToken<List<HotScenic>>() {
					}.getType());
		}

	
		PictureAndTextAdapter adapter = new PictureAndTextAdapter(
				getActivity(), hotScenicList);
		gridView.setAdapter(adapter);

		// initEvent();
	}

	/**
	 * 返回错误结果，提示。。
	 * 
	 * @param code
	 *            错误代码
	 */

	protected void handleFaild(String code) {
		Log.i("ThirdFragment", "handleFaild");
		sweetAlertDialog.dismiss();
		// baseProgress.cancel();
		ShowToast("请检查网络");
	}

	/**
	 * 不可见不加载，没准备好不加载
	 */
	@Override
	protected void lazyLoad() {

		if (!isPrepared || !isVisible) {
			Log.i("lazyLoad1", isPrepared + ":" + isPrepared);
			return;
		}

		Log.i("lazyLoad2", isPrepared + ":" + isPrepared);
		// baseProgress.show();

		sweetAlertDialog.show();
		postAsync("getAllHotScenic", null);
	}

	@Override
	public void onPause() {
		isVisible = false;
		isPrepared = false;
		sweetAlertDialog.dismiss();
		super.onPause();
	}

	@Override
	public boolean handleMessage(Message msg) {

		switch (msg.what) {
		case GET_SUCCESS:
			handleSuccess(msg.obj.toString());
			break;
		case GET_FAILD:
			handleFaild(msg.obj.toString());
			break;
		default:
			break;
		}

		return false;
	}

	@Override
	public void onResume() {
		super.onResume();
		gridView.setOnRefreshListener(new OnRefreshListener2<GridView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<GridView> refreshView) {
				Log.e("TAG", "onPullDownToRefresh"); // Do work to
				String label = DateUtils.formatDateTime(
						ThirdFragment.this.getActivity(),
						System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
								| DateUtils.FORMAT_SHOW_DATE
								| DateUtils.FORMAT_ABBREV_ALL);

				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

				new GetDataTask().execute();
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<GridView> refreshView) {
				Log.e("TAG", "onPullUpToRefresh"); // Do work to refresh
													// the list here.
				new GetDataTask().execute();
			}
		});
	}
}
