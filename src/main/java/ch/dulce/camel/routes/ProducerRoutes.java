package ch.dulce.camel.routes;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;

@ApplicationScoped
public class ProducerRoutes extends EndpointRouteBuilder {

  public static final String IBM_DYNAMIC_PRODUCER_EP = "direct:ibmDynamicProducer";
  public static final String ARTEMIS_DYNAMIC_PRODUCER_EP = "direct:artemisDynamicProducer";
  public static final String ACTIVEMQ_DYNAMIC_PRODUCER_EP = "direct:activemqDynamicProducer";

//  @Inject
//  @Identifier( IBM_DESTINATION_RESOLVER)
//  private DestinationResolver ibmDestinationResolver;

  @Override
  public void configure() throws Exception {

//    from(IBM_DYNAMIC_PRODUCER_EP)
//        .to(jms("dynamicQueue").connectionFactory(IBM_FACTORY_NAME)
//            .advanced().destinationResolver((session, destinationName, pubSubDomain) -> {
//                MQSession wmqSession = (MQSession) session;
//                return wmqSession.createQueue("queue:///" + destinationName + "?targetClient=1");
//            }))
//        .routeId("Producer-ibmmq")
//        .log("IBM dynamic producer sent msg to ${header.CamelJMSDestinationProduced}");
//
//    from(ARTEMIS_DYNAMIC_PRODUCER_EP)
//        .to(jms("dynamicQueue").connectionFactory(DEFAULT_JMS_CONNECTION_FACTORY))
//        .routeId("Producer-artemis")
//        .log("Artemis dynamic producer sent msg to ${header.CamelJMSDestinationProduced}");
//
//    from(ACTIVEMQ_DYNAMIC_PRODUCER_EP)
//        .to(jms("dynamicQueue").connectionFactory(ACTIVEMQ_FACTORY_NAME))
//        .routeId("Producer-activemq")
//        .log("Activemq dynamic producer sent msg to ${header.CamelJMSDestinationProduced}");
  }
}
