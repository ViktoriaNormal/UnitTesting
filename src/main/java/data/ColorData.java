package data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ColorData {
    private Integer id;
    private String name;
    private Integer year;
    private String color;
    private String pantone_value;
}
