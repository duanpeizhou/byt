package cn.zectec.contraceptive.management.system.collector.handler;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import cn.zectec.contraceptive.management.system.sdk.Context;
import cn.zectec.contraceptive.management.system.sdk.MessageReceivedHandler;
import cn.zectec.contraceptive.management.system.sdk.message.Request;

public class JPASessionManagerHandler implements MessageReceivedHandler{
	@Autowired
	private EntityManagerFactory entityManagerFactory;
	private static Logger logger =Logger.getLogger(JPASessionManagerHandler.class);

	public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public void setEntityManagerFactory(
			EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	public void handle(Context context, Request request){
		boolean participate = false;
		EntityManager em = null;
		if (TransactionSynchronizationManager.hasResource(entityManagerFactory)) {
			// Do not modify the EntityManager: just set the participate flag.
			participate = true;
		} else {
			try {
				em = entityManagerFactory.createEntityManager();
				TransactionSynchronizationManager.bindResource(entityManagerFactory, new EntityManagerHolder(em));
			} catch (PersistenceException ex) {
				throw new DataAccessResourceFailureException("Could not create JPA EntityManager", ex);
			}
		}
		try {
			if(request !=null)context.doNextHandler();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("JPASessionManagerHandler报错了 ==》"+ request);
			throw new RuntimeException(e);
		} finally {
			if (!participate) {
				EntityManagerHolder emHolder = (EntityManagerHolder) TransactionSynchronizationManager.unbindResource(entityManagerFactory);
				EntityManagerFactoryUtils.closeEntityManager(emHolder.getEntityManager());
			}
			
		}

	}

}
