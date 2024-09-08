package com.example.usermicroservice.service;

import com.example.usermicroservice.client.OrderServiceClient;
import com.example.usermicroservice.dto.UserDto;
import com.example.usermicroservice.entity.UserEntity;
import com.example.usermicroservice.repository.UserRepository;

import com.example.usermicroservice.vo.ResponseOrder;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    private final OrderServiceClient orderServiceClient;

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
    public UserDto getUserDetailsByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException(email));
        return new ModelMapper().map(user, UserDto.class);
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
//
////        List<ResponseOrder> orders = new ArrayList<>();
//
        UserDto userDto = new ModelMapper().map(userEntity,UserDto.class);
//
//        /* Using as rest template */
//        String orderUrl = String.format(env.getProperty("order-service.url"), id);
//
//        ResponseEntity<List<ResponseOrder>> orderListResponse = restTemplate.exchange(orderUrl, HttpMethod.GET, null,
//                new ParameterizedTypeReference<List<ResponseOrder>>() {
//                });
//
//
//
//        List<ResponseOrder> orderList = orderListResponse.getBody();

        /* Using a feign client */
        /* ErrorDecoder*/
        List<ResponseOrder> orderList =orderList = orderServiceClient.getOrders(id);

        userDto.setOrders(orderList);

        return userDto;

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findByEmail(username);
        UserEntity user = userEntity.orElseThrow(() -> new UsernameNotFoundException(username));

        return new User(user.getEmail(),user.getEncryptedPwd(),true,true,true,true,new ArrayList<>());
    }
}
