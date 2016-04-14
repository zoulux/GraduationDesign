package com.lyy.hitogether.util;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient.ConnectCallback;
import io.rong.imlib.RongIMClient.ErrorCode;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Conversation.ConversationType;
import io.rong.imlib.model.UserInfo;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import cn.bmob.im.BmobUserManager;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.CloudCodeListener;
import cn.bmob.v3.listener.UpdateListener;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lyy.hitogether.activity.LoginActivity;
import com.lyy.hitogether.bean.Group;
import com.lyy.hitogether.bean.MyUser;
import com.lyy.hitogether.global.App;
import com.lyy.hitogether.global.RongCloudEvent;
import com.lyy.hitogether.util.HttpUtils.MyResultListener;

public class ConnectRong {
	private static Context mContext;

	public interface MyConnectListener {
		void onSuccess(List<UserInfo> users);

		void onFaild(String str);
	}

	protected static int WHAT = 0x211;

	public static void connect(Context context, MyConnectListener listener) {
		mContext = context;
		setMyConnectListener(listener);
		/**
		 * 连接融云 
		 */

		MyUser user = BmobUser.getCurrentUser(context, MyUser.class);
		String token = user.getToken();
		String objectId = user.getObjectId();
		//String userId = user.getUsername();

		if ( TextUtils.isEmpty(token)) {
			Log.i("ConnectRong", "1");

			getToken( objectId);

		} else {
			Log.i("ConnectRong", "2");
			concent(token);

		}

		// Log.i(BmobUser.getCurrentUser(context, MyUser.class).getNick(),
		// token);

	}

	private static void concent(String token) {
		Log.i("ConnectRong", "6");
		RongIM.connect(token, new ConnectCallback() {

			@Override
			public void onSuccess(String str) {
				// mHandler.sendEmptyMessage(WHAT);

				Log.i("ConnectRong", "7");
				initUserInfo();

				/**
				 * 更新好友消息
				 */
				new Thread(new Runnable() {

					@Override
					public void run() {
						updateFrindsInfo();

					}
				}).start();

				/**
				 * 更新群组消息
				 */
				new Thread(new Runnable() {

					@Override
					public void run() {
						updateGroupsInfo();

					}

				}).start();

			}

			@Override
			public void onError(ErrorCode arg0) {
				mListener.onFaild("faild");
			}

			@Override
			public void onTokenIncorrect() {
				mListener.onFaild("faild");

			}
		});

	}

	private static void getToken(String userId) {

		JSONObject params = new JSONObject();
		try {
			params.put("userId", userId);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.i("ConnectRong", "3");
		HttpUtils.getHttpData(mContext, "connect", params,
				new CloudCodeListener() {

					@Override
					public void onSuccess(Object result) {
						Log.i("ConnectRong", "4");
						if (result != null) {
							Log.i("ConnectRong", "5");
							String token = result.toString();
							// updateUser(result.toString(), objectId);
							concent(token);

						}

					}

					@Override
					public void onFailure(int arg0, String arg1) {
						// TODO Auto-generated method stub

					}
				});

	}

	protected static void updateUser(final String token, String objectId) {

		MyUser user = new MyUser();
		user.setToken(token);
		user.update(mContext, objectId, new UpdateListener() {

			@Override
			public void onSuccess() {

				concent(token);
			}

			@Override
			public void onFailure(int arg0, String arg1) {

			}
		});
	}

	private static MyConnectListener mListener;

	public static void setMyConnectListener(MyConnectListener listener) {
		mListener = listener;
	}

	private static void updateGroupsInfo() {

		HttpUtils.getHttpData(mContext, "getAllGroup", null,
				new CloudCodeListener() {

					@Override
					public void onSuccess(Object result) {
						RongCloudEvent.getInstance().setOtherListener();
						String json = (String) result;
						Gson gson = new Gson();
						List<Group> groupList = gson.fromJson(json,
								new TypeToken<List<Group>>() {
								}.getType());

						App.getInsatnce().setGroupList(groupList);
					}

					@Override
					public void onFailure(int code, String err) {
						Toast.makeText(mContext, "获取群组消息失败", Toast.LENGTH_SHORT)
								.show();
					}
				});

	}

	public static void updateFrindsInfo() {

		List<Conversation> conversationList = RongIM.getInstance()
				.getRongIMClient()
				.getConversationList(ConversationType.PRIVATE);

		List<String> userIds = new ArrayList<String>();

		if (conversationList == null || conversationList.size() == 0) {

			if (mListener != null) {

				mListener.onSuccess(null);
			}

			return;
		}

		for (Conversation conversation : conversationList) {
			userIds.add(conversation.getTargetId());
		}
		HttpUtils.getFrends(mContext, userIds,
				new MyResultListener<UserInfo>() {

					@Override
					public void onSuccess(List<UserInfo> users) {

						App.getInsatnce().setUserInfos(users);
						if (mListener != null) {

							mListener.onSuccess(users);
						}

					}

					@Override
					public void onFaild(int code, String err) {

						if (mListener != null) {

							mListener.onFaild(code + "" + err);
						}
					}
				});

	}

	/**
	 * 将自己身份信息包装发出去
	 * 
	 */
	protected static void initUserInfo() {
		Uri uri = Uri.parse(BmobUserManager.getInstance(mContext)
				.getCurrentUser().getAvatar());
		RongIM.getInstance()
				.setCurrentUserInfo(
						new UserInfo(BmobUserManager.getInstance(mContext)
								.getCurrentUserObjectId(), BmobUserManager
								.getInstance(mContext).getCurrentUser()
								.getNick(), uri));
		RongIM.getInstance().setMessageAttachedUserInfo(true);
	}

}
