package malmo.frontend.view;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import malmo.frontend.dto.Article;
import malmo.frontend.view.layout.MainLayout;

import static malmo.frontend.view.util.Util.updateGridArticles;

@Route(value = "", layout = MainLayout.class)
public class MainView extends VerticalLayout {
    Grid<Article> grid = new Grid<>(Article.class, false);
    TextField filterText = new TextField();
    public MainView() {

        configureGrid();
        configureFilterText();

        add(
              filterText,
              grid
        );
        updateGridArticles(grid, filterText.getValue());
    }

    private void configureFilterText() {
        filterText.setPlaceholder("Filtrera på namn...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(event -> updateGridArticles(grid, filterText.getValue()));
    }

    private void configureGrid() {
        grid.addColumn(Article::getName).setHeader("Namn");
        grid.addColumn(Article::getDescription).setHeader("Beskrivning");
        grid.addColumn(Article::getPrice).setHeader("Pris");
    }

}
