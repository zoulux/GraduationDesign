package com.lyy.hitogether.activity;

import java.io.File;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow.OnDismissListener;

import cn.bmob.im.BmobUserManager;
import cn.bmob.push.a.be;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.ThumbnailUrlListener;

import com.bmob.BmobProFile;
import com.bmob.btp.callback.LocalThumbnailListener;
import com.bmob.btp.callback.ThumbnailListener;
import com.bmob.btp.callback.UploadBatchListener;
import com.lyy.hitogether.R;
import com.lyy.hitogether.adapter.MyJourneySetAdapter;
import com.lyy.hitogether.adapter.MyJourneySetAdapter.ItemClickListener;
import com.lyy.hitogether.bean.MyUser;
import com.lyy.hitogether.bean.Trip;
import com.lyy.hitogether.bean.TripItem;
import com.lyy.hitogether.bean.TripLocal;
import com.lyy.hitogether.mydialog.SweetAlertDialog;
import com.lyy.hitogether.util.FileService;
import com.lyy.hitogether.util.ImageUir;
import com.lyy.hitogether.view.CustomTitleBarView;
import com.lyy.hitogether.view.CustomTitleBarView.onLeftBarViewClickListener;
import com.lyy.hitogether.view.CustomTitleBarView.onRightBarViewClickListener;
import com.lyy.hitogether.view.MyJourneyPopupWindow;
import com.lyy.hitogether.view.MyJourneyPopupWindow.onToAlbumClickListener;
import com.lyy.hitogether.view.MyJourneyPopupWindow.onToCameraClickListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * 这一段太恶心了，有时间再维护吧
 * 
 * @author Lenovo
 * 
 */
