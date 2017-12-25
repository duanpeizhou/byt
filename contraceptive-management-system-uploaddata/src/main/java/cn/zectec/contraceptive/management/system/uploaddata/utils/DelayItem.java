package cn.zectec.contraceptive.management.system.uploaddata.utils;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayItem<T> implements Delayed{
	
	private T t;
	private long id;
	private long stayEndTime;
	private long stayTime;
	
	
	public DelayItem(T t, long id,  long stayTime) {
		super();
		this.id = id;
		this.t = t;
		this.stayEndTime = stayTime*60*1000+System.currentTimeMillis();
		this.stayTime = stayTime;
	}

	@Override
	public int compareTo(Delayed o) {
		return (int)(this.stayEndTime-(((DelayItem<?>)o).stayEndTime));
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
			return this.id == (((DelayItem<?>)obj).getId());
		}
		return super.equals(obj);
	}
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}

	public long getStayTime() {
		return stayTime;
	}

	public void setStayTime(long stayTime) {
		this.stayTime = stayTime;
	}
}
