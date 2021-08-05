package com.bukoudai.qpgame.service.impl;



import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bukoudai.qpgame.entitys.User;
import com.bukoudai.qpgame.mapper.UserMapper;
import com.bukoudai.qpgame.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


}
