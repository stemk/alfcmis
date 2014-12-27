package org.stemk.alfcmis.cmis;

/**
 * @author Stefano Maikin
 *
 */
public class CmisParameters {

	private final String url;
	private BindingType bindingType;
	private final String username;
	private final String password;
	private AuthType authType;
	private boolean compression;
	private boolean clientCompression;
	private boolean cookies;
	private String repositoryId;

	public CmisParameters(String url, BindingType bindingType,
			String username, String password, AuthType authType,
			boolean compression, boolean clientCompression, boolean cookies,
			String repositoryId) {
		this.url = url;
		this.bindingType = bindingType;
		this.username = username;
		this.password = password;
		this.authType = authType;
		this.compression = compression;
		this.clientCompression = clientCompression;
		this.cookies = cookies;
		this.repositoryId = repositoryId;
	}

	public static class Builder {

		private final String url;
		private BindingType bindingType = BindingType.ATOM_PUB;
		private final String username;
		private final String password;
		private AuthType authType = AuthType.STANDARD;
		private boolean compression = true;
		private boolean clientCompression = false;
		private boolean cookies = true;
		private String repositoryId = "";

		public Builder(String url, String username, String password) {
			this.url = url;
			this.username = username;
			this.password = password;
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
			return new CmisParameters(url, bindingType, username, password,
					authType, compression, clientCompression, cookies,
					repositoryId);
		}

	}

	public String getUrl() {
		return url;
	}

	public BindingType getBindingType() {
		return bindingType;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
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
