package malmo.frontend.view.util;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.router.RouterLink;
import malmo.frontend.api.ArticleAPI;
import malmo.frontend.dto.Article;
import malmo.frontend.dto.History;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;

import static malmo.frontend.api.HistoryAPI.getHistoryByArticleOrUser;


public class Util {
    public static Tab createTab(VaadinIcon icon, String viewName, Class clazz) {
        Icon viewIcon = icon.create();
        viewIcon.getStyle().set("box-sizing", "border-box")
                .set("margin-inline-end", "var(--lumo-space-m)")
                .set("margin-inline-start", "var(--lumo-space-xs)")
                .set("padding", "var(--lumo-space-xs)");

        RouterLink link = new RouterLink();
        link.add(viewIcon, new Span(viewName));
        link.setRoute(clazz);
        return new Tab(link);
    }
    public static Tab createTabWithNotification(VaadinIcon icon, String viewName, Class clazz, String sum) {
        Icon viewIcon = icon.create();
        viewIcon.getStyle().set("box-sizing", "border-box")
                .set("margin-inline-end", "var(--lumo-space-m)")
                .set("margin-inline-start", "var(--lumo-space-xs)")
                .set("padding", "var(--lumo-space-xs)");

        RouterLink link = new RouterLink();
        link.add(viewIcon, new Span(viewName));
        link.setRoute(clazz);
        Span span = new Span(sum);
        span.getElement().getThemeList().add("badge small contrast");
        span.getStyle().set("margin-inline-start", "var(--lumo-space-xs)");
        return new Tab(new Span(link), span);
    }
    public static void updateCartSum(Tab tab, String sum) {
        if (tab.getChildren().count() == 2 && tab.getChildren().toArray()[1] instanceof Span) {
            Span span = (Span) tab.getChildren().toArray()[1];
            span.setText(sum);
        } else System.out.println("fel med uppdatera tab");
    }
    public static void updateGridArticles(Grid<Article> grid, String searchTerm){
        try {
            grid.setItems(ArticleAPI.getArticles(searchTerm));
        } catch (IOException e){
            System.out.println(e.getMessage());
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updateGridHistory(Grid<History> grid, String searchTerm) {
            grid.setItems(getHistoryByArticleOrUser(searchTerm));
    }
}
