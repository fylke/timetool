package persistency.projects;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import persistency.PersistencyException;
import persistency.PersistencyHandler;
import persistency.settings.Settings;

public class CompanyAdder implements Runnable {
  private static final String storageFileName = "projectSet.xml";
  
  private final String compName;
  private final String compShortName;
  private final String employeeId;
  private final Settings settings;
  
  public CompanyAdder(final String compName, final String compShortName,
                      final String employeeId, final Settings settings) {
    super();
    this.compName = compName;
    this.compShortName = compShortName;
    this.employeeId = employeeId;
    this.settings = settings;
  }
  
  public void run() {
    PersistencyHandler ph = PersistencyHandler.getInstance();
    String storageDirName = ph.getStorageDir();

    File storageDir = new File(storageDirName);
    storageDir.mkdir();
    File storageFile = new File(storageDir, storageFileName);

    ProjectSet ps = null;
    if (storageFile.exists()) {
      InputStream is = null;
      try {
        is = new FileInputStream(storageFile);
        ps = ph.readProjectSet(is);
      } catch (FileNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (PersistencyException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } finally {
        try {
          is.close();
        } catch (IOException e) {
          // We don't care if closing fails...
        }
      }
    } else {
      ps = new ProjectSet();
      ps.setId(settings.getProjectSetId());
    }

    Company company = new Company();
    company.setId(ps.allocateCompanyId());
    company.setName(compName);
    company.setShortName(compShortName);
    company.setEmployeeId(employeeId);

    ps.addCompany(company);
    
    OutputStream os = null;    
    File tempFile = null;
    try {
      tempFile = File.createTempFile(storageFileName, ".tmp", storageDir);
      os = new FileOutputStream(tempFile);
      ph.writeProjectSet(ps, os);
    } catch (FileNotFoundException e) {
      // TODO Should probably be handled elsewhere
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Should probably be handled elsewhere
      e.printStackTrace();
    } finally {
      try {
        os.close();
      } catch (IOException e) {
        // We don't care if closing fails...
      }
    }

    File backupFile = new File(storageDir, storageFileName + ".bak");
    storageFile.renameTo(backupFile);
    if (tempFile.renameTo(storageFile)) {
      backupFile.delete();
    } else {
      backupFile.renameTo(storageFile);
      //TODO throw exception
    }
  }
}