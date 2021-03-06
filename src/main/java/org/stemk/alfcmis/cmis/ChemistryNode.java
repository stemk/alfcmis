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
