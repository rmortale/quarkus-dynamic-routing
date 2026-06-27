package ch.dulce.camel.routes;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import static ch.dulce.camel.config.ActiveMqConfig.ACTIVEMQ_FACTORY_NAME;
import static ch.dulce.camel.config.IbmConfig.IBM_FACTORY_NAME;
import static ch.dulce.camel.routes.ErrorRoutes.ERROR_EP;
import static ch.dulce.camel.routing.DynamicRouter.DYNAMIC_ROUTING_EP;

@ApplicationScoped
public class ConsumerRoutes extends EndpointRouteBuilder {

  public static final String CACHE_LEVEL_NAME = "CACHE_CONSUMER";
  public static final String DEFAULT_JMS_CONNECTION_FACTORY = "<default>";
  private static final String SERVICEID_CHECK_EP = "direct:serviceIdCheck";

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
        .to(SERVICEID_CHECK_EP);

    from(jms(ibmInQueue)
        .transacted(true)
        .cacheLevelName(CACHE_LEVEL_NAME)
        .maxConcurrentConsumers(ibmMaxConsumers).connectionFactory(IBM_FACTORY_NAME)
        .advanced().lazyCreateTransactionManager(false))
        .routeId("Routing-ibmmq")
        .to(SERVICEID_CHECK_EP);

    from(jms(activemqInQueue)
        .transacted(true)
        .cacheLevelName(CACHE_LEVEL_NAME)
        .maxConcurrentConsumers(activemqMaxConsumers).connectionFactory(ACTIVEMQ_FACTORY_NAME)
        .advanced().lazyCreateTransactionManager(false))
        .routeId("Routing-activemq")
        .to(SERVICEID_CHECK_EP);

    from(SERVICEID_CHECK_EP)
        .choice()
          .when(simple("${isEmpty(${header.serviceid})}"))
            .setHeader("errorDescription", constant("Required routing header 'serviceid' missing!"))
            .to(ERROR_EP)
          .otherwise()
            .to(DYNAMIC_ROUTING_EP);

  }
}
