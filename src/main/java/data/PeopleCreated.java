package data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PeopleCreated extends People {
    private Integer id;
    private String createdAt;
}
