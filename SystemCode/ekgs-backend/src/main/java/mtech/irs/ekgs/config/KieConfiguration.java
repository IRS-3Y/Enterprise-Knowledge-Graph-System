package mtech.irs.ekgs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mtech.irs.ekgs.kie.KieSystem;
import mtech.irs.ekgs.kie.SimpleKieSystem;
import mtech.irs.ekgs.util.ResourceUtils;

@Configuration
public class KieConfiguration {

	@Bean
	public KieSystem kie() {
		SimpleKieSystem kie = new SimpleKieSystem();
		kie.buildContainer(ResourceUtils.getResources(
			"classpath*:kie/**/*.drl",
			"classpath*:kie/**/*.xls"
		));
		return kie;
	}
}
