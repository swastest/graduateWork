package config;

import org.aeonbits.owner.Config;
@Config.Sources("classpath:properties/link.properties")
public interface LinkPropInterface extends Config {
    String baseUrl();
}
