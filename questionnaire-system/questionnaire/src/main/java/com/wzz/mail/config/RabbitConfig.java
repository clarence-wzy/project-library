package com.wzz.mail.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Title: RabbitConfig</p>
 * <p>Description: </p>
 *
 * @author lizihao
 * @version 1.0.0
 * @date 2019/9/2 11:26
 */
@Configuration
public class RabbitConfig {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CachingConnectionFactory connectionFactory;

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        //消息是否成功发送到Exchange
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                logger.info("消息成功发送到Exchange");
                String msgId = correlationData.getId();
                //TODO 更新日志
                //msgLogService.updateStatus(msgId,Constant)
            } else {
                logger.info("消息发送到Exchange失败，{}，cause：{}", correlationData, cause);
            }
        });
        //触发setReturnCallback回调必须设置mandatory=true，否则Exchange没有找到Queue就会丢弃掉消息，而不触发回调
        rabbitTemplate.setMandatory(true);
        //消息是否从Exchange路由到Queue，注意：这是一个失败回调，只有消息从Exchange路由到Queue失败才会回调这个方法
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            logger.info("消息从Exchange路由到Queue失败：exchange：{}，route：{}，replyCode：{}，replyText：{}，message：{}"
                    , exchange, routingKey, replyCode, replyText, message);
        });
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    public static final String MAIL_QUEUE_NAME = "mail.queue";
    public static final String MAIL_EXCHANGE_NAME = "mail.exchange";
    public static final String MAIL_ROUTING_KEY_NAME = "mail.routing.key";

    @Bean
    public Queue mailQueue() {
        return new Queue(MAIL_QUEUE_NAME, true);
    }

    @Bean
    public DirectExchange mailExchange() {
        return new DirectExchange(MAIL_EXCHANGE_NAME, true, false);
    }

    @Bean
    public Binding mailBinding() {
        return BindingBuilder.bind(mailQueue()).
                to(mailExchange()).with(MAIL_ROUTING_KEY_NAME);
    }
}
