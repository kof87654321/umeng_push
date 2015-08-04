package push;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashSet;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import push.api.PushSettings;

public abstract class UmengNotification {
	private static Logger log = LoggerFactory.getLogger(UmengNotification.class);
	// This JSONObject is used for constructing the whole request string.
	protected final JSONObject rootJson = new JSONObject();
	
	// This object is used for sending the post request to Umeng
	protected HttpClient client = new DefaultHttpClient();
	
	// The host
	protected static final String host = "http://msg.umeng.com";
	
	// The upload path
	protected static final String uploadPath = "/upload";
	
	// The post path
	protected static final String postPath = "/api/send";
	
	// The user agent
	protected final String USER_AGENT = "Mozilla/5.0";
	
	// Keys can be set in the root level
	protected static final HashSet<String> ROOT_KEYS = new HashSet<String>(Arrays.asList(new String[]{
			"appkey", "timestamp", "validation_token", "type", "device_tokens", "alias", "alias_type", "file_id", 
			"filter", "production_mode", "feedback", "description", "thirdparty_id"}));
	
	// Keys can be set in the policy level
	protected static final HashSet<String> POLICY_KEYS = new HashSet<String>(Arrays.asList(new String[]{
			"start_time", "expire_time", "max_send_num"
	}));
	
	// Set predefined keys in the rootJson, for extra keys(Android) or customized keys(IOS) please 
	// refer to corresponding methods in the subclass.
	public abstract boolean setPredefinedKeyValue(String key, Object value) throws Exception;
	
	public PushResult send() throws Exception
	{
		PushResult result = new PushResult();
		String url = host + postPath;
		HttpPost post = new HttpPost(url);
		post.setHeader("User-Agent", USER_AGENT);
		StringEntity se = new StringEntity(rootJson.toString(), "UTF-8");
		post.setEntity(se);
		// Send the post request and get the response
		HttpResponse response = client.execute(post);
		int status = response.getStatusLine().getStatusCode();
		log.debug("Response Code : {}", status);
//		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
//		StringBuilder result = new StringBuilder();
//		String line = "";
//		while ((line = rd.readLine()) != null)
//		{
//			result.append(line);
//		}
		String responseHtml = EntityUtils.toString(response.getEntity(), Charset.forName("utf-8"));
		log.info(responseHtml);
		log.info("PushSettings.isProduct:{}" , PushSettings.isProduct); 
		result.setStatus(status); 
		result.setResponseMessage(responseHtml);
		if (status == 200)
		{
			log.info("Notification sent successfully."+ status +" responseHtml:" + responseHtml );
			result.setSuccess(true);
		} else
		{
			log.warn("Failed to send the notification! status:"+ status +" responseHtml:" + responseHtml );
			result.setSuccess(false);
		}
		return result ;
	}
	
	
}
