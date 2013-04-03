package se.antongomes.pvt.view.panel;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import se.antongomes.pvt.PVTSession;

public class NavigationPanel extends Panel {
    private static final long serialVersionUID = 9016864306682188929L;

    public NavigationPanel(String id) {
        super(id);

        add(new WebMarkupContainer("membersOnly") {
            private static final long serialVersionUID = -3509848691937148180L;

            @Override
            public boolean isVisible() {
                return !PVTSession.get().isGuest();
            }
        });
    }
}
