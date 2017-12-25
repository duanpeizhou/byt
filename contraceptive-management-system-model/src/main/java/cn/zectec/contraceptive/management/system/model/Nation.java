package cn.zectec.contraceptive.management.system.model;

@SuppressWarnings("serial")
public class Nation extends Base {
	/**
	 * 名称
	 */
	private String name;
	private byte code;
	
	public Nation() {
		super();
	}

	public Nation(String name, byte code) {
		super();
		this.name = name;
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte getCode() {
		return code;
	}

	public void setCode(byte code) {
		this.code = code;
	}
}
