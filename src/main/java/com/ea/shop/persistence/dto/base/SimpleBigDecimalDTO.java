package com.ea.shop.persistence.dto.base;

import java.math.BigDecimal;

public class SimpleBigDecimalDTO {

    private BigDecimal param;

    public SimpleBigDecimalDTO() {

    }

    public SimpleBigDecimalDTO(BigDecimal param) {
        this.param = param;
    }

    public BigDecimal getParam() {
        return param;
    }

    public void setParam(BigDecimal param) {
        this.param = param;
    }

}
