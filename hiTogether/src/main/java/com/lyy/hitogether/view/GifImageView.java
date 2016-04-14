package com.lyy.hitogether.view;

import java.io.InputStream;
import java.lang.reflect.Field;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.lyy.hitogether.R;

public class GifImageView extends ImageView implements OnClickListener {
	private Movie mMovie;
	private int mImageWidth;
	private int mImageHeight;
	private boolean isAutoPlay;

	private Bitmap mStartPlay;
	private boolean isPlaying;
	private long mMovieStart;

	public GifImageView(Context context, AttributeSet attrs, int defStle) {
		super(context, attrs, defStle);
		init(context, attrs);
	}

	public GifImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public GifImageView(Context context) {
		super(context);
	}

	private void init(Context context, AttributeSet attrs) {
		TypedArray attributes = context.obtainStyledAttributes(attrs,
				R.styleable.GifImageView);

		int resourceId = getResourcesId(attributes, context, attrs);
		if (resourceId != 0) {

			InputStream is = getResources().openRawResource(resourceId);
			mMovie = Movie.decodeStream(is);

			if (mMovie != null) {

				Bitmap bitmap = BitmapFactory.decodeStream(is);
				mImageWidth = bitmap.getWidth();
				mImageHeight = bitmap.getHeight();

				// 释放
				bitmap.recycle();

				isAutoPlay = attributes.getBoolean(
						R.styleable.GifImageView_auto_play, false);
				if (!isAutoPlay) {
					mStartPlay = BitmapFactory.decodeResource(getResources(),
							R.drawable.hot);
					setOnClickListener(this);
				}

			}

		}

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		if (mMovie != null) {
			setMeasuredDimension(mImageWidth, mImageHeight);
		}

	}

	@Override
	protected void onDraw(Canvas canvas) {

		if (mMovie == null) {
			super.onDraw(canvas);
		} else {
			if (isAutoPlay) {
				playMovie(canvas);
				invalidate();
			} else {
				if (isPlaying) {

					if (playMovie(canvas)) {
						isPlaying = false;
					}
					invalidate();
				} else {
					mMovie.setTime(0);
					mMovie.draw(canvas, 0, 0);

					int offsetW = (mImageWidth - mStartPlay.getWidth()) / 2;
					int offsetH = (mImageHeight - mStartPlay.getHeight()) / 2;
					canvas.drawBitmap(mStartPlay, offsetW, offsetH, null);
				}
			}
		}

		super.onDraw(canvas);
	}

	private boolean playMovie(Canvas canvas) {
		long now = SystemClock.uptimeMillis();

		if (mMovieStart == 0) {
			mMovieStart = now;
		}

		int duration = mMovie.duration();
		if (duration == 0) {
			duration = 10000;
		}

		int relTime = (int) ((now - mMovieStart) % duration);

		mMovie.setTime(relTime);
		mMovie.draw(canvas, 0, 0);
		if (now - mMovieStart >= duration) {
			mMovieStart = 0;
			return true;
		}
		return false;

	}

	private int getResourcesId(TypedArray attributes, Context context,
			AttributeSet attrs) {

		try {
			Field field = TypedArray.class.getDeclaredField("mValue");
			field.setAccessible(true);
			TypedValue typedValue = (TypedValue) field.get(attributes);

			return typedValue.resourceId;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (attributes != null) {
				attributes.recycle();
			}
		}

		return 0;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == getId()) {
			isPlaying = true;
			invalidate();
		}
	}

}
