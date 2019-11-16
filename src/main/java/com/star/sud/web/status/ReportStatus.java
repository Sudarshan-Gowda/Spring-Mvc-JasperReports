/**
 * 
 */
package com.star.sud.web.status;

/**
 * @author Sudarshan
 *
 */
public class ReportStatus {

	// enum
	public enum STATUS {
		NEW, SUCCESS, FAILED
	}

	// Attributes
	////////////////
	private STATUS status;
	private String message;
	private String result;
	private String exception;

	public ReportStatus() {
		this.status = STATUS.NEW;
		this.result = "";
		this.exception = "";
	}

	// Properties
	////////////////
	/**
	 * @return the status
	 */
	public STATUS getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(STATUS status) {
		this.status = status;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * @return the exception
	 */
	public String getException() {
		return exception;
	}

	/**
	 * @param exception the exception to set
	 */
	public void setException(String exception) {
		this.exception = exception;
	}
}
