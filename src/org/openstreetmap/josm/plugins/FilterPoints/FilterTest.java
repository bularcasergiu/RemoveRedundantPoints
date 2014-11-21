package org.openstreetmap.josm.plugins.FilterPoints;

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
  private static final ArrayList<Node> Node = null;
  MapView mv;
  private JOptionPane optionPane;
  
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
    System.out.println("kkkkk" + bool);
    return bool;
  }
  
  public void setOptionPane(JOptionPane optionPane)
  {
    this.optionPane = optionPane;
  }
  
  public DataSet getDataSet()
  {
    return Main.main.getCurrentDataSet();
  }
  
  DataSet dataset = getDataSet();
}
