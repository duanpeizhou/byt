package cn.zectec.contraceptive.management.system.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Base implements Serializable, Cloneable {
	/**
	 * id 主键
	 */
	private long id;
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public Object clone() {
		Object obj = null;
		try {
			obj = super.clone();
		} catch (CloneNotSupportedException e) {
		}
		return obj;
	}

}
