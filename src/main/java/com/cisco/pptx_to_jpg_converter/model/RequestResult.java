package com.cisco.pptx_to_jpg_converter.model;

public class RequestResult {

	String result;
	String context;
	String code;

	public RequestResult() {

	}

	public RequestResult(String result, String context, String code) {
		this.result = result;
		this.context = context;
		this.code = code;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
