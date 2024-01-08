package malmo.frontend.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import malmo.frontend.api.CartAPI;
import malmo.frontend.dto.Cart;
import malmo.frontend.view.util.button.CartButton;
import malmo.frontend.view.layout.UserLayout;

import java.io.IOException;
import java.util.List;

import static malmo.frontend.api.CartAPI.getCart;
import static malmo.frontend.api.CartAPI.getCartAmount;
import static malmo.frontend.view.util.Util.showNotification;

@Route(value = "cart", layout = UserLayout.class)
public class CartView extends VerticalLayout {

    private Grid<Cart> grid = new Grid<>(Cart.class, false);
    private Button btnBuy = new Button("Köp");
    private H4 totalAmount = new H4();

    public CartView() {

        configureGrid();
        configureBuyButton();
        configureTotalAmount();
        add(
                grid,
                new HorizontalLayout(totalAmount, btnBuy)
        );
    }

    private void configureTotalAmount() {
        totalAmount.setText(getCartAmount());
    }


    private void configureBuyButton() {
        btnBuy.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

        btnBuy.addClickListener(click -> {

            try {
                CartAPI.buy();
                showNotification(NotificationVariant.LUMO_SUCCESS, "Köp genomfört!");
                updateGrid();
            } catch (IOException e) {
               showNotification(NotificationVariant.LUMO_ERROR, "Köp misslyckat!");
                throw new RuntimeException(e);
            }
        });
    }

    private void configureGrid() {
        grid.addColumn(cart -> cart.getArticle().getName()).setHeader("Namn");
        grid.addColumn(Cart::getQuantity).setHeader("Antal");
        grid.addColumn(cart -> cart.getArticle().getPrice() * cart.getQuantity()).setHeader("Pris");

        grid.addComponentColumn(cart -> {
            // uppdatera med en
            CartButton add = new CartButton(VaadinIcon.PLUS, cart.getArticle().getName(), false);
            add.addClickListener(click -> {
                updateGrid();

            });
            return add;
        }).setWidth("120px").setFlexGrow(0);
        grid.addComponentColumn(cart -> {
            // ta bort en
            CartButton minus = new CartButton(VaadinIcon.MINUS, cart.getArticle().getName(), cart.getQuantity());
            minus.addClickListener(click -> {
                updateGrid();
            });
            return minus;

        }).setWidth("120px").setFlexGrow(0);

        grid.addComponentColumn(cart -> {
            // ta bort alla
            CartButton delete = new CartButton(VaadinIcon.TRASH, cart.getArticle().getName(), true);
            delete.addClickListener(click -> updateGrid());
            return delete;
        }).setWidth("120px").setFlexGrow(0);
        updateGrid();
    }
    private void updateGrid() {
        List<Cart> items = getCart();
        grid.setItems(items);
        configureTotalAmount();
        if (items.isEmpty()) btnBuy.setEnabled(false);
    }

}
