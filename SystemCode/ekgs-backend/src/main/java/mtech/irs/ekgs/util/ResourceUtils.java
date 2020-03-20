package mtech.irs.ekgs.util;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

abstract public class ResourceUtils {

	public static Stream<Resource> getResources(String ... locationPattern) {
		ClassLoader cl = ResourceUtils.class.getClassLoader(); 
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);
        return Stream.of(locationPattern)
        		.flatMap(l -> {
        			try {
        		        Resource[] resources = resolver.getResources(l);
        		        return Stream.of(resources);
        			}catch(IOException e) {
        				throw new UncheckedIOException(e);
        			}
        		});
    }
}
