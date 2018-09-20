package ru.k0r0tk0ff.apache.camel.project.jms;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;


import javax.jms.TextMessage;

@Component
public class JmsTemplateProducer {

@Autowired
JmsTemplate jmsTemplate;

    private static Logger LOGGER = LogManager.getLogger(JmsTemplateProducer.class);

  /*  public void sendMessage(String i, String type) {
        final String j = i;
        jmsTemplate.send(
                session -> {
                    TextMessage message = session.createTextMessage();
                    message.setText("Text " + j);
                    message.setJMSType(type);
                    StringBuilder builder = new StringBuilder();
                    builder.append("Sent: <body = ");
                    builder.append(message.getText());
                    builder.append("; header = ");
                    builder.append(message.getJMSType());
                    builder.append(";>");
                    LOGGER.info(builder.toString());
                    return message;
                }
        );
    }*/
}
