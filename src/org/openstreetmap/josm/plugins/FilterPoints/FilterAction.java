package org.openstreetmap.josm.plugins.FilterPoints;

import java.awt.event.ActionEvent;
import org.openstreetmap.josm.actions.JosmAction;
import org.openstreetmap.josm.gui.Notification;
import org.openstreetmap.josm.tools.I18n;
import org.openstreetmap.josm.tools.Shortcut;

public class FilterAction
  extends JosmAction
{
  public FilterAction()
  {
    super(I18n.tr("Erase redundant points", new Object[0]), "images/dialogs/joinareas.png", 
      I18n.tr("Erase .", new Object[0]), 
      Shortcut.registerShortcut("menu:sumoexport", I18n.tr("Menu: {0}", new Object[] {I18n.tr("oiijlkjljlj", new Object[0]) }), 71, 5008), false);
  }
  
  public void actionPerformed(ActionEvent arg0)
  {
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
}
