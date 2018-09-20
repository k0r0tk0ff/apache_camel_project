package ru.k0r0tk0ff.apache.camel.project.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class ErrorProcessor implements Processor {

    private static Logger LOGGER = LogManager.getLogger(ErrorProcessor.class);

    public void process(Exchange exchange) throws Exception {
        Exception e = new Exception();
        LOGGER.error("Error! Invalid file extension!", e);
    }
}
