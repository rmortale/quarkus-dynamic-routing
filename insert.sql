

INSERT INTO public.routing_config
(serviceid, routing_config, order_num)
VALUES('service3', 'jms:avt.service1.davt1?connectionFactory=<default>', 0);

INSERT INTO public.routing_config
(serviceid, routing_config, order_num)
VALUES('service3', 'jms:avt.service1.davt2?connectionFactory=<default>', 1);
