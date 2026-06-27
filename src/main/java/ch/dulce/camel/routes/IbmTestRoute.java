package ch.dulce.camel.routes;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import static ch.dulce.camel.config.IbmConfig.IBM_FACTORY_NAME;
import static ch.dulce.camel.routes.ConsumerRoutes.CACHE_LEVEL_NAME;
import static ch.dulce.camel.routes.ConsumerRoutes.DEFAULT_JMS_CONNECTION_FACTORY;

@ApplicationScoped
public class IbmTestRoute extends EndpointRouteBuilder {

  @ConfigProperty(name = "app.ibm.routing.inqueue")
  private String ibmInQueue;

  private String artemisMaxConsumers = "25";

  @Override
  public void configure() throws Exception {
    from(jms("ibmTestQueue")
        .transacted(true)
        .cacheLevelName(CACHE_LEVEL_NAME)
        .maxConcurrentConsumers(artemisMaxConsumers).connectionFactory(DEFAULT_JMS_CONNECTION_FACTORY)
        .advanced().lazyCreateTransactionManager(false))
        .to(jms(ibmInQueue).connectionFactory(IBM_FACTORY_NAME));
  }
}
