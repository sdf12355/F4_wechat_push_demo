package com.yonyou.util;
 
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.springframework.stereotype.Service;

import com.yonyou.entity.Token;
import com.yonyou.param.MyX509TrustManager;
import com.yonyou.param.wxsmallTemplate;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

@Service
public class CommonUtil {
 
	
	private static String appID = "wx4025b0e5cc6efbc2";
	private static String secret = "57e5f6d1cd2bd2688783167a58236d87";
	// 凭证获取（GET）
	public final static String token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	// 通过code换取网页授权access_token
	public final static String scope_OpenId_Url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appID
			+ "&secret=" + secret + "&code=CODE&grant_type=authorization_code";
	// 获取用户基本信息（包括UnionID机制）
	public final static String user_Info_Url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID";

	// 菜单创建（POST） 限100（次/天）
	public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

	/**
	 * 发送 https 请求
	 *
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据getToken
	 * @return JSONObject（通过 JSONObject.get(key) 的方式获取 JSON 对象的属性值）
	 */
	public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {

		JSONObject jsonObject = null;

		try {
			// 创建 SSLContext 对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述 SSLContext 对象中得到 SSLSocketFactory 对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(ssf);

			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);

			// 设置请求方式（GET/POST）
			conn.setRequestMethod(requestMethod);

			// 当 outputStr 不为 null 时，向输出流写数据
			if (null != outputStr) {
				OutputStream outputStream = conn.getOutputStream();

				// 注意编码格式
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 从输入流读取返回内容
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();

			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}

			// 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			conn.disconnect();
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (ConnectException ce) {
		} catch (Exception e) {
			
		}
		return jsonObject;
	}

	/**
	 * 获取接口访问凭证
	 *
	 * @param appid
	 *            凭证
	 * @param appsecret
	 *            密钥
	 * @return
	 */
	public static Token getToken() {
		Token token = null;
		String requestUrl = token_url.replace("APPID", appID).replace("APPSECRET", secret);
		// 发起GET请求获取凭证
		JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);

		if (null != jsonObject) {
			try {
				token = new Token();
				token.setAccessToken(jsonObject.getString("access_token"));
				token.setExpiresIn(jsonObject.getInt("expires_in"));
			} catch (JSONException e) {
				token = null;
				 
			 
			}
		}
		return token;
	}

	/**
	 * 获取接口访问凭证
	 *
	 * @param appid
	 *            凭证
	 * @param appsecret
	 *            密钥
	 * @return
	 */
	/*public static Token smallAppsGetToken() {
		Token token = null;
		String smallAPPSECRET = "10fe077b8d87e4f9bcfc73f434c4a0d6";
		String smallAPPID = "wxd5a09e9be8209928";
		String requestUrl = token_url.replace("APPID", smallAPPID).replace("APPSECRET", smallAPPSECRET);
		// 发起GET请求获取凭证
		JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);

		if (null != jsonObject) {
			try {
				token = new Token();
				token.setAccessToken(jsonObject.getString("access_token"));
				token.setExpiresIn(jsonObject.getInt("expires_in"));
			} catch (JSONException e) {
				token = null;
				 
			}
		}
		return token;
	}*/

	/**
	 * URL编码（utf-8）
	 *
	 * @param source
	 * @return
	 */
	/*public static String urlEncodeUTF8(String source) {
		String result = source;
		try {
			result = java.net.URLEncoder.encode(source, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}*/

	/**
	 * 根据内容类型判断文件扩展名
	 *
	 * @param contentType
	 *            内容类型
	 * @return
	 */
	/*public static String getFileExt(String contentType) {
		String fileExt = "";
		if ("image/jpeg".equals(contentType))
			fileExt = ".jpg";
		else if ("audio/mpeg".equals(contentType))
			fileExt = ".mp3";
		else if ("audio/amr".equals(contentType))
			fileExt = ".amr";
		else if ("video/mp4".equals(contentType))
			fileExt = ".mp4";
		else if ("video/mpeg4".equals(contentType))
			fileExt = ".mp4";
		return fileExt;
	}*/

	/**
	 * @Title: getUserInfo
	 * @Description: 获取用户信息
	 * @param: @param
	 *             openId
	 * @param: @param
	 *             accessToken
	 * @param: @return
	 * @return: JSONObject
	 */
/*	public static JSONObject getUserInfo(String openId, String accessToken) {
		String url = user_Info_Url.replace("OPENID", openId).replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = httpsRequest(url, "POST", null);

		return jsonObject;
	}*/

	/**
	 * @Title: getOpenId
	 * @Description: 根据code值获取openid
	 * @param: @param
	 *             code
	 * @param: @return
	 * @return: Map<String,Object>
	 */
/*	public static Map<String, Object> getOpenId(String code) {
		Map<String, Object> resMap = new HashMap<>();
		String openId = null;
		String accessToken = null;
		String url = scope_OpenId_Url.replace("CODE", code);
		JSONObject jsonObject = httpsRequest(url, "POST", null);
		if (null != jsonObject) {
			if (!jsonObject.containsKey("errcode")) {
				openId = jsonObject.getString("openid");
				accessToken = jsonObject.getString("access_token");
				resMap.put("openId", openId);
				resMap.put("accessToken", accessToken);
			} else {
				int errorCode = jsonObject.getInt("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				System.out.println("通过code换取网页授权失败errorCode:{" + errorCode + "},errmsg:{" + errorMsg + "}");
			}
		}
		return resMap;
	}*/

	public static boolean sendTemplateMsg(String token,
			wxsmallTemplate template) {

		boolean flag = false;

		String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", token);
		System.out.println("requestUrl:"+requestUrl);
		net.sf.json.JSONObject jsonResult = httpsRequest(requestUrl, "POST", template.toJSON());
		System.out.println("jsonResult:"+jsonResult);
		System.err.println(template.toJSON());
		if (jsonResult != null) {
			Integer errorCode = jsonResult.getInt("errcode");
			String errorMessage = jsonResult.getString("errmsg");
			if (errorCode == 0) {
				flag = true;
			} else {
				System.out.println("模板消息发送失败:" + errorCode + "," + errorMessage);
				flag = false;
			}
		}
		return flag;

	}

	/**
	 * 将 1467341232351 转换为 指定格式 "yyyy-MM-dd HH:mm:ss.SSS"
	 * 
	 * @param time
	 *            "1467342217645"
	 * @param pattern
	 *            "yyyy-MM-dd HH:mm:ss.SSS"
	 */

	/*public static String parseLongTime2String(Long time, String pattern) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		return new SimpleDateFormat(pattern).format(calendar.getTime());
	}*/

	 
 
}