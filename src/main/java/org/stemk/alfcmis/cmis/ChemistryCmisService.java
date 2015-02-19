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
package org.stemk.alfcmis.cmis;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateful;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.FileableCmisObject;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.enums.BaseTypeId;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.apache.chemistry.opencmis.commons.enums.UnfileObject;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;

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
		if (cmisParameters.getRepositoryId() != null)
			parameters.put(SessionParameter.REPOSITORY_ID, cmisParameters.getRepositoryId());
	}

	@Override
	public List<Repository> getRepositories(CmisParameters cmisParameters) {
		try {
			setParameters(cmisParameters);
			List<Repository> repositories = new ArrayList<Repository>();
			SessionFactoryImpl.newInstance().getRepositories(parameters).iterator().forEachRemaining(r -> repositories.add(new ChemistryRepository(r)));
			return repositories;	
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	public void createSession(CmisParameters cmisParameters) {
		try {
			setParameters(cmisParameters);
			session = SessionFactoryImpl.newInstance().createSession(parameters);	
		} catch (Exception e) {
		}
	}
	
	@Override
	public void closeSession() {
		session = null;
	}

	@Override
	public NodeFolder getRootFolder() {
		try {
			return getFolder(session.getRootFolder().getId());
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	public NodeFolder getFolder(String id) {
		NodeFolder nodeFolder = null;
		try{
			CmisObject cmisObject = session.getObject(id);
			if (cmisObject.getType().getBaseTypeId() == BaseTypeId.CMIS_FOLDER){
				Folder folder = (Folder) cmisObject;
				nodeFolder = new ChemistryNodeFolder(folder);
			}
		} catch (Exception e){
			// TODO
		}
		return nodeFolder;
	}

	@Override
	public NodeFolder createFolder(String name, String parentId) {
		try {
			Folder parent = (Folder) session.getObject(parentId);
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(PropertyIds.NAME, name);
			properties.put(PropertyIds.OBJECT_TYPE_ID, BaseTypeId.CMIS_FOLDER.value());
			Folder folder = parent.createFolder(properties);
			return new ChemistryNodeFolder(folder);
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	public Node get(String id) {
		try {
			CmisObject cmisObject = session.getObject(id);
			ChemistryNode chemistryNode = null;
			if (cmisObject.getType().getBaseTypeId() == BaseTypeId.CMIS_FOLDER)
				chemistryNode = new ChemistryNodeFolder((Folder) cmisObject);
			else if (cmisObject.getType().getBaseTypeId() == BaseTypeId.CMIS_DOCUMENT)
				chemistryNode = new ChemistryNodeDocument((Document) cmisObject);
			else 
				chemistryNode = new ChemistryNode(cmisObject);
			return chemistryNode;			
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	public void delete(String id, boolean allVersion) {
		try {
			CmisObject cmisObject = session.getObject(id);
			if (cmisObject instanceof Folder) 
				((Folder) cmisObject).deleteTree(true,  UnfileObject.DELETE, true);
			else
				cmisObject.delete(true);
		} catch (Exception e) {
		}
	}

	@Override
	public NodeDocument createDocument(String fileName, String mimeType, InputStream content, String parentId) {
		Document document = null;
		try{
			if (mimeType == null || mimeType.isEmpty()) mimeType = "application/octet-stream";
			ContentStream contentStream = session.getObjectFactory().createContentStream(fileName, -1, mimeType, content);
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(PropertyIds.OBJECT_TYPE_ID, "cmis:document");
			properties.put(PropertyIds.NAME, contentStream.getFileName());
			CmisObject cmisObject = session.getObject(parentId);
			if (cmisObject.getType().getBaseTypeId() == BaseTypeId.CMIS_FOLDER){
				document = ((Folder) cmisObject).createDocument(properties, contentStream, VersioningState.MAJOR);
			}
		} catch (Exception e) {
			// TODO
		}
		if (document != null)
			return new ChemistryNodeDocument(document);
		else 
			return null;
	}

	@Override
	public Node copy(String sourceId, String targetId) {
		try {
			CmisObject source = session.getObject(sourceId);
			Folder target = (Folder) session.getObject(targetId);
			if (source instanceof Document)
				return copyDocument((Document) source, target);
			else if (source instanceof Folder)
			{
				// If source is a folder I can copy as long as target is not a subfolder of source
				String pId = target.getId();
				while(pId != null && pId != ""){
					if (pId.equals(source.getId()))
						throw new Exception();
					pId = ((Folder) session.getObject(pId)).getParentId();
				}
				return copyFolder((Folder) source, target);
			}
		} catch (Exception e) {
		}
		return null;
	}

	private NodeDocument copyDocument(Document source, Folder target) throws Exception {
		Map<String, Object> prop = new HashMap<String, Object>(2);
		prop.put(PropertyIds.NAME, source.getName());
		prop.put(PropertyIds.OBJECT_TYPE_ID, source.getBaseTypeId().value());
		return new ChemistryNodeDocument(source.copy(target, prop, null, null, null, null, null));
	}

	private NodeFolder copyFolder(Folder source, Folder target) throws Exception {
		Map<String,Object> prop = new HashMap<String, Object>();
		prop.put(PropertyIds.NAME, source.getName());
		prop.put(PropertyIds.OBJECT_TYPE_ID, source.getBaseTypeId().value());
		Folder sourceCopy = target.createFolder(prop);
		ItemIterable<CmisObject> children = source.getChildren();
		for(CmisObject child : children){
			if (child instanceof Document)
				copyDocument((Document) child, sourceCopy);
			else if (child instanceof Folder)
				copyFolder((Folder) child, sourceCopy);
		}
		return new ChemistryNodeFolder(sourceCopy);
	}

	@Override
	public Node move(String sourceId, String sourceParentId, String targetId) {
		try {
			CmisObject source = session.getObject(sourceId);
			CmisObject sourceParent = session.getObject(sourceParentId);
			CmisObject target = session.getObject(targetId);
			CmisObject movedObj = ((FileableCmisObject) source).move(sourceParent, target);
			if (movedObj instanceof Document) 
				return new ChemistryNodeDocument((Document) movedObj);
			else if (movedObj instanceof Folder) 
				return new ChemistryNodeFolder((Folder) movedObj);
			else 
				return new ChemistryNode(movedObj);
		} catch (Exception e) {
		}
		return null;
	}
}