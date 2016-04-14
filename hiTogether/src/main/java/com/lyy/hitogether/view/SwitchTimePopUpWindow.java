package com.lyy.hitogether.view;

import java.util.Calendar;

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

import com.lyy.hitogether.R;
import com.lyy.hitogether.adapter.ArrayWheelAdapter;
import com.lyy.hitogether.view.WheelView.OnWheelChangedListener;

/**
 * PopUpWindow选择时间的滚轮选择器
 * 
 * @author Administrator
 * 
 */
public class SwitchTimePopUpWindow extends BasePopUpWindow {

	private Context context;
	// 确认选择的按钮
	private ImageView yesImg;
	// 滚轮的年份
	private WheelView yearWV = null;
	// 滚轮的月份
	private WheelView monthWV = null;
	// 滚轮的天数
	private WheelView dayWV = null;

	String[] yearArrayString = null;
	String[] dayArrayString = null;
	String[] monthArrayString = null;
	// 当前选择的年份
	private String currentYear;
	// 当前选择的月份
	private String currentMonth;
	// 当前选择的日期
	private String currentDay;
	Calendar c = null;
	// 当期系统的年份
	private int nowTimeYear;
	// 当前系统的月份
	private int nowTimeMonth;
	private onCorrectClickListener2 mListener;

	// 创建确认选择的回调接口
	public interface onCorrectClickListener2 {
		void onCorrectClick2(View v, String Year, String Month, String Day);
	}

	public void setOnCorrectClickListener2(onCorrectClickListener2 listener) {
		this.mListener = listener;
	}

	public SwitchTimePopUpWindow(Context context) {
		this.context = context;
		// 获取当前系统时间
		c = Calendar.getInstance();
		nowTimeYear = c.get(Calendar.YEAR);
		nowTimeMonth = c.get(Calendar.MONTH);
		currentYear = c.get(Calendar.YEAR) + "";
		currentMonth = c.get(Calendar.MONTH) + "";

		yearArrayString = getYEARArray(c.get(Calendar.YEAR), 10);
		monthArrayString = getMonthArray(c.get(Calendar.MONTH),
				12 - c.get(Calendar.MONTH));
		setLayout(context, R.layout.pop_time_window, 0.3f,0,0);
	}

	@Override
	public void initId() {
		yesImg = (ImageView) mConvertView.findViewById(R.id.id_time_yes);
		yearWV = (WheelView) mConvertView.findViewById(R.id.id_time_year);
		monthWV = (WheelView) mConvertView.findViewById(R.id.id_time_month);
		dayWV = (WheelView) mConvertView.findViewById(R.id.id_time_day);

		// 设置每个滚轮的行数
		yearWV.setVisibleItems(5);
		monthWV.setVisibleItems(5);
		dayWV.setVisibleItems(5);
		// 设置滚轮的标签
		yearWV.setLabel("年");
		monthWV.setLabel("月");
		dayWV.setLabel("日");

		yearWV.setCyclic(false);
		monthWV.setCyclic(false);
		dayWV.setCyclic(false);
		/**
		 * 设置年，月，日数据
		 */
		setData();

	}

