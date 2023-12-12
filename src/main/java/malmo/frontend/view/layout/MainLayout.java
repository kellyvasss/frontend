package malmo.frontend.view.layout;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import malmo.frontend.view.ArticleView;
import org.apache.hc.core5.http.ParseException;
import static malmo.frontend.api.Login.login;
import static malmo.frontend.view.util.Util.createTab;

import java.io.IOException;

public class MainLayout extends AppLayout {
    public MainLayout() {
        createHeader();
        createDrawer();
    }
    private void createHeader() {
        H1 logo = new H1("MALMÖ SHOP");
        Button btnLogin = new Button("Logga in", e -> openLoginDialog());

        addToNavbar(new HorizontalLayout(new DrawerToggle(), logo, btnLogin));
    }

    private void openLoginDialog() {
        Dialog loginDialog = new Dialog();
        loginDialog.add(createLoginForm(loginDialog));
        loginDialog.open();
    }

    private FormLayout createLoginForm(Dialog loginDialog) {
        FormLayout loginForm = new FormLayout();
        TextField username = new TextField("Användarnamn");
        TextField password = new TextField("Lösenord");
        Button btnLogin = new Button("Logga in", event -> {
            try {
                handleLoginResult(loginDialog, username.getValue(), password.getValue());
            } catch (IOException e) {
                // Felhantering
                System.out.println("IO fel" + e.getMessage());
            } catch (ParseException e) {
                // Felhantering
                System.out.println("Parse fel" + e.getMessage());
            }
        });

        loginForm.add(username, password, btnLogin);
        return loginForm;
    }

    private void handleLoginResult(Dialog loginDialog, String username, String password) throws IOException, ParseException {
        boolean loginSuccess = performLogin(username, password);
        if (loginSuccess) {

            loginDialog.close();
        } // skicka felmeddelande
    }

    private boolean performLogin(String username, String password) throws IOException, ParseException{
        return login(username, password);
    }

    private void createDrawer() {
        Tabs tabs = new Tabs();
        tabs.add(
                createTab(VaadinIcon.PACKAGE, "Artiklar", ArticleView.class));
        addToDrawer(tabs);
    }


}