public class MyServiceActivity extends BaseActivity implements
		ItemClickListener, OnClickListener {

	private ListView mListView;

	private MyJourneySetAdapter mAdapter;
	private List<TripLocal> mList;
	private MyJourneyPopupWindow mMyJourneyPopupWindow;

	private int CODE_ALBUM = 0x001;
	private int CODE_CAMERA = 0x002;
	private Uri oriaginalUri;

	private int location;
	String realFilePath;
	private CustomTitleBarView titleBar;

	private Trip trip;

	public static final String TXT_TAG = "添加文字描述";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_my_service);
		ImageLoaderConfiguration configuration = ImageLoaderConfiguration
				.createDefault(this);
		ImageLoader.getInstance().init(configuration);
		init();

	}

	private void init() {
		mListView = (ListView) findViewById(R.id.id_activity_my_service_lv);
		titleBar = (CustomTitleBarView) findViewById(R.id.id_my_service_title_bar);
		mList = new ArrayList<TripLocal>();

		TripLocal bt = new TripLocal();
		bt.beanType = TripLocal.BUTTON;
		mList.add(bt);

		TripLocal detail = new TripLocal();
		detail.beanType = TripLocal.DETAIL;
		detail.picPath = "drawable://" + R.drawable.pictures_no;
		detail.txt = TXT_TAG;
		mList.add(0, detail);

		mAdapter = new MyJourneySetAdapter(this, mList);
		mListView.setAdapter(mAdapter);
		mAdapter.setmListener(this);

		mMyJourneyPopupWindow = new MyJourneyPopupWindow(this);

		setMyJourneyPopupWindowListener();
		setTitleBarListener();

		mFile = BmobProFile.getInstance(this);
		trip = new Trip();
		trip.setUser(BmobUserManager.getInstance(this).getCurrentUser(
				MyUser.class));

	}

	String[] arr;

	List<TripItem> tripItems = new ArrayList<TripItem>();

	private void setTitleBarListener() {
		titleBar.setOnRightBarViewClickListener(new onRightBarViewClickListener() {

			@Override
			public void onclick(View v) {
				if (baseDialog.getAlerType() == SweetAlertDialog.NORMAL_TYPE) {
					baseDialog.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
				}

				baseDialog.setTitleText("正在压缩图片...");
				baseDialog.show();
				List<String> path = new ArrayList<String>();
				tripItems.clear();

				String tagPic = "file:///";

				for (TripLocal bean : mList) {
					if (bean.getBeanType() == TripLocal.DETAIL) {

						TripItem item = new TripItem();
						String picPath = bean.getPicPath();
						String txt = bean.getTxt();
						// ShowLog(picPath + "醒目");

						if (picPath.startsWith("drawable")) {
							picPath = "";
						} else if (picPath.startsWith(tagPic)) {
							path.add(picPath.substring(tagPic.length()));
						}

						if (txt.startsWith(TXT_TAG)) {
							txt = "";
						}

						item.setPhotoPath(picPath);
						item.setPhotoDescribe(txt);
						tripItems.add(item);

					}

				}

				arr = new String[path.size()];
				for (int i = 0; i < arr.length; i++) {
					arr[i] = path.get(i);
					ShowLog(arr[i] + "压缩图片源地址");
				}

				// AsyncTask task = new CompressTask();
				// task.execute(arr);
				new Thread(new Runnable() {

					@Override
					public void run() {
						compressPic(arr);

					}
				}).start();

			}
		});
		titleBar.setOnLeftBarViewClickListener(new onLeftBarViewClickListener() {

			@Override
			public void onclick(View v) {
				File file = new File(Environment.getExternalStorageDirectory()
						+ "/Hitogether/");
				FileService.delete(file);
				// delete(file);
				MyServiceActivity.this.finish();
			}
		});

	}

	protected void upLoad(String[] path) {
		ShowLog("准备上传");

		baseDialog.setTitleText("正在上传数据...");
		BmobProFile.getInstance(this).uploadBatch(path,
				new UploadBatchListener() {

					@Override
					public void onError(int statuscode, String errormsg) {

						ShowLog(errormsg + "hehe");

					}

					@Override
					public void onSuccess(boolean isFinish, String[] fileNames,
							String[] urls, BmobFile[] files) {

						if (isFinish) {
							ShowLog("上传完成");
							int j = 0;
							int size = tripItems.size();
							for (int i = 0; i < size; i++) {
								TripItem item = tripItems.get(i);

								if (!TextUtils.isEmpty(item.getPhotoPath())) {
									item.setPhotoPath(files[j++].getUrl());

								}

							}
							trip.setData(tripItems);

							postDataToServer();

							//
							// for (BmobFile fi : files) {
							//
							// ShowLog("getFilename:" + fi.getFilename()
							// + "   getFileUrl:"
							// + fi.getFileUrl(MyServiceActivity.this)
							// + "  getGroup:" + fi.getGroup()
							// + "   getUrl:" + fi.getUrl());
							//
							// }

						}

					}

					@Override
					public void onProgress(int curIndex, int curPercent,
							int total, int totalPercent) {
						ShowLog(curPercent + "" + "hahha");

					}
				});

	}

	protected void postDataToServer() {
		ShowLog("提交数据");
		Trip newTrip = new Trip(trip);
		newTrip.save(MyServiceActivity.this, new SaveListener() {

			@Override
			public void onSuccess() {
				baseDialog.dismiss();

				ShowLog("onSuccess");

			}

			@Override
			public void onFailure(int code, String err) {
				ShowLog("code:" + code + "  err:" + err);

			}
		});

	}

	private void setMyJourneyPopupWindowListener() {
		mMyJourneyPopupWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				mMyJourneyPopupWindow.lightOn(MyServiceActivity.this);

			}
		});
		mMyJourneyPopupWindow
				.setOnToAlbumClickListener(new onToAlbumClickListener() {

					@Override
					public void onToAlbumClick(View v) {
						pickPhoto();
						mMyJourneyPopupWindow.dismiss();
						mMyJourneyPopupWindow.lightOn(MyServiceActivity.this);

					}
				});

		mMyJourneyPopupWindow
				.setOnToCameraClickListener(new onToCameraClickListener() {

					@Override
					public void onToCameraClick(View v) {
						pickCamera();
						mMyJourneyPopupWindow.dismiss();
						mMyJourneyPopupWindow.lightOn(MyServiceActivity.this);
					}

				});

	}

	@Override
	public void onClick(View v) {
		onClick(v, 0);
	}

	@Override
	public void onClick(View v, int position) {
		switch (v.getId()) {
		case R.id.id_bt_add:
			addItem();
			break;
		case R.id.id_bt_edit_txt:
			this.location = position;
			editTxt(v);
			break;
		case R.id.id_bt_edit_photo:
			this.location = position;
			editPic(v);
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		ContentResolver resolver = getContentResolver();
		if (resultCode == RESULT_OK) {

			if (requestCode == CODE_ALBUM) {
				oriaginalUri = data.getData();
				getAlbumRealPath();
				dealWithPic();
			} else if (requestCode == CODE_CAMERA) {
				getCameraRealPath();
				dealWithPic();
			}

		}
	}

	private void getCameraRealPath() {
		realFilePath = "file://" + imageUir.getNewPath();
	}

	private void getAlbumRealPath() {
		realFilePath = "file://"
				+ getRealFilePath(MyServiceActivity.this, oriaginalUri);

	}

	private void dealWithPic() {

		mList.get(location).setPicPath(realFilePath);
		mAdapter.notifyDataSetChanged();
		new Handler().postDelayed(new Runnable() {
			public void run() {
				mListView.setSelection(location);
			}
		}, 1000);

	}

	ImageUir imageUir;

	private void pickPhoto() {
		Intent getImage = new Intent(Intent.ACTION_GET_CONTENT);
		getImage.addCategory(Intent.CATEGORY_OPENABLE);
		getImage.setType("image/*");
		startActivityForResult(getImage, CODE_ALBUM);

	}

	private void pickCamera() {
		Intent cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		FileService.makeFile(Environment.getExternalStorageDirectory()
				+ "/Hitogether/journeySet/");
		imageUir = new ImageUir();
		doIt(cameraintent);
	}

	private synchronized void doIt(final Intent cameraintent) {

		cameraintent.putExtra(MediaStore.EXTRA_OUTPUT,
				Uri.fromFile(imageUir.getNewFile()));
		startActivityForResult(cameraintent, CODE_CAMERA);

	}

	private void editPic(View v) {
		mMyJourneyPopupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
		mMyJourneyPopupWindow.lightOff(MyServiceActivity.this);

	}

	private void editTxt(View v) {
		final EditText content = new EditText(this);
		new AlertDialog.Builder(this).setTitle("请输入").setView(content)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						String str = content.getText().toString();
						mList.get(location).setTxt(str);
						mAdapter.notifyDataSetChanged();
						new Handler().postDelayed(new Runnable() {
							public void run() {
								mListView.setSelection(location);
							}
						}, 1000);
					}
				}).setNegativeButton("取消", null).show();

	}

	private void addItem() {

		mList.add(mList.size() - 1, new TripLocal(TripLocal.DETAIL,
				"drawable://" + R.drawable.pictures_no, TXT_TAG));
		Log.i("TAG", mList.toString());

		mAdapter.notifyDataSetChanged();

		new Handler().postDelayed(new Runnable() {
			public void run() {
				mListView.smoothScrollToPosition(mListView.getHeight());
			}
		}, 200);
	}

	/**
	 * Try to return the absolute file path from the given Uri
	 * 
	 * @param context
	 * @param uri
	 * @return the file path or null
	 */
	public static String getRealFilePath(final Context context, final Uri uri) {
		if (null == uri)
			return null;
		final String scheme = uri.getScheme();
		String data = null;
		if (scheme == null)
			data = uri.getPath();
		else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
			data = uri.getPath();
		} else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
			Cursor cursor = context.getContentResolver().query(uri,
					new String[] { ImageColumns.DATA }, null, null, null);
			if (null != cursor) {
				if (cursor.moveToFirst()) {
					int index = cursor.getColumnIndex(ImageColumns.DATA);
					if (index > -1) {
						data = cursor.getString(index);
					}
				}
				cursor.close();
			}
		}
		return data;
	}

	// /**
	// * i 图片编号 compressAfterPath压缩后的图片地址
	// */
	// int i;
	// String[] compressAfterPath;
	//
	// class CompressTask extends AsyncTask<String, Integer, String[]> {
	//
	// @Override
	// protected String[] doInBackground(String... path) {
	// ShowLog("222");
	// i = 0;
	// compressAfterPath = new String[path.length];
	//
	// ShowLog(path.length + "path.length");
	// BmobProFile bmobProFile = BmobProFile
	// .getInstance(MyServiceActivity.this);
	//
	// for (String str : path) {
	//
	// bmobProFile.getLocalThumbnail(str, 2,
	// new LocalThumbnailListener() {
	//
	// @Override
	// public void onError(int arg0, String arg1) {
	// ShowLog("444");
	// Log.i("TAG", arg0 + " " + arg1);
	//
	// }
	//
	// @Override
	// public void onSuccess(String arg0) {
	// ShowLog("333");
	// Log.i("TAG", arg0);
	// publishProgress(i);
	// compressAfterPath[i++] = arg0;
	//
	// }
	// });
	// }
	//
	// try {
	// while (i <= path.length) {
	// Thread.sleep(1000);
	// }
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// }
	//
	// return null;
	// }
	//
	// @Override
	// protected void onPostExecute(String[] result) {
	// ShowLog("555");
	// upLoad(compressAfterPath);
	//
	// super.onPostExecute(result);
	// }
	//
	// @Override
	// protected void onProgressUpdate(Integer... values) {
	// ShowLog(values[0] + "个");
	//
	// super.onProgressUpdate(values);
	// }
	//
	// }

	List<String> compressAfter = new ArrayList<String>();

	protected void compressPic(String[] path) {
		final int size = path.length;
		ShowLog("压缩中1");
		for (int i = 0; i < size; i++) {
			ShowLog("压缩中12");
			final int j = i;
			// Message msg = mHandler.obtainMessage();
			// msg.obj = path[i];
			// msg.arg1 = i;
			// msg.what = MSG_WHAT_COMPRESS;
			// if (i == path.length - 1) {
			// msg.what = MSG_WHAT_COMPRESS;
			// }
			// mHandler.sendMessage(msg);

			mFile.getLocalThumbnail(path[i], 2, new LocalThumbnailListener() {

				@Override
				public void onError(int arg0, String arg1) {
					ShowLog("压缩错误了  "+arg0+"    "+arg1);
					flag = 1;

				}

				@Override
				public void onSuccess(String path) {
					compressAfter.add(path);
					ShowLog("压缩中2：：" + j);
					if (j == size - 1) {
						mHandler.sendEmptyMessage(MSG_WHAT_SUCESS);
 
					} else {
						mHandler.sendEmptyMessage(MSG_WHAT_COMPRESS);
					}

					// ShowLog("onSuccess:" + path);

				}
			});
			ShowLog("压缩中13");
			while (flag == 0) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			flag = 0;
			ShowLog("压缩中14");

		}

	}

	public final static int MSG_WHAT_COMPRESS = 1;
	public final static int MSG_WHAT_SUCESS = 2;

	Integer flag = 0;

	private BmobProFile mFile;

	private Handler mHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {

			if (msg.what == MSG_WHAT_COMPRESS) {
				flag = 1;
			} else if (msg.what == MSG_WHAT_SUCESS) {
				ShowLog("压缩中完成");
				flag = 1;
				String[] arr = new String[compressAfter.size()];
				for (int i = 0; i < arr.length; i++) {
					arr[i] = compressAfter.get(i);
				}

				upLoad(arr);
			}
		};
	};

}
