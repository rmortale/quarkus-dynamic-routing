package ch.dulce.camel.routes;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import static ch.dulce.camel.config.ActiveMqConfig.ACTIVEMQ_COMPONENT_NAME;
import static ch.dulce.camel.config.ArtemisConfig.ARTEMIS_COMPONENT_NAME;
import static ch.dulce.camel.config.IbmConfig.IBM_JMS_COMPONENT_SWIFT;
import static ch.dulce.camel.routes.templates.Templates.*;

@ApplicationScoped
public class ConsumerRoutes extends EndpointRouteBuilder {

  public static final String CACHE_LEVEL_NAME = "CACHE_CONSUMER";
  public static final String DEFAULT_JMS_CONNECTION_FACTORY = "<default>";

  @ConfigProperty(name = "app.artemis.routing.inqueue")
  private String artemisInQueue;
  @ConfigProperty(name = "app.artemis.routing.maxconsumers")
  private String artemisMaxConsumers;
  @ConfigProperty(name = "app.ibm.routing.inqueue")
  private String ibmInQueue;
  @ConfigProperty(name = "app.ibm.routing.maxconsumers")
  private String ibmMaxConsumers;
  @ConfigProperty(name = "app.activemq.routing.inqueue")
  private String activemqInQueue;
  @ConfigProperty(name = "app.activemq.routing.maxconsumers")
  private String activemqMaxConsumers;

  @Override
  public void configure() throws Exception {

    templatedRoute(DYNAMIC_ROUTING_TEMPLATE)
        .routeId("Routing-ibmmq")
        .parameter(INQUEUE, ibmInQueue)
        .parameter(IN_COMPONENT, IBM_JMS_COMPONENT_SWIFT)
        .parameter(MAX_CONSUMERS, ibmMaxConsumers);

    templatedRoute(DYNAMIC_ROUTING_TEMPLATE)
        .routeId("Routing-activemq")
        .parameter(INQUEUE, activemqInQueue)
        .parameter(IN_COMPONENT, ACTIVEMQ_COMPONENT_NAME)
        .parameter(MAX_CONSUMERS, activemqMaxConsumers);

    templatedRoute(DYNAMIC_ROUTING_TEMPLATE)
        .routeId("Routing-artemis")
        .parameter(INQUEUE, artemisInQueue)
        .parameter(IN_COMPONENT, ARTEMIS_COMPONENT_NAME)
        .parameter(MAX_CONSUMERS, artemisMaxConsumers);

  }
}
