package malmo.frontend.view.util;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.router.RouterLink;

public class Util {
    public static Tab createTab(VaadinIcon icon, String viewName, Class clazz) {
        Icon viewIcon = icon.create();
        viewIcon.getStyle().set("box-sizing", "border-box")
                .set("margin-inline-end", "var(--lumo-space-m)")
                .set("margin-inline-start", "var(--lumo-space-xs)")
                .set("padding", "var(--lumo-space-xs)");

        RouterLink link = new RouterLink();
        link.add(viewIcon, new Span(viewName));
        link.setRoute(clazz);
        return new Tab(link);
    }
}
