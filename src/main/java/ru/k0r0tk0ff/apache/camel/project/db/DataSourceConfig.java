package ru.k0r0tk0ff.apache.camel.project.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Configuration
@PropertySources({ @PropertySource("classpath:datasource-cfg.properties") })
public class DataSourceConfig {

    private final static Logger LOGGER =
            LogManager.getLogger(DataSourceConfig.class);

    @Autowired
    private Environment env;

    @Bean
    @Qualifier("h2DataSource")
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));
        return dataSource;
    }

    @PostConstruct
    @DependsOn("h2DataSource")
    void createDb(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());
        String createTable1 = "CREATE TABLE msg (" +
                "    msg_id INTEGER GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY," +
                "    message BLOB NOT NULL" +
                ")";
        String createTable2 = "CREATE TABLE headers (" +
                "    headers_id INTEGER NOT NULL," +
                "    FOREIGN KEY (headers_id) references msg(msg_id)," +
                "    head VARCHAR(256) NOT NULL" +
                ")";

        jdbcTemplate.execute(createTable1);
        jdbcTemplate.execute(createTable2);
        LOGGER.info("Create tables success.......");
    }

    @PreDestroy
    @DependsOn("h2DataSource")
    void dropTableInDb() {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());
            String deleteTable1 = "DROP TABLE msg";
            String deleteTable2 = "DROP TABLE headers";
            jdbcTemplate.execute(deleteTable1);
            jdbcTemplate.execute(deleteTable2);
            //System.out.println("Drop tables success.......");
            LOGGER.info("Drop tables success.......");
        } catch (Exception e) {
            LOGGER.error("Cannot drop tables !", e);
        }
    }
}
