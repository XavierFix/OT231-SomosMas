package com.alkemy.ong.infrastructure.rest.mapper;

import com.alkemy.ong.domain.User;
import com.alkemy.ong.infrastructure.config.spring.security.common.JwtUtils;
import com.alkemy.ong.infrastructure.rest.response.ListUserResponse;
import com.alkemy.ong.infrastructure.rest.response.UserResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
  
  @Autowired
  private JwtUtils jwtUtils;

  public ListUserResponse toResponse(List<User> users) {
    if (users == null || users.isEmpty()) {
      return new ListUserResponse(Collections.emptyList());
    }

    List<UserResponse> userResponses = new ArrayList<>(users.size());
    for (User user : users) {
      userResponses.add(toResponse(user));
    }
    return new ListUserResponse(userResponses);
  }

  public UserResponse toResponse(User user) {
    return UserResponse.builder()
        .email(user.getEmail())
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .photo(user.getPhoto())
        .build();
  }

  public User toDomain(String authorizationHeader) {
    return User.builder()
        .email(extractEmail(authorizationHeader))
        .build();
  }

  private String extractEmail(String token) {
    return jwtUtils.extractUsername(token);
  }

}