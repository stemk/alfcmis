package org.stemk.alfcmis.cmis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateful;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Repository;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.enums.BaseTypeId;
import org.apache.chemistry.opencmis.commons.enums.BindingType;

/**
 * @author Stefano Maikin
 *
 */
@Stateful
public class ChemistryCmisService implements CmisService {

	private Map<String, String> parameters;
	private Session session;

	public ChemistryCmisService() {
	}

	private void setParameters(CmisParameters cmisParameters) {
		parameters = new HashMap<String, String>();
		parameters.put(SessionParameter.USER, cmisParameters.getUsername());
		parameters.put(SessionParameter.PASSWORD, cmisParameters.getPassword());
		switch (cmisParameters.getBindingType()) {
		case ATOM_PUB:
			parameters.put(SessionParameter.ATOMPUB_URL, cmisParameters.getUrl());
			parameters.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());
			break;
		case WEB_SERVICE:
			parameters.put(SessionParameter.WEBSERVICES_REPOSITORY_SERVICE, cmisParameters.getUrl() + "RepositoryService?wsdl");
			parameters.put(SessionParameter.WEBSERVICES_NAVIGATION_SERVICE, cmisParameters.getUrl() + "NavigationService?wsdl");
			parameters.put(SessionParameter.WEBSERVICES_OBJECT_SERVICE, cmisParameters.getUrl() + "ObjectService?wsdl");
			parameters.put(SessionParameter.WEBSERVICES_VERSIONING_SERVICE, cmisParameters.getUrl() + "VersioningService?wsdl");
			parameters.put(SessionParameter.WEBSERVICES_RELATIONSHIP_SERVICE, cmisParameters.getUrl() + "RelationshipService?wsdl");
			parameters.put(SessionParameter.WEBSERVICES_DISCOVERY_SERVICE, cmisParameters.getUrl() + "DiscoveryService?wsdl");
			parameters.put(SessionParameter.WEBSERVICES_MULTIFILING_SERVICE, cmisParameters.getUrl() + "MultiFilingService?wsdl");
			parameters.put(SessionParameter.WEBSERVICES_ACL_SERVICE, cmisParameters.getUrl() + "ACLService?wsdl");
			parameters.put(SessionParameter.WEBSERVICES_POLICY_SERVICE, cmisParameters.getUrl() + "PolicyService?wsdl");
			parameters.put(SessionParameter.BINDING_TYPE, BindingType.WEBSERVICES.value());
			break;
		case BROWSER:
			parameters.put(SessionParameter.BROWSER_URL, cmisParameters.getUrl());
			parameters.put(SessionParameter.BINDING_TYPE, BindingType.BROWSER.value());
			break;
		default:
		}
		if (cmisParameters.isAlfresco()) 
			parameters.put(SessionParameter.OBJECT_FACTORY_CLASS, "org.alfresco.cmis.client.impl.AlfrescoObjectFactoryImpl");
		if (cmisParameters.getRepositoryId() != null)
			parameters.put(SessionParameter.REPOSITORY_ID, cmisParameters.getRepositoryId());
	}

	public List<String> getRepositoriesId(CmisParameters cmisParameters) {
		setParameters(cmisParameters);
		List<String> repositoriesId = new ArrayList<String>();
		List<Repository> repositories = SessionFactoryImpl.newInstance().getRepositories(parameters);
		if (repositories != null && repositories.size() != 0) {
			Iterator<Repository> iter = repositories.iterator();
			while (iter.hasNext())
				repositoriesId.add(iter.next().getId());
		}
		return repositoriesId;
	}

	public void createSession(CmisParameters cmisParameters) {
		setParameters(cmisParameters);
		this.session = SessionFactoryImpl.newInstance().createSession(parameters);
	}

	public NodeFolder getRootFolder() {
		return getFolder(session.getRootFolder().getId());
	}

	public NodeFolder getFolder(String id) {
		NodeFolder nodeFolder = null;
		CmisObject cmisObject = session.getObject(id);
		if (cmisObject.getType().getBaseTypeId() == BaseTypeId.CMIS_FOLDER)
			nodeFolder = new ChemistryNodeFolder(cmisObject);
		return nodeFolder;
	}
	
}