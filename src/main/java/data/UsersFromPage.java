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
public class UsersFromPage {
    private Integer page;
    private Integer per_page;
    private Integer total;
    private Integer total_pages;
    private List<UserData> data;
    private Support support;

    public UsersFromPage(List<UserData> data, Support support) {
        this.data = data;
        this.support = support;
    }
}
