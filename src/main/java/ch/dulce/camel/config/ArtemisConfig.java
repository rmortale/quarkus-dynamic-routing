package ch.dulce.camel.config;

import io.smallrye.common.annotation.Identifier;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSException;
import org.apache.camel.component.jms.JmsComponent;

public class ArtemisConfig {

  public static final String ARTEMIS_COMPONENT_NAME = "artemisComponent";

  @Inject
  @Default
  private ConnectionFactory defaultConnectionFactory;

  @Identifier(ARTEMIS_COMPONENT_NAME)
  public JmsComponent createJmsComponent() throws JMSException {
    return JmsComponent.jmsComponent(defaultConnectionFactory);
  }

}
