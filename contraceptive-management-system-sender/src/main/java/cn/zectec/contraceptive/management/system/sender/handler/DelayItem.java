package cn.zectec.contraceptive.management.system.sender.handler;

import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import cn.zectec.contraceptive.management.system.model.MachineryEquipmentStateChangeRecord;

public class DelayItem implements Delayed{
	
	

	private MachineryEquipmentStateChangeRecord mesc;
	
	private long id;
	
	public long stayEndTime;
	
	
	
	public DelayItem(MachineryEquipmentStateChangeRecord mesc,long stayEndTime) {
		super();
		this.mesc = mesc;
		this.id = mesc.getMachineryEquipment().getId();
		this.stayEndTime = stayEndTime;
	}

	@Override
	public int compareTo(Delayed o) {
		return (int)(this.stayEndTime-(((DelayItem)o).stayEndTime));
	}

	@Override
	public long getDelay(TimeUnit unit) {
		return stayEndTime - System.currentTimeMillis();
	}
	@Override
	public int hashCode() {
		return (int)id;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof DelayItem){
			return this.id == (((DelayItem)obj).getId());
		}
		return super.equals(obj);
	}

	public MachineryEquipmentStateChangeRecord getMesc() {
		return mesc;
	}

	public void setMesc(MachineryEquipmentStateChangeRecord mesc) {
		this.mesc = mesc;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getStayEndTime() {
		Date d = new Date(stayEndTime);
		return d;
	}

	public void setStayEndTime(long stayEndTime) {
		this.stayEndTime = stayEndTime;
	}

}
