package ru.k0r0tk0ff.apache.camel.project.camel;

import org.apache.activemq.ActiveMQXAConnectionFactory;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.spring.javaconfig.CamelConfiguration;
import org.springframework.context.annotation.Configuration;
import org.apache.camel.CamelContext;

@Configuration
public class CamelConfigurationImpl extends CamelConfiguration {
    @Override
    protected void setupCamelContext(CamelContext camelContext) throws Exception {
        ActiveMQXAConnectionFactory connectionFactory = new ActiveMQXAConnectionFactory();
        connectionFactory.setBrokerURL("vm://localhost?broker.persistent=false&broker.useJmx=false&broker.useShutdownHook=false");
        JmsComponent answer = new JmsComponent();
        answer.setConnectionFactory(connectionFactory);
        camelContext.addComponent("jms", answer);
    }
}
