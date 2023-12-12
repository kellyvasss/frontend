package malmo.frontend.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import malmo.frontend.dto.Article;
import malmo.frontend.view.form.ArticleForm;
import malmo.frontend.view.layout.AdminLayout;



@Route(value = "admin", layout = AdminLayout.class)
public class AdminView extends VerticalLayout {

    Grid<Article> grid = new Grid<>(Article.class, false);
    TextField filter = new TextField();
    ArticleForm form;

    public AdminView() {
        configureGrid();
        configureForm();

        add(
                getToolBar(),
                getContent()
        );
        updateList();
        closeEditor();
    }

    private HorizontalLayout getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.setSizeFull();
        return content;
    }
    private void configureForm() {
        form = new ArticleForm();
        form.setWidth("25em");
        form.addSaveListener(this::saveArticle);
        form.addDeleteListener(this::deleteArticle);
        form.addCloseListener(e -> closeEditor());
    }
    private void configureGrid() {
        grid.addColumn(Article::getName).setHeader("Namn");
        grid.addColumn(Article::getDescription).setHeader("Beskrivning");
        grid.addColumn(Article::getCost).setHeader("Pris");

        grid.asSingleSelect().addValueChangeListener(marked -> editArticle(marked.getValue()));
    }
    private Component getToolBar() {
        filter.setPlaceholder("Filtrera på namn...");
        filter.setClearButtonVisible(true);
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(search -> updateList());

        Button btnAddArticle = new Button("Lägg till artikel");
        btnAddArticle.addClickListener(click -> addArticle());
        return new HorizontalLayout(filter, btnAddArticle);
    }

    private void updateList() {
        grid.setItems(getArticles());
    }

    private void addArticle() {
        grid.asSingleSelect().clear();
        editArticle(new Article());
    }
    public void editArticle(Article article) {
        if (article == null) {
            closeEditor();
        }
        else {
            form.setArticle(article);
            form.setVisible(true);
        }
    }
    private void closeEditor() {
        form.setArticle(null);
        form.setVisible(false);
    }
    private void saveArticle(ArticleForm.SaveEvent event) {
        // Kalla på api för att spara artikeln
        event.getArticle();
        updateList();
        closeEditor();
    }
    private void deleteArticle(ArticleForm.DeleteEvent event) {
        // Kalla på api för att radera en artikel
        event.getArticle();
        updateList();
        closeEditor();
    }


}
