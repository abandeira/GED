/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diagram;

import diagram.componente.Vertice;
import java.util.Comparator;

/**
 *
 * @author ANDRE
 */
public class organizarAlfabeticamente implements Comparator{

    @Override
    public int compare(Object o1, Object o2) {
        Vertice V1 = (Vertice)o1;
        Vertice V2 = (Vertice)o2;
        System.out.println("");
        for (int i = 0; i < V1.getRotulo().getTexto().length(); i++) {
            String letra = ""+V1.getRotulo().getTexto().charAt(i);
            int letraV1 = Integer.valueOf(letra.toUpperCase().charAt(0));
            int letraV2 = 0;
            if (V2.getRotulo().getTexto().length()!=i)
            {
                letra = ""+V2.getRotulo().getTexto().charAt(i);
                letraV2 = Integer.valueOf(letra.toUpperCase().charAt(0));
            }
            else
            {
                return 1;
            }
            if(letraV1<letraV2)
            {
                return -1;
            }
            else
            {
                if(letraV1>letraV2)
                {
                    return 1;
                }
            }
        }
        return -1;
    }
}
