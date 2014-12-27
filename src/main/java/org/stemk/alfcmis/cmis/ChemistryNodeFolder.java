package org.stemk.alfcmis.cmis;

import java.util.ArrayList;
import java.util.List;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Property;
import org.apache.chemistry.opencmis.commons.enums.Action;

/**
 * @author Stefano Maikin
 *
 */
public class ChemistryNodeFolder extends ChemistryNode implements NodeFolder {

	List<Node> children = null;

	public ChemistryNodeFolder(Folder folder) {
		super(folder);
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

	@Override
	public String getParentId() {
		String parentId = "";
		Property<Object> pParentId = cmisObject.getProperty("cmis:parentId");
		if (pParentId != null && pParentId.getValueAsString() != null)
			parentId = pParentId.getValueAsString();
		return parentId;
	}

	@Override
	public boolean canCreateFolder() {
		for(Action a : cmisObject.getAllowableActions().getAllowableActions()){
			if (a == Action.CAN_CREATE_FOLDER)
				return true;
		}
		return false;
	}

	@Override
	public boolean canCreateDocument() {
		for(Action a : cmisObject.getAllowableActions().getAllowableActions()){
			if (a == Action.CAN_CREATE_DOCUMENT)
				return true;
		}
		return false;
	}
}