	@Override
	public void initEvent() {
		yesImg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				mListener.onCorrectClick2(v, currentYear, currentMonth,
						currentDay);
				c = Calendar.getInstance();
				setOriTime();
				dismiss();
			}
		});

	}

	// int year, month;
	private int currentMonthValue = 0;
	int year, month;

	/**
	 * 设置年，月，日数据
	 */
	private void setData() {
		// 给滚轮提供数据
		yearWV.setAdapter(new ArrayWheelAdapter<String>(yearArrayString));
		monthWV.setAdapter(new ArrayWheelAdapter<String>(monthArrayString));

		yearWV.addChangingListener(new OnWheelChangedListener() {
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {

				currentYear = yearArrayString[yearWV.getCurrentItem()];

				monthWV.setCurrentItem(0);
				dayWV.setCurrentItem(0);
				if (newValue != 0) {
					monthArrayString = getMonthArray(12);
					currentMonth = monthArrayString[monthWV.getCurrentItem()];
					dayArrayString = getDayArray(getDay(
							Integer.parseInt(currentYear),
							Integer.parseInt(currentMonth)));

				} else {
					monthArrayString = getMonthArray(c.get(Calendar.MONTH),
							12 - c.get(Calendar.MONTH));
					currentMonth = monthArrayString[monthWV.getCurrentItem()];
					dayArrayString = getDayArray(
							c.get(Calendar.DAY_OF_MONTH),
							getDay(Integer.parseInt(currentYear),
									Integer.parseInt(currentMonth))
									- c.get(Calendar.DAY_OF_MONTH));
				}
				monthWV.setAdapter(new ArrayWheelAdapter<String>(
						monthArrayString));
				// 给天数的滚轮设置数据
				dayWV.setAdapter(new ArrayWheelAdapter<String>(dayArrayString));
				// 防止数组越界
				if (dayWV.getCurrentItem() >= dayArrayString.length) {
					dayWV.setCurrentItem(dayArrayString.length - 1);
				}
				// 显示的时间
				showDate();
			}
		});

		// 当月变化时显示的时间
		monthWV.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				// TODO Auto-generated method stub
				// 获取年和月
				// year =
				// Integer.parseInt(yearArrayString[yearWV.getCurrentItem()]);
				currentMonth = monthArrayString[monthWV.getCurrentItem()];
				// 根据年和月生成天数数组
				dayArrayString = getDayArray(getDay(
						Integer.parseInt(currentYear),
						Integer.parseInt(currentMonth)));
				// 给天数的滚轮设置数据
				dayWV.setAdapter(new ArrayWheelAdapter<String>(dayArrayString));
				// 防止数组越界
				if (dayWV.getCurrentItem() >= dayArrayString.length) {
					dayWV.setCurrentItem(dayArrayString.length - 1);
				}
				// 显示的时间
				showDate();
			}
		});

		// 当天变化时，显示的时间
		dayWV.addChangingListener(new OnWheelChangedListener() {
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				showDate();
			}
		});
		setOriTime();

	}

	/**
	 * 设置当前系统的时间
	 */
	void setOriTime() {
		yearWV.setCurrentItem(getNumData(c.get(Calendar.YEAR) + "",
				yearArrayString));
		monthWV.setCurrentItem(getNumData(c.get(Calendar.MONTH) + 1 + "",
				monthArrayString) + 0);
		year = Integer.parseInt(yearArrayString[yearWV.getCurrentItem()]);
		month = Integer.parseInt(monthArrayString[monthWV.getCurrentItem()]);
		dayArrayString = getDayArray(c.get(Calendar.DAY_OF_MONTH),
				getDay(year, month) - c.get(Calendar.DAY_OF_MONTH));
		dayWV.setAdapter(new ArrayWheelAdapter<String>(dayArrayString));
		dayWV.setCurrentItem(getNumData(c.get(Calendar.DAY_OF_MONTH) + "",
				dayArrayString));

		// 初始化显示的时间
		showDate();
	}

	// 在数组Array[]中找出字符串s的位置=
	public int getNumData(String s, String[] Array) {
		int num = 0;
		for (int i = 0; i < Array.length; i++) {
			if (s.equals(Array[i])) {
				num = i;
				break;
			}
		}
		return num;
	}

	// 根据初始值start和step得到一个字符数组，自start起至start+step-1
	public String[] getYEARArray(int start, int step) {
		String[] yearArr = new String[step];
		for (int i = 0; i < step; i++) {
			yearArr[i] = start + i + "";
		}
		return yearArr;
	}

	// 根据数字生成一个字符串数组
	public String[] getDayArray(int day, int step) {
		String[] dayArr = new String[step + 1];
		for (int i = 0; i < step + 1; i++) {
			dayArr[i] = (day + i) + "";
		}
		return dayArr;
	}

	// 根据数字生成一个字符串数组
	public String[] getDayArray(int day) {
		String[] dayArr = new String[day];
		for (int i = 0; i < day; i++) {
			dayArr[i] = i + 1 + "";
		}
		return dayArr;
	}

	// 显示时间
	private void showDate() {
		createDate(yearArrayString[yearWV.getCurrentItem()],
				monthArrayString[monthWV.getCurrentItem()],
				dayArrayString[dayWV.getCurrentItem()]);

	}

	// 生成时间
	private void createDate(String year, String month, String day) {
		currentYear = year;
		currentMonth = month;
		currentDay = day;
	}

	// 根据当前年份和月份判断这个月的天数
	public int getDay(int year, int month) {
		int day;
		if (year % 4 == 0 && year % 100 != 0) { // 闰年
			if (month == 1 || month == 3 || month == 5 || month == 7
					|| month == 8 || month == 10 || month == 12) {
				day = 31;
			} else if (month == 2) {
				day = 29;
			} else {
				day = 30;
			}
		} else { // 平年
			if (month == 1 || month == 3 || month == 5 || month == 7
					|| month == 8 || month == 10 || month == 12) {
				day = 31;
			} else if (month == 2) {
				day = 28;
			} else {
				day = 30;
			}
		}
		return day;
	}

	// 根据数字生成一个字符串数组
	public String[] getMonthArray(int strMonth, int step) {
		String[] monthArr = new String[step];
		for (int i = 0; i < step; i++) {
			monthArr[i] = strMonth + 1 + i + "";
		}
		// leftMonthLenth = 12 - monthArr.length;
		return monthArr;
	}

	// 根据数字生成一个字符串数组
	public String[] getMonthArray(int strMonth) {
		String[] monthArr = new String[strMonth];
		for (int i = 0; i < strMonth; i++) {
			monthArr[i] = i + 1 + "";
		}
		// leftMonthLenth = 12 - monthArr.length;
		return monthArr;
	}

}
