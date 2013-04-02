package se.antongomes.pvt;

import org.apache.wicket.Session;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
* Wicket user web session.
* @see WebSession
*/
public class PVTSession extends WebSession {
    @SpringBean
    private FooService fooService;

    public PVTSession(Request request) {
        super(request);

        //Inject SpringBeans into this session object
        Injector.get().inject(this);
    }

    /**
     * Get the session for the calling thread.
     */
    public static se.deathgame.PVTSession get() {
        return (se.deathgame.PVTSession) Session.get();
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Whether the user is logged in or not.
     */
    public boolean isGuest() {
        return player == null;
    }

    /**
     * Whether the user has a higher or equal clearance to prefect or not.
     */
    public boolean isAdmin() {
        return player != null && (player.getPrivilegeLevel() == Player.PrivilegeLevel.GAMEMASTER || player.getPrivilegeLevel() == Player.PrivilegeLevel.PREFECT);
    }

    /**
     * Whether the user has a higher or equal clearance to moderator or not.
     */
    public boolean isMod() {
        return player != null && (player.getPrivilegeLevel() != Player.PrivilegeLevel.GAMEMASTER || player.getPrivilegeLevel() == Player.PrivilegeLevel.PREFECT || player.getPrivilegeLevel() == Player.PrivilegeLevel.MODERATOR);
    }

    /**
     * Logs the current user out.
     */
    public void logOut() {
        player = null;
        this.invalidate();
    }

    /**
     * Tries to log in with the specified credentials.
     * @return true if successful, otherwise false.
     */
    public boolean attemptLogin(String username, String password) {
        Player loggedInPlayer = playerService.attemptLogin(username, password);
        if (loggedInPlayer != null) {
            setPlayer(loggedInPlayer);
            return true;
        }
        return false;
    }
}
