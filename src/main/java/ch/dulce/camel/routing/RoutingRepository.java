package ch.dulce.camel.routing;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.Message;
import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.codejargon.fluentjdbc.api.mapper.Mappers;

import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class RoutingRepository {

  private static final String GET_ROUTES_SQL = "select routing_config from routing_config where serviceid = ? order by order_num asc";

  @Inject
  private FluentJdbc jdbc;

  public List<String> routesForService(Message message) {
    Log.info("Getting routes for service " + message.getHeaders());
    String serviceId = message.getHeader("serviceid", String.class);
    if (serviceId == null) {
      Log.info("Header serviceid not found!");
      return Collections.emptyList();
    }
    List<String> result = jdbc.query().select(GET_ROUTES_SQL).params(serviceId).listResult(Mappers.singleString());
    Log.info(result);
    return result;
  }
}
