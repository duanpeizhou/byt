package cn.zectec.contraceptive.management.system.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MachineryEquipmentStatusInfo {
	private String TFactoryID;
	//发放终端类型(厂家)1-中景，2-轻工，3-戈德，4-葆青
	private long FactoryType=4;
	//在线状态{1:在线; 0:离线}
	private long ConnectionState;
	//发放机门状态{1:关闭; 0:异常}
	private long DoorState;
	//身份证读卡器状态{1:可用; 0:异常}
	private long IDDeviceState;
	//设备工作环境温度
	private String Temperature;
	//设备工作环境湿度
	private String Humidity;
	private List<Channel> Channel=new ArrayList<Channel>();
	private Date Time;
	
	public MachineryEquipmentStatusInfo() {
		super();
	}
	public MachineryEquipmentStatusInfo(String tFactoryID, long factoryType,
			long connectionState, long doorState, long iDDeviceState,
			String temperature,
			List<Channel> channel, Date time) {
		super();
		TFactoryID = tFactoryID;
		FactoryType = factoryType;
		ConnectionState = connectionState;
		DoorState = doorState;
		IDDeviceState = iDDeviceState;
		Temperature = temperature;
		Channel = channel;
		Time = time;
	}
	public String getTFactoryID() {
		return TFactoryID;
	}
	public void setTFactoryID(String tFactoryID) {
		TFactoryID = tFactoryID;
	}
	public long getFactoryType() {
		return FactoryType;
	}
	public void setFactoryType(long factoryType) {
		FactoryType = factoryType;
	}
	public long getConnectionState() {
		return ConnectionState;
	}
	public void setConnectionState(long connectionState) {
		ConnectionState = connectionState;
	}
	public long getDoorState() {
		return DoorState;
	}
	public void setDoorState(long doorState) {
		DoorState = doorState;
	}
	public long getIDDeviceState() {
		return IDDeviceState;
	}
	public void setIDDeviceState(long iDDeviceState) {
		IDDeviceState = iDDeviceState;
	}
	public String getTemperature() {
		return Temperature;
	}
	public void setTemperature(String temperature) {
		Temperature = temperature;
	}
	public List<Channel> getChannel() {
		return Channel;
	}
	public void setChannel(List<Channel> channel) {
		this.Channel = channel;
	}
	public Date getTime() {
		return Time;
	}
	public void setTime(Date time) {
		Time = time;
	}
	public String getHumidity() {
		return Humidity;
	}
	public void setHumidity(String humidity) {
		Humidity = humidity;
	}
	
}
