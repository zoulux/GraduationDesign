package com.lyy.hitogether.global;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import com.lyy.hitogether.R;
import com.lyy.hitogether.activity.MainActivity;

import com.lyy.hitogether.provider.ContactsProvider;
import com.lyy.hitogether.provider.NewCameraInputProvider;
import com.lyy.hitogether.provider.PhotoCollectionsProvider;
import com.lyy.hitogether.provider.RealTimeLocationInputProvider;

import java.util.List;

import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.model.UIConversation;
import io.rong.imkit.widget.AlterDialogFragment;
import io.rong.imkit.widget.provider.CameraInputProvider;
import io.rong.imkit.widget.provider.InputProvider;
import io.rong.imkit.widget.provider.VoIPInputProvider;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.location.RealTimeLocationConstant;
import io.rong.imlib.location.message.RealTimeLocationStartMessage;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Conversation.ConversationType;
import io.rong.imlib.model.Group;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.UserInfo;
import io.rong.message.ContactNotificationMessage;
import io.rong.message.ImageMessage;
import io.rong.message.LocationMessage;
import io.rong.message.RichContentMessage;
import io.rong.message.TextMessage;
import io.rong.message.VoiceMessage;
import io.rong.notification.PushNotificationManager;
import io.rong.notification.PushNotificationMessage;

;

/**
 * Created by zhjchen on 1/29/15.
 */

/**
 * 融云SDK事件监听处理。 把事件统一处理，开发者可直接复制到自己的项目中去使用。
 * <p/>
 * 该类包含的监听事件有： 1、消息接收器：OnReceiveMessageListener。
 * 2、发出消息接收器：OnSendMessageListener。 3、用户信息提供者：GetUserInfoProvider。
 * 4、好友信息提供者：GetFriendsProvider。 5、群组信息提供者：GetGroupInfoProvider。
 * 7、连接状态监听器，以获取连接相关状态：ConnectionStatusListener。 8、地理位置提供者：LocationProvider。
 * 9、自定义 push 通知： OnReceivePushMessageListener。
 * 10、会话列表界面操作的监听器：ConversationListBehaviorListener。
 */
