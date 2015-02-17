package org.openstreetmap.josm.plugins.RemoveRedundantPoints;

import static org.openstreetmap.josm.tools.I18n.tr;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Collection;

import org.openstreetmap.josm.actions.JosmAction;
import org.openstreetmap.josm.data.osm.OsmPrimitive;
import org.openstreetmap.josm.gui.Notification;
import org.openstreetmap.josm.tools.I18n;
import org.openstreetmap.josm.tools.Shortcut;

public class FilterAction extends JosmAction{
  public FilterAction()
  {  
    super(I18n.tr("Remove redundant points", new Object[0]), "images/dialogs/DPimg.png", 
      I18n.tr("Remove redundant points from ways,based an threshold distance between point and ideal way", new Object[0]), 
      Shortcut.registerShortcut("menu:Remove redundant points from ways,railways,rivers,etc.", tr("Menu: {0}", tr("Remove redundant points")),
	  KeyEvent.VK_F, Shortcut.SHIFT), true,"Remove redundant points",true);
  }
  
  public void actionPerformed(ActionEvent arg0)
  {
	  if (!isEnabled())
          return;
	  	FilterTest dialog1 = new FilterTest();
	    if (dialog1.test_selection() == true)
	    {
	      FilterDialog dialog = new FilterDialog();
	      dialog.filteraction();
	    }
	    else
	    {
	      new Notification(I18n.tr("This plugin don't have action above this selection.\nSelect other entities", new Object[0])).setIcon(2).show();
	      return;
	    }
  }
  @Override
  protected void updateEnabledState() {
      if (getCurrentDataSet() == null) {
          setEnabled(false);
      } else {
          updateEnabledState(getCurrentDataSet().getSelected());
      }
  }
  
  @Override
  protected void updateEnabledState(Collection<? extends OsmPrimitive> selection) {
      setEnabled(selection != null && !selection.isEmpty());
  }
}
