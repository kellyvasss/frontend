package malmo.frontend.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import malmo.frontend.api.CartAPI;
import malmo.frontend.dto.Cart;
import malmo.frontend.view.util.button.CartButton;
import malmo.frontend.view.layout.UserLayout;

@Route(value = "cart", layout = UserLayout.class)
public class CartView extends VerticalLayout {

    private Grid<Cart> grid = new Grid<>(Cart.class, false);
    private double total;
    public CartView() {
        configureGrid();

        add(

                grid
        );
    }

    private void configureGrid() {
        grid.addColumn(cart -> cart.getArticle().getName()).setHeader("Namn");
        grid.addColumn(Cart::getQuantity).setHeader("Antal");
        grid.addColumn(cart -> cart.getArticle().getCost() * cart.getQuantity()).setHeader("Pris");

        grid.addComponentColumn(cart -> {
            // uppdatera med en
            CartButton add = new CartButton(VaadinIcon.PLUS, cart.getArticle().getName(), false);
            return add;
        }).setWidth("150px").setFlexGrow(0);
        grid.addComponentColumn(cart -> {
            // ta bort en
            CartButton minus = new CartButton(VaadinIcon.MINUS, cart.getArticle().getName(), cart.getQuantity());
            return minus;

        }).setWidth("150px").setFlexGrow(0);

        grid.addComponentColumn(cart -> {
            // ta bort alla
            CartButton delete = new CartButton(VaadinIcon.TRASH, cart.getArticle().getName(), true);
            return delete;
        }).setWidth("150px").setFlexGrow(0);

    }
    private static void deleteArticleFromCart(String articleName) {
        CartAPI.deleteItemFromCart(articleName);
    }

    private void showConfirmDialog(String articleName) {
        Dialog dialog = new Dialog();
        dialog.add(createDialogContent(dialog, articleName));

    }
    private static VerticalLayout createDialogContent(Dialog dialog, String articleName) {
        H2 header = new H2(String.format("Radera %s?", articleName));
        header.getStyle().set("margin", "var(--lumo-space-m) 0")
                .set("font-size", "1.5em").set("font-weight", "bold");
        
        Button btnCancel = new Button("Avbryt");
        Button btnDelete = new Button("Radera");
        btnDelete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        btnCancel.addClickListener(e -> dialog.close());
        btnDelete.addClickListener(e -> {
            deleteArticleFromCart(articleName);
            dialog.close();
        });
        return new VerticalLayout(header, btnCancel, btnDelete);
    }



}
