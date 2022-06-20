package config;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:properties/client.properties")
public interface ClientPropInterface extends Config{
    Integer idClient();
}
