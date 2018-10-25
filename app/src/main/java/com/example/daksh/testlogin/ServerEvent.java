package com.example.daksh.testlogin;

public class ServerEvent {
    private User serverResponse;

    public ServerEvent(User serverResponse) {
        this.serverResponse = serverResponse;
    }

    public User getServerResponse() {
        return serverResponse;
    }

    public void setServerResponse(User serverResponse) {
        this.serverResponse = serverResponse;
    }
}
