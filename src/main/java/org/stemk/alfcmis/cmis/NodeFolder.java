package org.stemk.alfcmis.cmis;

import java.util.List;

/**
 * @author Stefano Maikin
 *
 */
public interface NodeFolder extends Node {

	public List<Node> getChildren();

	public String getParentId();

	public boolean canCreateFolder();

	public boolean canCreateDocument();
}
