package com.ea.shop.persistence.dto.base;

import java.io.Serializable;

public class SimpleStringDTO {

	private String param;

	public SimpleStringDTO() {

	}

	public SimpleStringDTO(String param) {
		this.param = param;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

}
