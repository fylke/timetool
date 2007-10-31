package persistency.settings;

import gui.CreateUserFrame;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JFrame;

import persistency.PersistencyException;
import persistency.PersistencyHandler;

public class SettingsReader {
  public boolean read() {
    final String storageFileName = "settings.xml";
    PersistencyHandler ph = PersistencyHandler.getInstance();
    String storDirName = ph.getStorageDir();
  
    File storDir = new File(storDirName);
    storDir.mkdir();
    File storageFile = new File(storDir, storageFileName);
       
    if (storageFile.exists()) {
      InputStream is = null;
      try {
        is = new FileInputStream(storageFile);
        UserSettings.getInstance().setInstance(ph.readUserSettings(is));
      } catch (FileNotFoundException e) {
        System.err.println(e.getMessage());
        /*JOptionPane.showMessageDialog(this,
                                      "Could not open " + 
                                      storageFile.getAbsolutePath() + ".\n" + 
                                      e.getMessage(),
                                      "File error",
                                      JOptionPane.ERROR_MESSAGE);*/
      } catch (PersistencyException e) {
        System.err.println(e.getMessage());
        /*JOptionPane.showMessageDialog(this,
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
      return true;
    } else {
      // This means that we have to create a new user!
      java.awt.EventQueue.invokeLater(
          new Runnable() {
            public void run() {
              final JFrame createUser = new CreateUserFrame();
              createUser.setVisible(true);
            }
          }
        );
      return false;
    }
  }
}