package ch.dulce.camel.routes;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;

import static ch.dulce.camel.config.ActiveMqConfig.ACTIVEMQ_FACTORY_NAME;
import static ch.dulce.camel.config.IbmConfig.IBM_FACTORY_NAME;
import static ch.dulce.camel.routes.ConsumerRoutes.DEFAULT_JMS_CONNECTION_FACTORY;

@ApplicationScoped
public class ProducerRoutes extends EndpointRouteBuilder {

  public static final String IBM_DYNAMIC_PRODUCER_EP = "direct:ibmDynamicProducer";
  public static final String ARTEMIS_DYNAMIC_PRODUCER_EP = "direct:artemisDynamicProducer";
  public static final String ACTIVEMQ_DYNAMIC_PRODUCER_EP = "direct:activemqDynamicProducer";

  @Override
  public void configure() throws Exception {

    from(IBM_DYNAMIC_PRODUCER_EP)
        .to(jms("dynamicQueue").connectionFactory(IBM_FACTORY_NAME))
        .routeId("Producer-ibmmq")
        .log("IBM dynamic producer sent msg to ${header.CamelJMSDestinationProduced}");

    from(ARTEMIS_DYNAMIC_PRODUCER_EP)
        .to(jms("dynamicQueue").connectionFactory(DEFAULT_JMS_CONNECTION_FACTORY))
        .routeId("Producer-artemis")
        .log("Artemis dynamic producer sent msg to ${header.CamelJMSDestinationProduced}");

    from(ACTIVEMQ_DYNAMIC_PRODUCER_EP)
        .to(jms("dynamicQueue").connectionFactory(ACTIVEMQ_FACTORY_NAME))
        .routeId("Producer-activemq")
        .log("Activemq dynamic producer sent msg to ${header.CamelJMSDestinationProduced}");
  }
}
