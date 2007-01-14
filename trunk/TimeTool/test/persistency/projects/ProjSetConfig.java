package persistency.projects;

public class ProjSetConfig {
  final transient int projSetId;
  final transient int nrOfComps;
  final transient int nrOfProjsPerComp;
  final transient int projDepth;
  
  public ProjSetConfig(final int projSetId, final int nrOfComps, 
                          final int nrOfProjsPerComp, 
                          final int projDepth) {
    super();
    this.projSetId = projSetId;
    this.nrOfComps = nrOfComps;
    this.nrOfProjsPerComp = nrOfProjsPerComp;
    this.projDepth = projDepth;
  }
}
