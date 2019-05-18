package cn.srblog.faceverification.util;

import com.baidu.aip.face.AipFace;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Date;
import java.util.HashMap;

@Slf4j
public class FaceUtil {

	public static final String APP_ID = "******";
	public static final String API_KEY = "***********************";
	public static final String SECRET_KEY = "***********************";
	private final static AipFace client = new AipFace(APP_ID, API_KEY, SECRET_KEY);
	private static String imageType = "BASE64";
	private static String groupId = "*******";

	/**
	 * 初始化参数
	 */
	static {
		client.setConnectionTimeoutInMillis(2000);
		client.setSocketTimeoutInMillis(60000);
	}

	/**
	 * 人脸注册
	 * @param token
	 * @param imageBase64
	 * @return
	 */
	public static boolean addUser(String token,String imageBase64) {
		HashMap<String, String> options = new HashMap<String, String>();
		options.put("user_info", token);
		options.put("quality_control", "NONE");
		options.put("liveness_control", "HIGH");
		JSONObject res = client.addUser(imageBase64, imageType, groupId, token, options);
		log.info(res.toString());
		if (!res.isNull("result")) {
			if (res.getString("error_msg").equals("SUCCESS")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 人脸搜索
	 * @param imageBase64
	 * @return
	 */
	public static String search(String imageBase64) {
		HashMap<String, String> options = new HashMap<String, String>();
		String token = null;
		options.put("quality_control", "NORMAL");
		options.put("liveness_control", "LOW");
		options.put("max_user_num", "1");
		JSONObject res = client.search(imageBase64, imageType, groupId, options);
         log.info(res.toString());
		if (!res.isNull("result")) {
			JSONArray list = res.getJSONObject("result").getJSONArray("user_list");
			if (list.getJSONObject(0).getDouble("score") > 85.0) {
				token = list.getJSONObject(0).getString("user_id");
			}
		}
		return token;
	}

	/**
	 * 获取用户人脸列表
	 * @param token
	 * @return
	 */
	public static int faceGetlist(String token) {
		int count = 0;
		HashMap<String, String> options = new HashMap<String, String>();
		JSONObject res = client.faceGetlist(token, groupId, options);
        log.info(res.toString());
		if (!res.isNull("result")) {
			JSONArray list = res.getJSONObject("result").getJSONArray("face_list");
			if (list != null){
				count = list.length();
			}
		}
		return count;
	}

}
