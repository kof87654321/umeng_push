/**
 * 
 */
package push.api;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import push.UmengNotification;

/**
 * @author Ryan Hu
 *
 */
public abstract class NotificationBuilder {

	//----------  必填 ------------------
	
	private String appKey;
	private String secret;
	private String timestamp;
	private PushTarget target;
	
	//----------------------------------
	
	private Date expireTime;
	private boolean productionMode = PushSettings.isProduct;
	private String description;
	private String thirdpartyId;
	

	
	public NotificationBuilder (String appKey, String secret, PushTarget target) {
		this.appKey = appKey;
		this.secret = secret;
		this.target = target;
		this.timestamp = Integer.toString((int) (System.currentTimeMillis() / 1000));
	}
	
	protected abstract UmengNotification createNotification ();
	
	private String validationTokenAndroid(String appKey, String secret, String timestamp) {
		return DigestUtils.md5Hex(appKey.toLowerCase() + secret.toLowerCase() + timestamp);
	}
	
	public UmengNotification build () throws Exception {
		
		UmengNotification notification = createNotification();
		
		notification.setPredefinedKeyValue("appkey", appKey);
		notification.setPredefinedKeyValue("timestamp", timestamp);
		notification.setPredefinedKeyValue("validation_token", validationTokenAndroid(appKey, secret, timestamp));
		
		if (expireTime != null) notification.setPredefinedKeyValue("expire_time", DateFormatUtils.format(expireTime, "yyyy-MM-dd HH:mm:ss"));
		if (description != null) notification.setPredefinedKeyValue("description", description);
		if (thirdpartyId != null) notification.setPredefinedKeyValue("thirdparty_id", thirdpartyId);
		notification.setPredefinedKeyValue("production_mode", productionMode);
		
		return target.prepare(notification);
	}
	
	/**
	 * 可选 消息过期时间,其值不可小于发送时间,默认为3天后过期
	 */
	public NotificationBuilder expireTime (Date expireTime) {
		this.expireTime = expireTime;
		return this;
	}
	
	/**
	 * 可选 正式/测试模式。测试模式下，只会将消息发给测试设备，
	 * 测试设备需要到web上添加，
	 * Android: 测试设备属于正式设备的一个子集，
	 * iOS: 测试模式对应APNs的开发环境(sandbox),正式模式对应APNs的正式环境(prod), 正式、测试设备完全隔离。
	 */
	public NotificationBuilder productionMode (boolean productionMode) {
		this.productionMode = productionMode;
		return this;
	}
	
	/**
	 * 可选 发送消息描述，建议填写
	 */
	public NotificationBuilder description (String description) {
		this.description = description;
		return this;
	}
	
	/**
	 * 可选 开发者自定义消息标识ID, 开发者可以为同一批发送的消息提供同一个thirdparty_id, 便于后期合并统计数据
	 */
	public NotificationBuilder thirdpartyId (String thirdpartyId) {
		this.thirdpartyId = thirdpartyId;
		return this;
	}
}
