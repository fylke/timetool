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

public class ActAdder implements Runnable {
 private static final String storageFileName = "projectSet.xml";
  
  private final JFrame parent;
  
  private final int compId;
  private final int projId;
  
  private final String actName;
  private final String actShortName;
  private final String actRepCode;
  private ProjectSet ps;
  
  public ActAdder(final JFrame parent, final int compId, final int projId,  
                  final String actName, final String actShortName,
                  final String actRepCode, final ProjectSet projectSet) {
    super();
    this.parent = parent;
    this.compId = compId;
    this.projId = projId;
    this.actName = actName;
    this.actShortName = actShortName;
    this.actRepCode = actRepCode;
    this.ps = projectSet;
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
                                      "Could not open projectSet.xml. " + 
                                      e.getMessage(),
                                      "File error",
                                      JOptionPane.ERROR_MESSAGE);
      } catch (PersistencyException e) {
        JOptionPane.showMessageDialog(parent,
                                      "Could not open projectSet.xml. " + 
                                      e.getMessage(),
                                      "File error",
                                      JOptionPane.ERROR_MESSAGE);
      } finally {
        try {
          is.close();
        } catch (IOException e) {
          // We don't care if closing fails...
        }
      }
    } else {
      JOptionPane.showMessageDialog(parent,
                                    "projectSet.xml could not be found, " +
                                    "cannot add project.",
                                    "File error",
                                    JOptionPane.ERROR_MESSAGE);
      return;
    }

    Activity act = new Activity();
    act.setName(actName);
    act.setShortName(actShortName);
    act.setReportCode(actRepCode);    
    
    String msg = null;
    if (ps.getCompany(compId) == null) {
      msg = "Supplied company does not exist!";
    } else if (ps.getCompany(compId).getProject(projId) == null) {
      msg = "Company" + ps.getCompany(compId).getName() + " does not contain " +
            "specified project!";      
    }
    
    if (msg != null) {
      JOptionPane.showMessageDialog(parent, msg, "Bad input error", 
                                    JOptionPane.ERROR_MESSAGE);
      return;
    }

    try {
      ps.getCompany(compId).getProject(projId).addActivity(act);
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
                                    "Could not write changes. " + 
                                    e.getMessage(),
                                    "File error",
                                    JOptionPane.ERROR_MESSAGE);
    } catch (IOException e) {
      JOptionPane.showMessageDialog(parent,
                                    "Could not write changes. " +
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
                                    "Could not store project, no changes " +
                                    "will be made.",
                                    "File error",
                                    JOptionPane.ERROR_MESSAGE);
    }
  }
}