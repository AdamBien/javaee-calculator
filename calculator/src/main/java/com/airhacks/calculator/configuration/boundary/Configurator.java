
package com.airhacks.calculator.configuration.boundary;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

/**
 *
 * @author airhacks.com
 */
public class Configurator {

    @Produces
    public long expose(InjectionPoint ip) {
        String fieldName = ip.getMember().getName();
        String configuredValue = System.getenv().getOrDefault(fieldName, "0");
        return Long.parseLong(configuredValue);
    }
}
