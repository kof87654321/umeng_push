/**
 * 
 */
package push.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import push.UmengNotification;
import push.api.PushParameters.PushType;

/**
 * 接受push消息的目标用户(群)
 * 
 * @author Ryan Hu
 *
 */
public class PushTarget {

	private static final byte TYPE_ALL = 1;
	private static final byte TYPE_DEVICE_TOKEN = 2;
	private static final byte TYPE_ALIAS = 3;
	private static final byte TYPE_FILTER = 4;
	private static final byte TYPE_FILE = 5;
	
	private byte type;
	
	/**
	 * 可选 设备唯一表示,当type=unicast时,必填,表示指定的单个设备
	 */
	private String deviceToken;
	
	/**
	 * 可选 当type=customizedcast时,开发者填写自己的alias,友盟根据alias进行反查找,得到对应的device_token
	 */
	private String alias;
	private String aliasType;
	
	/**
	 * 可选 当type=filecast时，开发者需要填写此参数按照文件形似来发送
	 */
	private String fileId;
	
	/**
	 * 可选 当type=groupcast时，终端用户筛选条件,如用户标签、地域、应用版本以及渠道等
	 */
	private Query query;
	
	
	
	
	private PushTarget (byte type) {
		this.type = type;
	}
	
	/**
	 * 目标为所有用户(广播)
	 */
	public static final PushTarget createBroadcast () {
		return new PushTarget(TYPE_ALL);
	}
	
	/**
	 * 目标为一台指定设备
	 * 
	 * @param deviceToken 设备唯一标识
	 */
	public static final PushTarget createUniqueDevice (String deviceToken) {
		PushTarget rec = new PushTarget(TYPE_DEVICE_TOKEN);
		rec.deviceToken = deviceToken;
		return rec;
	}
	
	/**
	 * 目标为某个特定用户(客户端为其绑定了alias和aliasType)
	 * 
	 * @param alias	与客户端约定的值
	 * @param aliasType	与客户端约定的值
	 */
	public static final PushTarget createAlias (String alias, String aliasType) {
		PushTarget rec = new PushTarget(TYPE_ALIAS);
		rec.alias = alias;
		rec.aliasType = aliasType;
		return rec;
	}
	
	/**
	 * 目标为文件中的用户列表
	 * 
	 * @param fileId 文件唯一标识ID
	 */
	public static final PushTarget createFileList (String fileId) {
		PushTarget rec = new PushTarget(TYPE_FILE);
		rec.fileId = fileId;
		return rec;
	}
	
	/**
	 * 根据条件删选出的一组用户
	 * 
	 * @param query 删选条件
	 */
	public static final PushTarget createGruop (Query query) {
		PushTarget rec = new PushTarget(TYPE_FILTER);
		rec.query = query;
		return rec;
	}
	
	
	/*package*/ UmengNotification prepare (UmengNotification notification) throws Exception {
		
		PushType pushType;
		switch (type) {
		case TYPE_ALL:
			pushType = PushType.BROADCAST;
			break;
		case TYPE_DEVICE_TOKEN:
			pushType = PushType.UNICAST;
			notification.setPredefinedKeyValue("device_tokens", deviceToken);
			break;
		case TYPE_ALIAS:
			pushType = PushType.CUSTOMIZEDCAST;
			notification.setPredefinedKeyValue("alias", alias);
			notification.setPredefinedKeyValue("alias_type", aliasType);
			break;
		case TYPE_FILTER:
			pushType = PushType.GROUPCAST;
			
			notification.setPredefinedKeyValue("filter", query.makeFilter());
			break;
		case TYPE_FILE:
			pushType = PushType.FILECAST;
			notification.setPredefinedKeyValue("file_id", fileId);
			break;
		default:
			throw new IllegalArgumentException("invalid type: " + type);
		}
		
		notification.setPredefinedKeyValue("type", pushType.toString());
		
		return notification;
	}
	
	
	
	/**
	 * 选定用户的查询条件，用于组播模式,
	 * 见{@link PushTarget#createGruop}
	 */
	public static class Query {
		
		private static final String AND = "and";
		private static final String OR = "or";
		private static final String NOT = "not";
		
		private String type;
		private JSONArray conditions = new JSONArray();
		
		private Query (String type) {
			this.type = type;
		}
		
		public static Query and () {
			return new Query(AND);
		}
		
		public static Query or () {
			return new Query(OR);
		}
		
		public static Query not () {
			return new Query(NOT);
		}
		
		/*package*/ JSONObject makeFilter () throws JSONException {
			JSONObject ret = new JSONObject();
			ret.put("where", getJson());
			return ret;
		}

		/*package*/ JSONObject getJson () throws JSONException {
			JSONObject ret = new JSONObject();
			ret.put(type, conditions);
			return ret;
		}
		
		private Query addCondition (String name, String value) throws JSONException {
			JSONObject obj = new JSONObject();
			obj.put(name, value);
			conditions.put(obj);
			return this;
		}
		
		/**
		 * 应用程序版本
		 */
		public Query appVersion (String version) throws JSONException {
			return addCondition("app_version", version);
		}
		
		/**
		 * 渠道号
		 */
		public Query channel (String channel) throws JSONException {
			return addCondition("channel", channel);
		}
		
		/**
		 * 设备型号
		 */
		public Query deviceModel (String deviceModel) throws JSONException {
			return addCondition("device_model", deviceModel);
		}
		
		/**
		 * 国家
		 */
		public Query country (String country) throws JSONException {
			return addCondition("country", country);
		}
		
		/**
		 * 省份
		 */
		public Query province (String province) throws JSONException {
			return addCondition("province", province);
		}
		
		/**
		 * 语言
		 */
		public Query language (String language) throws JSONException {
			return addCondition("language", language);
		}

		/**
		 * 标签
		 */
		public Query tag (String tag) throws JSONException {
			return addCondition("tag", tag);
		}
		
		public Query appendQuery (Query query) throws JSONException {
			conditions.put(query.getJson());
			return this;
		}
		
	}
	
	@Override
	public String toString() {
		JSONObject json = new JSONObject() ;
		try {
			json.put("type", type) ;
			json.put("deviceToken", deviceToken) ;
			json.put("alias", alias) ;
			json.put("aliasType", aliasType) ;
			json.put("fileId", fileId) ;
			if(query != null){
				json.put("query.type", query.type) ;
				json.put("query.conditions", query.conditions) ;
			}
		} catch (JSONException e) {
			try {
				json.put("error", e.getMessage()) ;
			} catch (JSONException e1) {
			}
		}
		return json.toString();
	}
}
