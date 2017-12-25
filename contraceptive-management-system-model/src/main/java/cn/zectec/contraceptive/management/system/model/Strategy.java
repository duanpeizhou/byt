package cn.zectec.contraceptive.management.system.model;
/**
 * 策略
 * @author wxy
 *
 */
@SuppressWarnings("serial")
public class Strategy extends Base{
	/**
	 * 常开
	 */
	public static int NORMALLY_OPEN = 0;
	/**
	 * 常闭
	 */
	public static int NORMALLY_CLOSE = 1;	
	/**
	 * 时间段内开
	 */
	public static int TIME_BUCKET = 2;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 是否在用
	 */
	public boolean used;
	
	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	/**
	 * 自动开灯
	 */
	private int turnOnLightState;
	private int turnOnBeginLightHour;
	private int turnOnEndLightHour;
	private int turnOnBeginLightMinute;
	private int turnOnEndLightMinute;
	
	/**
	 * 自动开机
	 */
	private int turnOnPowerState;
	private int turnOnBeginPowerHour;
	private int turnOnEndPowerHour;
	private int turnOnBeginPowerMinute;
	private int turnOnEndPowerMinute;
	
	/**
	 * 自动视频
	 */
	private int turnOnVedioState;
	private int turnOnBeginVedioHour;
	private int turnOnEndVedioHour;
	private int turnOnBeginVedioMinute;
	private int turnOnEndVedioMinute;
	
	/**
	 * 报警温度上下限
	 */
	private int alarmMinTemperature;
	private int alarmMaxTemperature;
	
	/**
	 * 年龄上下限
	 */
	private int minAge;
	private int maxAge;
	
	/**
	 * 管理员密码
	 */
	private String adminPassword;
	/**
	 * 电话
	 */
	private String phone;
	/**
	 * 服务器ip
	 */
	private String ip;
	/**
	 * 服务器端口
	 */
	private Integer port;
	/**
	 * 总体发放策略是否可用
	 */
	private boolean giveOutStrategyEnable;
	/**
	 * 多少时间内可领用
	 */
	private int giveOutDay;
	/**
	 * 领用数量
	 */
	private int giveOutAmount;
	/**
	 * 缺货报警数量
	 */
	private int alarmOutStockAmount;
	
	/**
	 * 默认策略
	 */
	private boolean defaultStrategy;

	public static int getNORMALLY_OPEN() {
		return NORMALLY_OPEN;
	}

	public static void setNORMALLY_OPEN(int nORMALLY_OPEN) {
		NORMALLY_OPEN = nORMALLY_OPEN;
	}

	public static int getNORMALLY_CLOSE() {
		return NORMALLY_CLOSE;
	}

	public static void setNORMALLY_CLOSE(int nORMALLY_CLOSE) {
		NORMALLY_CLOSE = nORMALLY_CLOSE;
	}

	public static int getTIME_BUCKET() {
		return TIME_BUCKET;
	}

