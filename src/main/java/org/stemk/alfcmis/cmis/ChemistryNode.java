package org.stemk.alfcmis.cmis;

import org.apache.chemistry.opencmis.client.api.CmisObject;

/**
 * @author Stefano Maikin
 *
 */
public class ChemistryNode implements Node{

	CmisObject cmisObject;
	
	 public ChemistryNode(CmisObject cmisObject) {
		 this.cmisObject = cmisObject;
	 }
	
	@Override
	public String getId() {
		return cmisObject.getId();
	}

	@Override
	public String getName() {
		return cmisObject.getName();
	}

	@Override
	public String getType() {
		return cmisObject.getType().getId();
	}

	@Override
	public String getBaseType() {
		return cmisObject.getType().getBaseTypeId().value();
	}

}
