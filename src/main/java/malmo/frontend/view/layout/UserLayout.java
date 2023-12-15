package malmo.frontend.view.layout;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import malmo.frontend.view.AdminView;
import malmo.frontend.view.ArticleView;
import malmo.frontend.view.CartView;
import malmo.frontend.view.UserHistoryView;

import static malmo.frontend.api.LoginAPI.getJwt;
import static malmo.frontend.view.util.Util.createTab;

public class UserLayout extends AppLayout {
    private static String username = null;
    public UserLayout() {
        createHeader();
        createDrawer();
    }

    private void createDrawer() {
        addToDrawer(new VerticalLayout(
                createTab(VaadinIcon.PACKAGE, "Artiklar", ArticleView.class),
                createTab(VaadinIcon.CART, "Kundkorg", CartView.class),
                createTab(VaadinIcon.PIGGY_BANK_COIN, "Köphistorik", UserHistoryView.class)
        ));
    }

    private void createHeader() {
        H1 logo = new H1(String.format("Välkommen till Malmö Shop, %s!", getUsername()));
        //addToNavbar(new DrawerToggle(), logo);
        Button btnAdmin = new Button("Gå till adminsida");
        btnAdmin.addClickListener(click -> UI.getCurrent().navigate(AdminView.class));
        if (getUsername().equals("admin") && getJwt() != null) {
            // sätt admin funktion knapp
            addToNavbar(new DrawerToggle(), new H1("Välkommen Admin!"), btnAdmin);
        } else {
            addToNavbar(new DrawerToggle(), logo);
        }
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        UserLayout.username = username;
    }





}
