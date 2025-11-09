package pe.edu.uni.saborgourmet.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * Configuración para H2 Database
 * Deshabilita el shutdown executor automático para evitar warnings
 */
@Configuration
public class H2Config {
    
    /**
     * Deshabilita el shutdown executor de H2
     * Esto evita el warning: "Database is already closed"
     */
    @Bean(destroyMethod = "")
    @Primary
    public DataSource dataSource(DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }
}

