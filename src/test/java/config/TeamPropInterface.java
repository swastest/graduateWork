package config;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:properties/team.properties")
public interface TeamPropInterface extends Config {
    Integer teamId();
    String teamName();
    Integer teamLeaderId();
}
