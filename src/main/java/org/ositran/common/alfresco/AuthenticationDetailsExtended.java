/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.common.alfresco;

import java.util.Date;
import org.alfresco.webservice.util.AuthenticationDetails;

/**
 *
 * @author javier castillo
 */
public class AuthenticationDetailsExtended extends AuthenticationDetails {
    private Date lastTimeUsed;
    private long timeoutIntervalInMillis;
    private boolean noValido = false;

    public AuthenticationDetailsExtended(String userName, String ticket, String sessionId, long timeoutIntervalInMillis) {
        super(userName, ticket, sessionId);
        this.lastTimeUsed = new Date();
        this.timeoutIntervalInMillis = timeoutIntervalInMillis;
    }

    public AuthenticationDetailsExtended(AuthenticationDetails ad, long timeoutIntervalInMillis) {
        this(ad.getUserName(), ad.getTicket(), ad.getSessionId(), timeoutIntervalInMillis);
    }

    public AuthenticationDetailsExtended(boolean noValido) {
        super("", "", "");
        this.noValido = noValido;
    }

    public final boolean isTicketExpirado() {
        long nowInMillis = System.currentTimeMillis();
        long expirationTimeInMillis = (lastTimeUsed.getTime() + timeoutIntervalInMillis);

        return (nowInMillis > expirationTimeInMillis);
    }

    public void resetearTiempoTimeout() {
        lastTimeUsed = new Date();
    }

    public Date getLastTimeUsed() {
        return lastTimeUsed;
    }

    public long getTimeoutIntervalInMillis() {
        return timeoutIntervalInMillis;
    }

    public boolean isNoValido() {
        return noValido;
    }

    @Override
    public String toString() {
        return "AlfrescoAuthenticationDetails{" + "userName=" + getUserName() + ", ticket=" + getTicket() + ", sessionId=" + getSessionId() + ", lastTimeUsed=" + lastTimeUsed + '}';
    }
}
