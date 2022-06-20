package config;

import org.aeonbits.owner.Config;
@Config.Sources("classpath:properties/admin.properties")
public interface AdminPropInterface extends Config {
    String emailAdmin();
    String passwordAdmin();
    Integer idAdminUser();

}
