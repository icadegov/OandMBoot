package in.OAndM.Exception;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.api.ErrorMessage;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.server.ResponseStatusException;

import in.OAndM.DTO.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	// when a resource (e.g., a file, a database record) cannot be found. This can
	// occur due to various reasons, such as: Incorrect Resource Identifier,
	// Resource Deletion: Access Permissions:
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorMessage> handleResourceNotFoundException(ResourceNotFoundException ex) {
		logger.error("ResourceNotFoundException:" + ex.getMessage());
		ErrorMessage errorMessage = new ErrorMessage(ex.getMessage());
		return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
	}
	
	
	
	 @ExceptionHandler(ResponseStatusException.class)
	    public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException ex) {
	        logger.error("ResourceNotFoundException:" + ex.getMessage());
	        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage());
	        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
	    }
	

	// general exception thrown by the JPA implementation when an unexpected error
	// occurs. This can be due to various reasons, such as database connection
	// issues, transaction failures, or internal JPA errors.
	@ExceptionHandler(JpaSystemException.class)
	public ResponseEntity<ErrorResponse> handleJpaSystemException(JpaSystemException ex) {
		logger.error("JpaSystemException:" + ex.getMostSpecificCause().getMessage());
		// Customize error message for user-friendliness
		String errorMessage = "An unexpected error occurred while processing your request.";
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorMessage);
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	// typically thrown when a database constraint is violated, such as trying to
	// insert a duplicate record or violating a foreign key constraint
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
		logger.error("DataIntegrityViolationException:" + ex.getMessage());
		// Extract specific error message from the exception
		String errorMessage = ex.getMostSpecificCause().getMessage();
		// Customize error message for user-friendliness
		String userFriendlyMessage = "Data integrity violation: " + errorMessage;
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), userFriendlyMessage);
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	// If any validation errors are found, the framework throws a
	// MethodArgumentNotValidException.
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
		logger.error("MethodArgumentNotValidException:" + ex.getMessage());
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

	}

	// typically thrown when an arithmetic operation fails, such as division by
	// zero.
	@ExceptionHandler(ArithmeticException.class)
	public ResponseEntity<ErrorResponse> handleArithmeticException(ArithmeticException ex) {
		logger.error("ArithmeticException:" + ex.getMessage());
		// Customize error message for user-friendliness
		String errorMessage = "An error occurred while performing the calculation. Please check your input values.";
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), errorMessage);
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	// thrown when a method is called at an illegal or inappropriate time. This can
	// occur in various scenarios, such as attempting to access a resource that
	// hasn't been initialized or performing an operation on an object in an invalid
	// state.
	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<ErrorResponse> handleIllegalStateException(IllegalStateException ex) {
		logger.error("IllegalStateException:" + ex.getMessage());
		// Customize error message for user-friendliness
		String errorMessage = "An unexpected error occurred. Please try again later.";
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorMessage);
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// thrown when a method is passed an illegal or inappropriate argument. This can
	// occur in various scenarios, such as passing a null value to a method that
	// requires a non-null argument, or passing a value that is outside of the
	// expected range.
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
		logger.error("IllegalArgumentException:" + ex.getMessage());
		// Customize error message for user-friendliness
		String errorMessage = "Invalid input parameters. Please check your request.";
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), errorMessage);
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	// when a string cannot be parsed into a numeric value. This can occur when
	// trying to convert a string to an integer, double, or other numeric type.
	@ExceptionHandler(NumberFormatException.class)
	public ResponseEntity<ErrorResponse> andleNumberFormatException(NumberFormatException ex) {
		logger.error("NumberFormatException:" + ex.getMessage());
		// Customize error message for user-friendliness
		String errorMessage = "Invalid number format. Please enter a valid number.";
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), errorMessage);
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

	}

	// common runtime exception that occurs when you try to access a method or field
	// of an object reference that is null.

	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<ErrorResponse> handleNullPointerException(NullPointerException ex) {
		logger.error("NullPointerException:" + ex.getMessage());
		// Customize error message for user-friendliness
		String errorMessage = "An unexpected error occurred. Please try again later.";

		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorMessage);
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	// when a program tries to access or modify a field that does not exist in a
	// class
	@ExceptionHandler(NoSuchFieldException.class)
	public ResponseEntity<ErrorResponse> handleNoSuchFieldException(NoSuchFieldException ex) {
		logger.error("NoSuchFieldException:" + ex.getMessage());
		// Customize error message for user-friendliness
		String errorMessage = "An error occurred while accessing the requested field. Please check your request.";
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), errorMessage);
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	// thrown when a thread is interrupted while it's waiting, sleeping, or
	// otherwise occupied. (Thread Interruption:, Timeout Operations:, Asynchronous
	// Operations:)
	@ExceptionHandler(InterruptedException.class)
	public ResponseEntity<ErrorResponse> handleInterruptedException(InterruptedException ex) {
		logger.error("InterruptedException:" + ex.getMessage());
		// Customize error message for user-friendliness
		String errorMessage = "An unexpected error occurred. Please try again later.";
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorMessage);
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// when a file or directory cannot be found by a specified path
	@ExceptionHandler(FileNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleFileNotFoundException(FileNotFoundException ex) {
		logger.error("FileNotFoundException:" + ex.getMessage());
		// Customize error message for user-friendliness
		String errorMessage = "The requested file could not be found.";

		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), errorMessage);
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	// generic exception that occurs during input or output operations. This can
	// include issues with file I/O, network connections, or other input/output
	// related problems.
	@ExceptionHandler(IOException.class)
	public ResponseEntity<ErrorResponse> handleIOException(IOException ex) {
		logger.error("IOException:" + ex.getMessage());
		// Customize error message for user-friendliness
		String errorMessage = "An error occurred while processing your request. Please try again later.";
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorMessage);
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// when a class cannot be found by the Java Virtual Machine (JVM) (Missing
	// Classpath, Class Loading Issues:, Incorrect Class Name:)

	@ExceptionHandler(ClassNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleClassNotFoundException(ClassNotFoundException ex) {
		logger.error("ClassNotFoundException:" + ex.getMessage());
		// Customize error message for user-friendliness
		String errorMessage = "An error occurred while loading a required class. Please try again later.";
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorMessage);
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// ArrayIndexOutOfBoundsException : when you try to access an array element with
	// an invalid index. This typically happens when the index is either negative or
	// greater than or equal to the array's length

	@ExceptionHandler(ArrayIndexOutOfBoundsException.class)
	public ResponseEntity<ErrorResponse> handleArrayIndexOutOfBoundsException(ArrayIndexOutOfBoundsException ex) {
		logger.error("ArrayIndexOutOfBoundsException:" + ex.getMessage());
		// Customize error message for user-friendliness
		String errorMessage = "An error occurred while accessing array elements. Please check your input data.";

		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), errorMessage);
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessage> handleGenericException(Exception ex) {
		logger.error("GenericException:" + ex.getMessage());
		ErrorMessage errorMessage = new ErrorMessage("An unexpected error occurred");
		return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(APPException.class)
	public ResponseEntity<ErrorMessage> handleAppSpecific(APPException ex) {
		logger.error("APPException:" + ex.getMessage());
		ErrorMessage errorMessage = new ErrorMessage(ex.getMessage());
		return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
	}
}