package malmo.frontend.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import malmo.frontend.api.HistoryAPI;
import malmo.frontend.dto.History;
import malmo.frontend.view.layout.AdminLayout;
import malmo.frontend.view.layout.UserLayout;

import static malmo.frontend.api.HistoryAPI.getAllHistory;
import static malmo.frontend.view.util.Util.updateGridHistory;

@Route(value = "economy", layout = AdminLayout.class)
public class AdminHistoryView extends VerticalLayout {
    private Grid<History> grid = new Grid<>(History.class, false);
    private TextField filterText = new TextField();

    public AdminHistoryView() {


        configureGrid();
        configureFilterText();
        add(
                filterText,
                grid
        );
        updateGridHistory(grid, filterText.getValue());

    }
    private void configureGrid() {
        grid.addColumn(history -> history.getUser().getUsername()).setHeader("Användare").setSortable(true);
        grid.addColumn(history -> history.getArticle().getName()).setHeader("Namn").setSortable(true);
        grid.addColumn(History::getQuantity).setHeader("Antal").setSortable(true);
        grid.addColumn(history -> history.getArticle().getPrice() * history.getQuantity()).setHeader("Pris").setSortable(true);
        grid.setItems(getAllHistory());
    }
    private void configureFilterText() {
        filterText.setPlaceholder("Filtrera på artikelnamn eller användare...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        filterText.setSizeFull();
        filterText.addValueChangeListener(event -> updateGridHistory(grid, filterText.getValue()));
    }

}
