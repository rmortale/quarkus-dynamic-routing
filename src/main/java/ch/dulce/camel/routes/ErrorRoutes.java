package ch.dulce.camel.routes;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import static ch.dulce.camel.config.ActiveMqConfig.ACTIVEMQ_COMPONENT_NAME;
import static ch.dulce.camel.config.ArtemisConfig.ARTEMIS_COMPONENT_NAME;
import static ch.dulce.camel.config.IbmConfig.IBM_JMS_COMPONENT_SWIFT;


@ApplicationScoped
public class ErrorRoutes extends EndpointRouteBuilder {

  public static final String ERROR_EP = "direct:errorEp";

  @ConfigProperty(name = "app.artemis.error.queue")
  private String artemisErrorQueue;
  @ConfigProperty(name = "app.ibm.error.queue")
  private String ibmErrorQueue;
  @ConfigProperty(name = "app.activemq.error.queue")
  private String activemqErrorQueue;

  @Override
  public void configure() throws Exception {

    from(ERROR_EP)
        .routeId("Error-route")
        .log("Error occurred: ${header.errorDescription}")
        .choice()
          .when(simple("${fromRouteId} == 'Routing-ibmmq'"))
            .to(jms(IBM_JMS_COMPONENT_SWIFT, ibmErrorQueue))
          .when(simple("${fromRouteId} == 'Routing-artemis'"))
            .to(jms(ARTEMIS_COMPONENT_NAME, artemisErrorQueue))
          .when(simple("${fromRouteId} == 'Routing-activemq'"))
            .to(jms(ACTIVEMQ_COMPONENT_NAME, activemqErrorQueue));
  }
}
