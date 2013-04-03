package se.antongomes.pvt;

import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import se.antongomes.pvt.view.HomePage;
import se.antongomes.pvt.view.RegisterPage;
import se.antongomes.pvt.view.error.ForbiddenPage;
import se.antongomes.pvt.view.error.NotFoundPage;

@Component(value="wicketApplication")
public class PVTWicketApplication extends WebApplication {
    @Value("${wicket.deployment.mode}")
    private RuntimeConfigurationType configurationType = RuntimeConfigurationType.DEVELOPMENT;

    @Autowired
    private ApplicationContext appContext;

    @Override
    public Class<HomePage> getHomePage() {
            return HomePage.class;
    }

    @Override
    public void init()
    {
        super.init();

        //For Wicket 1.5 and newer
        this.getComponentInstantiationListeners().add(new SpringComponentInjector(this));

        //For Wicket 1.4
        //addComponentInstantiationListener(new SpringComponentInjector(this, appContext, true));

        //error pages
        mountPage("/404", NotFoundPage.class);
        mountPage("/403", ForbiddenPage.class);

        mountPage("/register", RegisterPage.class);

//        mountSharedResource("/static/pvt.css", new ResourceReference(PVTWicketApplication.class, "view/resource/pvt.css").getSharedResourceKey());

        //set encoding of HTML files to UTF-8
        getMarkupSettings().setDefaultMarkupEncoding("UTF-8");

        //we never want this!
        getMarkupSettings().setStripWicketTags(true);
    }

    @Override
    public Session newSession(Request request, Response response) {
        return new PVTSession(request);
    }

    @Override
    public RuntimeConfigurationType getConfigurationType() {
        return configurationType;
    }
}
