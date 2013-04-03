package se.antongomes.pvt.view.panel;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import se.antongomes.pvt.PVTSession;
import se.antongomes.pvt.service.UserService;

import java.io.Serializable;

/**
 * User authentication interface
 */
public class LoginPanel extends Panel {
    @SpringBean
    private UserService userService;

    public LoginPanel(String id) {
        super(id);

        add(new WebMarkupContainer("guest") {
            @Override
            public boolean isVisible() {
                return PVTSession.get().isGuest();
            }
        });

        WebMarkupContainer memberContainer = new WebMarkupContainer("member") {
            @Override
            public boolean isVisible() {
                return !PVTSession.get().isGuest();
            }
        };
        memberContainer.add(new Label("username", new Model() {
            @Override
            public Serializable getObject() {
                return PVTSession.get().isGuest() ? "" : PVTSession.get().getUser().getName();
            }
        }));
        memberContainer.add(new Link("logoutButton") {
            @Override
            public void onClick() {
                PVTSession.get().logOut();
            }
        });
        add(memberContainer);

        Form loginForm = new Form("loginForm");
        add(loginForm);

        final TextField usernameField = new RequiredTextField("username", Model.of(""));
        loginForm.add(usernameField);
        final TextField passwordField = new PasswordTextField("password", Model.of(""));
        loginForm.add(passwordField);

        final FeedbackPanel feedbackPanel = new FeedbackPanel("feedbackPanel");
        feedbackPanel.setOutputMarkupId(true);
        loginForm.add(feedbackPanel);

        loginForm.add(new AjaxButton("submit") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                setResponsePage(getPage().getClass(), getPage().getPageParameters());
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
//                loginForm.add(new AttributeModifier("class", Model.of("loginForm visible")));
                target.add(feedbackPanel);
            }
        });

        passwordField.add(new IValidator<String>() {

            private static final long serialVersionUID = 548356318447964952L;

            @Override
            public void validate(IValidatable<String> validatable) {
                if (!usernameField.getInput().isEmpty() && !passwordField.getInput().isEmpty()
                        && !PVTSession.get().attemptLogin(usernameField.getInput(), passwordField.getInput())) {
                    validatable.error(new ValidationError().addKey("loginFailed"));
                }
                else {
                    //TODO - this doesn't work. Go read WIA!
                    continueToOriginalDestination();
                }
            }
        });
    }
}
