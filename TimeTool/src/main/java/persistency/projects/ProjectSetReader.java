package persistency.projects;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import persistency.PersistencyException;
import persistency.PersistencyHandler;
import persistency.settings.UserSettings;

public class ProjectSetReader {
  private static final String storageFileName = "projectSet.xml";

  public void read() {
    PersistencyHandler ph = PersistencyHandler.getInstance();
    String storDirName = ph.getStorageDir();

    File storDir = new File(storDirName);
    storDir.mkdir();
    File storageFile = new File(storDir, storageFileName);
    
    if (storageFile.exists()) {
      InputStream is = null;
      try {
        is = new FileInputStream(storageFile);
        UserSettings.getInstance().setProjectSet(ph.readProjectSet(is));
      } catch (FileNotFoundException e) {
        System.err.println(e.getMessage());
        /*JOptionPane.showMessageDialog(parent,
                                      "Could not open " + 
                                      storageFile.getAbsolutePath() + ".\n" + 
                                      e.getMessage(),
                                      "File error",
                                      JOptionPane.ERROR_MESSAGE);*/
      } catch (PersistencyException e) {
        System.err.println(e.getMessage());
        /*JOptionPane.showMessageDialog(parent,
                                      "Could not open " + 
                                      storageFile.getAbsolutePath() + ".\n" + 
                                      e.getMessage(),
                                      "File error",
                                      JOptionPane.ERROR_MESSAGE);*/
      } finally {
        try {
          is.close();
        } catch (IOException e) {
          // We don't care if closing fails...
        }
      }
    } 
  }
}