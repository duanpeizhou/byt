package cn.zectec.contraceptive.management.system.repository.listener;

public interface DataChangeHandler<T> {
	public void doAfterSave(T t);
	
	public void doAfterUpdate(T t);
}
