package ch.dulce.camel.config;

import io.smallrye.common.annotation.Identifier;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSException;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.eclipse.microprofile.config.inject.ConfigProperty;

public class ActiveMqConfig {

  public static final String ACTIVEMQ_FACTORY_NAME = "activemqConnectionFactory";

  @ConfigProperty(name = "app.activemq.brokerurl")
  private String brokerurl;
  @ConfigProperty(name = "app.activemq.username")
  private String username;
  @ConfigProperty(name = "app.activemq.password")
  private String password;

  @Identifier(ACTIVEMQ_FACTORY_NAME)
  public ConnectionFactory createConnectionFactory() throws JMSException {
    ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory(username, password, brokerurl);
    return cf;
  }
}
