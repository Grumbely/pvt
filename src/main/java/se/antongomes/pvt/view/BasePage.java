package se.antongomes.pvt.view;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import se.antongomes.pvt.view.panel.LoginPanel;
import se.antongomes.pvt.view.panel.NavigationPanel;

public class BasePage extends WebPage {
    private static final long serialVersionUID = -5753095673495926422L;

    public BasePage() {
        this(new PageParameters());
    }

    public BasePage(PageParameters parameters) {
        super(parameters);
        add(new LoginPanel("loginPanel"));
        add(new NavigationPanel("navPanel"));
    }
}
