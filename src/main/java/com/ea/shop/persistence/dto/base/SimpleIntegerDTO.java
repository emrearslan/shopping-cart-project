package com.ea.shop.persistence.dto.base;

import java.io.Serializable;

public class SimpleIntegerDTO {

	private Integer param;

	public SimpleIntegerDTO() {

	}

	public SimpleIntegerDTO(Integer param) {
		this.param = param;
	}

	public Integer getParam() {
		return param;
	}

	public void setParam(Integer param) {
		this.param = param;
	}

}
