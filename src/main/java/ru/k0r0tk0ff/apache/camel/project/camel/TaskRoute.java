package ru.k0r0tk0ff.apache.camel.project.camel;

import org.apache.camel.builder.RouteBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


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
            .to("jms:queue:empty")
        .otherwise()
            .to("jms:invalid-queue:empty")
            .process(errorProcessor);
    }
}
