package se.antongomes.pvt.view;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import se.antongomes.pvt.view.panel.LoginPanel;
import se.antongomes.pvt.view.panel.NavigationPanel;

public class BasePage extends WebPage implements IHeaderContributor {
    private static final long serialVersionUID = -5753095673495926422L;

    public BasePage() {
        this(new PageParameters());
    }

    public BasePage(PageParameters parameters) {
        super(parameters);
        add(new LoginPanel("loginPanel"));
        add(new NavigationPanel("navPanel"));
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        response.render(CssHeaderItem.forUrl("static/style/pvt.css"));
        response.render(JavaScriptHeaderItem.forUrl("static/script/pvt.js"));
    }
}
