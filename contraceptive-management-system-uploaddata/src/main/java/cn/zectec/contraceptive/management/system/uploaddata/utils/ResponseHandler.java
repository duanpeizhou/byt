package cn.zectec.contraceptive.management.system.uploaddata.utils;

public interface ResponseHandler<K,T> {
	public void handle(K k,T t);

}
