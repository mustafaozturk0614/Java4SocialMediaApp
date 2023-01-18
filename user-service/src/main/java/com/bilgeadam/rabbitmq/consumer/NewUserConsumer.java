package com.bilgeadam.rabbitmq.consumer;

import com.bilgeadam.mapper.IUserMapper;
import com.bilgeadam.rabbitmq.model.NewCreateUserModel;
import com.bilgeadam.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewUserConsumer {


private  final UserProfileService userProfileService;

@RabbitListener(queues =("${rabbitmq.queueRegister}") )
public void newUserCreate(NewCreateUserModel model){
log.info("User:{}",model.toString());
userProfileService.save(IUserMapper.INSTANCE.toUserProfile(model));
}


}
