package se.antongomes.pvt.view;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import se.antongomes.pvt.PVTSession;
import se.antongomes.pvt.data.model.User;
import se.antongomes.pvt.service.UserService;

public class RegisterPage extends BasePage {

    @SpringBean
    private UserService userService;

    public RegisterPage() {
        Form registrationForm = new Form("registrationForm");
        add(registrationForm);

        final TextField usernameField = new RequiredTextField("username", Model.of(""));
        registrationForm.add(usernameField);
        final TextField passwordField = new PasswordTextField("password", Model.of(""));
        registrationForm.add(passwordField);
        final TextField emailField = new EmailTextField("email", Model.of(""));
        emailField.setRequired(true);
        registrationForm.add(emailField);

        final FeedbackPanel feedbackPanel = new FeedbackPanel("feedbackPanel");
        feedbackPanel.setOutputMarkupId(true);
        registrationForm.add(feedbackPanel);

        registrationForm.add(new AjaxButton("submit") {
            /*@Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
//                setResponsePage(getPage().getClass(), getPage().getPageParameters());
            }
*/
            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                target.add(feedbackPanel);
            }
        });


        passwordField.add(new IValidator<String>() {

            @Override
            public void validate(IValidatable<String> validatable) {
                if (usernameField.getInput().isEmpty() || passwordField.getInput().isEmpty() || emailField.getInput().isEmpty())
                    return;

                try {
                    User newUser = userService.createUserAccount(usernameField.getInput(), passwordField.getInput(), emailField.getInput());
                    //account creation was successful!
                    PVTSession.get().setUser(newUser);
                    setResponsePage(HomePage.class);
                } catch (UserService.ReservedUsernameException e) {
                    validatable.error(new ValidationError().addKey("registration.usernameTaken"));
                } catch (UserService.DuplicateEMailException e) {
                    validatable.error(new ValidationError().addKey("registration.duplicateEmail"));
                }
            }
        });
    }
}
