package org.openstreetmap.josm.plugins.RemoveRedundantPoints;

import static org.openstreetmap.josm.tools.I18n.tr;
import static org.openstreetmap.josm.tools.I18n.trn;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javafx.scene.layout.Pane;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.command.ChangeCommand;
import org.openstreetmap.josm.command.Command;
import org.openstreetmap.josm.command.DeleteCommand;
import org.openstreetmap.josm.command.SequenceCommand;
import org.openstreetmap.josm.data.coor.LatLon;
import org.openstreetmap.josm.data.osm.DataSet;
import org.openstreetmap.josm.data.osm.Node;
import org.openstreetmap.josm.data.osm.Way;
import org.openstreetmap.josm.gui.MapFrame;
import org.openstreetmap.josm.gui.MapView;

import com.sun.glass.events.WindowEvent;
import com.sun.javafx.collections.MappingChange.Map;
//import com.sun.xml.internal.bind.v2.runtime.property.Property;

public class FilterDialog
  extends JPanel
{
  private static final ArrayList<Node> Node = null;
  MapView mv;
  private JOptionPane optionPane;
  private static JDialog mainFrame;
  private JTextField display;
  private JCheckBox delete;
  private JComboBox portCombo;
  public JTextArea pen1;
  public JOptionPane epsi;
  public JTextArea pen2;
  public JTextArea pen3;
  public Object[] p;
  public Collection<String> x;
 
  /**
   * setting the threshold value 
   * setting action is made in UserInterface 
   * @return double=threshold
   */
  public static double epsilonvalue1()
  {
	  UserInterface ui=new UserInterface();
	  ui.setVisible(true);
	  //double scalare=Main.map.mapView.getScale()/2;
	  
	  double rez = ui.buttonAction();
	  return rez;
  }
  
  double eps = epsilonvalue1();
  
  /**
   * checking all nodes from the way
   * filtering them in the DPfilter class
   * erasing the redundant points from the way
   * representing the new way
   */
  public void filteraction()
  {
	List<Command> cmds = new LinkedList<>();
    Collection<Way> way = dataset.getSelectedWays();
    List<Node> reducedPoints = new ArrayList();
    List<Node> FiltrtedPoints = new ArrayList();
    List<Node> RPoints = new ArrayList();
    for (Way w : way)
    {
    	for (int i = 0; i < w.getNodes().size(); i++) {
        	FiltrtedPoints.add(w.getNode(i));
        }
      FilterTest dialog1 = new FilterTest();
      if (dialog1.test_selection() == true)
      {
        List<LatLon> points = new ArrayList(w.getNodes().size());
        for (int i = 0; i < w.getNodes().size(); i++) {
        	//System.out.println("punctul["+i+"]="+w.getNode(i).getCoor());
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
      }
    }
    
    for (Node n1new : reducedPoints)
    {
      boolean isConnection = n1new.isConnectionNode();
      if(!isConnection){
    	  RPoints.add(n1new);
    	  FiltrtedPoints.remove(n1new);
      }
    }
    for(Way w:way){
    	Way newWay= new Way(w);
    	newWay.setNodes(FiltrtedPoints);
    	cmds.add(new ChangeCommand(w, newWay));
	      if(RPoints.size()!=0){
			  cmds.add(new DeleteCommand(RPoints));
		}
    	
    }
    
    Main.main.undoRedo.add(new SequenceCommand("Add a new node to an existing way", cmds));
    Main.map.repaint();
    
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
