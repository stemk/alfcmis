package org.stemk.alfcmis.cmis;

import org.apache.chemistry.opencmis.commons.data.ContentStream;

public class ChemistryContent implements Content {

	ContentStream contentStream;

	public ChemistryContent(ContentStream contentStream) {
		this.contentStream = contentStream;
	}

}
