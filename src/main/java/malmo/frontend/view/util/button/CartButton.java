package malmo.frontend.view.util.button;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.NotificationVariant;
import malmo.frontend.api.CartAPI;
import malmo.frontend.dto.CartItem;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import static malmo.frontend.view.util.Util.showNotification;

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
                    showNotification(NotificationVariant.LUMO_PRIMARY, articleName + " uppdaterad!");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
            else {
                try {
                    CartAPI.deleteItemFromCart(articleName);
                    showNotification(NotificationVariant.LUMO_WARNING, articleName + " borttagen!");
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
                    showNotification(NotificationVariant.LUMO_WARNING, articleName + " borttagen!");
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
                    showNotification(NotificationVariant.LUMO_WARNING, articleName + " uppdaterad!");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

}
