package malmo.frontend.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.UI;
import malmo.frontend.dto.LoginResponse;
import malmo.frontend.dto.User;
import malmo.frontend.view.AdminView;
import malmo.frontend.view.ArticleView;
import malmo.frontend.view.layout.UserLayout;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.IOException;

import static malmo.frontend.api.Payload.createPayload;

public class LoginAPI {

    private static final CloseableHttpClient httpClient = HttpClients.createDefault();

    public static String getJwt() {
        return jwt;
    }

    private static String jwt = null;

    public static boolean login(String username, String password) throws IOException, ParseException {
        User loginUser = new User(username, password);
        HttpPost req = new HttpPost("http://localhost:8080/auth/login-user");
        req.setEntity(createPayload(loginUser));
        CloseableHttpResponse response = httpClient.execute(req);

        if (response.getCode() != 200) {
            return false;
        }
        HttpEntity payload = response.getEntity();
        ObjectMapper mapper = new ObjectMapper();
        LoginResponse loginResponse = mapper.readValue(EntityUtils.toString(payload), new TypeReference<LoginResponse>() {});

        if (loginResponse.user() == null) {
            return false;
        }
        jwt = loginResponse.jwt();
        UserLayout.setUsername(loginResponse.user().getUsername());
        if (loginResponse.user().getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ADMIN"))) {
            UI.getCurrent().navigate(AdminView.class);
        } else {
            UI.getCurrent().navigate(ArticleView.class);
        }
        return true;

    }
}
