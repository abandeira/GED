/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diagram;

import diagram.componente.Aresta;
import diagram.componente.Componente;
import diagram.componente.Vertice;
import java.util.ArrayList;
import java.util.Comparator;

public class organizarArestas implements Comparator {
    
    @Override
    public int compare(Object o1, Object o2) {
        Aresta ob1 = (Aresta)o1;
        Vertice vertice1;
        if(ob1.getVerticeOrigem().getY()<ob1.getVerticeDestino().getY())
        {
            vertice1 = ob1.getVerticeOrigem();
        }
        else
        {
            vertice1 = ob1.getVerticeDestino();
        }
        
        Aresta ob2 = (Aresta)o2;
        Vertice vertice2;
        if(ob2.getVerticeOrigem().getY()<ob2.getVerticeDestino().getY())
        {
            vertice2 = ob2.getVerticeOrigem();
        }
        else
        {
            vertice2 = ob2.getVerticeDestino();
        }        
        
        if(vertice1.getY() == vertice2.getY() ) 
        {
           if( vertice1.getX() < vertice2.getX() ) 
           {
                return -1;
           } 
           else 
           {
               if( vertice1.getX() > vertice2.getX() ) 
               {
                    return 1;
               }
           }
            return 0;    
        } 
        else 
        {
            if( vertice1.getY() < vertice2.getY() ) 
            {
                return -1;
            } 
            else 
            {
                if( vertice1.getY() > vertice2.getY() ) 
                {
                    return 1;
                }
            }
        }
        return 0;
    }    
}
