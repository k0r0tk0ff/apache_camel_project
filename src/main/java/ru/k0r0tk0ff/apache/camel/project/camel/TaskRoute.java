package ru.k0r0tk0ff.apache.camel.project.camel;

import org.apache.camel.builder.RouteBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.k0r0tk0ff.apache.camel.project.db.H2Repository;

@Component
public class TaskRoute extends RouteBuilder {

    @Autowired
    private CountProcessor countProcessor;

    @Override
    public void configure() throws Exception {
        errorHandler(
           deadLetterChannel("jms:queue:invalid-queue"));

        from("file:data?noop=true")
        .streamCaching()
        .choice()
        .when(header("CamelFileName").endsWith(".xml"))
            .to("jms:queue:empty")
            .process(countProcessor)
        .when(header("CamelFileName").endsWith(".txt"))
            .bean(H2Repository.class, "writeMessageToDb")
            .to("jms:queue:empty")
            .process(countProcessor)
        .otherwise()
            .process(countProcessor)
            .throwException(new Exception(" Wrong extension !"))
        .end()
            .choice()
            .when(header("to").isNotNull())
            .to("smtp://localhost");
    }
}
