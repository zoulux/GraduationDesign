package com.lyy.hitogether.view;

import java.util.List;

import com.lyy.hitogether.R;
import com.lyy.hitogether.bean.FolderBean;
import com.lyy.hitogether.util.ImageLoader;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class ListImageDirPopUpWindow extends BasePopUpWindow {

	private ListView mListView;
	private List<FolderBean> mDatas;
	private Context context;

	public interface onDirSelectListener {
		void onSelected(FolderBean folderBean);
	}

	public onDirSelectListener mListener;

	// public void setOnDirSelectedListener(onDirSelectListener
	// dirSelectListener) {
	//
	// }

	public void setOnDirSelectedListener(onDirSelectListener dirSelectListener) {
		mListener = dirSelectListener;

	}

	public ListImageDirPopUpWindow(Context context, final List<FolderBean> mDatas) {
		this.context = context;
		this.mDatas = mDatas;
		setLayout(context, R.layout.popup_main, 0.7f,0,0);
	}
	
	@Override
	public void initId() {
		mListView = (ListView) mConvertView.findViewById(R.id.id_list_dir);
		mListView.setAdapter(new ListDirAdapter(context, mDatas));
	}

	@Override
	public void initEvent() {
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (mListener != null) {
					mListener.onSelected(mDatas.get(position));
				}
			}
		});
		
	}



	private class ListDirAdapter extends ArrayAdapter<FolderBean> {
		private LayoutInflater mInflater;

		public ListDirAdapter(Context context, List<FolderBean> objects) {
			super(context, 0, objects);
			mInflater = LayoutInflater.from(context);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null;
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.item_popup, parent,
						false);
				viewHolder.mImage = (ImageView) convertView
						.findViewById(R.id.id_dir_item_image);
				viewHolder.mDirCount = (TextView) convertView
						.findViewById(R.id.id_dir_item_count);
				viewHolder.mDirName = (TextView) convertView
						.findViewById(R.id.id_dir_item_name);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			FolderBean folderBean = getItem(position);
			// ����
			viewHolder.mImage.setImageResource(R.drawable.pictures_no);
			ImageLoader.getInstance().LoadImage(folderBean.getFirstImagePath(),
					viewHolder.mImage);
			viewHolder.mDirName.setText(folderBean.getName());
			viewHolder.mDirCount.setText(folderBean.getCount() + "");

			return convertView;
		}

		private class ViewHolder {
			ImageView mImage;
			TextView mDirName;
			TextView mDirCount;
		}

	}

	

}
