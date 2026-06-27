package ch.dulce.camel.routing;

import io.quarkus.cache.CacheResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.codejargon.fluentjdbc.api.mapper.Mappers;

import java.util.List;

@ApplicationScoped
public class RoutingRepository {

  private static final String GET_ROUTES_SQL = "select endpoint from routing_config where serviceid = ? order by order_num asc";

  @Inject
  private FluentJdbc jdbc;

  @CacheResult(cacheName = "routes-cache")
  public List<String> routesForService(String serviceid) {
    return jdbc.query().select(GET_ROUTES_SQL).params(serviceid).listResult(Mappers.singleString());
  }
}
