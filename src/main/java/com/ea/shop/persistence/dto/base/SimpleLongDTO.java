package com.ea.shop.persistence.dto.base;

import java.io.Serializable;

public class SimpleLongDTO {

	private Long param;

	public SimpleLongDTO() {

	}

	public SimpleLongDTO(Long param) {
		this.param = param;
	}

	public Long getParam() {
		return param;
	}

	public void setParam(Long param) {
		this.param = param;
	}

}
