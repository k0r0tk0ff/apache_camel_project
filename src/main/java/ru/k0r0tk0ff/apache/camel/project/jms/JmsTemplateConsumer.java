package ru.k0r0tk0ff.apache.camel.project.jms;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import ru.k0r0tk0ff.apache.camel.project.db.JmsRepository;

import javax.jms.TextMessage;


@Component
public class JmsTemplateConsumer {

 /*   private static Logger LOGGER =
            LogManager.getLogger(JmsTemplateConsumer.class);

    @Autowired
    private JmsRepository jmsRepository;

    @JmsListener(destination = "empty")
    public void receiveMessage(TextMessage textMessage) {
        try {
            jmsRepository.writeMessageToDb(textMessage);
            StringBuilder builder = new StringBuilder();
            builder.append("Received: <body = ");
            builder.append(textMessage.getText());
            builder.append("; header = ");
            builder.append(textMessage.getJMSType());
            builder.append(";>");
            LOGGER.info(builder.toString());
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Error in JmsTemplateConsumer class!!", e);
        }
    }*/
}

