package ru.k0r0tk0ff.apache.camel.project.camel;

import org.apache.camel.builder.RouteBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.k0r0tk0ff.apache.camel.project.db.JmsRepository;


@Component
public class TaskRoute extends RouteBuilder {

    @Autowired
    private ErrorProcessor errorProcessor;

    @Override
    public void configure() throws Exception {
        from("file:data?noop=true")
        .choice()
        .when(header("CamelFileName").endsWith(".xml"))
            .to("jms:queue:empty")
        .when(header("CamelFileName").endsWith(".txt"))
            .bean(JmsRepository.class, "writeMessageToDb")
            .to("jms:queue:empty")
        .otherwise()
            .to("jms:queue:invalid-queue")
            .process(errorProcessor);
    }
}
