package push.api.android;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import push.android.AndroidUnicast;

/**
 * 
 * @author is_zhoufeng
 */
public class AndroidUnicastNotification {

	private static final Logger log = LoggerFactory.getLogger(AndroidUnicastNotification.class);

	private AndroidUnicast androidUnicast ;

	public AndroidUnicastNotification(AndroidUnicast androidUnicast) {
		this.androidUnicast = androidUnicast;
	}
	
	public boolean send(String deviceTokens , String displayType , String productionMode , 
			String ticker ,String title ,String text){
		boolean result = false ;
		try {
			androidUnicast.setPredefinedKeyValue("device_tokens", deviceTokens);
			androidUnicast.setPredefinedKeyValue("ticker", ticker);
			androidUnicast.setPredefinedKeyValue("title",  title);
			androidUnicast.setPredefinedKeyValue("text",   text);
			androidUnicast.setPredefinedKeyValue("after_open", "go_app");
			androidUnicast.setPredefinedKeyValue("display_type", displayType);
			androidUnicast.setPredefinedKeyValue("production_mode",productionMode);
			result = androidUnicast.send(); 
			log.info("消息发送成功[{}]",result);  
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new RuntimeException(e); 
		}
		return result ;
	}



}
