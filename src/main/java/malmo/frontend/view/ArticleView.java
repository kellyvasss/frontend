package malmo.frontend.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import malmo.frontend.api.CartAPI;
import malmo.frontend.dto.Article;
import malmo.frontend.dto.CartItem;
import malmo.frontend.view.layout.UserLayout;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


import static malmo.frontend.view.util.Util.updateGridArticles;

@Route(value = "articles", layout = UserLayout.class)
public class ArticleView extends VerticalLayout {

    private Grid<Article> grid = new Grid<>(Article.class, false);
    private TextField filterText = new TextField();
    private Article article;
    public ArticleView() {

        configureGrid();
        configureFilterText();

        add(
                filterText,
                grid
        );

    }

    private void configureGrid() {
        grid.addColumn(Article::getName).setHeader("Namn");
        grid.addColumn(Article::getDescription).setHeader("Beskrivning");
        grid.addColumn(Article::getPrice).setHeader("Pris");

        grid.addComponentColumn(article -> {
            Icon cart = new Icon(VaadinIcon.CART);
            cart.addClickListener(click -> {
                // Dialog ruta för köp
                article.getName();
                this.article = article;
                openAddArticleDialog();
                
            });
            return cart;
        }).setWidth("150px").setFlexGrow(0);
        //grid.asSingleSelect().addValueChangeListener(marked -> article = marked.getValue());
        updateGridArticles(grid, filterText.getValue());
    }

    private void openAddArticleDialog() {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Lägg till artikel " + article.getName());
        ComboBox<Integer> quantity = new ComboBox<>("Antal", IntStream.rangeClosed(1,10).boxed().collect(Collectors.toList()));
        quantity.setValue(1);
        dialog.add(quantity);

        Button btnSave = createSaveButton(dialog, quantity);
        Button btnCancel = new Button("Avbryt", click -> dialog.close());
        dialog.getFooter().add(btnSave, btnCancel);
        dialog.open();

    }

    private Button createSaveButton(Dialog dialog, ComboBox<Integer> quantity) {
        Button btnSave = new Button("Lägg till i kundkorg", click -> {
            // kalla på api spara artikel till kundkorg
            //double quantity = quantityField.getValue() >= 1 && quantityField.getValue() != null  ? quantityField.getValue().doubleValue() : 1;
            Integer selected = quantity.getValue();
            CartItem cartItem = new CartItem(article.getName(), selected);
            try {
                CartAPI.addToCart(cartItem);

            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }
            dialog.close();
        });
        btnSave.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        return btnSave;
    }

    private void configureFilterText() {
        filterText.setPlaceholder("Filtrera på namn...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(event -> updateGridArticles(grid, filterText.getValue()));
    }


    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }



}
