package cn.zectec.contraceptive.management.system.sender.handler;

import cn.zectec.contraceptive.management.system.model.Base;


public interface ResponseHandler<K extends Base,T> {
	public void handle(K k,T t);
}
