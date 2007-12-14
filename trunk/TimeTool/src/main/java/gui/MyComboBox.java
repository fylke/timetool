package gui;

import java.util.Collection;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.MutableComboBoxModel;

public class MyComboBox extends JComboBox {
  public boolean dispLong = true;

  private static final long serialVersionUID = 1L;

  private final Vector<MyComboBoxDisplayable> contents;
  private final String emptyMsg;
  private static final String defEmptyMsg = "Inget definerat";
  private boolean isEmpty;

  public MyComboBox() {
    this(new Vector<MyComboBoxDisplayable>(), defEmptyMsg);
  }

  public MyComboBox(final Vector<MyComboBoxDisplayable> contents,
                    final String emptyMsg) {
    super();
    this.contents = contents;
    this.emptyMsg = emptyMsg;
    setModel(new DefaultComboBoxModel(contents));
    if(contents.isEmpty()) {
      makeEmpty();
    }
  }

  public void setContents(final Collection<MyComboBoxDisplayable> contents) {
    if ((contents != null) && !contents.isEmpty()) {
      makeEmpty();
      for (final MyComboBoxDisplayable element : contents) {
        addContents(element);
      }
    }
  }

  public void addContents(final Collection<MyComboBoxDisplayable> contents) {
    if ((contents != null) && !contents.isEmpty()) {
      for (final MyComboBoxDisplayable element : contents) {
        addContents(element);
      }
    }
  }

  public void addContents(final MyComboBoxDisplayable element) {
    if (element != null) {
      if (isEmpty) {
        makeUnEmpty();
      }
      contents.add(element);
      final String strToShow = dispLong ? element.getLongDispString() :
                                    element.getShortDispString();
      ((MutableComboBoxModel) dataModel).addElement(strToShow);
    }
  }

  public void removeContents(final Collection<MyComboBoxDisplayable> contents) {
    if ((contents != null) && !contents.isEmpty()) {
      contents.removeAll(contents);
      for (final MyComboBoxDisplayable element : contents) {
        removeContents(element);
      }
    }
  }

  public void removeContents(final MyComboBoxDisplayable element) {
    if ((element != null) && contents.contains(element)) {
      contents.remove(element);
      final String strToRem = dispLong ? element.getLongDispString() :
                                   element.getShortDispString();
      ((MutableComboBoxModel) dataModel).removeElement(strToRem);
    }
    if (contents.isEmpty()) {
      makeEmpty();
    }
  }

  public MyComboBoxDisplayable getSelected() {
    return isEmpty ? null : contents.get(getSelectedIndex());
  }

  private void makeEmpty() {
    contents.clear();
    removeAllItems();
    ((MutableComboBoxModel) dataModel).addElement(emptyMsg);
    isEmpty = true;
  }

  private void makeUnEmpty() {
    contents.clear();
    removeAllItems();
    isEmpty = false;
  }
}