package ru.bahusdivus.bhope.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.bahusdivus.bhope.entities.User;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {
    private long id;
    private String name;

    public UserDto(User user) {
        id = user.getId();
        name = user.getName();
    }
}
