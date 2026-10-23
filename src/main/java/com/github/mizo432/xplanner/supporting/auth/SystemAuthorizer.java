package com.github.mizo432.xplanner.supporting.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SystemAuthorizer {

    @Autowired
    private Authorizer authorizer;

    public Authorizer get() {
        return authorizer;
    }

    public void set(Authorizer authorizer) {
        this.authorizer = authorizer;
    }
}
