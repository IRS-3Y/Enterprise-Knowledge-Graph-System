package mtech.irs.ekgs.kie;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.stream.Stream;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message.Level;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

/**
 * Simple {@link KieSystem} with a single {@link KieContainer}
 * 
 * @author tanshyi
 */
public class SimpleKieSystem implements KieSystem{

	protected KieContainer container;

	public KieContainer getContainer() {
		return container;
	}
	
	public void buildContainer(Stream<Resource> resources) {
		KieContainer newContainer = buildKieContainer(resources);
		if(container != null) {
			container.dispose();
		}
		container = newContainer;
	}
	
	@Override
	public KieSession newSession() {
		return container.newKieSession();
	}

	@Override
	public StatelessKieSession newStatelessSession() {
		return container.newStatelessKieSession();
	}
	
	public static KieContainer buildKieContainer(Stream<Resource> resources) {
		final KieServices ks = KieServices.Factory.get();
		
        final KieFileSystem kfs = ks.newKieFileSystem();
        resources.forEach(r -> {
        	try {
                String path = r.getURL().getPath();
                int index = path.lastIndexOf("kie");
                if(index < 0) {
                    throw new IllegalArgumentException("Resource path does not contain kie folder: "+path);
                }
                path = "src/main/resources/mtech/irs/ekgs/"+path.substring(index);
                
                kfs.write(path, 
                	ks.getResources()
                        .newByteArrayResource(StreamUtils.copyToByteArray(r.getInputStream()))
                        .setResourceType(ResourceType.determineResourceType(path)));
            }catch(IOException e) {
                throw new UncheckedIOException(e);
            }
        });
        
        final KieBuilder kb = ks.newKieBuilder(kfs);
        kb.buildAll(); // KieModule is automatically deployed to KieRepository if successfully built.
        if (kb.getResults().hasMessages(Level.ERROR)) {
            throw new IllegalStateException("KIE system has build errors:\n" + kb.getResults().toString());
        }
        
        return ks.newKieContainer(kb.getKieModule().getReleaseId());
	}

}
