package config;

import org.aeonbits.owner.Config;
@Config.Sources("classpath:properties/manager.properties")
public interface ManagerPropInterface extends Config {
    String emailManager();
    String passwordManager();
    Integer idManagerUser();

}
