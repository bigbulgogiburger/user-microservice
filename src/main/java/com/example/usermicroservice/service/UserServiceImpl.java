package com.example.usermicroservice.service;

import com.example.usermicroservice.dto.UserDto;
import com.example.usermicroservice.entity.UserEntity;
import com.example.usermicroservice.repository.UserRepository;
import com.example.usermicroservice.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = mapper.map(userDto,UserEntity.class);

        userEntity.setEncryptedPwd(passwordEncoder.encode(userDto.getPwd()));
        userRepository.save(userEntity);

        return mapper.map(userEntity,UserDto.class);

    }


    @Override
    public List<UserEntity> getUserByAll() {
        return userRepository.findAll();
    }

    @Override
    public UserDto getUserById(String id) {
        UserEntity userEntity = userRepository.findByUserId(id);
        if(userEntity == null){
            throw new UsernameNotFoundException("사용자가 없습니다.");
        }

        List<ResponseOrder> orders = new ArrayList<>();
        UserDto userDto = new ModelMapper().map(userEntity,UserDto.class);

        userDto.setOrders(orders);

        return userDto;

    }

}
