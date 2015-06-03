package push.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import push.android.AndroidUnicast;
import push.api.android.AndroidUnicastNotification;

/**
 * 
 * @author is_zhoufeng
 */
public class AndroidNotificationBuilder {
	
	private static final Logger log = LoggerFactory.getLogger(AndroidNotificationBuilder.class);
	
	private String appkey ;
	private String appMasterSecret ;
	
	public void setAppkey(String appkey){
		this.appkey = appkey ;
	}
	
	public void sestappMasterSecret(String appMasterSecret){
		this.appMasterSecret = appMasterSecret ;
	}
	
	
	public AndroidUnicastNotification buildUnicastNotification(){
		assertArgument();
		AndroidUnicast unicast = new AndroidUnicast();
		unicast.setAppMasterSecret(appMasterSecret);
		try {
			unicast.setPredefinedKeyValue("appkey", this.appkey);
			unicast.setPredefinedKeyValue("timestamp", Integer.toString((int)(System.currentTimeMillis() / 1000)));  
		} catch (Exception e) {
			log.error(e.getMessage() ,e);  
			throw new RuntimeException(e) ; 
		}
		return new AndroidUnicastNotification(unicast);
	}
	
	public void assertArgument(){
		if(appkey == null){
			throw new IllegalArgumentException("appkey can not be null");
		}
		if(appMasterSecret == null){
			throw new IllegalArgumentException("appMasterSecret can not be null");
		}
			
	}
	
}
