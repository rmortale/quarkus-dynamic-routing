

INSERT INTO public.routing_config
(serviceid, endpoint, headers, order_num)
VALUES('service3', 'wmqSwift:queue:DEV.QUEUE.2',
       'CamelJmsDestinationName,queue:///DEV.QUEUE.3?targetClient=1',
       0);

INSERT INTO public.routing_config
(serviceid, endpoint, headers, order_num)
VALUES('service4', 'direct:ibmDynamicProducer',
       'CamelJmsDestinationName,queue:///DEV.QUEUE.2?targetClient=1',
       1);
