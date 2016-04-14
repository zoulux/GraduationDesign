package com.lyy.hitogether.view;

import com.lyy.hitogether.R;
import com.lyy.hitogether.adapter.ArrayWheelAdapter;
import com.lyy.hitogether.view.WheelView.OnWheelChangedListener;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

/**
 * PopupWindow地点滚轮选择器
 * 
 * @author Administrator
 * 
 */
public class SwitchDesPopUpWindow extends BasePopUpWindow {

	private Context context;
	// 确认选择的按钮
	private ImageView yesImg;

	String[] shengs = { "安徽", "福建", "广西", "贵州", "海南", "北京" };
	String[] scene01 = { "黄山", "宏村", "天柱山", "姥山岛", "花亭湖", "九华山" };
	String[] scene02 = { "武夷山", "霞浦" };
	String[] scene03 = { "阳塑" };
	String[] scene04 = { "黄果树瀑布" };
	String[] scene05 = { "南山", "亚龙湾" };
	String[] scene06 = { "故宫", "颐和园", "长城" };

	private WheelView shengWV = null;
	private WheelView sceneWV = null;
	private onCorrectClickListener mListener;

	public interface onCorrectClickListener {
		void onCorrectClick(View v, String provinve, String city);
	}

	public void setOnCorrectClickListener(onCorrectClickListener listener) {
		this.mListener = listener;
	}

	public SwitchDesPopUpWindow(Context context) {
		this.context = context;
		setLayout(context, R.layout.pop_des_window, 0.3f,0,0);
	}

	@Override
	public void initId() {
		shengWV = (WheelView) mConvertView.findViewById(R.id.other_place_sheng);
		sceneWV = (WheelView) mConvertView.findViewById(R.id.other_place_shi);
		yesImg = (ImageView) mConvertView.findViewById(R.id.id_yes);

		shengWV.setVisibleItems(5);
		sceneWV.setVisibleItems(5);

		shengWV.setCyclic(false);
		sceneWV.setCyclic(false);
		chosePlce();

	}

	@Override
	public void initEvent() {
		yesImg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mListener != null) {
					mListener.onCorrectClick(v, currentProvence, currentScene);
					SwitchDesPopUpWindow.this.dismiss();
				}

			}
		});

	}

	public String getBigLable() {
		return shengWV.getLabel();
	}

	public String getSmallLable() {
		return sceneWV.getLabel();
	}

	private String currentProvence;
	private String currentScene;

	private void chosePlce() {
		shengWV.setAdapter(new ArrayWheelAdapter<String>(shengs));
		sceneWV.setAdapter(new ArrayWheelAdapter<String>(scene01));
		shengWV.setLabel("省");
		sceneWV.setLabel("景区");
		currentProvence = shengs[shengWV.getCurrentItem()];
		currentScene = scene01[sceneWV.getCurrentItem()];

		shengWV.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				// TODO Auto-generated method stub
				sceneWV.setCurrentItem(0);
				String sheng = shengs[newValue];
				String scene = "";
				if (sheng.equals("北京")) {
					shengWV.setLabel("市");
					sceneWV.setLabel("景区");
					sceneWV.setAdapter(new ArrayWheelAdapter<String>(scene06));
					scene = scene06[sceneWV.getCurrentItem()];
				} else {
					shengWV.setLabel("省");
					sceneWV.setLabel("景区");

					if (sheng.equals("安徽")) {
						sceneWV.setAdapter(new ArrayWheelAdapter<String>(
								scene01));
						scene = scene01[sceneWV.getCurrentItem()];
					} else if (sheng.equals("福建")) {

						sceneWV.setAdapter(new ArrayWheelAdapter<String>(
								scene02));
						scene = scene02[sceneWV.getCurrentItem()];
					} else if (sheng.equals("广西")) {
						sceneWV.setAdapter(new ArrayWheelAdapter<String>(
								scene03));
						scene = scene03[sceneWV.getCurrentItem()];
					} else if (sheng.equals("贵州")) {
						sceneWV.setAdapter(new ArrayWheelAdapter<String>(
								scene04));
						scene = scene04[sceneWV.getCurrentItem()];
					} else if (sheng.equals("海南")) {
						sceneWV.setAdapter(new ArrayWheelAdapter<String>(
								scene05));
						scene = scene05[sceneWV.getCurrentItem()];
					}

				}
				currentProvence = shengs[shengWV.getCurrentItem()];
				currentScene = scene;
			}
		});

		sceneWV.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				// TODO Auto-generated method stub
				String sheng = shengs[shengWV.getCurrentItem()];
				String scene = "";
				if (sheng.equals("北京")) {
					scene = scene06[sceneWV.getCurrentItem()];
				} else if (sheng.equals("安徽")) {
					scene = scene01[sceneWV.getCurrentItem()];
				} else if (sheng.equals("福建")) {
					scene = scene02[sceneWV.getCurrentItem()];
				} else if (sheng.equals("广西")) {
					scene = scene03[sceneWV.getCurrentItem()];
				} else if (sheng.equals("贵州")) {
					scene = scene04[sceneWV.getCurrentItem()];
				} else if (sheng.equals("海南")) {
					scene = scene05[sceneWV.getCurrentItem()];
				}
				currentScene = scene;
			}
		});
	}

}
