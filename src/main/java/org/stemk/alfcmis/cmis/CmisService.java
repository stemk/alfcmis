package org.stemk.alfcmis.cmis;

import java.util.List;

import javax.ejb.Local;

/**
 * @author Stefano Maikin
 *
 */
@Local
public interface CmisService {

	public List<String> getRepositoriesId(CmisParameters cmisParameters);

	public void createSession(CmisParameters cmisParameters);

	public NodeFolder getRootFolder();

	public NodeFolder getFolder(String id);
}
