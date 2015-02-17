package org.openstreetmap.josm.plugins.RemoveRedundantPoints;

import java.util.ArrayList;

import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.data.osm.Relation;
import org.openstreetmap.josm.gui.MapFrame;
import org.openstreetmap.josm.gui.preferences.PreferenceSetting;
import org.openstreetmap.josm.plugins.Plugin;
import org.openstreetmap.josm.plugins.PluginInformation;
import org.openstreetmap.josm.plugins.turnrestrictions.list.TurnRestrictionsListDialog;
import org.openstreetmap.josm.plugins.turnrestrictions.preferences.PreferenceEditor;
import org.openstreetmap.josm.plugins.turnrestrictions.*;

/**
 * This is the main class for the sumoconvert plugin.
 * 
 */
public class FilterPlugin extends Plugin{
    
	private final ArrayList<Relation> turnrestrictions = new ArrayList<Relation>();
	
    public FilterPlugin(PluginInformation info) {
        super(info);
        FilterAction plg=new FilterAction();
        Main.main.menu.toolsMenu.add(plg);
        System.out.println(getPluginDir());
    }
    
    /**
     * Called when the JOSM map frame is created or destroyed. 
     */
    @Override
    public void mapFrameInitialized(MapFrame oldFrame, MapFrame newFrame) {             
        if (oldFrame == null && newFrame != null) { 
        }
    }

    @Override
    public PreferenceSetting getPreferenceSetting() {
        return new PreferenceEditor();
    }
}
