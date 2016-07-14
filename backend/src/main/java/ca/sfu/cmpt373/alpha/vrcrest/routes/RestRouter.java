package ca.sfu.cmpt373.alpha.vrcrest.routes;

import ca.sfu.cmpt373.alpha.vrcladder.ApplicationManager;
import ca.sfu.cmpt373.alpha.vrcladder.users.User;
import ca.sfu.cmpt373.alpha.vrcladder.users.UserManager;
import ca.sfu.cmpt373.alpha.vrcladder.users.authentication.SecurityManager;
import ca.sfu.cmpt373.alpha.vrcladder.users.authorization.UserAction;
import ca.sfu.cmpt373.alpha.vrcladder.users.personal.UserId;
import ca.sfu.cmpt373.alpha.vrcrest.security.RouteSignature;
import com.google.gson.Gson;
import spark.Request;

import java.util.List;

public abstract class RestRouter {

    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String PARAM_ID = ":id";

    public static final String JSON_PROPERTY_ERROR = "error";

    protected static final String ERROR_COULD_NOT_COMPLETE_REQUEST = "Request could not be completed.";
    protected static final String ERROR_MALFORMED_JSON = "The provided JSON in the request body is malformed.";
    protected static final String ERROR_INVALID_RESOURCE_ID = "The provided resource identifier is invalid.";

    private SecurityManager securityManager;
    private UserManager userManager;
    private Gson gson;

    public RestRouter(ApplicationManager applicationManager) {
        securityManager = applicationManager.getSecurityManager();
        userManager = applicationManager.getUserManager();
        gson = buildGson();
    }

    protected UserId extractUserIdFromRequest(Request request) {
        String authorizationToken = request.headers(HEADER_AUTHORIZATION);
        return securityManager.parseToken(authorizationToken);
    }

    protected User extractUserFromRequest(Request request) {
        String authorizationToken = request.headers(HEADER_AUTHORIZATION);
        UserId userId = securityManager.parseToken(authorizationToken);
        return userManager.getById(userId);
    }

    protected Gson getGson() {
        return gson;
    }

    public abstract void attachRoutes();
    public abstract List<RouteSignature> getPublicRouteSignatures();
    protected abstract Gson buildGson();

}
