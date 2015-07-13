/**
 * 
 */
package push.api;

/**
 * @author Ryan Hu
 *
 */
public class PushParameters {

	/**
	 * 推送类型
	 */
	public static enum PushType {
		
		/**
		 * 单播
		 */
		UNICAST,
		
		/**
		 * 组播(按照filter条件筛选特定用户群, 具体请参照filter参数)
		 */
		GROUPCAST,
		
		/**
		 * 广播
		 */
		BROADCAST,
		
		/**
		 * 文件播(多个device_token可以通过文件形式批量发送)
		 */
		FILECAST,
		
		/**
		 * 通过开发者定义的alias和友盟的device_tokens进行映射,可以传入单个alias, 也可以传入文件id。具体请参照alias和file_id参数
		 */
		CUSTOMIZEDCAST;
		
		
		
		@Override
		public String toString () {
			switch (this) {
			case UNICAST:
				return "unicast";
			case GROUPCAST:
				return "groupcast";
			case BROADCAST:
				return "broadcast";
			case FILECAST:
				return "filecast";
			case CUSTOMIZEDCAST:
				return "customizedcast";
			}
			
			return "";
		}
	}
	
	
	/**
	 * android平台相关参数
	 */
	public static class Android {
		
		/**
		 * 消息类型
		 */
		public static enum DisplayType {
			
			/**
			 * 通知
			 */
			NOTIFICATION,
			
			/**
			 * 消息
			 */
			MESSAGE;
			
			
			
			@Override
			public String toString () {
				switch (this) {
				case NOTIFICATION:
					return "notification";
				case MESSAGE:
					return "message";
				}
				
				return "";
			}
		}
		
		
		/**
		 * 点击"通知/消息"的后续行为
		 */
		public static enum AfterOpen {
			
			/**
			 * 打开应用
			 */
			GO_APP,
			
			/**
			 * 跳转到URL
			 */
			GO_URL,
			
			/**
			 * 打开特定的activity
			 */
			GO_ACTIVITY,
			
			/**
			 * 用户自定义内容
			 */
			GO_CUSTOM;
			
			
			
			@Override
			public String toString () {
				switch (this) {
				case GO_APP:
					return "go_app";
				case GO_URL:
					return "go_url";
				case GO_ACTIVITY:
					return "go_activity";
				case GO_CUSTOM:
					return "go_custom";
				}
				
				return "";
			}
		}
	}
	
	
	
	/**
	 * ios平台相关参数
	 *
	 */
	public static class Ios {
		
	}
}
