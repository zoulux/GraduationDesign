package com.lyy.hitogether.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.LruCache;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 图片加载类
 *
 * @author Administrator
 *
 */
public class ImageLoader {

	private static ImageLoader mInstance;
	// 图片缓存才核心对象
	private LruCache<String, Bitmap> mLruCache;
	// 线程池
	private ExecutorService mThreadPool;

	private static final int DEAFULT_THREAD_COUNT = 1;
	// 队列的调度方式
	private static Type mType = Type.LIFO;

	public enum Type {
		FIFO, LIFO;
	}

	// 任务队列
	private LinkedList<Runnable> mTaskQueue;
	// 后台轮询线程
	private Thread mPoolThread;

	private Handler mPoolThreadHandle;
	// UI线程中的Handler
	private Handler mUIHandler;
	private Semaphore mSemaphorePoolThreadHandle = new Semaphore(0);

	private Semaphore mSemaphoreThreadPool;
	private String isTrue = "1";

	private ImageLoader(int threadCount, Type type) {
		init(threadCount, type);
	}
	/**
	 * 初始化
	 *
	 * @param threadCount
	 * @param type
	 */
	private void init(int threadCount, Type type) {

		if (isTrue.equals("1")) {
			// 后台轮询线程
			mPoolThread = new Thread() {
				@Override
				public void run() {
					Looper.prepare();
					mPoolThreadHandle = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							// 线程池取出一个任务进行执行

							mThreadPool.execute(getTask());
							try {
								mSemaphoreThreadPool.acquire();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}

						}

					};
					// 释放一个信号量
					mSemaphorePoolThreadHandle.release();
					Looper.loop();
				};
			};

