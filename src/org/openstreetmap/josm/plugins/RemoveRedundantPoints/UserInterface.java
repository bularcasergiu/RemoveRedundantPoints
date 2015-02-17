package org.openstreetmap.josm.plugins.RemoveRedundantPoints;

import static org.openstreetmap.josm.tools.I18n.tr;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;



import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.actions.InfoAction;
import org.openstreetmap.josm.data.osm.OsmPrimitive;
import org.openstreetmap.josm.gui.ExtendedDialog;
import org.openstreetmap.josm.gui.Notification;
import org.openstreetmap.josm.tools.I18n;

import com.sun.glass.events.KeyEvent;

public class UserInterface extends ExtendedDialog{
	private JTextField epsilon;
	private JSlider epsLevel;
	private static final int FPS_MIN = 1;
	private static final int FPS_MAX =  53;
	/**
	 * make user interface from that plugin
	 */
	public UserInterface() {
		 super(Main.parent, tr("Remove redundant points"), new String[] { tr("OK"), tr("Cancel") }, true);
		 JPanel editPanel = createContentPane();

	        setContent(editPanel);
	        setButtonIcons(new String[] { "ok.png", "cancel.png" });
	        setDefaultButton(1);
	        setupDialog();
	        getRootPane().setDefaultButton(defaultButton);

	        // middle of the screen
	        setLocationRelativeTo(null);
	        SwingUtilities.invokeLater(new Runnable()  {
	            @Override
	            public void run() {
	            	epsilon.requestFocus();
	            	epsilon.selectAll();
	            }
	        });
	        
		// TODO Auto-generated constructor stub
	}
	
	/**
	 *setting the UI entities 
	 * 
	 * @return JPanel "editPanel"
	 */
	 private JPanel createContentPane() {
		 	JPanel editPanel = new JPanel(new GridBagLayout());
		 	
	     	GridBagConstraints c = new GridBagConstraints();
	        
	     	JLabel seqLabel = new JLabel(tr("Set the threshold distance(filtering level) in meters:"));
	     	c.fill = GridBagConstraints.HORIZONTAL;
	     	c.gridx = 0;
	     	c.gridy = 0;
	        c.weightx = 0;
	        c.gridwidth = 1;
	        editPanel.add(seqLabel, c);
	        
	        epsilon = new JTextField();
	        epsilon.setPreferredSize(new Dimension(100, 24));
	        c.fill = GridBagConstraints.HORIZONTAL;
	        c.gridx = 0;
	        c.gridy = 1;
	        c.weightx = 0;
	        c.gridwidth = 1;
	    	editPanel.add(epsilon, c);
	    	
	    	
	        epsLevel = new JSlider(JSlider.HORIZONTAL,  FPS_MIN, FPS_MAX,1){
	        	
	        };
	        epsLevel.setPaintTicks(true);
	        epsLevel.setMajorTickSpacing(4);
	        epsLevel.setMinorTickSpacing(1);
	        epsLevel.setPaintLabels(true);
	        epsLevel.setSnapToTicks(true);
	        epsLevel.addChangeListener(new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent e) {
					JSlider source=(JSlider)e.getSource();
					epsilon.setText(""+source.getValue());
					epsilon.setBackground(new Color(255-3*source.getValue(),255-3*source.getValue(),255-3*source.getValue()));
				}
			});
	        epsLevel.setPreferredSize(new Dimension(500, 60));
	        c.gridx = 0;
	        c.gridy = 7;
	        c.weightx = 1;
	        c.gridwidth = 1;
	        editPanel.add(epsLevel, c);
	    return editPanel;	   	 
	 }
	 
	 /**
	  * OK button action 
	  * make restrictions about threshold value
	  * checking threshold value
	  * @return double threshold
	  */
	 protected double buttonAction() {
	        double number = 0;
	        double eps = 0;
	        eps=epsLevel.getValue();
	        if(!epsilon.getText().isEmpty()){
	        	try {
		            number = Double.parseDouble(epsilon.getText());
		        } catch (NumberFormatException e)  {
		        	JOptionPane.showMessageDialog(
		                    Main.parent,
		                    tr("Please write an number >0 and <31"),
		                    tr("Error"),
		                    JOptionPane.ERROR_MESSAGE
		            ); 
		        }
		        if (number > 0 && number<=FPS_MAX) {
		        	number=Double.valueOf(epsilon.getText());
		        	eps=number;
		        	//System.out.println("EPS="+number);  
		        }
		        else {
		        	JOptionPane.showMessageDialog(
		                    Main.parent,
		                    tr("Please write an number >0 and <31"),
		                    tr("Error"),
		                    JOptionPane.ERROR_MESSAGE
		            );
		        }
	        }
	        else{
	        	eps=epsLevel.getValue();
	        	epsilon.setText(epsilon.getText()+eps);
		        //System.out.println("nivelul e:"+eps);
	        }
	        setVisible(false);
	        return eps;
	    }
	   
}
