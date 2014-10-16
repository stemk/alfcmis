package org.stemk.alfcmis.cmis;

import java.util.ArrayList;
import java.util.List;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Folder;

/**
 * @author Stefano Maikin
 *
 */
public class ChemistryNodeFolder extends ChemistryNode implements NodeFolder {

	List<Node> children = null;

	public ChemistryNodeFolder(CmisObject cmisObject) {
		super(cmisObject);
	}

	@Override
	public List<Node> getChildren() {
		if (children == null) {
			children = new ArrayList<Node>();
			for (CmisObject child : ((Folder) cmisObject).getChildren())
				children.add(new ChemistryNode(child));
		}
		return children;
	}
}
