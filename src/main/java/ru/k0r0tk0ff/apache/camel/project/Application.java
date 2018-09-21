package ru.k0r0tk0ff.apache.camel.project;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import ru.k0r0tk0ff.apache.camel.project.db.JmsRepository;

import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.spring.javaconfig.CamelConfiguration;
import java.util.concurrent.TimeUnit;

@Configuration
@SpringBootApplication(scanBasePackages = "ru.k0r0tk0ff.apache.camel.project")
public class Application extends CamelConfiguration implements ApplicationRunner {

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

    @Override
    protected void setupCamelContext(CamelContext camelContext) throws Exception {
        // setup the ActiveMQ component
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL("vm://localhost?broker.persistent=false&broker.useJmx=false");

        // and register it into the CamelContext
        JmsComponent answer = new JmsComponent();
        answer.setConnectionFactory(connectionFactory);
        camelContext.addComponent("jms", answer);
    }
}
