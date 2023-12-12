package malmo.frontend.view;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import malmo.frontend.dto.Article;
import malmo.frontend.view.layout.MainLayout;

import static malmo.frontend.view.util.Util.updateGrid;

@Route(value = "", layout = MainLayout.class)
public class HomeView extends VerticalLayout {
    Grid<Article> grid = new Grid<>(Article.class, false);
    TextField filterText = new TextField();
    public HomeView() {

        configureGrid();
        configureFilterText();

        add(
              filterText,
              grid
        );
        updateGrid(grid, filterText.getValue());
    }

    private void configureFilterText() {
        filterText.setPlaceholder("Filtrera på namn...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(event -> updateGrid(grid, filterText.getValue()));
    }

    private void configureGrid() {
        grid.addColumn(Article::getName).setHeader("Namn");
        grid.addColumn(Article::getDescription).setHeader("Beskrivning");
        grid.addColumn(Article::getPrice).setHeader("Pris");
    }

}
