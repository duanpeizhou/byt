package cn.zectec.contraceptive.management.system.service.impl;

import cn.zectec.contraceptive.management.system.model.GetMedicineRecord;

import java.text.SimpleDateFormat;

/**
 * @author duanpeizhou on 2019-03-21 10:11.
 */
public class MedicineRecordDto {
    private static SimpleDateFormat SIMPLE = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String machineCode;
    private String agentCode = "baoqing";
    private Integer channelCode;
    private String productCode = "327";
    private String billNumber;
    private Integer quantity = 1;//数量为1
    private String time;//2019-02-13 12:00:00
    private Integer type = 1;//
    private String identity;//身份证号
    private String name;//姓名
    private String gender;//性别

    public MedicineRecordDto(GetMedicineRecord record) {
        this.machineCode = record.getMachineryEquipment().getNo();
        if (record.getCargoRoadNo() == null) {
            this.channelCode = 1;
        } else {
            this.channelCode = Integer.getInteger(record.getCargoRoadNo());
        }
        this.billNumber = record.getBillNumber().toString();
        this.time = SIMPLE.format(record.getGetMedicineDate());
        this.identity = record.getIdNumber();
        this.name = record.getName();
        this.gender = record.getSex();
    }

    public String getMachineCode() {
        return machineCode;
    }

    public String getAgentCode() {
        return agentCode;
    }

    public Integer getChannelCode() {
        return channelCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getTime() {
        return time;
    }

    public Integer getType() {
        return type;
    }

    public String getIdentity() {
        return identity;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }
}
