package malmo.frontend.view.layout;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import malmo.frontend.view.AdminView;
import malmo.frontend.view.AdminHistoryView;
import malmo.frontend.view.ArticleView;
import malmo.frontend.view.MainView;

import static malmo.frontend.view.layout.UserLayout.getUsername;
import static malmo.frontend.view.util.Util.createTab;

public class AdminLayout extends AppLayout {
    
    public AdminLayout() {

        createHeader();
        createDrawer();
    }
    private void createHeader() {
        H1 logo = new H1("Välkommen Boss!");
        Icon boss = new Icon(VaadinIcon.HANDSHAKE);
        Button btnAdmin = new Button("Gå till användarsida");
        btnAdmin.addClickListener(click -> UI.getCurrent().navigate(ArticleView.class));
        addToNavbar(new DrawerToggle(), logo, boss, btnAdmin);
    }
    private void createDrawer() {
        addToDrawer(
                createTab(VaadinIcon.PACKAGE, "Artiklar", AdminView.class),
                createTab(VaadinIcon.LINE_CHART, "Ekonomi", AdminHistoryView.class)
        );

    }



}
