package malmo.frontend.view;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import malmo.frontend.dto.Article;
import malmo.frontend.dto.Cart;
import malmo.frontend.dto.CartItem;
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
            Icon plus = new Icon(VaadinIcon.PLUS);
            plus.addClickListener(click -> {
                // Uppdatera antal med en
            });
            return plus;
        });
        grid.addComponentColumn(cart -> {
            Icon minus = new Icon(VaadinIcon.MINUS);
            minus.addClickListener(click -> {
                // Ta bort en från kundkorg
            });
            return minus;
        })

        grid.addComponentColumn(cart -> {
            Icon trash = new Icon(VaadinIcon.TRASH);
            trash.addClickListener(click -> {
                // Ta bort alla

            });
            return trash;
        }).setWidth("150px").setFlexGrow(0);



        grid.asSingleSelect().addValueChangeListener(marked -> article = marked.getValue());
    }


}
