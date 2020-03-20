package mtech.irs.ekgs.kie;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;

/**
 * Wrapping KIE sub-system functionalities
 * 
 * @author tanshyi
 */
public interface KieSystem {

	KieSession newSession();
	
	StatelessKieSession newStatelessSession();
	
}
