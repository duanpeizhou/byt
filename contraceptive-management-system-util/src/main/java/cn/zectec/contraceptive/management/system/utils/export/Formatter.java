package cn.zectec.contraceptive.management.system.utils.export;

public interface Formatter<T> {
	public String formatter(T t,int index,Filed<T> filed);
}
