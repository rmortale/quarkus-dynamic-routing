package ch.dulce.camel.routing;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.builder.RouteBuilder;

@ApplicationScoped
public class DynamicRouter extends RouteBuilder {

  public static final String DYNAMIC_ROUTING_EP = "direct:dynamicRouting";

  @Inject
  private RoutingRepository routingRepository;

  @Override
  public void configure() throws Exception {

    from(DYNAMIC_ROUTING_EP)
        .routeId("Dynamic-routing")
        .log("Routing service ${header.serviceid}")
        .routingSlip(method(routingRepository, "routesForService(${header.serviceid})")).ignoreInvalidEndpoints();
  }

}
