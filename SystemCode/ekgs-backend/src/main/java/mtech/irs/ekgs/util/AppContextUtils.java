package mtech.irs.ekgs.util;

import javax.annotation.PostConstruct;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

abstract public class AppContextUtils {
	
	private static Impl impl;
	
	public static ApplicationContext getContext() {
		return impl.getApplicationContext();
	}

	public static <T> T getBean(Class<T> beanType) {
		return getContext().getBean(beanType);
	}
	
	public static class Impl implements ApplicationContextAware{

		private ApplicationContext applicationContext;
		
		@Override
		public void setApplicationContext(ApplicationContext applicationContext) {
			this.applicationContext = applicationContext;
		}
		public ApplicationContext getApplicationContext() {
			return applicationContext;
		}
		
		@PostConstruct
		public void init() {
			impl = this;
		}
	}
}
