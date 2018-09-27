package ru.k0r0tk0ff.apache.camel.project.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CountProcessor implements Processor {
    private final static int BATCH_OF_MESSAGES_SIZE = 3;
    private final static String TXT = "txt";
    private final static String XML = "xml";
    private final static String UNDEFINED = "undefined";

    private long timestamp;
    private Map<String, Integer> fileTypesMap;

    public CountProcessor() {
        timestamp = System.nanoTime();
        fileTypesMap = new HashMap<>();
    }
    private static Logger LOGGER = LogManager.getLogger(CountProcessor.class);
    private int summOfFiles = 0;

    public void process(Exchange exchange) throws Exception {
        messageExtensionHandler(exchange);
        summOfFiles++;

        if(summOfFiles % BATCH_OF_MESSAGES_SIZE == 0) {
            sendEmail(exchange);
        }

        if(LOGGER.isDebugEnabled()) {
            fileTypesMap
                    .forEach(
                            (x, y) -> LOGGER.debug(
                                    "Extension = " + x
                                            + " Count of files = " + y
                            )
                    );
        }
    }

    private void messageExtensionHandler(Exchange exchange) {
        String fileName = getFileName(exchange);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("---------- FILENAME ------------------");
            LOGGER.debug(fileName);
        }
        String extension = getExtension(fileName);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.info("---------- FILE EXTENSION ------------------");
            LOGGER.info(extension);
        }

        if(extension.equals(TXT) || extension.equals(XML)) {
            increaseCountFileWithExtension(extension);
        } else {
            increaseCountFileWithExtension(UNDEFINED);
        }
    }

    private String getFileName(Exchange exchange) {
        return exchange
                .getMessage()
                .getHeader("CamelFileName")
                .toString();
    }

    private String getExtension(String filename) {
        String[] parsedBlocks = filename.split("\\.");
        return parsedBlocks[parsedBlocks.length - 1].toLowerCase();
    }

    void increaseCountFileWithExtension(String extension) {
        if(fileTypesMap.containsKey(extension)) {
            int buffer = fileTypesMap.get(extension);
            fileTypesMap.put(extension, ++buffer);
        } else {
            fileTypesMap.put(extension, 1);
        }
    }

    private void sendEmail(Exchange exchange) {
        long currentTime = System.nanoTime();
        long deltaTime = currentTime - timestamp;
        timestamp = currentTime;
        StringBuilder mailBody = new StringBuilder();
        mailBody.append("countTxt: ");
        mailBody.append(fileTypesMap.get(TXT));
        mailBody.append(System.getProperty("line.separator"));
        mailBody.append("countXml: ");
        mailBody.append(fileTypesMap.get(XML));
        mailBody.append(System.getProperty("line.separator"));
        mailBody.append("countOther: ");
        mailBody.append(fileTypesMap.get(UNDEFINED));
        mailBody.append(System.getProperty("line.separator"));
        mailBody.append("elapsed time: ");
        mailBody.append(deltaTime);
        mailBody.append(" nanoseconds");

        Message message = exchange.getOut();
        message.setHeader("host", "localhost");
        message.setHeader("to", "korotkov_a_a@magnit.ru");
        message.setHeader("From", "korotkov_a_a@magnit.ru");
        message.setHeader("Subject", "Count of files");
        message.setBody(mailBody);
    }
}
