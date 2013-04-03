package se.antongomes.pvt.view;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import se.antongomes.pvt.service.UserService;

public class HomePage extends BasePage {

    private static final long serialVersionUID = -7706895432167836127L;
    @SpringBean
    private UserService userService;

    public HomePage() {
        add(new Label("userCount", new Model<Long>() {
            @Override
            public Long getObject() {
                return userService.userCount();
            }
        }));
    }
}