package mtech.irs.ekgs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mtech.irs.ekgs.util.AppContextUtils;
import mtech.irs.ekgs.util.GraphUtils;

@Configuration
public class UtilsConfiguration {

	@Bean
	public AppContextUtils.Impl appContextUtils(){
		return new AppContextUtils.Impl();
	}
	
	@Bean
	public GraphUtils.Impl graphUtils(){
		return new GraphUtils.Impl();
	}
}
