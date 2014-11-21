package org.openstreetmap.josm.plugins.FilterPoints;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import org.openstreetmap.josm.data.coor.LatLon;

public class DPfilter
{
  private double epsilon;
  
  public DPfilter(double epsilon)
  {
    if (epsilon <= 0) {
      throw new IllegalArgumentException("Epsilon must be > 0");
    }
    this.epsilon = epsilon;
  }
  
  public double[][] filter(List<LatLon> data)
  {
    return ramerDouglasPeuckerFunction(data, 0, data.size() - 1);
  }
  
  public double getEpsilon()
  {
    return this.epsilon;
  }
  
  protected double[][] ramerDouglasPeuckerFunction(List<LatLon> points, int startIndex, int endIndex)
  {
    List<LatLon> projection = new ArrayList();
    List<LatLon> projectionNodes = new ArrayList();
    double[][] first_lastNodes = new double[2][2];
    
    double dmax = 0;
    int idx = 0;
    int a = 0;
    double distance = 0;
    double xx = 0;
    double yy = 0;
    double length = 0;
    

    first_lastNodes[0][0] = ((LatLon)points.get(startIndex)).lat();
    first_lastNodes[0][1] = ((LatLon)points.get(startIndex)).lon();
    first_lastNodes[1][0] = ((LatLon)points.get(endIndex)).lat();
    first_lastNodes[1][1] = ((LatLon)points.get(endIndex)).lon();
    for (int i = startIndex + 1; i < endIndex; i++)
    {
      double x0 = ((LatLon)points.get(i)).lon();double y0 = ((LatLon)points.get(i)).lat();
      double x1 = ((LatLon)points.get(startIndex)).lon();double y1 = ((LatLon)points.get(startIndex)).lat();
      double x2 = ((LatLon)points.get(endIndex)).lon();double y2 = ((LatLon)points.get(endIndex)).lat();
      

      double px = x2 - x1;double py = y2 - y1;double dAB = px * px + py * py;
      double u = ((x0 - x1) * px + (y0 - y1) * py) / dAB;
      xx = x1 + u * px;yy = y1 + u * py;
      if ((x2 == x1) && (y2 == y1)) {
        distance = Math.hypot(x1 - x0, y1 - y0);
      } else {
        distance = Math.abs((x2 - x1) * (y1 - y0) - (x1 - x0) * (y2 - y1)) / Math.hypot(x2 - x1, y2 - y1);
      }
      System.out.println("proiectiidoug(" + i + ")=" + yy + ";" + xx);
      projection.add(new LatLon(yy, xx));
      LatLon lastN = (LatLon)points.get(i);
      LatLon firstN = (LatLon)projection.get(a);
      a++;
      projectionNodes.add(firstN);
      projectionNodes.add(lastN);
      System.out.println("lisss=" + projectionNodes);
      projectionNodes.removeAll(projectionNodes);
      
      length = lastN.greatCircleDistance(firstN);
      System.out.println("distance[(" + i + "]=" + length);
      if (length > dmax)
      {
        idx = i;
        dmax = length;
      }
    }
    if (dmax >= this.epsilon)
    {
      double[][] recursiveResult1 = ramerDouglasPeuckerFunction(points, startIndex, idx);
      
      double[][] recursiveResult2 = ramerDouglasPeuckerFunction(points, idx, endIndex);
      
      double[][] result = new double[recursiveResult1.length - 1 + recursiveResult2.length][2];
      
      System.arraycopy(recursiveResult1, 0, result, 0, recursiveResult1.length - 1);
      
      System.arraycopy(recursiveResult2, 0, result, recursiveResult1.length - 1, recursiveResult2.length);
      
      return result;
    }
    return first_lastNodes;
  }
  
  public void setEpsilon(double epsilon)
  {
    if (epsilon <= 0.0D) {
      throw new IllegalArgumentException("Epsilon must be > 0");
    }
    this.epsilon = epsilon;
  }
}
