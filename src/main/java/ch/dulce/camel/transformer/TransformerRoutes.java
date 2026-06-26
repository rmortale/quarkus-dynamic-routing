package ch.dulce.camel.transformer;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.builder.RouteBuilder;

@ApplicationScoped
public class TransformerRoutes extends RouteBuilder {

  public static final String UPPERCASE_TRANSFORM_EP = "direct:uppercaseTransformator";

  @Override
  public void configure() throws Exception {

    from(UPPERCASE_TRANSFORM_EP)
        .routeId("uppercaseTransformer")
        .transform().simple("${uppercase()}")
        .log("${body}");
  }
}
