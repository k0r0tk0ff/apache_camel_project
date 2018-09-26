package ru.k0r0tk0ff.apache.camel.project.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CountProcessor implements Processor {


    long timestamp;

    public CountProcessor() {
        timestamp = System.nanoTime();
    }

    private static Logger LOGGER = LogManager.getLogger(CountProcessor.class);

    private int xmlExtensionFileCount = 0;
    private int txtExtensionFileCount = 0;
    private int otherExtensionFileCount = 0;
    private int summOfFiles = 0;

    public void process(Exchange exchange) throws Exception {
        boolean anotherExtensionFlag = true;
        if (exchange.getMessage()
                .getHeader("CamelFileName")
                .toString()
                .endsWith(".xml")
                ) {
            xmlExtensionFileCount++;
            summOfFiles++;
            anotherExtensionFlag = false;
        }

        if (exchange.getMessage()
                .getHeader("CamelFileName")
                .toString()
                .endsWith(".txt")
                ) {
            txtExtensionFileCount++;
            summOfFiles++;
            anotherExtensionFlag = false;
        }

        if (anotherExtensionFlag) {
            otherExtensionFileCount++;
            summOfFiles++;
        }

        if(summOfFiles % 3 == 0) {
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

            if(LOGGER.isDebugEnabled()) {
                LOGGER.info("XmlFilesCount = " + xmlExtensionFileCount);
                LOGGER.info("TxtFilesCount = " + txtExtensionFileCount);
                LOGGER.info("OtherFilesCount = " + otherExtensionFileCount);
            }
        }
    }
}
