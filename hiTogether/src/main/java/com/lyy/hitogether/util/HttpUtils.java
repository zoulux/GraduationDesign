package com.lyy.hitogether.util;

import io.rong.imlib.model.UserInfo;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.CloudCodeListener;
import cn.bmob.v3.listener.FindListener;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lyy.hitogether.bean.MyUser;

public class HttpUtils {
	private static AsyncCustomEndpoints code;

	public static void getHttpData(Context context, String method,
			JSONObject params, CloudCodeListener listener) {
		code = new AsyncCustomEndpoints();
		code.callEndpoint(context, method, params, listener);
	};

	public interface MyResultListener<T> {
		void onSuccess(List<T> list);

		void onFaild(int code, String err);

	}

	public static void getGroup(Context context, String groupId,
			final MyResultListener<String> Listener) {
		JSONObject params = new JSONObject();
		try {
			params.put("groupId", groupId);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getHttpData(context, "GroupMember", params, new CloudCodeListener() {

			@Override
			public void onSuccess(Object results) {
				// Log.i("GroupMember", results.toString());
				String json = (String) results.toString();
				List<String> list = new ArrayList<String>();

				JSONArray jsonArray = null;
				try {
					jsonArray = new JSONArray(json);

					int size = jsonArray.length();

					// Log.i("JSONArraySIze", size + "");
					// Log.i("jsonArray", jsonArray.toString());
					for (int i = 0; i < size; i++) {
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						String str = (String) jsonObject.get("id");
						list.add(str);
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
				Listener.onSuccess(list);

			}

			@Override
			public void onFailure(int arg0, String arg1) {
				Listener.onFaild(arg0, arg1);

			}
		});
	}

	public static void getFrends(Context mContext, List<String> objectIds,
			final MyResultListener<UserInfo> myFindListener) {

		BmobQuery<MyUser> query = new BmobQuery<MyUser>();

		query.addWhereContainedIn("objectId", objectIds);
		query.findObjects(mContext, new FindListener<MyUser>() {

			@Override
			public void onSuccess(List<MyUser> users) {
				UserInfo userInfo = null;
				List<UserInfo> list = new ArrayList<UserInfo>();
				for (MyUser user : users) {
					userInfo = new UserInfo(user.getObjectId(), user.getNick(),
							Uri.parse(user.getAvatar()));
					list.add(userInfo);
				}

				myFindListener.onSuccess(list);
			}

			@Override
			public void onError(int arg0, String arg1) {
				myFindListener.onFaild(arg0, arg1);

			}
		});

	}
}
