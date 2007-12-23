package persistency.projects;

public class CompanyConfig {
	final int compId;
	final int nrOfProjsPerComp;
	final int nrOfActsPerProj;
	final int projDepth;

	public CompanyConfig(final int compId,
											 final int nrOfProjsPerComp,
											 final int nrOfActsPerProj,
											 final int projDepth) {
		super();
		this.compId = compId;
		this.nrOfProjsPerComp = nrOfProjsPerComp;
		this.nrOfActsPerProj = nrOfActsPerProj;
		this.projDepth = projDepth;
	}
}
