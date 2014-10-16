package org.stemk.alfcmis.cmis;

import java.util.List;

/**
 * @author Stefano Maikin
 *
 */
public interface NodeFolder extends Node {

	public List<Node> getChildren();

}