	public static void setTIME_BUCKET(int tIME_BUCKET) {
		TIME_BUCKET = tIME_BUCKET;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTurnOnLightState() {
		return turnOnLightState;
	}

	public void setTurnOnLightState(int turnOnLightState) {
		this.turnOnLightState = turnOnLightState;
	}

	public int getTurnOnBeginLightHour() {
		return turnOnBeginLightHour;
	}

	public void setTurnOnBeginLightHour(int turnOnBeginLightHour) {
		this.turnOnBeginLightHour = turnOnBeginLightHour;
	}

	public int getTurnOnEndLightHour() {
		return turnOnEndLightHour;
	}

	public void setTurnOnEndLightHour(int turnOnEndLightHour) {
		this.turnOnEndLightHour = turnOnEndLightHour;
	}

	public int getTurnOnBeginLightMinute() {
		return turnOnBeginLightMinute;
	}

	public void setTurnOnBeginLightMinute(int turnOnBeginLightMinute) {
		this.turnOnBeginLightMinute = turnOnBeginLightMinute;
	}

	public int getTurnOnEndLightMinute() {
		return turnOnEndLightMinute;
	}

	public void setTurnOnEndLightMinute(int turnOnEndLightMinute) {
		this.turnOnEndLightMinute = turnOnEndLightMinute;
	}

	public int getTurnOnPowerState() {
		return turnOnPowerState;
	}

	public void setTurnOnPowerState(int turnOnPowerState) {
		this.turnOnPowerState = turnOnPowerState;
	}

	public int getTurnOnBeginPowerHour() {
		return turnOnBeginPowerHour;
	}

	public void setTurnOnBeginPowerHour(int turnOnBeginPowerHour) {
		this.turnOnBeginPowerHour = turnOnBeginPowerHour;
	}

	public int getTurnOnEndPowerHour() {
		return turnOnEndPowerHour;
	}

	public void setTurnOnEndPowerHour(int turnOnEndPowerHour) {
		this.turnOnEndPowerHour = turnOnEndPowerHour;
	}

	public int getTurnOnBeginPowerMinute() {
		return turnOnBeginPowerMinute;
	}

	public void setTurnOnBeginPowerMinute(int turnOnBeginPowerMinute) {
		this.turnOnBeginPowerMinute = turnOnBeginPowerMinute;
	}

	public int getTurnOnEndPowerMinute() {
		return turnOnEndPowerMinute;
	}

	public void setTurnOnEndPowerMinute(int turnOnEndPowerMinute) {
		this.turnOnEndPowerMinute = turnOnEndPowerMinute;
	}

	public int getTurnOnVedioState() {
		return turnOnVedioState;
	}

	public void setTurnOnVedioState(int turnOnVedioState) {
		this.turnOnVedioState = turnOnVedioState;
	}

	public int getTurnOnBeginVedioHour() {
		return turnOnBeginVedioHour;
	}

	public void setTurnOnBeginVedioHour(int turnOnBeginVedioHour) {
		this.turnOnBeginVedioHour = turnOnBeginVedioHour;
	}

	public int getTurnOnEndVedioHour() {
		return turnOnEndVedioHour;
	}

	public void setTurnOnEndVedioHour(int turnOnEndVedioHour) {
		this.turnOnEndVedioHour = turnOnEndVedioHour;
	}

	public int getTurnOnBeginVedioMinute() {
		return turnOnBeginVedioMinute;
	}

	public void setTurnOnBeginVedioMinute(int turnOnBeginVedioMinute) {
		this.turnOnBeginVedioMinute = turnOnBeginVedioMinute;
	}

	public int getTurnOnEndVedioMinute() {
		return turnOnEndVedioMinute;
	}

	public void setTurnOnEndVedioMinute(int turnOnEndVedioMinute) {
		this.turnOnEndVedioMinute = turnOnEndVedioMinute;
	}

	public int getAlarmMinTemperature() {
		return alarmMinTemperature;
	}

	public void setAlarmMinTemperature(int alarmMinTemperature) {
		this.alarmMinTemperature = alarmMinTemperature;
	}

	public int getAlarmMaxTemperature() {
		return alarmMaxTemperature;
	}

	public void setAlarmMaxTemperature(int alarmMaxTemperature) {
		this.alarmMaxTemperature = alarmMaxTemperature;
	}

	public int getMinAge() {
		return minAge;
	}

	public void setMinAge(int minAge) {
		this.minAge = minAge;
	}

	public int getMaxAge() {
		return maxAge;
	}

	public void setMaxAge(int maxAge) {
		this.maxAge = maxAge;
	}

	public String getAdminPassword() {
		return adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public boolean isGiveOutStrategyEnable() {
		return giveOutStrategyEnable;
	}

	public void setGiveOutStrategyEnable(boolean giveOutStrategyEnable) {
		this.giveOutStrategyEnable = giveOutStrategyEnable;
	}

	public int getGiveOutDay() {
		return giveOutDay;
	}

	public void setGiveOutDay(int giveOutDay) {
		this.giveOutDay = giveOutDay;
	}

	public int getGiveOutAmount() {
		return giveOutAmount;
	}

	public void setGiveOutAmount(int giveOutAmount) {
		this.giveOutAmount = giveOutAmount;
	}

	public int getAlarmOutStockAmount() {
		return alarmOutStockAmount;
	}

	public void setAlarmOutStockAmount(int alarmOutStockAmount) {
		this.alarmOutStockAmount = alarmOutStockAmount;
	}

	public boolean isDefaultStrategy() {
		return defaultStrategy;
	}

	public void setDefaultStrategy(boolean defaultStrategy) {
		this.defaultStrategy = defaultStrategy;
	}

	@Override
	public String toString() {
		return "Strategy [name=" + name + ", used=" + used
				+ ", turnOnLightState=" + turnOnLightState
				+ ", turnOnBeginLightHour=" + turnOnBeginLightHour
				+ ", turnOnEndLightHour=" + turnOnEndLightHour
				+ ", turnOnBeginLightMinute=" + turnOnBeginLightMinute
				+ ", turnOnEndLightMinute=" + turnOnEndLightMinute
				+ ", turnOnPowerState=" + turnOnPowerState
				+ ", turnOnBeginPowerHour=" + turnOnBeginPowerHour
				+ ", turnOnEndPowerHour=" + turnOnEndPowerHour
				+ ", turnOnBeginPowerMinute=" + turnOnBeginPowerMinute
				+ ", turnOnEndPowerMinute=" + turnOnEndPowerMinute
				+ ", turnOnVedioState=" + turnOnVedioState
				+ ", turnOnBeginVedioHour=" + turnOnBeginVedioHour
				+ ", turnOnEndVedioHour=" + turnOnEndVedioHour
				+ ", turnOnBeginVedioMinute=" + turnOnBeginVedioMinute
				+ ", turnOnEndVedioMinute=" + turnOnEndVedioMinute
				+ ", alarmMinTemperature=" + alarmMinTemperature
				+ ", alarmMaxTemperature=" + alarmMaxTemperature + ", minAge="
				+ minAge + ", maxAge=" + maxAge + ", adminPassword="
				+ adminPassword + ", phone=" + phone + ", ip=" + ip + ", port="
				+ port + ", giveOutStrategyEnable=" + giveOutStrategyEnable
				+ ", giveOutDay=" + giveOutDay + ", giveOutAmount="
				+ giveOutAmount + ", alarmOutStockAmount="
				+ alarmOutStockAmount + ", defaultStrategy=" + defaultStrategy
				+ ", getId()=" + getId() + "]";
	}
}
