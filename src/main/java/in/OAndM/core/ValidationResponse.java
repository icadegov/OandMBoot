package in.OAndM.core;

import java.util.ArrayList;
import java.util.List;

public class ValidationResponse implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean valid;
	private List<String> messages;

	public ValidationResponse() {
		this.valid = true;
		this.messages = new ArrayList<String>();
	}

	public ValidationResponse(boolean valid, List<String> messages) {
		this.valid = valid;
		this.messages = messages;
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
	public void add(String message){
		this.valid = false;
		this.messages.add(message);
	}
}
