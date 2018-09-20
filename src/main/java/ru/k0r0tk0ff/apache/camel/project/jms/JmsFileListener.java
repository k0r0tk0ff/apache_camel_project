package ru.k0r0tk0ff.apache.camel.project.jms;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.BytesMessage;

@Component
public class JmsFileListener {

    private static Logger LOGGER =
            LogManager.getLogger(JmsFileListener.class);

    @JmsListener(destination = "empty")
    public void receiveXmlFile(BytesMessage bytesMessage) {
        try {
            byte[] bytes = new byte[(int) bytesMessage.getBodyLength()];
            bytesMessage.readBytes(bytes);
            String out = new String(bytes);
            LOGGER.info("Read received file:" + out);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Error in JmsFileListener class!!", e);
        }
    }
}
