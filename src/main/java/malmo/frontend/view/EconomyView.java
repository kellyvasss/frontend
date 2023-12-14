package malmo.frontend.view;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import malmo.frontend.api.HistoryAPI;
import malmo.frontend.dto.History;
import malmo.frontend.view.layout.AdminLayout;

import static malmo.frontend.view.util.Util.updateGridHistory;

@Route(value = "economy", layout = AdminLayout.class)
public class EconomyView extends VerticalLayout {
    private Grid<History> grid = new Grid<>(History.class, false);
    private TextField filterText = new TextField();

    public EconomyView() {

    }
    private void configureGrid() {
        grid.addColumn(history -> history.getArticle().getName()).setHeader("Namn");
        grid.addColumn(History::getQuantity).setHeader("Antal");
        grid.addColumn(history -> history.getArticle().getPrice() * history.getQuantity()).setHeader("Pris");
        grid.setItems(HistoryAPI.getHistoryForUser());
    }
    private void configureFilterText() {
        filterText.setPlaceholder("Filtrera på artikelnamn...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(event -> updateGridHistory(grid, filterText.getValue()));
    }

}
