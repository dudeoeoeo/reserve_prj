package com.kei.userservice.service;

import com.kei.userservice.dto.UserDto;
import com.kei.userservice.entity.UserEntity;
import com.kei.userservice.entity.UserRepository;
import com.kei.userservice.entity.UserRole;
import com.kei.userservice.security.token.TokenProvider;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private TokenProvider tokenProvider;
    private final ModelMapper modelMapper = new ModelMapper();

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
                           TokenProvider tokenProvider, Environment env)
    {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        userDto.setUserId(UUID.randomUUID().toString());

        final UserEntity newUserEntity = modelMapper.map(userDto, UserEntity.class);

        newUserEntity.setEncryptedPwd(bCryptPasswordEncoder.encode(userDto.getPwd()));
        newUserEntity.setRole(UserRole.USER);

        userRepository.save(newUserEntity);

        return modelMapper.map(newUserEntity, UserDto.class);
    }

    @Override
    public UserDto getUserDetailByEmail(String email) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        final UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException(email)
        );

        final UserDto userDto = modelMapper.map(userEntity, UserDto.class);

        return userDto;
    }

    @Override
    public String renewToken(String token) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        final String userId = tokenProvider.getUserIdFromToken(token);
        final UserEntity userEntity = findByUserId(userId);

        final UserDto userDto = modelMapper.map(userEntity, UserDto.class);
        final String renewAccessToken = tokenProvider.renewAccessToken(userDto);
        return renewAccessToken;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final UserEntity user = userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException(email)
        );

        return new User(
                user.getEmail(),
                user.getEncryptedPwd(),
                true,
                true,
                true,
                true,
                new ArrayList()
        );
    }

    private UserEntity findByUserId(String userId) {
        return userRepository.findByUserId(userId).orElseThrow(
                () -> new UsernameNotFoundException(userId)
        );
    }
}
