package ch.dulce.camel.routes;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import static ch.dulce.camel.config.ActiveMqConfig.ACTIVEMQ_FACTORY_NAME;
import static ch.dulce.camel.config.IbmConfig.IBM_FACTORY_NAME;
import static ch.dulce.camel.routes.ErrorRoutes.*;
import static ch.dulce.camel.routing.DynamicRouter.DYNAMIC_ROUTING_EP;

@ApplicationScoped
public class ConsumerRoutes extends EndpointRouteBuilder {

  private static final String CACHE_LEVEL_NAME = "CACHE_CONSUMER";
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

    from(jms(artemisInQueue)
        .transacted(true)
        .cacheLevelName(CACHE_LEVEL_NAME)
        .maxConcurrentConsumers(artemisMaxConsumers).connectionFactory(DEFAULT_JMS_CONNECTION_FACTORY)
        .advanced().lazyCreateTransactionManager(false))
        .routeId("Routing-artemis")
        .choice()
        .when(simple("${header.serviceid} != null"))
          .to(DYNAMIC_ROUTING_EP)
        .otherwise()
          .setHeader("errorDescription", constant("Routing header 'serviceid' missing!"))
          .to(ARTEMIS_ERROR_EP);

    from(jms(ibmInQueue)
        .transacted(true)
        .cacheLevelName(CACHE_LEVEL_NAME)
        .maxConcurrentConsumers(ibmMaxConsumers).connectionFactory(IBM_FACTORY_NAME)
        .advanced().lazyCreateTransactionManager(false))
        .routeId("Routing-ibmmq")
        .choice()
        .when(simple("${header.serviceid} != null"))
          .to(DYNAMIC_ROUTING_EP)
        .otherwise()
          .setHeader("errorDescription", constant("Routing header 'serviceid' missing!"))
          .to(IBM_ERROR_EP);

    from(jms(activemqInQueue)
        .transacted(true)
        .cacheLevelName(CACHE_LEVEL_NAME)
        .maxConcurrentConsumers(activemqMaxConsumers).connectionFactory(ACTIVEMQ_FACTORY_NAME)
        .advanced().lazyCreateTransactionManager(false))
        .routeId("Routing-activemq")
        .choice()
        .when(simple("${header.serviceid} != null"))
          .to(DYNAMIC_ROUTING_EP)
        .otherwise()
          .setHeader("errorDescription", constant("Routing header 'serviceid' missing!"))
          .to(ACTIVEMQ_ERROR_EP);

  }
}
