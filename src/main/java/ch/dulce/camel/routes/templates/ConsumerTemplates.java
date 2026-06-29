package ch.dulce.camel.routes.templates;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;

import static ch.dulce.camel.routes.ErrorRoutes.ERROR_EP;
import static ch.dulce.camel.routing.DynamicRouter.DYNAMIC_ROUTING_EP;

@ApplicationScoped
public class ConsumerTemplates extends EndpointRouteBuilder {

  public static final String DYNAMIC_ROUTING_TEMPLATE = "dynamicRoutingTemplate";

  private static final String CACHE_LEVEL_NAME = "CACHE_CONSUMER";
  private static final String DEFAULT_MAX_CONSUMERS = "10";
  public static final String INQUEUE = "inqueue";
  public static final String IN_COMPONENT = "inComponent";
  public static final String MAX_CONSUMERS = "maxConsumers";
  public static final String DYNAMIC_ROUTING_EP_PARAM = "dynamicRoutingEp";
  public static final String ERROR_EP_NAME = "errorEp";


  @Override
  public void configure() throws Exception {

    routeTemplate(DYNAMIC_ROUTING_TEMPLATE)
        .templateParameter(INQUEUE)
        .templateParameter(IN_COMPONENT)
        .templateParameter(MAX_CONSUMERS, DEFAULT_MAX_CONSUMERS)
        .templateParameter(DYNAMIC_ROUTING_EP_PARAM,  DYNAMIC_ROUTING_EP)
        .templateParameter(ERROR_EP_NAME, ERROR_EP)
        .from(jms("{{inComponent}}", "{{inqueue}}")
          .transacted(true)
          .cacheLevelName(CACHE_LEVEL_NAME)
          .maxConcurrentConsumers("{{maxConsumers}}")
          .advanced().lazyCreateTransactionManager(false))
          .choice()
            .when(simple("${isEmpty(${header.serviceid})}"))
              .setHeader("errorDescription", constant("Required routing header 'serviceid' missing!"))
              .to("{{errorEp}}")
            .otherwise()
              .to("{{dynamicRoutingEp}}");

  }

}
