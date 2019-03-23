package cn.zectec.contraceptive.management.system.service.impl;

/**
 * @author duanpeizhou on 2019-03-22 21:29.
 */
public class ChannelDto {
    private Integer index;
    private String productCode="327";
    private Integer status;
    private Integer quantity;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
