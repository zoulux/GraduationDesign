package com.lyy.hitogether.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lyy.hitogether.R;
import com.lyy.hitogether.util.DensityUtils;



public class CustomTitleBarView extends RelativeLayout {
	// 图片View
	private ImageView leftImage;
	private ImageView rightImage;
	private ImageView centerImage;
	// 文字View
	private TextView leftTextView;
	private TextView rightTextView;
	private TextView centerTextView;
	// 按钮View
	private Button leftButton;
	private Button rightButton;
	private Button centerButton;

	private static final int LEFT_TYPE_TEXT = 0;

	private static final int LEFT_TYPE_IMAGE = 1;

	private static final int LEFT_TYPE_BUTTON = 2;

	private static final int RIGHT_TYPE_TEXT = 0;

	private static final int RIGHT_TYPE_IMAGE = 1;

	private static final int RIGHT_TYPE_BUTTON = 2;

	private static final int CENTER_TYPE_TEXT = 0;

	private static final int CENTER_TYPE_IMAGE = 1;

	private static final int CENTER_TYPE_BUTTON = 2;
	// 左边的文字的属性
	private String leftText;
	private int leftTextColor;
	private float leftTextSize;
	private float leftTextPadding;
	private float leftTextPaddingLeft;
	private float leftTextPaddingRight;
	private float leftTextPaddingTop;
	private float leftTextPaddingBottom;
	// 左边的图片的属性
	private int leftImageSource;
	private float leftImagePadding;
	private float leftImagePaddingLeft;
	private float leftImagePaddingRight;
	private float leftImagePaddingTop;
	private float leftImagePaddingBottom;
	// 左边的系统按钮的属性
	private int leftButtonBg;
	private String leftButtonText;
	private float leftButtonTextSize;
	private int leftButtonTextColor;
	private float leftButtonPadding;
	private float leftButtonPaddingLeft;
	private float leftButtonPaddingRight;
	private float leftButtonPaddingTop;
	private float leftButtonPaddingBottom;
	private float leftButtonMargIn;
	private float leftButtonMargInLeft;
	private float leftButtonMargInRight;
	private float leftButtonMargInTop;
	private float leftButtonMargInBottom;
	// 右边的文字的属性
	private String rightText;
	private int rightTextColor;
	private float rightTextSize;
	private float rightTextPadding;
	private float rightTextPaddingLeft;
	private float rightTextPaddingRight;
	private float rightTextPaddingTop;
	private float rightTextPaddingBottom;
	// 右边的图片的属性
	private int rightImageSource;
	private float rightImagePadding;
	private float rightImagePaddingLeft;
	private float rightImagePaddingRight;
	private float rightImagePaddingTop;
	private float rightImagePaddingBottom;

	// 右边的系统按钮的属性
	private int rightButtonBg;
	private String rightButtonText;
	private float rightButtonTextSize;
	private int rightButtonTextColor;
	private float rightButtonPadding;
	private float rightButtonPaddingLeft;
	private float rightButtonPaddingRight;
	private float rightButtonPaddingTop;
	private float rightButtonPaddingBottom;
	private float rightButtonMargIn;
	private float rightButtonMargInLeft;
	private float rightButtonMargInRight;
	private float rightButtonMargInTop;
	private float rightButtonMargInBottom;

	// 中间的文字的属性
	private String centerText;
	private int centerTextColor;
	private float centerTextSize;
	private float centerTextPadding;
	private float centerTextPaddingLeft;
	private float centerTextPaddingRight;
	private float centerTextPaddingTop;
	private float centerTextPaddingBottom;

	private int leftType;
	private int rightType;
	private int centerType;
	private View leftView;
	private View rightView;
	private View centerView;

	private onLeftBarViewClickListener mLeftBarViewClickListener = null;
	private onRightBarViewClickListener mRightBarViewClickListener = null;

	private Context context;

	public interface onLeftBarViewClickListener {
		void onclick(View v);
	}

	public void setOnLeftBarViewClickListener(
			onLeftBarViewClickListener leftBarViewClickListener) {
		mLeftBarViewClickListener = leftBarViewClickListener;
	}

