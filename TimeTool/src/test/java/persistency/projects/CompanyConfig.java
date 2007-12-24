package persistency.projects;

public class CompanyConfig {
	final int compId;
	final int nrOfProjsPerComp;
	final int nrOfActsPerComp;
	final int projDepth;

	public CompanyConfig(final int compId,
											 final int nrOfProjsPerComp,
											 final int nrOfActsPerComp,
											 final int projDepth) {
		super();
		this.compId = compId;
		this.nrOfProjsPerComp = nrOfProjsPerComp;
		this.nrOfActsPerComp = nrOfActsPerComp;
		this.projDepth = projDepth;
	}
}
