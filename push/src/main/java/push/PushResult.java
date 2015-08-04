package push;

import java.io.Serializable;

/**
 * 
 * @author is_zhoufeng
 *
 */
public class PushResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6544613632197291687L;

	private boolean success ;
	
	private int status ;
	
	private String responseMessage ;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	@Override
	public String toString() {
		return "PushResult [success=" + success + ", status=" + status
				+ ", responseMessage=" + responseMessage + "]";
	}
	
}
