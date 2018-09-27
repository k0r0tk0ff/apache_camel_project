package ru.k0r0tk0ff.apache.camel.project.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class CountProcessor implements Processor {
    long timestamp;
    public CountProcessor() {
        timestamp = System.nanoTime();
    }

    private final static int BATCH_OF_MESSAGES_SIZE = 3;
    private static Logger LOGGER = LogManager.getLogger(CountProcessor.class);
    private int xmlExtensionFileCount = 0;
    private int txtExtensionFileCount = 0;
    private int otherExtensionFileCount = 0;
    private int summOfFiles = 0;

    public void process(Exchange exchange) throws Exception {
        messageExtensionHandler(exchange);
        if(summOfFiles % BATCH_OF_MESSAGES_SIZE == 0) {
            sendEmail(exchange);
        }

        if(LOGGER.isDebugEnabled()) {
            LOGGER.debug("XmlFilesCount = " + xmlExtensionFileCount);
            LOGGER.debug("TxtFilesCount = " + txtExtensionFileCount);
            LOGGER.debug("OtherFilesCount = " + otherExtensionFileCount);
        }
    }

    private void messageExtensionHandler(Exchange exchange) {
        switch (exchange.getMessage()
                .getHeader("CamelFileName")
                .toString()) {
            case "txt" :  txtExtensionFileCount++; summOfFiles++; break;
            case "xml" :  xmlExtensionFileCount++; summOfFiles++; break;
            default: summOfFiles++; break;
        }
    }

    private void sendEmail(Exchange exchange) {
        long currentTime = System.nanoTime();
        long deltaTime = currentTime - timestamp;
        timestamp = currentTime;
        String body = "countTxt:"
                + txtExtensionFileCount
                + "\n" + "countXml: "
                + xmlExtensionFileCount + "\n"
                + "countOther: "
                + otherExtensionFileCount
                + "\n" + "sum of files: "
                + summOfFiles + "\n"
                + "elapsed time: " + deltaTime + " nanoseconds";

        exchange.getOut().setHeader("host", "localhost");
        exchange.getOut().setHeader("to", "korotkov_a_a@magnit.ru");
        exchange.getOut().setHeader("From", "korotkov_a_a@magnit.ru");
        exchange.getOut().setHeader("Subject", "Count of files");
        exchange.getOut().setBody(body);
    }
}
