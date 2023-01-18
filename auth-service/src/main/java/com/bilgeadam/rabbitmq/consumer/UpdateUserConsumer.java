package com.bilgeadam.rabbitmq.consumer;

import com.bilgeadam.mapper.IAuthMapper;
import com.bilgeadam.rabbitmq.model.NewCreateUserModel;
import com.bilgeadam.rabbitmq.model.UpdateUserProfileModel;
import com.bilgeadam.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpdateUserConsumer {

private final AuthService authService;


   @RabbitListener(queues =("${rabbitmq.queueUpdate}") )
    public void newUserCreate(UpdateUserProfileModel model){
        log.info("User:{}",model.toString());
        authService.save(IAuthMapper.INSTANCE.toUserAuth(model));
    }
}
