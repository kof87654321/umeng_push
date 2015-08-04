package push.api;

import java.util.Date;
import java.util.Map;

import push.PushResult;
import push.api.PushParameters.Android.AfterOpen;
import push.api.PushParameters.Android.DisplayType;

/**
 * 安卓平台的push消息发送接口
 * 
 * @author Ryan Hu
 *
 */
public class UmengAndroidApi {
	
	private static volatile UmengAndroidApi instance;
	
	private UmengAndroidApi () {}
	
	public static UmengAndroidApi getInstance() {
		
		if (instance == null) {
			synchronized (UmengAndroidApi.class) {
				if (instance == null)
					instance = new UmengAndroidApi();
			}
		}
		
		return instance;
	}
	

	/**
	 * 向安卓设备发送一条消息，点击后打开应用程序
	 * 
	 * @param target 推送目标
	 * @param displayType 显示类型 message/notification
	 * @param ticker 手机状态栏标题
	 * @param title 通知标题
	 * @param text 通知内容
	 * @param expireTime 过期时间
	 * 
	 * @return 成功/失败
	 * 
	 * @throws Exception
	 */
	public PushResult sendGoApp(PushTarget target, String ticker, String title, String text, Map<String, String> extras, Date expireTime) throws Exception {
		
		AndroidNotificationBuilder builder = new AndroidNotificationBuilder(
				PushSettings.ANDROID_APP_KEY, PushSettings.ANDROID_MASTER_SECRET, target, DisplayType.NOTIFICATION, ticker, title, text, AfterOpen.GO_APP);
		
		if (extras != null) builder.extra(extras);
		if (expireTime != null) builder.expireTime(expireTime);
		
		return builder.build().send();
	}

	/**
	 * 向安卓设备发送一条消息，点击后打开一个网址
	 * 
	 * @param target 推送目标
	 * @param displayType 显示类型 message/notification
	 * @param ticker 手机状态栏标题
	 * @param title 通知标题
	 * @param text 通知内容
	 * @param url 要打开的网址
	 * @param expireTime 过期时间
	 * 
	 * @return 成功/失败
	 * 
	 * @throws Exception
	 */
	public PushResult sendGoUrl (PushTarget target, String ticker, String title, String text, String url, Map<String, String> extras, Date expireTime) throws Exception {
		
		AndroidNotificationBuilder builder = new AndroidNotificationBuilder(
				PushSettings.ANDROID_APP_KEY, PushSettings.ANDROID_MASTER_SECRET, target, DisplayType.NOTIFICATION, ticker, title, text);
		
		if (extras != null) builder.extra(extras);
		if (expireTime != null) builder.expireTime(expireTime);
		
		return builder.goUrl(url).build().send();
	}
	
	/**
	 * 向安卓设备发送一条消息，点击后跳到一个指定的安卓程序的界面
	 * 
	 * @param target 推送目标
	 * @param displayType 显示类型 message/notification
	 * @param ticker 手机状态栏标题
	 * @param title 通知标题
	 * @param text 通知内容
	 * @param activity 安卓程序的界面名称
	 * @param expireTime 过期时间
	 * 
	 * @return 成功/失败
	 * 
	 * @throws Exception
	 */
	public PushResult sendGoActivity (PushTarget target, String ticker, String title, String text, String activity, Map<String, String> extras, Date expireTime) throws Exception {
		
		AndroidNotificationBuilder builder = new AndroidNotificationBuilder(
				PushSettings.ANDROID_APP_KEY, PushSettings.ANDROID_MASTER_SECRET, target, DisplayType.NOTIFICATION, ticker, title, text);
		
		if (extras != null) builder.extra(extras);
		if (expireTime != null) builder.expireTime(expireTime);
		
		return builder.goActivity(activity).build().send();
	}
	
	
	/**
	 * 向安卓设备发送一条自定义消息
	 * 
	 * @param target 推送目标
	 * @param displayType 显示类型 message/notification
	 * @param ticker 手机状态栏标题
	 * @param title 通知标题
	 * @param text 通知内容
	 * @param custom 自定义消息
	 * @param expireTime 过期时间
	 * 
	 * @return 成功/失败
	 * 
	 * @throws Exception
	 */
	public PushResult sendGoCustom (PushTarget target, String ticker, String title, String text, String custom, Date expireTime) throws Exception {
		
		AndroidNotificationBuilder builder = new AndroidNotificationBuilder(
				PushSettings.ANDROID_APP_KEY, PushSettings.ANDROID_MASTER_SECRET, target, DisplayType.MESSAGE, ticker, title, text);
		
		if (expireTime != null) builder.expireTime(expireTime);
		
		return builder.goCustom(custom).build().send();
	}
	
	
	/**
	 * 向安卓设备发送一条自定义消息(状态栏不显示通知)
	 * 
	 * @param target 推送目标
	 * @param custom 自定义消息
	 * @param expireTime 过期时间
	 * 
	 * @return 成功/失败
	 * 
	 * @throws Exception
	 */
	public PushResult sendGoCustomMessage (PushTarget target, String custom, Date expireTime) throws Exception {
		
		AndroidNotificationBuilder builder = new AndroidNotificationBuilder(
				PushSettings.ANDROID_APP_KEY, PushSettings.ANDROID_MASTER_SECRET, target);
		
		if (expireTime != null) builder.expireTime(expireTime);
		
		return builder.goCustomMessage(custom).build().send();
	}
}
