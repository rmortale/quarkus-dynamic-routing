package ch.dulce.camel.routes.templates;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;

@ApplicationScoped
public class TransformerRoutes extends EndpointRouteBuilder {

  public static final String UPPERCASE_TRANSFORMATION = "uppercaseTransformation";
  public static final String WRAPBODY_TRANSFORMATION = "wrapbodyTransformation";

  private static final String VAI_MESSAGE = """
      <object>
        <header>
          <system>${header.system}</system>
          <serviceid>${header.serviceid}</serviceid>
        </header>
        <body>
          ${body}
        </body>
      </object>
      """;

  @Override
  public void configure() throws Exception {

    from(direct(UPPERCASE_TRANSFORMATION))
        .routeId(UPPERCASE_TRANSFORMATION)
        .transform().simple("${uppercase()}")
        .log(LoggingLevel.DEBUG, "Transformed ${body}");

    from(direct(WRAPBODY_TRANSFORMATION))
        .routeId(WRAPBODY_TRANSFORMATION)
        .transform().simple(VAI_MESSAGE)
        .log(LoggingLevel.DEBUG, "Transformed ${body}");

  }

}
