package com.lyy.hitogether.util;

import java.util.List;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lyy.hitogether.bean.Service;

public class JsonUtils {

	private String json = "{\"results\":[{\"createdAt\":\"2015-10-20 20:00:23\",\"destination\":\"南京\",\"introduction\":\"传奇与典故，写下南京的沧桑；江河与湖泊，铸就南京的魂魄。\",\"objectId\":\"e74c6b2e88\",\"price\":500,\"serviceId\":\"2015102019311234\",\"showImg\":\"http://pic3.nipic.com/20090623/1412106_071314044_2.jpg\",\"summary\":\"南京是中国好地方\",\"updatedAt\":\"2015-10-20 20:18:24\",\"userId\":\"rKZNIIIQ\",\"userName\":\"demo\"},{\"createdAt\":\"2015-10-20 20:05:40\",\"destination\":\"上海\",\"introduction\":\"上海是中国的经济、交通、科技、工业、金融、会展和航运中心之一。\",\"objectId\":\"ad96d48b57\",\"price\":1500,\"serviceId\":\"2015102020021234\",\"showImg\":\"http://img5.imgtn.bdimg.com/it/u=977178979,3700482650\u0026fm=21\u0026gp=0.jpg\",\"summary\":\"上海是中国的经济中心\",\"updatedAt\":\"2015-10-20 20:18:30\",\"userId\":\"P8hsIIIW\",\"userName\":\"demo1\"},{\"createdAt\":\"2015-10-20 20:09:22\",\"destination\":\"北京\",\"introduction\":\"北京文化底蕴深厚，是一个古典与现代结合的城市。旅游资源十分丰富，景色都很宏伟壮观。北京的交通和购物都很方便，也有很多美食，小吃和北京烤鸭好吃，雾霾比较严重。\",\"objectId\":\"7277a8ee2c\",\"price\":1800,\"serviceId\":\"2015102020061234\",\"showImg\":\"http://travel.cz001.com.cn/attachement/jpg/site2/20110421/bc305bc8b0e00f19c88219.jpg\",\"summary\":\"北京是中国的政治中心\",\"updatedAt\":\"2015-10-20 20:19:07\",\"userId\":\"xmdL111k\",\"userName\":\"demo2\"},{\"createdAt\":\"2015-10-20 20:11:44\",\"destination\":\"西藏\",\"introduction\":\"西藏自治区位于青藏高原的西南部，雪域高原的人好像就为给众生祈福而生，每天念经、转经筒、磕长头，日复一日，年复一年，这就是他们的生活习惯。\",\"objectId\":\"386ae6e150\",\"price\":9800,\"serviceId\":\"2015102020112347\",\"showImg\":\"http://sc.sinaimg.cn/2010/0120/201012084541.jpg\",\"summary\":\"西藏在黄土高坡\",\"updatedAt\":\"2015-10-20 20:19:13\",\"userId\":\"H4OLfffk\",\"userName\":\"demo3\"}]}";
	private String json2 = "[{\"createdAt\":\"2015-10-20 20:00:23\",\"destination\":\"南京\",\"introduction\":\"传奇与典故，写下南京的沧桑；江河与湖泊，铸就南京的魂魄。\",\"objectId\":\"e74c6b2e88\",\"price\":500,\"serviceId\":\"2015102019311234\",\"showImg\":\"http://pic3.nipic.com/20090623/1412106_071314044_2.jpg\",\"summary\":\"南京是中国好地方\",\"updatedAt\":\"2015-10-20 20:18:24\",\"userId\":\"rKZNIIIQ\",\"userName\":\"demo\"},{\"createdAt\":\"2015-10-20 20:05:40\",\"destination\":\"上海\",\"introduction\":\"上海是中国的经济、交通、科技、工业、金融、会展和航运中心之一。\",\"objectId\":\"ad96d48b57\",\"price\":1500,\"serviceId\":\"2015102020021234\",\"showImg\":\"http://img5.imgtn.bdimg.com/it/u=977178979,3700482650\u0026fm=21\u0026gp=0.jpg\",\"summary\":\"上海是中国的经济中心\",\"updatedAt\":\"2015-10-20 20:18:30\",\"userId\":\"P8hsIIIW\",\"userName\":\"demo1\"},{\"createdAt\":\"2015-10-20 20:09:22\",\"destination\":\"北京\",\"introduction\":\"北京文化底蕴深厚，是一个古典与现代结合的城市。旅游资源十分丰富，景色都很宏伟壮观。北京的交通和购物都很方便，也有很多美食，小吃和北京烤鸭好吃，雾霾比较严重。\",\"objectId\":\"7277a8ee2c\",\"price\":1800,\"serviceId\":\"2015102020061234\",\"showImg\":\"http://travel.cz001.com.cn/attachement/jpg/site2/20110421/bc305bc8b0e00f19c88219.jpg\",\"summary\":\"北京是中国的政治中心\",\"updatedAt\":\"2015-10-20 20:19:07\",\"userId\":\"xmdL111k\",\"userName\":\"demo2\"},{\"createdAt\":\"2015-10-20 20:11:44\",\"destination\":\"西藏\",\"introduction\":\"西藏自治区位于青藏高原的西南部，雪域高原的人好像就为给众生祈福而生，每天念经、转经筒、磕长头，日复一日，年复一年，这就是他们的生活习惯。\",\"objectId\":\"386ae6e150\",\"price\":9800,\"serviceId\":\"2015102020112347\",\"showImg\":\"http://sc.sinaimg.cn/2010/0120/201012084541.jpg\",\"summary\":\"西藏在黄土高坡\",\"updatedAt\":\"2015-10-20 20:19:13\",\"userId\":\"H4OLfffk\",\"userName\":\"demo3\"}]";

	// private String json =
	// "[{\"name\":\"Yen\",\"age\":22},{\"name\":\"Lee\",\"age\":24}]";

	public static List<Service> getResultList(String json) {
		Gson gson = new Gson();
		return gson.fromJson(json, new TypeToken<List<Service>>() {
		}.getType());

	}

}
