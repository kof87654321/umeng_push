/**
 * 
 */
package push.api;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.time.DateFormatUtils;

import push.AndroidNotification;
import push.UmengNotification;
import push.api.PushParameters.Android.AfterOpen;
import push.api.PushParameters.Android.DisplayType;

/**
 * @author Ryan Hu
 *
 */
public class AndroidNotificationBuilder extends NotificationBuilder {

	//------------	必填		--------------
	
	private DisplayType displayType;
	private String ticker;
	private String title;
	private String text;
	private AfterOpen afterOpen = AfterOpen.GO_APP; 
	
	//----------------------------------
	
	/**
	 * 可选 默认为0，用于标识该通知采用的样式。 开发者在集成SDK时，可为不同的id指定不同的通知样式。
	 */
	private int builderId;
	
	/**
	 * 可选 状态栏图标ID, R.drawable.[smallIcon],如果没有, 默认使用应用图标，
	 * 图片要求为24*24dp的图标,或24*24px放在drawable-mdpi下，
	 * 注意四周各留1个dp的空白像素
	 */
	private String icon;
	
	/**
	 * 可选 通知栏拉开后左侧图标ID, R.drawable.[largeIcon]，
	 * 图片要求为64*64dp的图标,可设计一张64*64px放在drawable-mdpi下，
	 * 注意图片四周留空，不至于显示太拥挤
	 */
	private String largeIcon;
	
	/**
	 * 可选 通知栏大图标的URL链接。该字段的优先级大于largeIcon，
	 * 该字段要求以http或者https开头。
	 */
	private String img;
	
	//--------	 通知到达设备后的提醒方式	--------
	
	private boolean playVibrate = true;
	private boolean playLights = true;
	private boolean playSound = true;
	
	//------------------------------------------
	
	/**
	 * 可选 当"after_open"为"go_url"时，必填，
	 * 通知栏点击后跳转的URL，要求以http或者https开头  
	 */
	private String url;
	
	/**
	 * 可选 当"after_open"为"go_activity"时，必填，
	 * 通知栏点击后打开的Activity
	 */
	private String activity;
	
	/**
	 * 可选 display_type=message,
	 * 或者display_type=notification且"after_open"为"go_custom"时，
	 * 该字段必填。用户自定义内容, 可以为字符串或者JSON格式。
	 */
	private String custom;
	
	/**
	 * 可选 定时发送时间,默认为立即发送.发送时间不能小于当前时间，
	 * 格式: "YYYY-MM-DD hh:mm:ss"
	 * 
	 * 注意, start_time只对broadcast,groupcast以及customizedcast且file_id不为空的情况生效,对单播不生效
	 */
	private Date startTime;
	
	/**
	 * 可选 发送限速，每秒发送的最大条数。整数值，
	 * 开发者发送的消息体如果有请求自己服务器的资源，可以考虑此参数。
	 */
	private int maxSendNum;
	
	/**
	 * 其他参数
	 */
	private HashMap<String, String> extra = new HashMap<String, String>();
	
	
	public AndroidNotificationBuilder (String appKey, String secret, PushTarget target) {
		super(appKey, secret, target);
	}
	
	public AndroidNotificationBuilder (String appKey, String secret, PushTarget target, DisplayType displayType, String ticker, String title, String text) {
		super(appKey, secret, target);
		this.displayType = displayType;
		this.ticker = ticker;
		this.title = title;
		this.text = text;
	}
	
	public AndroidNotificationBuilder (String appKey, String secret, PushTarget target, DisplayType displayType, String ticker, String title, String text, AfterOpen afterOpen) {
		this(appKey, secret, target, displayType, ticker, title, text);
		this.afterOpen = afterOpen;
	}
	
	@Override
	protected UmengNotification createNotification () {
		return new AndroidNotification() {};
	}
	
