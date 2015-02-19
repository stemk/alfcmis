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
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.stemk.alfcmis.cmis.AuthType;
import org.stemk.alfcmis.cmis.BindingType;
import org.stemk.alfcmis.cmis.CmisParameters;
import org.stemk.alfcmis.cmis.Repository;

/**
 * @author Stefano Maikin
 *
 */
@Named("sessionPar")
@ConversationScoped
public class SessionParameterBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private SessionBean session;
	
	@Inject
	private Conversation conversation;


	private static final SelectItem[] bindingItems = {
			new SelectItem(BindingType.ATOM_PUB, "Atom Pub"),
			new SelectItem(BindingType.WEB_SERVICE, "Web Service"),
			new SelectItem(BindingType.BROWSER, "Browser") };

	private static final SelectItem[] authItems = {
			new SelectItem(AuthType.NONE, "None"),
			new SelectItem(AuthType.STANDARD, "Standard"),
			new SelectItem(AuthType.NTLM, "NTLM"),
			new SelectItem(AuthType.OAUTH_2_0, "OAuth 2.0") };

	private static final SelectItem[] compressionItems = {
			new SelectItem(Boolean.TRUE, "On"),
			new SelectItem(Boolean.FALSE, "Off") };

	private static final SelectItem[] clientCompressionItems = {
			new SelectItem(Boolean.TRUE, "On"),
			new SelectItem(Boolean.FALSE, "Off") };

	private static final SelectItem[] cookiesItems = {
			new SelectItem(Boolean.TRUE, "On"),
			new SelectItem(Boolean.FALSE, "Off") };

	private String url;
	private BindingType binding;
	private String username;
	private String password;
	private AuthType auth;
	private Boolean compression;
	private Boolean clientCompression;
	private Boolean cookies;
	private String repositoryId;
	private List<SelectItem> repositoryIdItems;

	public SelectItem[] getBindingItems() {
		return bindingItems;
	}

	public SelectItem[] getAuthItems() {
		return authItems;
	}

	public SelectItem[] getCompressionItems() {
		return compressionItems;
	}

	public SelectItem[] getClientCompressionItems() {
		return clientCompressionItems;
	}

	public SelectItem[] getCookiesItems() {
		return cookiesItems;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public BindingType getBinding() {
		return binding;
	}

	public void setBinding(BindingType binding) {
		this.binding = binding;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public AuthType getAuth() {
		return auth;
	}

	public void setAuth(AuthType auth) {
		this.auth = auth;
	}

	public Boolean getCompression() {
		return compression;
	}

	public void setCompression(Boolean compression) {
		this.compression = compression;
	}

	public Boolean getClientCompression() {
		return clientCompression;
	}

	public void setClientCompression(Boolean clientCompression) {
		this.clientCompression = clientCompression;
	}

	public Boolean getCookies() {
		return cookies;
	}

	public void setCookies(Boolean cookies) {
		this.cookies = cookies;
	}

	public String getRepositoryId() {
		return repositoryId;
	}

	public void setRepositoryId(String repositoryId) {
		this.repositoryId = repositoryId;
	}

	public List<SelectItem> getRepositoryIdItems() {
		return repositoryIdItems;
	}

	@PostConstruct
	public void init() {
		url = "";
		binding = BindingType.ATOM_PUB;
		username = "";
		password = "";
		auth = AuthType.STANDARD;
		compression = Boolean.TRUE;
		clientCompression = Boolean.FALSE;
		cookies = Boolean.TRUE;
		repositoryId = "";
		repositoryIdItems = null;
	}

	public void initConversation() {
		if (!FacesContext.getCurrentInstance().isPostback() && conversation.isTransient()) {
			conversation.begin();
		}	
	}
	
	public String loadRepo() {
		CmisParameters cmisParameters = new CmisParameters(url, binding, username, password, auth, compression, clientCompression, cookies, "");
		List<Repository> repositories = session.getCmisService().getRepositories(cmisParameters);
		if (repositories == null || repositories.size() == 0)
			return "step1?faces-redirect=true";
		repositoryIdItems = new ArrayList<SelectItem>();
		repositories.forEach(r -> repositoryIdItems.add(new SelectItem(r.getId(), r.getId())));
		repositoryId = repositories.get(0).getId();
		return "step2?faces-redirect=true";
	}

	public String login() {
		CmisParameters cmisParameters = new CmisParameters(url, binding, username, password, auth, compression, clientCompression, cookies, repositoryId);
		session.getCmisService().createSession(cmisParameters);
		session.setCreated(true);
		if (!conversation.isTransient()) {
			conversation.end();
		}
		return "browser?faces-redirect=true";
	}
}