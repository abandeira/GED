/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diagram;

import diagram.componente.Componente;
import diagram.componente.Vertice;
import java.util.ArrayList;
import java.util.Comparator;

public class organizarVertices implements Comparator {
    
    @Override
    public int compare(Object o1, Object o2) {
        Vertice ob1 = (Vertice)o1;
        Vertice ob2 = (Vertice)o2;
        if(ob1.getY() == ob2.getY() ) 
        {
           if( ob1.getX() < ob2.getX() ) 
           {
                return -1;
           } 
           else 
           {
               if( ob1.getX() > ob2.getX() ) 
               {
                    return 1;
               }
           }
            return 0;    
        } 
        else 
        {
            if( ob1.getY() < ob2.getY() ) 
            {
                return -1;
            } 
            else 
            {
                if( ob1.getY() > ob2.getY() ) 
                {
                    return 1;
                }
            }
        }
        return 0;
    }    
}