public final class RongCloudEvent implements
		RongIMClient.OnReceiveMessageListener, RongIM.OnSendMessageListener,
		RongIM.UserInfoProvider, RongIM.GroupInfoProvider,
		RongIM.ConversationBehaviorListener,
		RongIMClient.ConnectionStatusListener, RongIM.LocationProvider,
		RongIMClient.OnReceivePushMessageListener,
		RongIM.ConversationListBehaviorListener, Handler.Callback {

	private static final String TAG = RongCloudEvent.class.getSimpleName();

	private static RongCloudEvent mRongCloudInstance;

	private Context mContext;


	private Handler mHandler;


	/**
	 * 初始化 RongCloud.
	 * 
	 * @param context
	 *            上下文。
	 */
	public static void init(Context context) {

		if (mRongCloudInstance == null) {

			synchronized (RongCloudEvent.class) {

				if (mRongCloudInstance == null) {
					mRongCloudInstance = new RongCloudEvent(context);
				}
			}
		}

	}

	/**
	 * 构造方法。
	 * 
	 * @param context
	 *            上下文。
	 */
	private RongCloudEvent(Context context) {
		mContext = context;
		initDefaultListener();
		mHandler = new Handler(this);
	}

	/**
	 * RongIM.init(this) 后直接可注册的Listener。
	 */
	private void initDefaultListener() {
		RongIM.setUserInfoProvider(this, true);// 设置用户信息提供者。
		RongIM.setGroupInfoProvider(this, true);// 设置群组信息提供者。
		RongIM.setConversationBehaviorListener(this);// 设置会话界面操作的监听器。
		RongIM.setLocationProvider(this);// 设置地理位置提供者,不用位置的同学可以注掉此行代码
		RongIM.setConversationListBehaviorListener(this);
		// 消息体内是否有 userinfo 这个属性
		// RongIM.getInstance().setMessageAttachedUserInfo(true);
		// RongIM.getInstance().getRongIMClient().setOnReceivePushMessageListener(this);//自定义
		// push 通知。
	}

	/*
	 * 连接成功注册。 <p/> 在RongIM-connect-onSuccess后调用。
	 */
	@SuppressWarnings("static-access")
	public void setOtherListener() {

		RongIM.getInstance().getRongIMClient()
				.setOnReceiveMessageListener(this);// 设置消息接收监听器。
		RongIM.getInstance().setSendMessageListener(this);// 设置发出消息接收监听器.
		RongIM.getInstance().getRongIMClient()
				.setConnectionStatusListener(this);// 设置连接状态监听器。

		// 扩展功能自定义
		InputProvider.ExtendProvider[] provider = {
				new PhotoCollectionsProvider(RongContext.getInstance()),// 图片
				new NewCameraInputProvider(RongContext.getInstance()),// 相机
				new VoIPInputProvider(RongContext.getInstance()),// 语音通话
				new ContactsProvider(RongContext.getInstance()),// 通讯录
		};

		InputProvider.ExtendProvider[] provider1 = {
				new PhotoCollectionsProvider(RongContext.getInstance()),// 图片
				new CameraInputProvider(RongContext.getInstance()),// 相机
				new RealTimeLocationInputProvider(RongContext.getInstance()),// 地理位置
		};

		RongIM.getInstance().resetInputExtensionProvider(
				Conversation.ConversationType.PRIVATE, provider);

		RongIM.getInstance().resetInputExtensionProvider(
				Conversation.ConversationType.DISCUSSION, provider1);
		RongIM.getInstance().resetInputExtensionProvider(
				Conversation.ConversationType.GROUP, provider1);
		RongIM.getInstance().resetInputExtensionProvider(
				Conversation.ConversationType.CUSTOMER_SERVICE, provider1);
		RongIM.getInstance().resetInputExtensionProvider(
				Conversation.ConversationType.CHATROOM, provider1);
		// RongIM.getInstance().setPrimaryInputProvider(new
		// InputTestProvider((RongContext) mContext));

	}

	/**
	 * 自定义 push 通知。
	 * 
	 * @param msg
	 * @return
	 */
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	public boolean onReceivePushMessage(PushNotificationMessage msg) {

		PushNotificationManager.getInstance().onReceivePush(msg);

		Intent intent = new Intent();
		Uri uri;

		intent.setAction(Intent.ACTION_VIEW);

		Conversation.ConversationType conversationType = msg
				.getConversationType();

		uri = Uri.parse("rong://" + RongContext.getInstance().getPackageName())
				.buildUpon().appendPath("conversationlist").build();
		intent.setData(uri);
		Log.d(TAG, "onPushMessageArrive-url:" + uri.toString());

		Notification notification = null;

		PendingIntent pendingIntent = PendingIntent.getActivity(
				RongContext.getInstance(), 0, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);

		if (android.os.Build.VERSION.SDK_INT < 11) {
//
//			notification = new Notification(RongContext.getInstance()
//					.getApplicationInfo().icon, "自定义 notification",
//					System.currentTimeMillis());
//			notification.
//			notification.setLatestEventInfo(RongContext.getInstance(),
//					"自定义 title", "这是 Content:" + msg.getObjectName(),
//					pendingIntent);
//			notification.flags = Notification.FLAG_AUTO_CANCEL;
//			notification.defaults = Notification.DEFAULT_SOUND;
		} else {

			notification = new Notification.Builder(RongContext.getInstance())
					.setLargeIcon(getAppIcon()).setSmallIcon(R.drawable.icon)
					.setTicker("自定义 notification").setContentTitle("自定义 title")
					.setContentText("这是 Content:" + msg.getObjectName())
					.setContentIntent(pendingIntent).setAutoCancel(true)
					.setDefaults(Notification.DEFAULT_ALL).build();

		}

		NotificationManager nm = (NotificationManager) RongContext
				.getInstance().getSystemService(
						RongContext.getInstance().NOTIFICATION_SERVICE);

		nm.notify(0, notification);

		return true;
	}

	private Bitmap getAppIcon() {
		BitmapDrawable bitmapDrawable;
		Bitmap appIcon;
		bitmapDrawable = (BitmapDrawable) RongContext.getInstance()
				.getApplicationInfo()
				.loadIcon(RongContext.getInstance().getPackageManager());
		appIcon = bitmapDrawable.getBitmap();
		return appIcon;
	}

	/**
	 * 获取RongCloud 实例。
	 * 
	 * @return RongCloud。
	 */
	public static RongCloudEvent getInstance() {
		return mRongCloudInstance;
	}

	/**
	 * 接收消息的监听器：OnReceiveMessageListener 的回调方法，接收到消息后执行。
	 * 
	 * @param message
	 *            接收到的消息的实体信息。
	 * @param left
	 *            剩余未拉取消息数目。
	 */
	@Override
	public boolean onReceived(Message message, int left) {

		MessageContent messageContent = message.getContent();

		if (messageContent instanceof TextMessage) {// 文本消息
			TextMessage textMessage = (TextMessage) messageContent;
			textMessage.getExtra();
			Log.d(TAG, "onReceived-TextMessage:" + textMessage.getContent());
		} else if (messageContent instanceof ImageMessage) {// 图片消息
			ImageMessage imageMessage = (ImageMessage) messageContent;
			Log.d(TAG, "onReceived-ImageMessage:" + imageMessage.getRemoteUri());
		} else if (messageContent instanceof VoiceMessage) {// 语音消息
			VoiceMessage voiceMessage = (VoiceMessage) messageContent;
			Log.d(TAG, "onReceived-voiceMessage:"
					+ voiceMessage.getUri().toString());
		} else if (messageContent instanceof RichContentMessage) {// 图文消息
			RichContentMessage richContentMessage = (RichContentMessage) messageContent;
			Log.d(TAG,
					"onReceived-RichContentMessage:"
							+ richContentMessage.getContent());
		}
		// else if (messageContent instanceof InformationNotificationMessage)
		// {// 小灰条消息
		// InformationNotificationMessage informationNotificationMessage =
		// (InformationNotificationMessage) messageContent;
		// Log.d(TAG, "onReceived-informationNotificationMessage:"
		// + informationNotificationMessage.getMessage());
		// if (RongContext.getInstance() != null)
		// getFriendByUserIdHttpRequest = RongContext
		// .getInstance()
		// .getDemoApi()
		// .getUserInfoByUserId(message.getSenderUserId(),
		// (ApiCallback<User>) this);
		// } else if (messageContent instanceof DeAgreedFriendRequestMessage)
		// {// 好友添加成功消息
		// DeAgreedFriendRequestMessage deAgreedFriendRequestMessage =
		// (DeAgreedFriendRequestMessage) messageContent;
		// Log.d(TAG, "onReceived-deAgreedFriendRequestMessage:"
		// + deAgreedFriendRequestMessage.getMessage());
		// receiveAgreeSuccess(deAgreedFriendRequestMessage);
		// }
		else if (messageContent instanceof ContactNotificationMessage) {// 好友添加消息
			ContactNotificationMessage contactContentMessage = (ContactNotificationMessage) messageContent;
			Log.d(TAG, "onReceived-ContactNotificationMessage:getExtra;"
					+ contactContentMessage.getExtra());
			Log.d(TAG, "onReceived-ContactNotificationMessage:+getmessage:"
					+ contactContentMessage.getMessage().toString());
			Intent in = new Intent();

			in.setAction(MainActivity.ACTION_DMEO_RECEIVE_MESSAGE);
			in.putExtra("rongCloud", contactContentMessage);
			in.putExtra("has_message", true);
			mContext.sendBroadcast(in);
		} else {
			Log.d(TAG, "onReceived-其他消息，自己来判断处理");
		}

		return false;

	}

	/**
	 * // * @param deAgreedFriendRequestMessage //
	 */
	// private void receiveAgreeSuccess(
	// DeAgreedFriendRequestMessage deAgreedFriendRequestMessage) {
	// // if (DemoContext.getInstance() != null) {
	// // if(deAgreedFriendRequestMessage.getUserInfo()!=null) {
	// //
	// if(DemoContext.getInstance().hasUserId(deAgreedFriendRequestMessage.getUserInfo().getUserId())){
	// //
	// DemoContext.getInstance().updateUserInfos(deAgreedFriendRequestMessage.getUserInfo().getUserId(),
	// // "1");
	// // }else{
	// //
	// DemoContext.getInstance().insertOrReplaceUserInfo(deAgreedFriendRequestMessage.getUserInfo(),
	// // "1");
	// // }
	// //
	// // }
	// // }
	// Intent in = new Intent();
	// in.setAction(MainActivity.ACTION_DMEO_AGREE_REQUEST);
	// in.putExtra("AGREE_REQUEST", true);
	// mContext.sendBroadcast(in);
	//
	// }

	@Override
	public Message onSend(Message message) {
		message.setExtra("my extra");
		Log.e("qinxiao", "onSend:" + message.getObjectName() + ", extra="
				+ message.getExtra());
		return message;
	}

	/**
	 * 消息在UI展示后执行/自己的消息发出后执行,无论成功或失败。
	 * 
	 * @param message
	 *            消息。
	 */
	@Override
	public boolean onSent(Message message,
			RongIM.SentMessageErrorCode sentMessageErrorCode) {
		Log.e("qinxiao", "onSent:" + message.getObjectName() + ", extra="
				+ message.getExtra());

		if (message.getSentStatus() == Message.SentStatus.FAILED) {

			if (sentMessageErrorCode == RongIM.SentMessageErrorCode.NOT_IN_CHATROOM) {// 不在聊天室

			} else if (sentMessageErrorCode == RongIM.SentMessageErrorCode.NOT_IN_DISCUSSION) {// 不在讨论组

			} else if (sentMessageErrorCode == RongIM.SentMessageErrorCode.NOT_IN_GROUP) {// 不在群组

			} else if (sentMessageErrorCode == RongIM.SentMessageErrorCode.REJECTED_BY_BLACKLIST) {// 你在他的黑名单中
				// WinToast.toast(mContext, "你在对方的黑名单中");
			}
		}

		MessageContent messageContent = message.getContent();

		if (messageContent instanceof TextMessage) {// 文本消息
			TextMessage textMessage = (TextMessage) messageContent;
			Log.d(TAG, "onSent-TextMessage:" + textMessage.getContent());
		} else if (messageContent instanceof ImageMessage) {// 图片消息
			ImageMessage imageMessage = (ImageMessage) messageContent;
			Log.d(TAG, "onSent-ImageMessage:" + imageMessage.getRemoteUri());
		} else if (messageContent instanceof VoiceMessage) {// 语音消息
			VoiceMessage voiceMessage = (VoiceMessage) messageContent;
			Log.d(TAG, "onSent-voiceMessage:"
					+ voiceMessage.getUri().toString());
		} else if (messageContent instanceof RichContentMessage) {// 图文消息
			RichContentMessage richContentMessage = (RichContentMessage) messageContent;
			Log.d(TAG,
					"onSent-RichContentMessage:"
							+ richContentMessage.getContent());
		} else {
			Log.d(TAG, "onSent-其他消息，自己来判断处理");
		}
		return false;
	}

	/**
	 * 用户信息的提供者：GetUserInfoProvider 的回调方法，获取用户信息。
	 * 
	 * @param userId
	 *            用户 Id。
	 * @return 用户信息，（注：由开发者提供用户信息）。
	 */

	@Override
	public UserInfo getUserInfo(String userId) {
		/**
		 * demo 代码 开发者需替换成自己的代码。
		 */

		List<UserInfo> userInfos = App.getInsatnce().getUserInfos();

		// Log.i("RongCloudEvent", userId + "RongCloudEvent" + "1");

		if (userInfos != null) {
			for (UserInfo userInfo : userInfos) {
				if (userInfo.getUserId().equals(userId)) {

					// Log.i("RongCloudEvent", userId + "" + "RongCloudEvent"
					// + "2");
					return userInfo;
				}
			}

		}

		return RongContext.getInstance().getUserInfoFromCache(userId);
		// mUserInfosDao = DBManager.getInstance(mContext).getDaoSession()
		// .getUserInfosDao();
		// if (userId == null)
		// return null;
		// UserInfos userInfo = mUserInfosDao.queryBuilder()
		// .where(UserInfosDao.Properties.Userid.eq(userId)).unique();
		// // Uri uri = Uri.fromFile(new File("/sdcard/gcc.png"));
		// Uri uri = Uri
		// .parse("http://img5.duitang.com/uploads/item/201505/16/20150516063832_PFK3T.thumb.224_0.jpeg");
		// UserInfo user = new UserInfo("rKZNIIIQ", "巴菲特", uri);
		//
		// if (userInfo == null && RongContext.getInstance() != null) {
		// // getUserInfoByUserIdHttpRequest = AppContext.getInstance()
		// // .getDemoApi()
		// // .getUserInfoByUserId(userId, (ApiCallback<User>) this);
		// }
		//
		// // return RongContext.getInstance().getUserInfoFromCache(userId);
		// return user;
	}

	/**
	 * 群组信息的提供者：GetGroupInfoProvider 的回调方法， 获取群组信息。
	 * 
	 * @param groupId
	 *            群组 Id.
	 * @return 群组信息，（注：由开发者提供群组信息）。
	 */
	@Override
	public Group getGroupInfo(String groupId) {
		/**
		 * demo 代码 开发者需替换成自己的代码。
		 */

		List<com.lyy.hitogether.bean.Group> groupList = App.getInsatnce()
				.getGroupList();

		for (com.lyy.hitogether.bean.Group g : groupList) {
			if (groupId.equals(g.getGroupId())) {
				Uri uri = Uri.parse(g.getGroupImg());
				return new Group(groupId, g.getGroupName(), uri);
			}

		}

		// Group group=new Group(id, name, portraitUri)
		//
		//
		// if (RongContext.getInstance().getGroupInfoCache() == null)
		// return null;

		// return RongContext.getInstance().getGroupInfoFromCache(groupId);
		return null;
	}

	/**
	 * 会话界面操作的监听器：ConversationBehaviorListener 的回调方法，当点击用户头像后执行。
	 * 
	 * @param context
	 *            应用当前上下文。
	 * @param conversationType
	 *            会话类型。
	 * @param user
	 *            被点击的用户的信息。
	 * @return 返回True不执行后续SDK操作，返回False继续执行SDK操作。
	 */
	@Override
	public boolean onUserPortraitClick(Context context,
			Conversation.ConversationType conversationType, UserInfo user) {
		Log.e(TAG, "----onUserPortraitClick");

		/**
		 * demo 代码 开发者需替换成自己的代码。
		 */
		if (user != null) {

			if (conversationType
					.equals(Conversation.ConversationType.PUBLIC_SERVICE)
					|| conversationType
							.equals(Conversation.ConversationType.APP_PUBLIC_SERVICE)) {
				RongIM.getInstance().startPublicServiceProfile(mContext,
						conversationType, user.getUserId());
			} else {
				Intent in = new Intent(context, MainActivity.class);
				in.putExtra("USER", user);
				in.putExtra("SEARCH_USERID", user.getUserId());
				in.putExtra("SEARCH_CONVERSATIONTYPE", conversationType);
				context.startActivity(in);

			}
		}

		return false;
	}

	@Override
	public boolean onUserPortraitLongClick(Context context,
			Conversation.ConversationType conversationType, UserInfo userInfo) {
		Log.e(TAG, "----onUserPortraitLongClick");

		return true;
	}

	/**
	 * 会话界面操作的监听器：ConversationBehaviorListener 的回调方法，当点击消息时执行。
	 * 
	 * @param context
	 *            应用当前上下文。
	 * @param message
	 *            被点击的消息的实体信息。
	 * @return 返回True不执行后续SDK操作，返回False继续执行SDK操作。
	 */
	@Override
	public boolean onMessageClick(final Context context, final View view,
			final Message message) {
		Log.e(TAG, "----onMessageClick");

		// real-time location message begin
		if (message.getContent() instanceof RealTimeLocationStartMessage) {
			RealTimeLocationConstant.RealTimeLocationStatus status = RongIMClient
					.getInstance().getRealTimeLocationCurrentState(
							message.getConversationType(),
							message.getTargetId());

			// if (status ==
			// RealTimeLocationConstant.RealTimeLocationStatus.RC_REAL_TIME_LOCATION_STATUS_IDLE)
			// {
			// startRealTimeLocation(context, message.getConversationType(),
			// message.getTargetId());
			// } else
			if (status == RealTimeLocationConstant.RealTimeLocationStatus.RC_REAL_TIME_LOCATION_STATUS_INCOMING) {

				final AlterDialogFragment alterDialogFragment = AlterDialogFragment
						.newInstance("", "加入位置共享", "取消", "加入");
				alterDialogFragment
						.setOnAlterDialogBtnListener(new AlterDialogFragment.AlterDialogBtnListener() {

							@Override
							public void onDialogPositiveClick(
									AlterDialogFragment dialog) {
								RealTimeLocationConstant.RealTimeLocationStatus status = RongIMClient
										.getInstance()
										.getRealTimeLocationCurrentState(
												message.getConversationType(),
												message.getTargetId());

								if (status == null
										|| status == RealTimeLocationConstant.RealTimeLocationStatus.RC_REAL_TIME_LOCATION_STATUS_IDLE) {
									startRealTimeLocation(context,
											message.getConversationType(),
											message.getTargetId());
								} else {
									joinRealTimeLocation(context,
											message.getConversationType(),
											message.getTargetId());
								}

							}

							@Override
							public void onDialogNegativeClick(
									AlterDialogFragment dialog) {
								alterDialogFragment.dismiss();
							}
						});

				alterDialogFragment.show(((FragmentActivity) context)
						.getSupportFragmentManager());
			} else {

				if (status != null
						&& (status == RealTimeLocationConstant.RealTimeLocationStatus.RC_REAL_TIME_LOCATION_STATUS_OUTGOING || status == RealTimeLocationConstant.RealTimeLocationStatus.RC_REAL_TIME_LOCATION_STATUS_CONNECTED)) {

					Intent intent = new Intent(((FragmentActivity) context),
							MainActivity.class);
					intent.putExtra("conversationType", message
							.getConversationType().getValue());
					intent.putExtra("targetId", message.getTargetId());
					context.startActivity(intent);
				}
			}
			return true;
		}

		// real-time location message end
		/**
		 * demo 代码 开发者需替换成自己的代码。
		 */
		if (message.getContent() instanceof LocationMessage) {
			Intent intent = new Intent(context, MainActivity.class);
			intent.putExtra("location", message.getContent());
			context.startActivity(intent);
		} else if (message.getContent() instanceof RichContentMessage) {
			RichContentMessage mRichContentMessage = (RichContentMessage) message
					.getContent();
			Log.d("Begavior", "extra:" + mRichContentMessage.getExtra());

		} else if (message.getContent() instanceof ImageMessage) {
			ImageMessage imageMessage = (ImageMessage) message.getContent();
			Intent intent = new Intent(context, MainActivity.class);

			intent.putExtra(
					"photo",
					imageMessage.getLocalUri() == null ? imageMessage
							.getRemoteUri() : imageMessage.getLocalUri());
			if (imageMessage.getThumUri() != null)
				intent.putExtra("thumbnail", imageMessage.getThumUri());

			context.startActivity(intent);
		}

		Log.d("Begavior",
				message.getObjectName() + ":" + message.getMessageId());

		Log.i("onMessageClick", "onMessageClick");
		return false;
	}

	private void startRealTimeLocation(Context context,
			Conversation.ConversationType conversationType, String targetId) {
		RongIMClient.getInstance().startRealTimeLocation(conversationType,
				targetId);

		Intent intent = new Intent(((FragmentActivity) context),
				MainActivity.class);
		intent.putExtra("conversationType", conversationType.getValue());
		intent.putExtra("targetId", targetId);
		context.startActivity(intent);
	}

	private void joinRealTimeLocation(Context context,
			Conversation.ConversationType conversationType, String targetId) {
		RongIMClient.getInstance().joinRealTimeLocation(conversationType,
				targetId);

		Intent intent = new Intent(((FragmentActivity) context),
				MainActivity.class);
		intent.putExtra("conversationType", conversationType.getValue());
		intent.putExtra("targetId", targetId);
		context.startActivity(intent);
	}

	/**
	 * 当点击链接消息时执行。
	 * 
	 * @param context
	 *            上下文。
	 * @param link
	 *            被点击的链接。
	 * @return 如果用户自己处理了点击后的逻辑处理，则返回 true， 否则返回 false, false 走融云默认处理方式。
	 */
	@Override
	public boolean onMessageLinkClick(Context context, String link) {
		return false;
	}

	@Override
	public boolean onMessageLongClick(Context context, View view,
			Message message) {

		Log.e(TAG, "----onMessageLongClick");
		return false;
	}

	/**
	 * 连接状态监听器，以获取连接相关状态:ConnectionStatusListener 的回调方法，网络状态变化时执行。
	 * 
	 * @param status
	 *            网络状态。
	 */
	@Override
	public void onChanged(ConnectionStatus status) {
		Log.d(TAG, "onChanged:" + status);
		if (status.getMessage().equals(
				ConnectionStatus.DISCONNECTED.getMessage())) {
		}
	}

	/**
	 * 位置信息提供者:LocationProvider 的回调方法，打开第三方地图页面。
	 * 
	 * @param context
	 *            上下文
	 * @param callback
	 *            回调
	 */
	@Override
	public void onStartLocation(Context context, LocationCallback callback) {

		/**
		 * demo 代码 开发者需替换成自己的代码。
		 */

		// RongContext.getInstance().setLastLocationCallback(callback);
		// context.startActivity(new Intent(context,
		// SOSOLocationActivity.class));// SOSO地图
	}

	/**
	 * 点击会话列表 item 后执行。
	 * 
	 * @param context
	 *            上下文。
	 * @param view
	 *            触发点击的 View。
	 * @param conversation
	 *            会话条目。
	 * @return 返回 true 不再执行融云 SDK 逻辑，返回 false 先执行融云 SDK 逻辑再执行该方法。
	 */
	@Override
	public boolean onConversationClick(Context context, View view,
			UIConversation conversation) {
		MessageContent messageContent = conversation.getMessageContent();

		// if
		// (conversation.getConversationType().equals(ConversationType.PRIVATE))
		// {
		// Log.e("onConversationClick", "PRIVATE");
		//
		// RongIM.getInstance().startConversation(context,
		// ConversationType.PRIVATE,
		// conversation.getConversationTargetId(), "hhh");

		// Intent intent = new Intent(context, ConversationActivity.class);
		// intent.putExtra("type", ConversationType.PRIVATE);
		// context.startActivity(intent);
		// }

		if (conversation.getConversationType().equals(ConversationType.GROUP)) {
			RongIM.getInstance().startConversation(context,
					ConversationType.GROUP,
					conversation.getConversationTargetId(), "hhh");
			Log.e("onConversationClick", "GROUP");
		}

		if (messageContent instanceof TextMessage) {// 文本消息

			TextMessage textMessage = (TextMessage) messageContent;
			textMessage.getExtra();
		} else if (messageContent instanceof ContactNotificationMessage) {
			Log.e(TAG, "---onConversationClick--ContactNotificationMessage-");

			context.startActivity(new Intent(context, MainActivity.class));
			return true;
		}

		Log.e("onConversationClick", "onConversationClick");
		return false;
	}

	@Override
	public boolean onConversationPortraitClick(Context context, ConversationType conversationType, String s) {
		return false;
	}

	@Override
	public boolean onConversationPortraitLongClick(Context context, ConversationType conversationType, String s) {
		return false;
	}

	/**
	 * 长按会话列表 item 后执行。
	 * 
	 * @param context
	 *            上下文。
	 * @param view
	 *            触发点击的 View。
	 * @param conversation
	 *            长按会话条目。
	 * @return 返回 true 不再执行融云 SDK 逻辑，返回 false 先执行融云 SDK 逻辑再执行该方法。
	 */
	@Override
	public boolean onConversationLongClick(Context context, View view,
			UIConversation conversation) {
		return false;
	}

	@Override
	public boolean handleMessage(android.os.Message message) {
		return false;
	}

}
