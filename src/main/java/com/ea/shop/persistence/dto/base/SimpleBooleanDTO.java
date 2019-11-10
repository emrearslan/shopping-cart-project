package com.ea.shop.persistence.dto.base;

import java.io.Serializable;

public class SimpleBooleanDTO {

	private Boolean param;

	public SimpleBooleanDTO() {

	}

	public SimpleBooleanDTO(Boolean param) {
		this.param = param;
	}

	public Boolean getParam() {
		return param;
	}

	public void setParam(Boolean param) {
		this.param = param;
	}

}
