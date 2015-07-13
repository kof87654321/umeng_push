/**
 * 
 */
package push.api;

import java.util.Date;

/**
 * 通用push消息接口,用于简单的跨平台消息推送,如需推送针对特定平台特点的定制消息，请使用{@link UmengAndroidApi}和{@link UmengIosApi}
 * 
 * @author Ryan Hu
 *
 */
public class UmengCommonApi {
	
	private static volatile UmengCommonApi instance;
	
	
	private UmengCommonApi () {}
	
	public static UmengCommonApi getInstance() {
		
		if (instance == null) {
			synchronized (UmengCommonApi.class) {
				if (instance == null)
					instance = new UmengCommonApi();
			}
		}
		
		return instance;
	}
	
	
	/**
	 * 向所有平台推送消息
	 * 
	 * @param target 推送目标
	 * @param title 消息标题
	 * @param text 消息内容
	 * @param expireTime 过期时间
	 * 
	 * @throws Exception
	 */
	public void send (PushTarget target, String title, String text, Date expireTime) throws Exception {
		UmengAndroidApi.getInstance().sendGoApp(target, title, title, text, null, expireTime);
		UmengIosApi.getInstance().send(target, text, expireTime);
	}
}
