package in.OAndM.DTO;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
	private Date timestamp;
	private String message;
	private String details;

	public ErrorResponse(Date timestamp, String message, String details) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}

	public ErrorResponse(int value, String errorMessage) {
	 this.message=errorMessage;
	}

}