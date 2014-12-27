package org.stemk.alfcmis.cmis;

/**
 * @author Stefano Maikin
 *
 */
public class ChemistryRepository implements Repository {

	private org.apache.chemistry.opencmis.client.api.Repository repository;

	public ChemistryRepository(org.apache.chemistry.opencmis.client.api.Repository repository) {
		this.repository = repository;
	}

	@Override
	public String getId() {
		return repository.getId();
	}

	@Override
	public String getName(){
		return repository.getName();
	}
}