	public onLeftBarViewClickListener getLeftBarViewClickListener() {
		return mLeftBarViewClickListener;
	}

	public interface onRightBarViewClickListener {
		void onclick(View v);
	}

	public void setOnRightBarViewClickListener(
			onRightBarViewClickListener rightBarViewClickListener) {
		mRightBarViewClickListener = rightBarViewClickListener;
	}

	public onRightBarViewClickListener getRightBarViewClickListener() {
		return mRightBarViewClickListener;
	}

	public CustomTitleBarView(Context context) {
		this(context, null);
		this.context = context;
		// TODO Auto-generated constructor stub
	}

	public CustomTitleBarView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		this.context = context;
		// TODO Auto-generated constructor stub
	}

	private RelativeLayout.LayoutParams lp1;
	private RelativeLayout.LayoutParams lp2;
	private RelativeLayout.LayoutParams lp3;
	private RelativeLayout.LayoutParams lp4;
	private RelativeLayout.LayoutParams lp5;
	private RelativeLayout.LayoutParams lp6;

	private void initXmlLeftLayoutParams() {
		lp1 = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		lp1.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
	}

	private void initXmlRightLayoutParams() {
		lp2 = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		lp2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
	}

	private void initXmlCenterLayoutParams() {
		lp3 = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		lp3.addRule(RelativeLayout.CENTER_IN_PARENT);
	}

	private void initJavaCenterLayoutParams() {
		lp4 = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		lp4.addRule(RelativeLayout.CENTER_IN_PARENT);
	}

	private void initJavaLeftLayoutParams() {
		lp5 = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		lp5.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
	}
	
	private void initJavaRightLayoutParams() {
		lp6 = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		lp6.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
	}

	public CustomTitleBarView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		leftView = null;
		rightView = null;
		centerView = null;

		TypedArray ta = context.obtainStyledAttributes(attrs,
				R.styleable.barView, defStyle, 0);
		leftType = ta.getInt(R.styleable.barView_leftType, -1);
		rightType = ta.getInt(R.styleable.barView_rightType, -1);
		centerType = ta.getInt(R.styleable.barView_centerType, -1);

		initXmlLeftLayoutParams();

		initXmlRightLayoutParams();

		initXmlCenterLayoutParams();
		
			switch (leftType) {

			case LEFT_TYPE_IMAGE:
				leftImage = new ImageView(context);
				leftView = leftImage;
				if (ta.getResourceId(R.styleable.barView_leftImageSource,-1)!=-1) {
					initLeftImage(ta, context);
					addView(leftView, lp1);
					setLeftView(leftView);	
				}
				
				break;
			case LEFT_TYPE_TEXT:
				leftTextView = new TextView(context);
				leftView = leftTextView;
				if (ta.getString(R.styleable.barView_leftText) != null) {
					initLeftText(ta, context);
					addView(leftView, lp1);
					setLeftView(leftView);
				}
				break;
			case LEFT_TYPE_BUTTON:
				leftButton = new Button(context);
				leftView = leftButton;
				initLeftButton(ta, context);

				addView(leftView, lp1);
				setLeftView(leftView);
				break;
			default:
				break;

			}
		

		switch (rightType) {

		case RIGHT_TYPE_IMAGE:
			rightImage = new ImageView(context);
			rightView = rightImage;
			if (ta.getResourceId(R.styleable.barView_rightImageSource,-1)!=-1) {
				initRightImage(ta, context);
				addView(rightView, lp2);
				setLeftView(rightView);	
			}
			break;
		case RIGHT_TYPE_TEXT:
			rightTextView = new TextView(context);
			rightView = rightTextView;
			
			if (ta.getString(R.styleable.barView_rightText) != null) {
				initRightText(ta, context);
				addView(rightView, lp2);
				setRightView(rightView);
			}
			
			break;
		case RIGHT_TYPE_BUTTON:
			rightButton = new Button(context);
			rightView = rightButton;
			initRightButton(ta, context);
			addView(rightView, lp2);
			setRightView(rightView);
			break;
		default:
			break;
		}

		switch (centerType) {

		case CENTER_TYPE_IMAGE:
			// Todo
			centerImage = new ImageView(context);
			centerView = centerImage;
			addView(centerView, lp3);
			break;
		case CENTER_TYPE_TEXT:
			centerTextView = new TextView(context);
			centerView = centerTextView;
			if (ta.getString(R.styleable.barView_centerText) == null) {
				System.out.println("null>>>>>");
			} else {
				initCenterText(ta, context);
			}

			addView(centerView, lp3);
			setCenterView(centerView);

			break;
		case CENTER_TYPE_BUTTON:
			// Todo
			centerButton = new Button(context);
			centerView = centerButton;
			addView(centerView, lp3);
			break;
		default:
			break;

		}
		initEvent(leftView, rightView);
		ta.recycle();

	}

	/**
	 * java代码控制中间的文字的属性
	 * 
	 * @param text
	 */
	public void setCenterText(String text) {
		initJavaCenterLayoutParams();
		centerTextView = new TextView(context);

		centerTextView.setText(text);
		addView(centerTextView, lp4);

	}

	public void setCenterTextColor(int color) {
		centerTextView.setTextColor(color);
	}

	public void setCenterTextSize(float size) {
		centerTextView.setTextSize(size);
	}
	
	public void setLeftText(String text) {
		initJavaLeftLayoutParams();
		leftTextView = new TextView(context);

		leftTextView.setText(text);
		leftTextView.setGravity(Gravity.CENTER);
		addView(leftTextView, lp5);
		leftView = leftTextView;
		initEvent(leftView, rightView);

	}

	public void setLeftTextColor(int color) {
		leftTextView.setTextColor(color);
	}

	public void setLeftTextSize(float size) {
		leftTextView.setTextSize(size);
	}
	
	public void setLeftTextPadding(int padding){
		leftTextView.setPadding(padding, padding, padding, padding);
	}
	
	public void setLeftTextPaddingLeft(int left){
		leftTextView.setPadding(left, 0, 0, 0);
	}
	public void setLeftTextPaddingRight(int right){
		leftTextView.setPadding(0, 0, right, 0);
	}
	
	public void setLeftTextPaddingTop(int top){
		leftTextView.setPadding(0, top, 0, 0);
	}
	public void setLeftTextPaddingBottom(int bottom){
		leftTextView.setPadding(0, 0, 0, bottom);
	}

	public void setLeftImageSuorce(int source) {
		initJavaLeftLayoutParams();
		leftImage = new ImageView(context);
		leftImage.setImageResource(source);
		addView(leftImage, lp5);
		leftView = leftImage;
		initEvent(leftView, rightView);
		
	}
	
	public void setLeftImagePadding(int padding){
		leftImage.setPadding(padding, padding, padding, padding);
	}
	
	public void setLeftImagePaddingLeft(int left){
		leftImage.setPadding(left, 0, 0, 0);
	}
	public void setLeftImagePaddingRight(int right){
		leftImage.setPadding(0, 0, right, 0);
	}
	
	public void setLeftImagePaddingTop(int top){
		leftImage.setPadding(0, top, 0, 0);
	}
	public void setLeftImagePaddingBottom(int bottom){
		leftImage.setPadding(0, 0, 0, bottom);
	}
	
	public void setRightImageSuorce(int source) {
		initJavaRightLayoutParams();
		rightImage = new ImageView(context);
		rightImage.setImageResource(source);
		addView(rightImage, lp6);
		rightView = rightImage;
		initEvent(leftView, rightView);
		
	}
	
	public void setRightImagePadding(int padding){
		rightImage.setPadding(padding, padding, padding, padding);
	}
	
	public void setRightImagePaddingLeft(int left){
		rightImage.setPadding(left, 0, 0, 0);
	}
	public void setRightImagePaddingRight(int right){
		rightImage.setPadding(0, 0, right, 0);
	}
	
	public void setRightImagePaddingTop(int top){
		rightImage.setPadding(0, top, 0, 0);
	}
	public void setRightImagePaddingBottom(int bottom){
		rightImage.setPadding(0, 0, 0, bottom);
	}
	
	public void setRightText(String text) {
		initJavaRightLayoutParams();
		rightTextView = new TextView(context);

		rightTextView.setText(text);
		rightTextView.setGravity(Gravity.CENTER);
		addView(rightTextView, lp6);
		rightView = rightTextView;
		initEvent(leftView, rightView);

	}

	public void setRightTextColor(int color) {
		rightTextView.setTextColor(color);
	}

	public void setRightTextSize(float size) {
		rightTextView.setTextSize(size);
	}
	
	public void setRightTextPadding(int padding){
		rightTextView.setPadding(padding, padding, padding, padding);
	}
	
	public void setRightTextPaddingLeft(int left){
		rightTextView.setPadding(left, 0, 0, 0);
	}
	public void setRightTextPaddingRight(int right){
		rightTextView.setPadding(0, 0, right, 0);
	}
	
	public void setRightTextPaddingTop(int top){
		rightTextView.setPadding(0, top, 0, 0);
	}
	public void setRightTextPaddingBottom(int bottom){
		rightTextView.setPadding(0, 0, 0, bottom);
	}

	private void initEvent(final View leftView, final View rightView) {
		if (leftView != null) {
			leftView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (mLeftBarViewClickListener != null) {
						mLeftBarViewClickListener.onclick(leftView);
					}

				}
			});
		}

		if (rightView != null) {
			rightView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (mRightBarViewClickListener != null) {
						mRightBarViewClickListener.onclick(rightView);
					}

				}
			});
		}

	}

	/**
	 * 设置中间的控件
	 * 
	 * @param centerView
	 */
	private void setCenterView(View centerView) {
		if (centerView == centerTextView) {
			// System.out.println("leftText " + leftText + "leftTextSize "
			// + leftTextSize + "leftTextColor " + leftTextColor
			// + "leftTextPadding " + leftTextPadding);
			centerTextView.setText(centerText);
			centerTextView.setTextSize(centerTextSize);
			centerTextView.setTextColor(centerTextColor);
			if (centerTextPadding == 0) {
				centerTextView.setPadding((int) centerTextPaddingLeft,
						(int) centerTextPaddingTop,
						(int) centerTextPaddingRight,
						(int) centerTextPaddingBottom);
			} else {
				centerTextView.setPadding((int) centerTextPadding,
						(int) centerTextPadding, (int) centerTextPadding,
						(int) centerTextPadding);
			}
			centerTextView.setGravity(Gravity.CENTER);
		}

	}

	/**
	 * 初始化中间的文字按钮
	 * 
	 * @param ta
	 * @param context
	 */
	private void initCenterText(TypedArray ta, Context context) {
		centerText = ta.getString(R.styleable.barView_centerText);
		if (centerText == null) {
			centerText = "请设置文字";
		}
		centerTextColor = ta.getColor(R.styleable.barView_centerTextColor,
				Color.BLACK);

		centerTextSize = ta.getDimension(R.styleable.barView_centerTextSize,
				14.0f);
		if (centerTextSize != 14.0f) {
			centerTextSize = DensityUtils.px2sp(context, centerTextSize);
		}
		// 默认为0
		centerTextPadding = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_centerTextPadding, 0));
		centerTextPaddingLeft = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_centerTextPaddingLeft, 0));
		// System.out.println(leftTextPaddingLeft+"<><>");
		centerTextPaddingRight = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_centerTextPaddingRight, 0));
		centerTextPaddingTop = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_centerTextPaddingTop, 0));
		centerTextPaddingBottom = DensityUtils
				.px2dp(context, ta.getDimension(
						R.styleable.barView_centerTextPaddingBottom, 0));

	}

	/**
	 * 初始化左边的按钮图片
	 * 
	 * @param ta
	 * @param context
	 */
	private void initLeftImage(TypedArray ta, Context context) {
		// ta.getResources();
		leftImageSource = ta.getResourceId(R.styleable.barView_leftImageSource,
				R.drawable.ic_launcher);
		leftImagePadding = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_leftImagePadding, 0));
		leftImagePaddingLeft = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_leftImagePaddingLeft, 0));
		leftImagePaddingRight = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_leftImagePaddingRight, 0));
		leftImagePaddingTop = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_leftImagePaddingTop, 0));
		leftImagePaddingBottom = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_leftImagePaddingBottom, 0));
	}

	/**
	 * 初始化左边按钮文字
	 * 
	 * @param ta
	 * @param context
	 */
	private void initLeftText(TypedArray ta, Context context) {
		leftText = ta.getString(R.styleable.barView_leftText);
		if (leftText == null) {
			leftText = "请设置文字";
		}
		leftTextColor = ta.getColor(R.styleable.barView_leftTextColor,
				Color.BLACK);

		leftTextSize = ta.getDimension(R.styleable.barView_leftTextSize, 14.0f);
		if (leftTextSize != 14.0f) {
			leftTextSize = DensityUtils.px2sp(context, leftTextSize);
		}
		// 默认为0
		leftTextPadding = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_leftTextPadding, 0));
		leftTextPaddingLeft = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_leftTextPaddingLeft, 0));
		// System.out.println(leftTextPaddingLeft+"<><>");
		leftTextPaddingRight = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_leftTextPaddingRight, 0));
		leftTextPaddingTop = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_leftTextPaddingTop, 0));
		leftTextPaddingBottom = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_leftTextPaddingBottom, 0));

	}

	/**
	 * 初始化左边的系统按钮
	 * 
	 * @param ta
	 * @param context
	 */
	private void initLeftButton(TypedArray ta, Context context) {
		leftButtonBg = ta.getResourceId(
				R.styleable.barView_leftButtonBackground, Color.GRAY);
		leftButtonText = ta.getString(R.styleable.barView_leftButtonText);
		leftButtonTextColor = ta.getColor(
				R.styleable.barView_leftButtonTextColor, Color.WHITE);
		leftButtonTextSize = ta.getDimension(
				R.styleable.barView_leftButtonTextSize, 12.0f);
		if (leftButtonTextSize != 12.0f) {
			leftButtonTextSize = DensityUtils
					.px2sp(context, leftButtonTextSize);
		}

		leftButtonPadding = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_leftButtonPadding, 0));
		leftButtonPaddingLeft = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_leftButtonPaddingLeft, 0));
		leftButtonPaddingRight = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_leftButtonPaddingRight, 0));
		leftButtonPaddingTop = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_leftButtonPaddingTop, 0));
		leftButtonPaddingBottom = DensityUtils
				.px2dp(context, ta.getDimension(
						R.styleable.barView_leftButtonPaddingBottom, 0));

		leftButtonMargIn = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_leftButtonMargIn, 0));
		leftButtonMargInLeft = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_leftButtonMargInLeft, 0));
		leftButtonMargInRight = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_leftButtonMargInRight, 0));
		leftButtonMargInTop = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_leftButtonMargInTop, 0));
		leftButtonMargInBottom = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_leftButtonMargInBottom, 0));
	}

	/**
	 * 设置左边的自定义控件
	 * 
	 * @param leftView
	 */
	private void setLeftView(View leftView) {
		if (leftView == leftTextView) {
			// System.out.println("leftText " + leftText + "leftTextSize "
			// + leftTextSize + "leftTextColor " + leftTextColor
			// + "leftTextPadding " + leftTextPadding);
			leftTextView.setText(leftText);
			leftTextView.setTextSize(leftTextSize);
			leftTextView.setTextColor(leftTextColor);
			if (leftTextPadding == 0) {
				leftTextView.setPadding((int) leftTextPaddingLeft,
						(int) leftTextPaddingTop, (int) leftTextPaddingRight,
						(int) leftTextPaddingBottom);
			} else {
				leftTextView.setPadding((int) leftTextPadding,
						(int) leftTextPadding, (int) leftTextPadding,
						(int) leftTextPadding);
			}
			leftTextView.setGravity(Gravity.CENTER);
		} else if (leftView == leftImage) {
			leftImage.setImageResource(leftImageSource);
			if (leftImagePadding == 0) {

				leftImage.setPadding((int) leftImagePaddingLeft,
						(int) leftImagePaddingTop, (int) leftImagePaddingRight,
						(int) leftImagePaddingBottom);
			} else {
				leftImage.setPadding((int) leftImagePadding,
						(int) leftImagePadding, (int) leftImagePadding,
						(int) leftImagePadding);
			}

		} else if (leftView == leftButton) {
			leftButton.setText(leftButtonText);
			leftButton.setTextSize(leftButtonTextSize);
			leftButton.setTextColor(leftButtonTextColor);
			leftButton.setBackgroundResource(leftButtonBg);

			if (leftButtonPadding == 0) {

				leftButton.setPadding((int) leftButtonPaddingLeft,
						(int) leftButtonPaddingTop,
						(int) leftButtonPaddingRight,
						(int) leftButtonPaddingBottom);
			} else {
				leftButton.setPadding((int) leftButtonPadding,
						(int) leftButtonPadding, (int) leftButtonPadding,
						(int) leftButtonPadding);
			}
			RelativeLayout.LayoutParams layoutParams = (LayoutParams) leftButton
					.getLayoutParams();
			if (leftButtonMargIn == 0) {

				layoutParams.setMargins((int) leftButtonMargInLeft,
						(int) leftButtonMargInTop, (int) leftButtonMargInRight,
						(int) leftButtonMargInBottom);

			} else {
				layoutParams.setMargins((int) leftButtonMargIn,
						(int) leftButtonMargIn, (int) leftButtonMargIn,
						(int) leftButtonMargIn);
			}
			leftButton.setLayoutParams(layoutParams);

		}

	}

	/**
	 * 设置右边的自定义控件
	 * 
	 * @param rightView
	 */
	private void setRightView(View rightView) {
		if (rightView == rightTextView) {
			// System.out.println("leftText " + leftText + "leftTextSize "
			// + leftTextSize + "leftTextColor " + leftTextColor
			// + "leftTextPadding " + leftTextPadding);
			rightTextView.setText(rightText);
			rightTextView.setTextSize(rightTextSize);
			rightTextView.setTextColor(rightTextColor);
			if (rightTextPadding == 0) {
				rightTextView.setPadding((int) rightTextPaddingLeft,
						(int) rightTextPaddingTop, (int) rightTextPaddingRight,
						(int) rightTextPaddingBottom);
			} else {
				rightTextView.setPadding((int) rightTextPadding,
						(int) rightTextPadding, (int) rightTextPadding,
						(int) rightTextPadding);
			}
			rightTextView.setGravity(Gravity.CENTER);
		} else if (rightView == rightImage) {
			rightImage.setImageResource(rightImageSource);
			if (rightImagePadding == 0) {

				rightImage.setPadding((int) rightImagePaddingLeft,
						(int) rightImagePaddingTop,
						(int) rightImagePaddingRight,
						(int) rightImagePaddingBottom);
			} else {
				rightImage.setPadding((int) rightImagePadding,
						(int) rightImagePadding, (int) rightImagePadding,
						(int) rightImagePadding);
			}
		} else if (rightView == rightButton) {
			rightButton.setText(rightButtonText);
			rightButton.setTextSize(rightButtonTextSize);
			rightButton.setTextColor(rightButtonTextColor);
			rightButton.setBackgroundResource(rightButtonBg);

			if (rightButtonPadding == 0) {

				rightButton.setPadding((int) rightButtonPaddingLeft,
						(int) rightButtonPaddingTop,
						(int) rightButtonPaddingRight,
						(int) rightButtonPaddingBottom);
			} else {
				rightButton.setPadding((int) rightButtonPadding,
						(int) rightButtonPadding, (int) rightButtonPadding,
						(int) rightButtonPadding);
			}
			RelativeLayout.LayoutParams layoutParams = (LayoutParams) rightButton
					.getLayoutParams();
			if (rightButtonMargIn == 0) {

				layoutParams.setMargins((int) rightButtonMargInLeft,
						(int) rightButtonMargInTop,
						(int) rightButtonMargInRight,
						(int) rightButtonMargInBottom);

			} else {
				layoutParams.setMargins((int) rightButtonMargIn,
						(int) rightButtonMargIn, (int) rightButtonMargIn,
						(int) rightButtonMargIn);
			}
			rightButton.setLayoutParams(layoutParams);

		}
	}

	/**
	 * 初始化右边的文字按钮
	 * 
	 * @param ta
	 * @param context
	 */
	private void initRightText(TypedArray ta, Context context) {
		rightText = ta.getString(R.styleable.barView_rightText);
		if (rightText == null) {
			rightText = "请设置文字";
		}
		rightTextColor = ta.getColor(R.styleable.barView_rightTextColor,
				Color.BLACK);

		rightTextSize = ta.getDimension(R.styleable.barView_rightTextSize,
				14.0f);
		if (rightTextSize != 14.0f) {
			rightTextSize = DensityUtils.px2sp(context, rightTextSize);
		}
		// 默认为0
		rightTextPadding = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_rightTextPadding, 0));
		rightTextPaddingLeft = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_rightTextPaddingLeft, 0));
		// System.out.println(leftTextPaddingLeft+"<><>");
		rightTextPaddingRight = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_rightTextPaddingRight, 0));
		rightTextPaddingTop = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_rightTextPaddingTop, 0));
		rightTextPaddingBottom = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_rightTextPaddingBottom, 0));

	}

	/**
	 * 初始化右边图片按钮
	 * 
	 * @param ta
	 * @param context
	 */
	private void initRightImage(TypedArray ta, Context context) {
		rightImageSource = ta.getResourceId(
				R.styleable.barView_rightImageSource, R.drawable.ic_launcher);
		rightImagePadding = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_rightImagePadding, 0));
		rightImagePaddingLeft = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_rightImagePaddingLeft, 0));
		rightImagePaddingRight = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_rightImagePaddingRight, 0));
		rightImagePaddingTop = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_rightImagePaddingTop, 0));
		rightImagePaddingBottom = DensityUtils
				.px2dp(context, ta.getDimension(
						R.styleable.barView_rightImagePaddingBottom, 0));

	}

	private void initRightButton(TypedArray ta, Context context) {
		rightButtonBg = ta.getResourceId(
				R.styleable.barView_rightButtonBackground, Color.GRAY);
		rightButtonText = ta.getString(R.styleable.barView_rightButtonText);
		rightButtonTextColor = ta.getColor(
				R.styleable.barView_rightButtonTextColor, Color.WHITE);
		rightButtonTextSize = ta.getDimension(
				R.styleable.barView_rightButtonTextSize, 12.0f);
		if (rightButtonTextSize != 12.0f) {
			rightButtonTextSize = DensityUtils.px2sp(context,
					rightButtonTextSize);
		}

		rightButtonPadding = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_rightButtonPadding, 0));
		rightButtonPaddingLeft = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_rightButtonPaddingLeft, 0));
		rightButtonPaddingRight = DensityUtils
				.px2dp(context, ta.getDimension(
						R.styleable.barView_rightButtonPaddingRight, 0));
		rightButtonPaddingTop = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_rightButtonPaddingTop, 0));
		rightButtonPaddingBottom = DensityUtils.px2dp(context, ta.getDimension(
				R.styleable.barView_rightButtonPaddingBottom, 0));

		rightButtonMargIn = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_rightButtonMargIn, 0));
		rightButtonMargInLeft = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_rightButtonMargInLeft, 0));
		rightButtonMargInRight = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_rightButtonMargInRight, 0));
		rightButtonMargInTop = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_rightButtonMargInTop, 0));
		rightButtonMargInBottom = DensityUtils
				.px2dp(context, ta.getDimension(
						R.styleable.barView_rightButtonMargInBottom, 0));

	}

}
