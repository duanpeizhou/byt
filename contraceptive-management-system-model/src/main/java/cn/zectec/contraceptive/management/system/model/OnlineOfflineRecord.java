package cn.zectec.contraceptive.management.system.model;

import java.util.Date;

/**
 * 上线离线状态
 * @author wxy
 *
 */
@SuppressWarnings("serial")
public class OnlineOfflineRecord extends Record{
	/**
	 * 上线时间
	 */
	private Date onlineDate;
	/**
	 * 离线时间
	 */
	private Date offlineDate;
	
	public Date getOnlineDate() {
		return onlineDate;
	}
	public void setOnlineDate(Date onlineDate) {
		this.onlineDate = onlineDate;
	}
	public Date getOfflineDate() {
		return offlineDate;
	}
	public void setOfflineDate(Date offlineDate) {
		this.offlineDate = offlineDate;
	}	
}
