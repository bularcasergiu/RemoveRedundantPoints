package org.openstreetmap.josm.plugins.RemoveRedundantPoints;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.data.osm.DataSet;
import org.openstreetmap.josm.data.osm.Node;
import org.openstreetmap.josm.data.osm.Way;
import org.openstreetmap.josm.gui.MapView;

public class FilterTest
  extends JPanel
{
 
  /**
   * test if selected object is an "building" object
   * if true FilterAction make his action
   * else, inform user with that problem
   * @return boolean 
   */
  
  public boolean test_selection()
  {
    boolean bool = false;
    Collection<Way> way = this.dataset.getSelectedWays();
    
    String key = "building";
    for (Way w : way) {
      if (!w.getInterestingTags().containsKey(key))
      {
        bool = true;
        return bool;
      }
    }
    //System.out.println("kkkkk" + bool);
    return bool;
  }
  
  
  /**
   * checking all data information about selected object
   * @return DataSet 
   */
  public DataSet getDataSet()
  {
    return Main.main.getCurrentDataSet();
  }
  
  DataSet dataset = getDataSet();
}
