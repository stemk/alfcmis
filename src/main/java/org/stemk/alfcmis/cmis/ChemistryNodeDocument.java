package org.stemk.alfcmis.cmis;

import org.apache.chemistry.opencmis.client.api.Document;

public class ChemistryNodeDocument extends ChemistryNode implements NodeDocument {

	public ChemistryNodeDocument(Document document) {
		super(document);
	}
}
