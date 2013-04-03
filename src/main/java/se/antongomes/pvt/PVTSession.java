package se.antongomes.pvt;

import org.apache.wicket.Session;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;
import org.apache.wicket.spring.injection.annot.SpringBean;
import se.antongomes.pvt.data.model.User;
import se.antongomes.pvt.service.UserService;

/**
* Wicket user web session.
* @see WebSession
*/
public class PVTSession extends WebSession {
    @SpringBean
    private UserService userService;

    private User user;

    public PVTSession(Request request) {
        super(request);

        //Inject SpringBeans into this session object
        Injector.get().inject(this);
    }

    /**
     * Get the session for the calling thread.
     */
    public static se.antongomes.pvt.PVTSession get() {
        return (se.antongomes.pvt.PVTSession) Session.get();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Whether the user is logged in or not.
     */
    public boolean isGuest() {
        return user == null;
    }

    /**
     * Logs the current user out.
     */
    public void logOut() {
        user = null;
        this.invalidate();
    }

    /**
     * Tries to log in with the specified credentials.
     * @return true if successful, otherwise false.
     */
    public boolean attemptLogin(String username, String password) {
        User loggedInUser = userService.attemptLogin(username, password);
        if (loggedInUser != null) {
            setUser(loggedInUser);
            return true;
        }
        return false;
    }
}
