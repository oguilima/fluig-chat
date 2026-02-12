package com.fluig;

import com.fluig.customappkey.Keyring;
import com.fluig.sdk.api.component.activation.ActivationEvent;
import com.fluig.sdk.api.component.activation.ActivationListener;

import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

@Singleton(mappedName = "activator/chat", name = "activator/chat")
public class Activate implements ActivationListener {
	public String getArtifactFileName() throws Exception {
        return "chat.war";
    }
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void install(ActivationEvent event) throws Exception {
    }

    public void disable(ActivationEvent evt) throws Exception {
    }

    public void enable(ActivationEvent evt) throws Exception {
        Keyring.provision("aaab-bbaa-dacb-bcda");
    }
}