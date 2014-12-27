package org.stemk.alfcmis.cmis;

import java.io.InputStream;
import java.util.List;

import javax.ejb.Local;

/**
 * @author Stefano Maikin
 *
 */
@Local
public interface CmisService {

	public List<Repository> getRepositories(CmisParameters cmisParameters);

	public void createSession(CmisParameters cmisParameters);

	public void closeSession();

	public NodeFolder getRootFolder();

	public NodeFolder getFolder(String id);

	public NodeFolder createFolder(String name, String parentId);

	public Node get(String id);

	public void delete(String id, boolean allVersion);

	public NodeDocument createDocument(String fileName, String mimeType, InputStream content, String parentId);

	public Node copy(String sourceId, String targetId);
	
	public Node move(String sourceId, String sourceParentId, String targetId);
}
