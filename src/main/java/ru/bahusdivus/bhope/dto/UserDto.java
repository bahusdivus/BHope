package ru.bahusdivus.bhope.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.bahusdivus.bhope.entities.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private long id;
    private String name;

    public UserDto(long id) {
        this.id = id;
    }

    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
    }
}
