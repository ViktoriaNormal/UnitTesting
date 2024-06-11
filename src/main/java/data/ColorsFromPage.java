package data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

@Getter
@AllArgsConstructor
public class ColorsFromPage {
    private Integer page;
    private Integer per_page;
    private Integer total;
    private Integer total_pages;
    private List<ColorData> data;
    private Support support;
}
