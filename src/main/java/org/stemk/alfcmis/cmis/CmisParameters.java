package org.stemk.alfcmis.cmis;

/**
 * @author Stefano Maikin
 *
 */
public class CmisParameters {

	private final String username;
	private final String password;
	private final String url;
	private BindingType bindingType;
	private AuthType authType;
	private boolean compression;
	private boolean clientCompression;
	private boolean cookies;
	private boolean alfresco;
	private String repositoryId;

	private CmisParameters(String username, String password, String url,
			BindingType bindingType, AuthType authType, boolean compression,
			boolean clientCompression, boolean cookies, boolean alfresco,
			String repositoryId) {
		this.username = username;
		this.password = password;
		this.url = url;
		this.bindingType = bindingType;
		this.authType = authType;
		this.compression = compression;
		this.clientCompression = clientCompression;
		this.cookies = cookies;
		this.alfresco = alfresco;
		this.repositoryId = repositoryId;
	}

	public static class Builder {

		private final String username;
		private final String password;
		private final String url;
		private BindingType bindingType = BindingType.ATOM_PUB;
		private AuthType authType = AuthType.STANDARD;
		private boolean compression = false;
		private boolean clientCompression = false;
		private boolean cookies = false;
		private boolean alfresco = false;
		private String repositoryId = "";

		public Builder(String username, String password, String url) {
			this.username = username;
			this.password = password;
			this.url = url;
		}

		public Builder bindingType(BindingType bindingType) {
			this.bindingType = bindingType;
			return this;
		}

		public Builder authType(AuthType authType) {
			this.authType = authType;
			return this;
		}

		public Builder compression(boolean compression) {
			this.compression = compression;
			return this;
		}

		public Builder clientCompression(boolean clientCompression) {
			this.clientCompression = clientCompression;
			return this;
		}

		public Builder cookies(boolean cookies) {
			this.cookies = cookies;
			return this;
		}

		public Builder repositoryId(String repositoryId) {
			this.repositoryId = repositoryId;
			return this;
		}

		public CmisParameters build() {
			return new CmisParameters(username, password, url, bindingType,
					authType, compression, clientCompression, cookies,
					alfresco, repositoryId);
		}

	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getUrl() {
		return url;
	}

	public BindingType getBindingType() {
		return bindingType;
	}

	public AuthType getAuthType() {
		return authType;
	}

	public boolean isCompression() {
		return compression;
	}

	public boolean isClientCompression() {
		return clientCompression;
	}

	public boolean isAlfresco() {
		return alfresco;
	}
	
	public boolean isCookies() {
		return cookies;
	}

	public String getRepositoryId() {
		return repositoryId;
	}

	public void setRepositoryId(String repositoryId) {
		this.repositoryId = repositoryId;
	}

}
