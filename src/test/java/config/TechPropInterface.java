package config;

import org.aeonbits.owner.Config;
@Config.Sources("classpath:properties/tech.properties")
public interface TechPropInterface extends Config {
    String emailTech();
    String passwordTech();
    Integer idTechUser();

}
