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
