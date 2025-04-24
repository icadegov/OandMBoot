package in.OAndM.core;

import lombok.Data;

/**
 * The Class ResponseJson.
 *
 * @param <S> the generic type
 * @param <T> the generic type
 */
@Data
public class BaseResponse<S, T> {
	
	private S status;
	private String message;
	private T data;
	private boolean success;
	private int total;
	
}
