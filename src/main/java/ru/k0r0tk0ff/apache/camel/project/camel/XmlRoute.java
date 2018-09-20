package ru.k0r0tk0ff.apache.camel.project.camel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class XmlRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
/*        from("file:data?noop=true")
                .to("jms:queue:empty");*/
    }
}
