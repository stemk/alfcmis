/* 
 * Copyright (C) 2015 stemk
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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