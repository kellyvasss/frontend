package malmo.frontend.view;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import malmo.frontend.dto.History;
import malmo.frontend.view.layout.UserLayout;

import static malmo.frontend.api.HistoryAPI.getHistoryForUser;
//import static malmo.frontend.view.util.Util.updateGridHistory;


@Route(value = "history", layout = UserLayout.class)
public class UserHistoryView extends VerticalLayout {
    private Grid<History> grid = new Grid<>(History.class, false);
    private TextField filterText = new TextField();
    public UserHistoryView() {
        configureGrid();
        configureFilterText();

        add(
                filterText,
                grid
        );

    }
    private void configureGrid() {
        grid.addColumn(history -> history.getArticle().getName()).setHeader("Namn").setSortable(true);
        grid.addColumn(History::getQuantity).setHeader("Antal").setSortable(true);
        grid.addColumn(history -> history.getArticle().getPrice() * history.getQuantity()).setHeader("Pris").setSortable(true);
        grid.setItems(getHistoryForUser());
    }
    private void configureFilterText() {
        filterText.setPlaceholder("Filtrera på artikelnamn...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
       // filterText.addValueChangeListener(event -> getHistoryForUser());
    }


}
