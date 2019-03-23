package cn.zectec.contraceptive.management.system.service.impl;

import cn.zectec.contraceptive.management.system.model.Aisle;
import cn.zectec.contraceptive.management.system.model.MachineryEquipment;
import cn.zectec.contraceptive.management.system.model.MachineryEquipmentStateInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author duanpeizhou on 2019-03-22 21:18.
 */
public class MachineInfoDto {
    private String machineCode;
    private String agentCode = "BJBQ";
    private String location = "";
    private int connectionStatus;//1=正常，2=离线
    private int machineStatus;//1=正常，2=故障
    private int stockStatus;//1.正常 2.缺货
    private List<ChannelDto> channels = new ArrayList<>();

    public MachineInfoDto() {
    }

    public MachineInfoDto(MachineryEquipment me) {
        this.machineCode = me.getNo();
        MachineryEquipmentStateInfo state = me.getMachineryEquipmentState();
        if (state.isConnectionState()) {
            this.connectionStatus = 1;
            this.machineStatus = 1;
        } else {
            this.connectionStatus = 2;
            this.machineStatus = 2;
        }
        this.stockStatus = state.stockOut ? 2 : 1;
        List<Aisle> aisles = me.getAisles();
        int index = 1;
        for (Aisle aisle : aisles) {
            ChannelDto dto = new ChannelDto();
            dto.setIndex(index++);
            dto.setQuantity(aisle.getNum());
            dto.setStatus(aisle.getStockout() ? 2 : 1);
            channels.add(dto);
        }
    }


    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getConnectionStatus() {
        return connectionStatus;
    }

    public void setConnectionStatus(int connectionStatus) {
        this.connectionStatus = connectionStatus;
    }

    public int getMachineStatus() {
        return machineStatus;
    }

    public void setMachineStatus(int machineStatus) {
        this.machineStatus = machineStatus;
    }

    public int getStockStatus() {
        return stockStatus;
    }

    public void setStockStatus(int stockStatus) {
        this.stockStatus = stockStatus;
    }

    public List<ChannelDto> getChannels() {
        return channels;
    }

    public void setChannels(List<ChannelDto> channels) {
        this.channels = channels;
    }
}
