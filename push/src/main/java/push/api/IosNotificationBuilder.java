/**
 * 
 */
package push.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import push.IOSNotification;
import push.UmengNotification;

/**
 * @author Ryan Hu
 *
 */
public class IosNotificationBuilder extends NotificationBuilder {

	//------------	必填		--------------

	private String alert;
	private int badge;

	//----------------------------------
	
	/**
	 * 可选 
	 * 注意：ios8才支持该字段
	 */
	private int category;
	private String sound;
	private String contentAvailable;
	
	/**
	 * 其他参数
	 */
	private HashMap<String, String> extra = new HashMap<String , String>();
	
	
	
	public IosNotificationBuilder (String appKey, String secret, PushTarget target, String alert) {
		super(appKey, secret, target);
		this.alert = alert;
	}
	
	public IosNotificationBuilder (String appKey, String secret, PushTarget target, String alert, int badge) {
		this(appKey, secret, target, alert);
		this.badge = badge;
	}
	
	@Override
	protected UmengNotification createNotification () {
		return new IOSNotification() {};
	}
	
	@Override
	public UmengNotification build () throws Exception {
		
		IOSNotification notification = (IOSNotification)super.build();
		
		notification.setPredefinedKeyValue("alert", alert);
		notification.setPredefinedKeyValue("badge", badge);
		
		if (category > 0) notification.setPredefinedKeyValue("category", category);
		if (sound != null) notification.setPredefinedKeyValue("sound", sound);
		if (contentAvailable != null) notification.setPredefinedKeyValue("contentAvailable", contentAvailable);
		
		Set<Entry<String, String>> entries = extra.entrySet();
		for (Entry<String, String> entry : entries) {
			notification.setCustomizedField(entry.getKey(), entry.getValue());
		}
		
		return notification;
	}
	
	public IosNotificationBuilder category (int category) {
		this.category = category;
		return this;
	}
	
	public IosNotificationBuilder sound (String sound) {
		this.sound = sound;
		return this;
	}
	
	public IosNotificationBuilder contentAvailable (String contentAvailable) {
		this.contentAvailable = contentAvailable;
		return this;
	}
	
	public IosNotificationBuilder extra (String key, String value) {
		extra.put(key, value);
		return this;
	}
	
	public IosNotificationBuilder extra (Map<String, String> extras) {
		extra.putAll(extras);
		return this;
	}
}
