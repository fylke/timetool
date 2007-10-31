package persistency.projects;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import persistency.ItemAlreadyDefinedException;
import persistency.PersistencyException;
import persistency.PersistencyHandler;

public class CompanyAdder implements Runnable {
  private static final String storageFileName = "projectSet.xml";
  
  private final JFrame parent;
  
  private final String compName;
  private final String compShortName;
  private final String employeeId;
  private ProjectSet ps;
  
  public CompanyAdder(final JFrame parent, final String compName, 
                      final String compShortName,
                      final String employeeId) {
    super();
    this.parent = parent;
    this.compName = compName;
    this.compShortName = compShortName;
    this.employeeId = employeeId;
  }
  
  public void run() {
    PersistencyHandler ph = PersistencyHandler.getInstance();
    String storageDirName = ph.getStorageDir();

    File storageDir = new File(storageDirName);
    storageDir.mkdir();
    File storageFile = new File(storageDir, storageFileName);

    if (storageFile.exists()) {
      InputStream is = null;
      try {
        is = new FileInputStream(storageFile);
        ps = ph.readProjectSet(is);
      } catch (FileNotFoundException e) {
        JOptionPane.showMessageDialog(parent,
                                      "Could not open  " + storageFileName +
                                      ". " + e.getMessage(),
                                      "File error",
                                      JOptionPane.ERROR_MESSAGE);
      } catch (PersistencyException e) {
        JOptionPane.showMessageDialog(parent,
                                      "Could not open  " + storageFileName +
                                      ". " + e.getMessage(),
                                      "File error",
                                      JOptionPane.ERROR_MESSAGE);
      } finally {
        try {
          is.close();
        } catch (IOException e) {
          // We don't care if closing fails...
        }
      }
    }

    Company company = new Company();
    company.setName(compName);
    company.setShortName(compShortName);
    company.setEmployeeId(employeeId);

    if (ps == null) {
      ps = new ProjectSet();
    }
    
    try {
      ps.addCompany(company);
    } catch (ItemAlreadyDefinedException e) {
      JOptionPane.showMessageDialog(parent,
                                    e.getMessage() + 
                                    " No changes will be made.",
                                    "Item already defined",
                                    JOptionPane.WARNING_MESSAGE);
    }
    
    OutputStream os = null;    
    File tempFile = null;
    try {
      tempFile = File.createTempFile(storageFileName, ".tmp", storageDir);
      os = new FileOutputStream(tempFile);
      ph.writeProjectSet(ps, os);
    } catch (FileNotFoundException e) {
      JOptionPane.showMessageDialog(parent,
                                    e.getMessage(),
                                    "File error",
                                    JOptionPane.ERROR_MESSAGE);
    } catch (IOException e) {
      JOptionPane.showMessageDialog(parent,
                                    e.getMessage(),
                                    "File error",
                                    JOptionPane.ERROR_MESSAGE);
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
      JOptionPane.showMessageDialog(parent,
                                    "Could not store company, no changes " +
                                    "will be made.",
                                    "File error",
                                    JOptionPane.ERROR_MESSAGE);
    }
  }
}