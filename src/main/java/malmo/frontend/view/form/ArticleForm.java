package malmo.frontend.view.form;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import malmo.frontend.dto.Article;

public class ArticleForm extends FormLayout {

    TextField name = new TextField("Namn");
    TextField description = new TextField("Beskrivning");
    NumberField price = new NumberField("Pris");
    Button save = new Button("Spara");
    Button delete = new Button("Radera");
    Button cancel = new Button("Avbryt");

    Binder<Article> binder = new Binder<>(Article.class);

    public ArticleForm() {
        binder.bindInstanceFields(this);

        add(
                name,
                description,
                price,
                createButtons()
        );

    }

    private Component createButtons() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);

        save.addClickListener(click -> validateAndSave());
        delete.addClickListener(click -> fireEvent(new DeleteEvent(this, binder.getBean())));
        cancel.addClickListener(click -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, cancel);
    }

    private void validateAndSave() {
        if (binder.isValid()) {
            fireEvent(new SaveEvent(this, binder.getBean()));
        }
    }

    public void setArticle(Article article) {
        binder.setBean(article);
    }


    // Save, Cancel och Delete-events

    public static abstract class ArticleFormEvent extends ComponentEvent<ArticleForm> {
        private Article article;

        protected ArticleFormEvent(ArticleForm source, Article article) {
            super(source, false);
            this.article = article;
        }

        public Article getArticle() {
            return article;
        }
    }
    public static class SaveEvent extends ArticleFormEvent{

        SaveEvent(ArticleForm source, Article article) {
            super(source, article);
        }
    }
    public static class DeleteEvent extends ArticleFormEvent{
        DeleteEvent(ArticleForm source, Article article) {
            super(source, article);
        }
    }
    public static class CloseEvent extends ArticleFormEvent {
        CloseEvent(ArticleForm source) {
            super(source, null);
        }
    }
    public Registration addSaveListener(ComponentEventListener<SaveEvent> listener) {
        return addListener(SaveEvent.class, listener);
    }
    public Registration addDeleteListener(ComponentEventListener<DeleteEvent> listener) {
        return addListener(DeleteEvent.class, listener);
    }
    public Registration addCloseListener(ComponentEventListener<CloseEvent> listener) {
        return addListener(CloseEvent.class, listener);
    }

}
