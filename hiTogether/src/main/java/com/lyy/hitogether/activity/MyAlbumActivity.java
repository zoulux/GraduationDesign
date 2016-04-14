package com.lyy.hitogether.activity;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lyy.hitogether.R;
import com.lyy.hitogether.adapter.ImageAdapter;
import com.lyy.hitogether.adapter.ImageAdapter.onAdapterItemClickListener;
import com.lyy.hitogether.bean.FolderBean;
import com.lyy.hitogether.util.ImageLoader;
import com.lyy.hitogether.util.SPUtils;
import com.lyy.hitogether.util.ImageLoader.Type;
import com.lyy.hitogether.view.ListImageDirPopUpWindow;
import com.lyy.hitogether.view.ListImageDirPopUpWindow.onDirSelectListener;

public class MyAlbumActivity extends BaseActivity {
	private GridView mGridView;
	// ����ͼƬ���ڵ�·��
	private List<String> mImgs;
	// ��������ͼƬ��������
	private ImageAdapter mImageAdapter;
	// �������ʾ�Ĳ���
	private RelativeLayout mBottomLy;
	// Ŀ¼�����
	private TextView mDirName;
	// Ŀ¼����ͼƬ������
	private TextView mDirCount;
	// ��ǰĿ¼���ڵ��ļ���
	private File mCurrentDir;
	// ͼƬ���������
	private int mMaxCount;

	private ProgressDialog mProgressDialog;

	private static final int DATA_LOADED = 0X110;
	// �ӵ��µ����Ĳ˵�PopUpWindow
	private ListImageDirPopUpWindow mDirPopUpWindow;
	// ȷ��ѡ��İ�ť
	private Button titleBtSend;

	private Handler mHandle = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == DATA_LOADED) {
				mProgressDialog.dismiss();
				data2View();
				mImageAdapter
						.setAdapterItemClickListener(new onAdapterItemClickListener() {

							@Override
							public void onAdapterItemClick(View v, int count) {
								titleBtSend.setText(count + "/9");
							}
						});

				initDirPopUpWindow();
			}
		};
	};

	private List<FolderBean> mFolderBeans = new ArrayList<FolderBean>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_album);
		initView();
		initDatas();
		initEvent();
	}

	protected void initDirPopUpWindow() {
		mDirPopUpWindow = new ListImageDirPopUpWindow(this, mFolderBeans);
		mDirPopUpWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				mDirPopUpWindow.lightOn(MyAlbumActivity.this);
			}
		});

		mDirPopUpWindow.setOnDirSelectedListener(new onDirSelectListener() {

			@Override
			public void onSelected(FolderBean folderBean) {
				mCurrentDir = new File(folderBean.getDir());

				mImgs = Arrays.asList(mCurrentDir.list(new FilenameFilter() {

					@Override
					public boolean accept(File dir, String filename) {
						if (filename.endsWith(".jpg")
								|| filename.endsWith(".jpeg")
								|| filename.endsWith(".png"))
							return true;
						return false;
					}

				}));

				mImageAdapter = new ImageAdapter(MyAlbumActivity.this,
						mCurrentDir.getAbsolutePath(), mImgs);
				System.out.println(mCurrentDir.getAbsolutePath()+">>>>>>>>>>>>");

				mGridView.setAdapter(mImageAdapter);
				mDirCount.setText(mImgs.size() + "");
				mDirName.setText(folderBean.getName());
				mDirPopUpWindow.dismiss();
			}
		});
	}

	/**
	 * ����ͼƬ
	 */
	protected void data2View() {
		if (mCurrentDir == null) {
			Toast.makeText(this, "δɨ�赽�κ�ͼƬ", 1).show();
			return;
		}
		mImgs = Arrays.asList(mCurrentDir.list());
		mImageAdapter = new ImageAdapter(this, mCurrentDir.getAbsolutePath(),
				mImgs);
		mGridView.setAdapter(mImageAdapter);
		mDirCount.setText(mMaxCount + "");
		mDirName.setText(mCurrentDir.getName());

	}

	// private int selectedPicCount = 0;
	private void initEvent() {
		mBottomLy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mDirPopUpWindow.showAsDropDown(mBottomLy, 0, 0);
				mDirPopUpWindow.lightOff(MyAlbumActivity.this);
			}
		});

		titleBtSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// selectedPicCount = mImageAdapter.getSelectedImgSize();

				
				
				startIntent((ArrayList<String>) mImageAdapter.getSelectedImgPath());
			}
		});

	}

	protected void startIntent(ArrayList<String> path) {
		Intent intent = new Intent(MyAlbumActivity.this,
				ShareMyTravalActivity.class);
		intent.putStringArrayListExtra("path",
				path);
		MyAlbumActivity.this.setResult(RESULT_OK, intent);
		MyAlbumActivity.this.finish();
		mImageAdapter.end(MyAlbumActivity.this);
		
	}

	

	/**
	 * ����contentProviderɨ���ֻ��е�ͼƬ
	 */
	private void initDatas() {
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			Toast.makeText(this, "sd卡不可用", 1).show();
			return;
		}
		mProgressDialog = ProgressDialog.show(this, null, "加载中...");
		new Thread() {
			public void run() {
				Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				ContentResolver cr = MyAlbumActivity.this.getContentResolver();
				Cursor cursor = cr.query(mImageUri, null,
						MediaStore.Images.Media.MIME_TYPE + " =?or "
								+ MediaStore.Images.Media.MIME_TYPE + " =? ",
						new String[] { "image/jpeg", "image/png" },
						MediaStore.Images.Media.DATE_MODIFIED);

				Set<String> mDirPath = new HashSet<String>();

				while (cursor.moveToNext()) {
					String path = cursor.getString(cursor
							.getColumnIndex(MediaStore.Images.Media.DATA));
					File parentFile = new File(path).getParentFile();
					if (parentFile == null) {
						continue;
					}
					String dirPath = parentFile.getAbsolutePath();
					FolderBean folderBean = null;
					if (mDirPath.contains(dirPath)) {
						continue;
					} else {
						mDirPath.add(dirPath);
						folderBean = new FolderBean();
						folderBean.setDir(dirPath);
						folderBean.setFirstImagePath(path);

					}
					if (parentFile.list() == null) {
						continue;
					}
					int picSize = parentFile.list(new FilenameFilter() {

						@Override
						public boolean accept(File dir, String filename) {
							if (filename.endsWith(".jpg")
									|| filename.endsWith(".jpeg")
									|| filename.endsWith(".png")) {
								return true;
							}
							return false;
						}
					}).length;

					folderBean.setCount(picSize);
					mFolderBeans.add(folderBean);

					if (picSize > mMaxCount) {
						mMaxCount = picSize;
						mCurrentDir = parentFile;
					}

				}
				cursor.close();
				// ֪ͨhandlerɨ��ͼ�����
				mHandle.sendEmptyMessage(DATA_LOADED);
			};
		}.start();

	}

	private void initView() {
		mGridView = (GridView) findViewById(R.id.id_grideView);
		mBottomLy = (RelativeLayout) findViewById(R.id.id_bottom_ly);
		mDirName = (TextView) findViewById(R.id.id_dir_name);
		mDirCount = (TextView) findViewById(R.id.id_dir_count);
		titleBtSend = (Button) findViewById(R.id.id_title_bar_bt);

	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			startIntent(new ArrayList<String>());
		}
		return true;
	}
	
	

}
