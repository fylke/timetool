package persistency.settings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import persistency.PersistencyHandler;

public class UserAdder implements Runnable {
  private static final String storageFileName = "settings.xml";
  
  private final JFrame parent;
  
  public UserAdder(final JFrame parent) {
    super();
    this.parent = parent;
  }
  
  public void run() {
    PersistencyHandler ph = PersistencyHandler.getInstance();
    String storageDirName = ph.getStorageDir();

    File storageDir = new File(storageDirName);
    storageDir.mkdir();
    File storageFile = new File(storageDir, storageFileName);
    
    Settings user = UserSettings.getInstance();
        
    OutputStream os = null;    
    File tempFile = null;
    try {
      tempFile = File.createTempFile(storageFileName, ".tmp", storageDir);
      os = new FileOutputStream(tempFile);
      ph.writeUserSettings(user, os);
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
                                    "Could not store user, no changes " +
                                    "will be made.",
                                    "File error",
                                    JOptionPane.ERROR_MESSAGE);
    }
  }
}