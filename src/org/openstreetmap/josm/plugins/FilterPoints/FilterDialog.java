package org.openstreetmap.josm.plugins.FilterPoints;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.data.coor.LatLon;
import org.openstreetmap.josm.data.osm.DataSet;
import org.openstreetmap.josm.data.osm.Node;
import org.openstreetmap.josm.data.osm.Way;
import org.openstreetmap.josm.gui.MapFrame;
import org.openstreetmap.josm.gui.MapView;

public class FilterDialog
  extends JPanel
{
  private static final ArrayList<Node> Node = null;
  MapView mv;
  private JOptionPane optionPane;
  private JCheckBox delete;
  private JComboBox portCombo;
  public JTextArea pen1;
  public JOptionPane epsi;
  public JTextArea pen2;
  public JTextArea pen3;
  public Object[] p;
  public Collection<String> x;
  
  public static double epsilonvalue()
  {
    Object[] selectionValues = { "low", "midle", "high" };
    JOptionPane pane = new JOptionPane(Integer.valueOf(2), 3, 2);
    pane.setWantsInput(true);
    pane.setVisible(true);
    pane.setSelectionValues(selectionValues);
    pane.setInitialSelectionValue(selectionValues[0]);
    
    JDialog dialog = pane.createDialog(Main.parent, "Filtering Points");
    pane.selectInitialValue();
    pane.setMessage("Set the filtering level");
    dialog.show();
    double rez = 0;
    if (pane.getInputValue() == selectionValues[0]) {
      rez = 3;
    }
    if (pane.getInputValue() == selectionValues[1]) {
      rez = 10;
    }
    if (pane.getInputValue() == selectionValues[2]) {
      rez = 30;
    }
    dialog.dispose();
    return rez;
  }
  
  double eps = epsilonvalue();
  
  public void filteraction()
  {
    Collection<Way> way = this.dataset.getSelectedWays();
    List<Node> reducedPoints = new ArrayList();
    for (Way w : way)
    {
      FilterTest dialog1 = new FilterTest();
      //int i;
      if (dialog1.test_selection() == true)
      {
        List<LatLon> points = new ArrayList(w.getNodes().size());
        for (int i = 0; i < w.getNodes().size(); i++) {
          points.add(w.getNode(i).getCoor());
        }
        double[][] fpoints = new double[points.size()][2];
        DPfilter rdpf = new DPfilter(eps);
        fpoints = rdpf.filter(points);
        double[][] redpoints = new double[points.size()][2];
        for (int i = 0; i < points.size(); i++) {
          for (int j = 0; j < fpoints.length; j++) {
            if ((((LatLon)points.get(i)).lat() == fpoints[j][0]) && (((LatLon)points.get(i)).lon() == fpoints[j][1]))
            {
              redpoints[i][0] = ((LatLon)points.get(i)).lat();
              redpoints[i][1] = ((LatLon)points.get(i)).lon();
            }
          }
        }
        for (int i = 0; i < w.getNodes().size(); i++) {
          if ((redpoints[i][0] != w.getNode(i).getCoor().lat()) && (redpoints[i][1] != w.getNode(i).getCoor().lon())) {
            reducedPoints.add(w.getNode(i));
          }
        }
        for (Node n1new : reducedPoints)
        {
          boolean isConnection = n1new.isConnectionNode();
          if (!isConnection)
          {
            this.dataset.removePrimitive(n1new);
            w.removeNode(n1new);
          }
        }
      }
      Main.map.repaint();
    }
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
