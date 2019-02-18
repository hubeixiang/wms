package com.sven.wms.db.configure;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.transaction.SystemException;

/**
 * @author sven
 * @date 2019/2/15 14:43
 */
@Configuration
@ConditionalOnClass({ UserTransactionManager.class, UserTransactionImp.class, JtaTransactionManager.class })
public class AtomikosSpringJPATransactionManager {

	@Configuration
	protected static class AtomikosTransactionManager {

		@Bean(initMethod = "init", destroyMethod = "close")
		public UserTransactionManager userTransactionManager() {
			UserTransactionManager userTransactionManager = new UserTransactionManager();
			userTransactionManager.setForceShutdown(true);
			return userTransactionManager;
		}

		@Bean
		public UserTransactionImp userTransactionImp() throws SystemException {
			UserTransactionImp userTransactionImp = new UserTransactionImp();
			userTransactionImp.setTransactionTimeout(300);
			return userTransactionImp;
		}

		@Bean
		@ConditionalOnBean(name = { "userTransactionManager", "userTransactionImp" })
		public JtaTransactionManager jtaTransactionManager(
				@Qualifier("userTransactionManager") UserTransactionManager userTransactionManager,
				@Qualifier("userTransactionImp") UserTransactionImp userTransactionImp) {
			JtaTransactionManager jtaTransactionManager = new JtaTransactionManager(userTransactionImp, userTransactionManager);
			return jtaTransactionManager;
		}
	}
}
