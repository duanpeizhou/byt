package cn.zectec.contraceptive.management.system.utils.export;

public class Filed<T> {
	private String name;
	private String filedName;
	private Formatter<T> formaterr;
	private int width = 150;
	
	public Filed(String name, String filedName, int width) {
		super();
		this.name = name;
		this.filedName = filedName;
		this.width = width;
	}
	public Filed(String name, String filedName) {
		super();
		this.name = name;
		this.filedName = filedName;
	}
	public Filed(String name, String filedName, Formatter<T> formaterr) {
		super();
		this.name = name;
		this.filedName = filedName;
		this.formaterr = formaterr;
	}
	public Filed() {
		super();
	}
	public Filed(String name, String filedName, Formatter<T> formaterr,
			int width) {
		super();
		this.name = name;
		this.filedName = filedName;
		this.formaterr = formaterr;
		this.width = width;
	}
	public String getFiledName() {
		return filedName;
	}
	public void setFiledName(String filedName) {
		this.filedName = filedName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public Formatter<T> getFormaterr() {
		return formaterr;
	}
	public void setFormaterr(Formatter<T> formaterr) {
		this.formaterr = formaterr;
	}
}
