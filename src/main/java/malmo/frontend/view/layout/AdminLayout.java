package malmo.frontend.view.layout;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;

import java.time.Instant;
public class AdminLayout extends AppLayout {
    
    public AdminLayout() {
        setId("cool-clock");
        createHeader();
        //createDrawer();
    }

    private void createHeader() {
        Instant instant = Instant.now();
        H1 h1 = new H1("Välkommen Boss! ");
    }
    @Override
    protected void onAttach(AttachEvent event) {
        super.onAttach(event);
    }


}
