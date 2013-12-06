package im.dadoo.dadooauth.dto;

public class DTException {

	private String message;
	private String url;
	private String method;
	private Integer status;
	private Integer code;
	
	public DTException() {}
	
	public DTException(Integer status, Integer code, String url, String method, String message) {
		this.status = status;
		this.code = code;
		this.url = url;
		this.method = method;
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
	
}
