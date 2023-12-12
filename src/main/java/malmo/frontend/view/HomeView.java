package malmo.frontend.view;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import malmo.frontend.view.layout.MainLayout;

@Route(value = "home", layout = MainLayout.class)
public class HomeView extends VerticalLayout {
    public HomeView() {
        H1 h1 = new H1("HEJ");
    }
}
