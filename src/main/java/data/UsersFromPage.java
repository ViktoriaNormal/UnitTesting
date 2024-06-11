package data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

@Getter
@AllArgsConstructor
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
