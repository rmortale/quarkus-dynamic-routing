package ch.dulce.camel.config;

import com.ibm.mq.jakarta.jms.MQConnectionFactory;
import com.ibm.mq.jakarta.jms.MQSession;
import com.ibm.msg.client.jakarta.wmq.WMQConstants;
import io.smallrye.common.annotation.Identifier;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSException;
import org.apache.camel.component.jms.JmsComponent;
import org.eclipse.microprofile.config.ConfigProvider;

public class IbmConfig {

  public static final String IBM_FACTORY_NAME = "ibmConnectionFactory";
  public static final String IBM_JMS_COMPONENT_SWIFT = "wmqSwift";

  @Identifier(IBM_FACTORY_NAME)
  public ConnectionFactory createConnectionFactory() throws JMSException {
    MQConnectionFactory mq = new MQConnectionFactory();
    mq.setHostName(ConfigProvider.getConfig().getValue("ibm.mq.host", String.class));
    mq.setPort(ConfigProvider.getConfig().getValue("ibm.mq.port", Integer.class));
    mq.setChannel(ConfigProvider.getConfig().getValue("ibm.mq.channel", String.class));
    mq.setQueueManager(ConfigProvider.getConfig().getValue("ibm.mq.queueManagerName", String.class));
    mq.setTransportType(WMQConstants.WMQ_CM_CLIENT);
    mq.setStringProperty(WMQConstants.USERID, ConfigProvider.getConfig().getValue("ibm.mq.username", String.class));
    mq.setStringProperty(WMQConstants.PASSWORD, ConfigProvider.getConfig().getValue("ibm.mq.password", String.class));
    return mq;
  }

  @Identifier(IBM_JMS_COMPONENT_SWIFT)
  public JmsComponent createJmsComponent() throws JMSException {
    JmsComponent jmsComponent = new JmsComponent();
    jmsComponent.setConnectionFactory(createConnectionFactory());
    jmsComponent.setDestinationResolver((session, destinationName, pubSubDomain) -> {
      MQSession wmqSession = (MQSession) session;
      return wmqSession.createQueue("queue:///" + destinationName + "?targetClient=1");
    });
    return jmsComponent;
  }

}