			mPoolThread.start();
		}

		// 获取我们应用的最大可用内存
		int maxMemory = (int) Runtime.getRuntime().maxMemory();
		int cachaMemory = maxMemory / 8;

		mLruCache = new LruCache<String, Bitmap>(cachaMemory) {
			@Override
			protected int sizeOf(String key, Bitmap value) {
				return value.getRowBytes() * value.getHeight();
			};
		};
		// 创建线程池
		mThreadPool = Executors.newFixedThreadPool(threadCount);
		mSemaphoreThreadPool = new Semaphore(threadCount);
		mTaskQueue = new LinkedList<Runnable>();
		mType = type;

	}

	public void end(Context context) {
		SPUtils.put(context,"isTrue", "0");

	}

	public static ImageLoader getInstance() {
		if (mInstance == null) {
			synchronized (ImageLoader.class) {
				if (mInstance == null) {
					mInstance = new ImageLoader(DEAFULT_THREAD_COUNT, mType);

				}
			}
		}
		return mInstance;
	}

	public static ImageLoader getInstance(int ThreadCount, Type type) {
		if (mInstance == null) {
			synchronized (ImageLoader.class) {
				if (mInstance == null) {
					mInstance = new ImageLoader(ThreadCount, type);
				}
			}
		}
		return mInstance;
	}

	/**
	 * 从任务队列中取出一个方法
	 *
	 * @return
	 */
	private synchronized Runnable getTask() {
		if (mType == Type.FIFO) {
			return mTaskQueue.removeFirst();
		} else if (mType == Type.LIFO) {
			return mTaskQueue.removeLast();
		}
		return null;
	}

	private String path;

	public void LoadImage(final String path, final ImageView imageView) {

		imageView.setTag(path);

		if (mUIHandler == null) {
			mUIHandler = new Handler() {

				@Override
				public void handleMessage(Message msg) {
					// 获取得到的图片，为ImageView回调设置图片
					ImageHolder holder = (ImageHolder) msg.obj;
					ImageView imageView = holder.imageView;
					Bitmap bm = holder.bitmap;
					String path = holder.path;
					setPath(path);

					// 将path与gettag的存储的路径进行比较
					if (imageView.getTag().toString().equals(path)) {
						imageView.setImageBitmap(bm);
						// imageView.setColorFilter(null);
						// mSelected
						// .setImageResource(R.drawable.picture_unselected);
					}
				}
			};
		}
		// 根据path在缓存中获取Bitmap
		Bitmap bm = getBitmapFromLruCache(path);

		if (bm != null) {
			reFreshBitmap(path, bm, imageView);
		} else {
			addTask(new Runnable() {
				public void run() {
					// 加载图片
					// 图片的压缩
					// 1.获得图片需要显示的大小
					ImageSize imageSize = getImageViewSize(imageView);

					// 2压缩图片
					Bitmap bm = decodeSampleBitmapFromPath(path,
							imageSize.width, imageSize.height);
					// 3,把图片加入到缓存中去
					addBitmapToLruCache(path, bm);

					reFreshBitmap(path, bm, imageView);
					mSemaphoreThreadPool.release();
				}

			});

		}
	}

	protected void setPath(String path) {
		this.path = path;
	}

	public void reFreshBitmap(String path, Bitmap bm, ImageView imageView) {
		ImageHolder holder = new ImageHolder();
		holder.bitmap = bm;
		holder.path = path;
		holder.imageView = imageView;
		Message message = Message.obtain();
		message.obj = holder;
		mUIHandler.sendMessage(message);

	}

	/**
	 * 将图片家入到缓存中
	 *
	 * @param path
	 * @param bm
	 */
	protected void addBitmapToLruCache(String path, Bitmap bm) {
		if (getBitmapFromLruCache(path) == null) {
			if (bm != null) {
				mLruCache.put(path, bm);
			}
		}
	}

	/**
	 * 根据图片需要显示的宽和搞进行压缩
	 *
	 * @param path
	 * @param width
	 * @param height
	 * @return
	 */
	private Bitmap decodeSampleBitmapFromPath(String path, int width, int height) {
		// 获得图片的宽和高，并不把图片加载到内存中
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;

		BitmapFactory.decodeFile(path, options);
		options.inSampleSize = cuculateInSampleSize(options, width, height);

		// 使用获取到的insampleSize获取图片,可以把图片加载到内存中了
		options.inJustDecodeBounds = false;
		Bitmap bmp = BitmapFactory.decodeFile(path, options);
		return bmp;
	}

	/**
	 * 根据需求的宽和搞以及图片的实际的宽和高计算SampleSize
	 *
	 * @param options
	 * @param width
	 * @param height
	 * @return
	 */
	private int cuculateInSampleSize(BitmapFactory.Options options,
									 int reqWidth, int reqHeight) {
		int width = options.outWidth;
		int height = options.outHeight;

		int inSampleSize = 1;

		if (width > reqWidth && height > reqHeight) {
			int widthRadio = Math.round(width * 1.0f / reqWidth);
			int heightRadio = Math.round(height * 1.0f / reqHeight);
			inSampleSize = Math.max(widthRadio, heightRadio);
		}

		return inSampleSize;
	}

	/**
	 * 根据ImgaView获取适当的压缩的宽和高
	 *
	 * @param imageView
	 * @return
	 */

	private ImageSize getImageViewSize(ImageView imageView) {

		DisplayMetrics displayMetrics = imageView.getResources()
				.getDisplayMetrics();

		ImageSize imageSize = new ImageSize();
		LayoutParams lp = imageView.getLayoutParams();
		int width = imageView.getWidth();// 获取ImageView的实际宽度
		// int width = getImageViewFieldValue(imageView, "mMaxWidth");
		if (width <= 0) {
			width = lp.width;// 获取imgeview在layout中声明的宽度
		}
		if (width <= 0) {
			width = getImageViewFieldValue(imageView, "mMaxWidth");// 检查最大值
		}
		if (width <= 0) {
			width = displayMetrics.widthPixels;
		}

		int height = imageView.getHeight();// 获取ImageView的实际高度
		// int height = ;

		if (height <= 0) {
			height = lp.height;// 获取imgeview在layout中声明的高度
		}
		if (height <= 0) {
			height = getImageViewFieldValue(imageView, "mMaxHeight");// 检查最大值
		}
		if (height <= 0) {
			height = displayMetrics.heightPixels;
		}

		imageSize.width = width;
		imageSize.height = height;
		return imageSize;
	}

	private synchronized void addTask(Runnable runnable) {

		try {
			if (mPoolThreadHandle == null) {
				mSemaphorePoolThreadHandle.acquire();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		mTaskQueue.add(runnable);
		mPoolThreadHandle.sendEmptyMessage(0x110);
	}

	/**
	 * 通过反射获取ImageView的摸个属性值
	 *
	 * @param object
	 * @param fieldName
	 * @return
	 */
	private static int getImageViewFieldValue(Object object, String fieldName) {
		int value = 0;
		try {
			Field field = ImageView.class.getDeclaredField(fieldName);
			field.setAccessible(true);

			int fieldValue = field.getInt(object);
			if (fieldValue > 0 && fieldValue < Integer.MAX_VALUE) {
				value = fieldValue;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * 根据path在缓存中获取Bitmap
	 *
	 * @param path
	 * @return
	 */
	private Bitmap getBitmapFromLruCache(String path) {
		return mLruCache.get(path);
	}

	private class ImageSize {
		int width;
		int height;
	}

	private class ImageHolder {
		Bitmap bitmap;
		ImageView imageView;
		String path;
	}

	/**
	 * 获取压缩后的Bitmap
	 * @param imageView
	 * @param path
	 * @return
	 */
	public Bitmap getCompressedBitmap(ImageView imageView, String path) {
		ImageSize imageSize = getImageViewSize(imageView);
		return decodeSampleBitmapFromPath(path, imageSize.width,
				imageSize.height);
	}
}
