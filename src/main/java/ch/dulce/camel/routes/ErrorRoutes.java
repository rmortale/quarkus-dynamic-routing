package ch.dulce.camel.routes;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import static ch.dulce.camel.config.ActiveMqConfig.ACTIVEMQ_FACTORY_NAME;
import static ch.dulce.camel.config.IbmConfig.IBM_FACTORY_NAME;
import static ch.dulce.camel.routes.ConsumerRoutes.DEFAULT_JMS_CONNECTION_FACTORY;


@ApplicationScoped
public class ErrorRoutes extends EndpointRouteBuilder {

  public static final String IBM_ERROR_EP = "direct:ibmErrorEp";
  public static final String ARTEMIS_ERROR_EP = "direct:artemisErrorEp";
  public static final String ACTIVEMQ_ERROR_EP = "direct:activemqErrorEp";

  @ConfigProperty(name = "app.artemis.error.queue")
  private String artemisErrorQueue;
  @ConfigProperty(name = "app.ibm.error.queue")
  private String ibmErrorQueue;
  @ConfigProperty(name = "app.activemq.error.queue")
  private String activemqErrorQueue;

  @Override
  public void configure() throws Exception {

    from(IBM_ERROR_EP)
        .log("Error occurred: ${header.errorDescription}")
        .to(jms(ibmErrorQueue).connectionFactory(IBM_FACTORY_NAME))
        .routeId("Error-ibmmq");

    from(ARTEMIS_ERROR_EP)
        .log("Error occurred: ${header.errorDescription}")
        .to(jms(artemisErrorQueue).connectionFactory(DEFAULT_JMS_CONNECTION_FACTORY))
        .routeId("Error-artemis");

    from(ACTIVEMQ_ERROR_EP)
        .log("Error occurred: ${header.errorDescription}")
        .to(jms(activemqErrorQueue).connectionFactory(ACTIVEMQ_FACTORY_NAME))
        .routeId("Error-activemq");
  }
}
