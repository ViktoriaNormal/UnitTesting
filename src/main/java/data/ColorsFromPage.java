package data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ColorsFromPage {
    private Integer page;
    private Integer per_page;
    private Integer total;
    private Integer total_pages;
    private List<ColorData> data;
    private Support support;
}
