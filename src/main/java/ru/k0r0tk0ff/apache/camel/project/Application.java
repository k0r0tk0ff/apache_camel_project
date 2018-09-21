package ru.k0r0tk0ff.apache.camel.project;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import ru.k0r0tk0ff.apache.camel.project.db.JmsRepository;
import java.util.concurrent.TimeUnit;

@Configuration
@SpringBootApplication(scanBasePackages = "ru.k0r0tk0ff.apache.camel.project")
public class Application implements ApplicationRunner {

    private static Logger LOGGER = LogManager.getLogger(Application.class);

    @Autowired
    private JmsRepository jmsRepository;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        TimeUnit.SECONDS.sleep(3);
        jmsRepository.showDataInDb();
        System.exit(0);
    }
}
