package com.lyy.hitogether.test;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.listener.CloudCodeListener;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lyy.hitogether.R;
import com.lyy.hitogether.activity.BaseActivity;
import com.lyy.hitogether.bean.Service;
import com.lyy.hitogether.util.HttpUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class TestActivity extends BaseActivity {

	private ImageView mImageViewPic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_test);
	//	ViewUtils.inject(this);  
		Button bt = (Button) findViewById(R.id.id_bt_test);
		mImageViewPic = (ImageView) findViewById(R.id.id_iv_pic);

		bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				try {
					getIntentPic();

					// getJsonFromBmob();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private DisplayImageOptions options;

	private ImageLoadingListener imageLoadingListener = new ImageLoadingListener() {

		@Override
		public void onLoadingStarted(String imageUri, View view) {
			Log.i("ImageLoadingListener", "onLoadingStarted");

		}

		@Override
		public void onLoadingFailed(String imageUri, View view,
				FailReason failReason) {
			Log.i("ImageLoadingListener", "onLoadingFailed");

		}

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			Log.i("ImageLoadingListener", "onLoadingComplete");

		}

		@Override
		public void onLoadingCancelled(String imageUri, View view) {
			Log.i("ImageLoadingListener", "onLoadingCancelled");

		}
	};

	protected void getIntentPic() {
		String uri = "http://img5.imgtn.bdimg.com/it/u=977178979,3700482650&fm=21&gp=0.jpg";
		options = new DisplayImageOptions.Builder()

				.showStubImage(R.drawable.icon)
				.bitmapConfig(Bitmap.Config.ARGB_8888)
				.showImageForEmptyUri(R.drawable.icon)
				.showImageOnFail(R.drawable.icon).cacheInMemory(true)
				.cacheOnDisc(true).displayer(new FadeInBitmapDisplayer(2000))
				.build();



		ImageLoader.getInstance().displayImage(uri, mImageViewPic, options);
	}

	protected void testUtils() throws JSONException {

		HttpUtils.getHttpData(TestActivity.this, "test1",
				new JSONObject().put("id", "22").put("name", "zou"),
				new CloudCodeListener() {

					@Override
					public void onSuccess(Object result) {
						Log.i("TAG", result.toString());
					}

					@Override
					public void onFailure(int arg0, String arg1) {
						Log.i("TAG", "faild");
					}
				});
	}

	protected void getJsonFromBmob() {
		String cloudName = "getAllService";
		AsyncCustomEndpoints cloudCode = new AsyncCustomEndpoints();
		cloudCode.callEndpoint(TestActivity.this, cloudName,
				new CloudCodeListener() {

					@Override
					public void onSuccess(Object result) {

						Message msg = Message.obtain();
						msg.obj = result;
						mhHandler.sendMessage(msg);

					}

					@Override
					public void onFailure(int errCode, String err) {

					}
				});

	}

	Handler mhHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			Log.i("TAg", msg.obj.toString());
			Gson gson = new Gson();

			List<Service> list = gson.fromJson(msg.obj.toString(),
					new TypeToken<List<Service>>() {
					}.getType());

			Log.i("TAg", list.toString());

			// List<Service> list = JsonUtils.getList(msg.obj.toString());
			// for (Service service : list) {
			// Log.i("TAG", service.toString());
			// }
		};
	};

}
