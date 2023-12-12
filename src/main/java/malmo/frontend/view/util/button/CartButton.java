package malmo.frontend.view.util.button;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import malmo.frontend.api.CartAPI;
import malmo.frontend.dto.CartItem;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;

public class CartButton extends Button {
    // Andvänd vid uppdatera (+) och ta bort en hel rad
    public CartButton(VaadinIcon vaadinIcon, String articleName, boolean delete) {
        Icon icon = new Icon(vaadinIcon);
        setIcon(icon);
        CartItem cartItem = new CartItem(articleName, 1);
        addClickListener(e -> {
            if (!delete) {
                try {
                    CartAPI.updateCart(cartItem);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
            else {
                try {
                    CartAPI.deleteItemFromCart(articleName);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

        });
    }

    // Använd vid uppdatera (-), kontrollerar först om en hel rad skall raderas
    public CartButton(VaadinIcon vaadinIcon, String articleName, int oldQuantity) {
        Icon icon = new Icon(vaadinIcon);
        setIcon(icon);
        if (oldQuantity - 1 == 0) {
            addClickListener(click ->
            {
                try {
                    CartAPI.deleteItemFromCart(articleName);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        else {
            CartItem cartItem = new CartItem(articleName, -1);
            addClickListener(click -> {
                try {
                    CartAPI.updateCart(cartItem);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

}
