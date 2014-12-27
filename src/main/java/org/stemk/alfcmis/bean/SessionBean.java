package org.stemk.alfcmis.bean;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.stemk.alfcmis.cmis.CmisService;

/**
 * @author Stefano Maikin
 *
 */
@Named("cmisSession")
@SessionScoped
public class SessionBean implements Serializable{

	private static final long serialVersionUID = 1L;

	@EJB
	private CmisService cmisService;

	boolean isCreated = false;
	
	public CmisService getCmisService() {
		return cmisService;
	}

	public boolean isCreated() {
		return isCreated;
	}
	
	public void setCreated(boolean isCreated) {
		this.isCreated = isCreated;
	}
}