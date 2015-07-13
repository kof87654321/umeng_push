package push.api;

import java.util.Date;
import java.util.Map;

/**
 * IOS平台的push消息发送接口
 * 
 * @author Ryan Hu
 *
 */
public class UmengIosApi {
	
	private static volatile UmengIosApi instance;
	
	private UmengIosApi () {}
	
	public static UmengIosApi getInstance() {
		
		if (instance == null) {
			synchronized (UmengIosApi.class) {
				if (instance == null) 
					instance = new UmengIosApi();
			}
		}
		
		return instance;
	}
	
	/**
	 * 向IOS设备发送一条消息
	 * 
	 * @param target 推送目标
	 * @param alert 消息内容
	 * @param expireTime 过期时间
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean send (PushTarget target, String alert, Date expireTime) throws Exception {
		
		return send(target, alert, 0, null, expireTime);
	}
	
	/**
	 * 向IOS设备发送一条消息
	 * 
	 * @param target 推送目标
	 * @param alert 消息内容
	 * @param badge 应用程序图标上显示的数字
	 * @param extras 自定义参数
	 * @param expireTime 过期时间
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean send (PushTarget target, String alert, int badge, Map<String, String> extras, Date expireTime) throws Exception {
		
		IosNotificationBuilder builder = new IosNotificationBuilder(PushSettings.IOS_APP_KEY, PushSettings.IOS_MASTER_SECRET, target, alert, badge);
		
		if (extras != null) builder.extra(extras);
		if (expireTime != null) builder.expireTime(expireTime);
		
		return builder.build().send();
	}
}
