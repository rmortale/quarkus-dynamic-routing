

INSERT INTO public.routing_config
(serviceid, endpoint, order_num)
VALUES('service3', 'wmqSwift:queue:DEV.QUEUE.2', 0);

INSERT INTO public.routing_config
(serviceid, endpoint, order_num)
VALUES('service4', 'direct:ibmDynamicProducer',1);
