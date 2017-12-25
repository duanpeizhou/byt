package cn.zectec.contraceptive.management.system.model;

import java.util.Date;

/**
 * 日志类
 * @author wxy
 *
 */
@SuppressWarnings("serial")
public class Log extends Base{
	public static enum LogType{
		LoginSuccess,LoginFailure,ExceptionOperation,LogoutOperation,/**越权操作*/UnauthorizedOperation,OtherOperation
	}
	private String title;
	private String titleDescription;
	private String uri;
	private Date operationDate;
	private String ip;
	private String username;
	private LogType logType;
	private Long managerID;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitleDescription() {
		return titleDescription;
	}
	public void setTitleDescription(String titleDescription) {
		this.titleDescription = titleDescription;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}

	public Date getOperationDate() {
		return operationDate;
	}
	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public LogType getLogType() {
		return logType;
	}
	public void setLogType(LogType logType) {
		this.logType = logType;
	}
	public Long getManagerID() {
		return managerID;
	}
	public void setManagerID(Long managerID) {
		this.managerID = managerID;
	}	
}
