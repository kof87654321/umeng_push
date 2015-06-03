package push;

/**
 * 
 * @author is_zhoufeng
 */
public interface PushConsts {

	public static interface DisplayType{
		/**
		 * 消息
		 */
		static final String MESSAGE = "message";
		/**
		 * 通知
		 */
		static final String NOTIFICATION = "notification";
	}
	
	public static interface ProductionMode{
		/**
		 * 测试环境
		 */
		static final String TEST = "false";
		/**
		 * 生产环境
		 */
		static final String PRODUCTION = "true";
		
	}
	
	
}
