package com.bilgeadam.rabbitmq.producer;

import com.bilgeadam.rabbitmq.model.EmailSenderModel;
import com.bilgeadam.rabbitmq.model.NewCreateUserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailProducer {
    @Value("${rabbitmq.exchange-auth}")
    private String directExchange;
    @Value("${rabbitmq.bindingKeyEmail}")
    private String emailBindingKey;

    private  final RabbitTemplate rabbitTemplate;

    public void sendActivationCode(EmailSenderModel model){
        rabbitTemplate.convertAndSend(directExchange,emailBindingKey,model);
    }
}