	@Override
	public UmengNotification build () throws Exception {
		
		AndroidNotification notification = (AndroidNotification)super.build();
		
		notification.setPredefinedKeyValue("display_type", displayType.toString());
		notification.setPredefinedKeyValue("ticker", ticker);
		notification.setPredefinedKeyValue("title", title);
		notification.setPredefinedKeyValue("text", text);
		notification.setPredefinedKeyValue("after_open", afterOpen);

		if (builderId > 0) notification.setPredefinedKeyValue("builder_id", builderId);
		if (icon != null) notification.setPredefinedKeyValue("icon", icon);
		if (largeIcon != null) notification.setPredefinedKeyValue("largeIcon", largeIcon);
		if (img != null) notification.setPredefinedKeyValue("img", img);
		if (afterOpen == AfterOpen.GO_URL && url != null) notification.setPredefinedKeyValue("url", url);
		if (afterOpen == AfterOpen.GO_ACTIVITY && activity != null) notification.setPredefinedKeyValue("activity", activity);
		if (custom != null) notification.setPredefinedKeyValue("custom", custom);
		if (startTime != null) notification.setPredefinedKeyValue("start_time", DateFormatUtils.format(startTime, "yyyy-MM-dd HH:mm:ss"));
		if (maxSendNum > 0) notification.setPredefinedKeyValue("max_send_num", maxSendNum);
		notification.setPredefinedKeyValue("play_vibrate", playVibrate);
		notification.setPredefinedKeyValue("play_lights", playLights);
		notification.setPredefinedKeyValue("play_sound", playSound);
		
		Set<Entry<String, String>> entries = extra.entrySet();
		for (Entry<String, String> entry : entries) {
			notification.setExtraField(entry.getKey(), entry.getValue());
		}
		
		return notification;
	}
	
	public AndroidNotificationBuilder afterOpen (AfterOpen afterOpen) {
		this.afterOpen = afterOpen;
		return this;
	}
	
	public AndroidNotificationBuilder builderId (int builderId) {
		this.builderId = builderId;
		return this;
	}
	
	public AndroidNotificationBuilder icon (String icon) {
		this.icon = icon;
		return this;
	}
	
	public AndroidNotificationBuilder largeIcon (String largeIcon) {
		this.largeIcon = largeIcon;
		return this;
	}
	
	public AndroidNotificationBuilder img (String img) {
		this.img = img;
		return this;
	}
	
	public AndroidNotificationBuilder playVibrate (boolean playVibrate) {
		this.playVibrate = playVibrate;
		return this;
	}
	
	public AndroidNotificationBuilder playLights (boolean playLights) {
		this.playLights = playLights;
		return this;
	}
	
	public AndroidNotificationBuilder playSound (boolean playSound) {
		this.playSound = playSound;
		return this;
	}
	
	/**
	 * 用户点击消息后跳转到一个指定的url
	 */
	public AndroidNotificationBuilder goUrl (String url) {
		this.afterOpen = AfterOpen.GO_URL;
		this.url = url;
		return this;
	}
	
	/**
	 * 用户点击消息后跳转到一个指定的activity
	 */
	public AndroidNotificationBuilder goActivity (String activity) {
		this.afterOpen = AfterOpen.GO_ACTIVITY;
		this.activity = activity;
		return this;
	}
	
	public AndroidNotificationBuilder goCustom (String custom) {
		this.displayType = DisplayType.NOTIFICATION;
		this.afterOpen = AfterOpen.GO_CUSTOM;
		this.custom = custom;
		return this;
	}
	
	public AndroidNotificationBuilder goCustomMessage (String custom) {
		this.displayType = DisplayType.MESSAGE;
		this.custom = custom;
		return this;
	}
	
	/**
	 * 可选 定时发送时间，默认为立即发送。发送时间不能小于当前时间。
	 * 注意, start_time只对broadcast，groupcast以及customizedcast且file_id不为空的情况生效, 对单播不生效。
	 */
	public AndroidNotificationBuilder startTime (Date startTime) {
		if (startTime.getTime() < System.currentTimeMillis()) throw new IllegalArgumentException("startTime must be after current time");
		this.startTime = startTime;
		return this;
	}
	
	/**
	 * 可选 发送限速，每秒发送的最大条数。整数值。
	 * 开发者发送的消息体如果有请求自己服务器的资源，可以考虑此参数。
	 */
	public AndroidNotificationBuilder maxSendNum (int maxSendNum) {
		if (maxSendNum < 0) throw new IllegalArgumentException("maxSendNum must be positive");
		this.maxSendNum = maxSendNum;
		return this;
	}
	
	public AndroidNotificationBuilder extra (String key, String value) {
		extra.put(key, value);
		return this;
	}
	
	public AndroidNotificationBuilder extra (Map<String, String> extras) {
		extra.putAll(extras);
		return this;
	}
	
}
