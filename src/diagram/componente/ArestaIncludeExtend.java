/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diagram.componente;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

/**
 *
 * @author ANDRE
 */
public class ArestaIncludeExtend extends Aresta{
	/**
	 * A cor padr�o da aresta utilizada pelo editor GEDE,
	 */
	public static final Color		CORPADRAO = Color.black;

	private static final int		DISTANCIA_CLIQUE_MOUSE = 3;
		
	private Color					corAresta;
	
        final static float dash1[] = {10.0f};  
        private BasicStroke dashed;
  
  

	/**
	 * Inst�ncia uma nova aresta simples dentro do editor com os seus respectivos v�rtices.
	 * 
	 * @param verticeOrigem o v�rtice inicial na qual a aresta est� associada
	 * @param verticeDestino o v�rtice final na qual a aresta est� associada
	 * @see diagram.Grafo#getAresta
	 */
	public ArestaIncludeExtend(String Tipo, Vertice verticeOrigem, Vertice verticeDestino) 
	{
		super(verticeOrigem, verticeDestino);
                Rotulo rotulo = new Rotulo();
                rotulo.setTexto(Tipo+" de "+verticeOrigem.getRotulo().getTexto()+" à "+verticeDestino.getRotulo().getTexto());
                this.setRotulo(rotulo);
		setCorArestaSimples(CORPADRAO);
                this.setDescricao(rotulo.getTexto());
	}
	
	/**
	 * Inst�ncia uma nova aresta simples dentro do editor com os seus respectivos v�rtices e seu c�digo.
	 * 
	 * @param codigo o novo c�digo da aresta.
	 * @param verticeOrigem o v�rtice inicial na qual a aresta est� associada
	 * @param verticeDestino o v�rtice final na qual a aresta est� associada
	 * @see diagram.Grafo#getAresta
	 */
	public ArestaIncludeExtend(int codigo, Vertice verticeOrigem, Vertice verticeDestino) 
	{
		super(codigo, verticeOrigem, verticeDestino);
		setCorArestaSimples(CORPADRAO);
	}
	
// M�todos get...
	
	/**
	 * Retorna a cor atual configurada para a aresta.
	 *
	 * @return a cor da aresta
	 * @see ArestaSimples#setCorArestaSimples
	 */
	public Color getCorArestaSimples()
	{
		return corAresta;
	}
	
// M�todos set...
	
	/**
	 * Altera a cor atual da aresta.
	 *
	 * @param a nova cor da aresta
	 * @see ArestaSimples#getCorArestaSimples
	 */
	public void setCorArestaSimples(Color corAresta)
	{
		this.corAresta = corAresta;
	}
	
	/**
	 * Desenha uma aresta no editor de diagramas utilizando o contexto gr�fico 
	 * passado pelo par�metro.
	 *
	 * @param desenho o contexto gr�fico do editor na qual desenhar� a aresta
	 * @see diagram.editor.AreaApplet#paint
	 * @see diagram.editor.AreaAplicativo#paintComponent
	 */

    @Override
	public final void desenharAresta(Graphics desenho)
	{
           
                dashed = new BasicStroke(1.0f,   
                                          BasicStroke.CAP_BUTT,   
                                          BasicStroke.JOIN_MITER,   
                                          10.0f, dash1, 0.0f);  
                Graphics2D g2 = (Graphics2D) desenho;
                                BasicStroke s =(BasicStroke) g2.getStroke();
                g2.setStroke(dashed);
                

		int i,
			pontoXInicial,
			pontoYInicial,
			pontoXFinal,
			pontoYFinal;

		desenho.setColor(corAresta);
		
		//Define o ponto inicial da aresta (O centro do verticeOrigem)
		pontoXInicial = getVerticeOrigem().getX() + getVerticeOrigem().getLargura()/2;
		pontoYInicial = getVerticeOrigem().getY() + getVerticeOrigem().getAltura()/2;
		
		//Define o ponto final (sem quebra) ou segundo ponto (com quebra) da aresta...
		if (getNumeroQuebras() > 0)
		{
			//(com quebra)
			pontoXFinal = getCoordenadaQuebraX(0);
			pontoYFinal = getCoordenadaQuebraY(0);
		}
		else
		{
			//(sem quebra)
			pontoXFinal = getVerticeDestino().getX() + getVerticeDestino().getLargura()/2;
			pontoYFinal = getVerticeDestino().getY() + getVerticeDestino().getAltura()/2;
		}
 
               
                //Chamada do método para conseguir os pontos de intersecção entre uma reta e uma elipse
                ArrayList<Integer> interseccaoDestino = interseccao(getVerticeDestino(),pontoXInicial, pontoYInicial, pontoXFinal, pontoYFinal);
                     
                //Desenha a linha entre os dois vertices
                desenho.drawLine(pontoXInicial, pontoYInicial, interseccaoDestino.get(0), interseccaoDestino.get(1));

                //Chamada do método usado para calcular a ponta da seta
                ArrayList<ArrayList<Integer>> v = calcularPonta(pontoXInicial,pontoYInicial,interseccaoDestino.get(0),interseccaoDestino.get(1));
                
                //Desenha a ponta da seta
                desenho.drawLine(v.get(0).get(0), v.get(0).get(1),v.get(0).get(2), v.get(0).get(3));
                desenho.drawLine(v.get(1).get(0), v.get(1).get(1),v.get(1).get(2), v.get(1).get(3));

                
		//Desenha a aresta com todas as quebras existentes at� o ponto final(verticeDestino)...
		for (i = 1; i <= getNumeroQuebras(); i++)
		{
			pontoXInicial = pontoXFinal;
			pontoYInicial = pontoYFinal;
			
			if (i == getNumeroQuebras())
			{
				pontoXFinal = getVerticeDestino().getX() + getVerticeDestino().getLargura()/2;
				pontoYFinal = getVerticeDestino().getY() + getVerticeDestino().getAltura()/2;
			}
			else
			{
				pontoXFinal = getCoordenadaQuebraX(i);
				pontoYFinal = getCoordenadaQuebraY(i);
			}
			
			desenho.drawLine(pontoXInicial, pontoYInicial, pontoXFinal, pontoYFinal);
    
                        
		}
                g2.setStroke(s);
	}
	
	/**
	 * Identifica se a coordenada passada pelo par�metro est� contida em algum dos
	 * pontos pertencentes a aresta.
	 *
	 * @param x a coordenada do eixo X
	 * @param y a coordenada do eixo X
	 * @return se a coordenada est� contida na aresta
	 */
    @Override
	public final boolean coordenadaPertenceAresta(int x, int y)
	{
		int 			i, pontoXInicial, pontoYInicial, pontoXFinal, pontoYFinal;
		boolean			existePonto;
		Line2D.Float 	aresta;
		
		existePonto = false;
		if (getNumeroQuebras() != 0)
		{
			pontoXInicial = getVerticeOrigem().getX() + (getVerticeOrigem().getLargura() / 2);
			pontoYInicial = getVerticeOrigem().getY() + (getVerticeOrigem().getAltura() / 2);
			
			for (i = 0; i <= getNumeroQuebras(); i++)
			{
				if (i != 0)
				{
					pontoXInicial = getCoordenadaQuebraX(i - 1);
					pontoYInicial = getCoordenadaQuebraY(i - 1);	
				}
				
				if (i == getNumeroQuebras())
				{
					pontoXFinal = getVerticeDestino().getX() + (getVerticeDestino().getLargura() / 2);
					pontoYFinal = getVerticeDestino().getY() + (getVerticeDestino().getAltura() / 2);	
				}
				else
				{
					pontoXFinal = getCoordenadaQuebraX(i);
					pontoYFinal = getCoordenadaQuebraY(i);
				}
				
				aresta = new Line2D.Float(pontoXInicial, pontoYInicial, pontoXFinal, pontoYFinal);
				existePonto = aresta.intersects(x - DISTANCIA_CLIQUE_MOUSE, y - DISTANCIA_CLIQUE_MOUSE, DISTANCIA_CLIQUE_MOUSE * 2, DISTANCIA_CLIQUE_MOUSE * 2);
				
				if (existePonto == true)
					break;
			}
		}
		else
		{
			pontoXInicial = getVerticeOrigem().getX() + (getVerticeOrigem().getLargura() / 2);
			pontoYInicial = getVerticeOrigem().getY() + (getVerticeOrigem().getAltura() / 2);
			pontoXFinal = getVerticeDestino().getX() + (getVerticeDestino().getLargura() / 2);
			pontoYFinal = getVerticeDestino().getY() + (getVerticeDestino().getAltura() / 2);
			
			aresta = new Line2D.Float(pontoXInicial, pontoYInicial, pontoXFinal, pontoYFinal);
			
			existePonto = aresta.intersects(x - DISTANCIA_CLIQUE_MOUSE, y - DISTANCIA_CLIQUE_MOUSE, DISTANCIA_CLIQUE_MOUSE * 2, DISTANCIA_CLIQUE_MOUSE * 2);
		}
		
		return existePonto;
	}

    //Metodo que retorno os pontos para o desenho da ponta
    public ArrayList<ArrayList<Integer>> calcularPonta(double xini, double yini, double xfim, double yfim)
    {
        //Variavel que representa se a reta está totalmente na vertical ou seja (x2-x1)=0
        int vertical =0;
        
        //Lista que recebe uma lista de inteiros representando os pontos de interse
        ArrayList<ArrayList<Integer>> seta = new ArrayList<ArrayList<Integer>>();
        //Equacao da reta
        double ca,y2,y1,x2,x1;
        y2 = yfim; y1 = yini; x1 = xini; x2 = xfim;
        try{
             ca = (y2-y1)/(x2-x1);
        }catch(java.lang.ArithmeticException e)
        {
            ca=0;
            vertical =1;
        }
        if(Double.doubleToLongBits(ca) == Double.doubleToLongBits(Double.NaN))
        {
            ca = 0;
            vertical =1;
        }
        double cl = (y1)-(ca*x1);
        String retaredu = "y="+ca+"x+"+cl;
        //System.out.println((y2-y1)/(x2-x1));
        //System.out.println("Reta="+retaredu);
        
       
        double caPer=(-1/ca);
        
        //Calcular o ponto de 9 partes da reta
        double Xm;
        double Ym;
        if(x2>x1)
        {

            Xm= x2 - ((x2-x1)/10);

        }
        else
        {

            Xm= x2 + ((x1-x2)/10);

        }
        
        if(y2>y1)
        {
            Ym= y2 - ((y2-y1)/10);
        }
        else
        {
            Ym= y2 + ((y1-y2)/10);
        }
          
        double clPer = 0;
        //System.out.println("ca="+ca);
        //System.out.println("clPer="+caPer);

        //System.out.println("ca="+ca);
        //System.out.println("dif="+(x2-x1));
        if((ca!=0&&caPer!=0)&&((x2-x1>70||(x2-x1<-70))))
        {
            //System.out.println("CASO 1");
            //SYSTEM.out.println("ca="+ca);
            //SYSTEM.out.println("cl="+cl);
            //SYSTEM.out.println("capar="+caPer);
            //reta perpendicular
            clPer = (Ym-(caPer*Xm));
            String retaPer =  "y="+caPer+"x+"+clPer;
            //System.out.println("Reta Perpendicular:"+retaPer);
        
            //reta paralela inferior
            double caParInf = ca;
            double clParInf = cl+20;
            String retaParInf = "y="+caParInf+"x+"+clParInf;
            //System.out.println("Reta Paralela Inferior:"+retaParInf);

            //Pegar ponto de itersecção da reta perpendicular e a paralela inferior
            double xI = (clParInf - clPer)/(caPer - caParInf);
            double yI = (caParInf*xI)+clParInf;    

            //TESTE DE RETA INFERIOR
            ArrayList<Integer> pontos = new ArrayList<Integer>();
            
            pontos.add((int)xI);
            pontos.add((int)yI);
            
            pontos.add((int)x2);
            pontos.add((int)y2);
            
            
            
            seta.add(pontos);

            //reta paralela superior
            double caParSup = ca;
            double clParSup = cl-(20);
            String retaParSup = "y="+caParSup+"x+"+clParSup;
            //System.out.println("Reta Paralela Superior="+retaParSup);

            //Pegar ponto de itersecção da reta perpendicular e a paralela inferior
            double xS = (clParSup - clPer)/(caPer - caParSup);
            double yS = (caParSup*xS)+clParSup;    

            //TESTE DE RETA 
            ArrayList<Integer> pontos2 = new ArrayList<Integer>();
            pontos2.add((int)xS);
            pontos2.add((int)yS);
            
            pontos2.add((int)x2);
            pontos2.add((int)y2);
            
            seta.add(pontos2);
            
            
        }
        else
        {
            //Reta horizontal
            if((ca==0&&vertical!=1)&&((x2-x1>70||(x2-x1<-70))))
            {  
                //System.out.println("caso 2");
                //reta perpendicular
                clPer = Xm;
                caPer = 1;
                String retaPer =  "x="+Xm;
                //System.out.println("Reta Perpendicular:"+retaPer); 

                //reta paralela inferior
                double caParInf = ca;
                double clParInf = cl+20;
                String retaParInf = "y="+caParInf+"x+"+clParInf;
                //System.out.println("Reta Paralela Inferior:"+retaParInf);

                //Pegar ponto de itersecção da reta perpendicular e a paralela inferior
                double xI = Xm;
                double yI = (caParInf*xI)+clParInf;    

                //TESTE DE RETA INFERIOR
 
                ArrayList<Integer> pontos = new ArrayList<Integer>();
                pontos.add((int)xI);
                pontos.add((int)yI);
            
                pontos.add((int)x2);
                pontos.add((int)y2);
            

            
                seta.add(pontos);

                //reta paralela superior
                double caParSup = ca;
                double clParSup = cl-20;
                String retaParSup = "y="+caParSup+"x+"+clParSup;
                //System.out.println("Reta Paralela Superior="+retaParSup);

                //Pegar ponto de itersecção da reta perpendicular e a paralela inferior
                double xS = Xm;
                double yS = (caParSup*xS)+clParSup;  

                //TESTE DE RETA 
                ArrayList<Integer> pontos2 = new ArrayList<Integer>();
                pontos2.add((int)xS);
                pontos2.add((int)yS);
            
                pontos2.add((int)x2);
                pontos2.add((int)y2);
            
        
            
                seta.add(pontos2);
            }
            else
            {

                //System.out.println("CASO 3");

                //Pegar ponto de itersecção da reta perpendicular e a paralela inferior
                double yI= Ym;
                double xI = x2+20;
  
                
                //TESTE DE RETA INFERIOR
                ArrayList<Integer> pontos = new ArrayList<Integer>();
                pontos.add((int)xI);
                pontos.add((int)yI);
            
                pontos.add((int)x2);
                pontos.add((int)y2);
  
            
                seta.add(pontos);

                //Pegar ponto de itersecção da reta perpendicular e a paralela inferior
                double yS= Ym;
                double xS = x2-20;

                //TESTE DE RETA 
                ArrayList<Integer> pontos2 = new ArrayList<Integer>();
                pontos2.add((int)xS);
                pontos2.add((int)yS);
            
                pontos2.add((int)x2);
                pontos2.add((int)y2);

                seta.add(pontos2);
            }
        }
        return seta;
    }
   
     //calcula o ponto de intersecção da reta e da elipse
    public ArrayList<Integer> interseccao(Vertice v,int pontoXini,int pontoYini, int pontoXfim,int pontoYfim)
    {
        
        ArrayList<Integer> interseccao = new ArrayList<Integer>();
        
        //Definindo os dados da elipse
       double h = v.getX()+(v.getLargura()/2);
       double k = v.getY()+(v.getAltura()/2);
       
       double a = (v.getLargura()/2);
       double b = (v.getAltura()/2);
       //System.out.println("Eq da elipse: (x+"+h+")²/"+a+"² + (y+"+k+")²/"+b+"²");
       
       
                
       //Definindo os dados da reta
        double m,y2,y1,x2,x1;
        y2 = pontoYfim; y1 = pontoYini; x1 = pontoXini; x2 = pontoXfim;
        try{
            m = (y2-y1)/(x2-x1);
        }catch(java.lang.ArithmeticException e)
        {
            m=0;
        }
        
        double n = (y1)-(m*x1);
        //System.out.println("Eq da reta: "+m+"x +"+n);    
        int xlinha, ylinha, xlinha2,ylinha2;
        if(x2!=x1)
        {   
            //System.out.println("aqui");
           xlinha =(int) (((-(((-2*h)/(a*a))+((2*m*n)/(b*b))-((2*m*k)/(b*b))))
                    +(Math.sqrt(((((-2*h)/(a*a))+((2*m*n)/(b*b))+((-2*m*k)/(b*b)))*(((-2*h)/(a*a))+((2*m*n)/(b*b))+((-2*m*k)/(b*b))))
                    - (4*((1/(a*a))+((m*m)/(b*b)))*(((h*h)/(a*a))+((-2*k*n)/(b*b))+((k*k)/(b*b))+(-1)+((n*n)/(b*b)))))))
                    /(2*((1/(a*a))+((m*m)/(b*b)))));

           ylinha = (int)((m*xlinha)+n);

           xlinha2 =(int) (((-(((-2*h)/(a*a))+((2*m*n)/(b*b))-((2*m*k)/(b*b))))
                    -(Math.sqrt(((((-2*h)/(a*a))+((2*m*n)/(b*b))+((-2*m*k)/(b*b)))*(((-2*h)/(a*a))+((2*m*n)/(b*b))+((-2*m*k)/(b*b))))
                    - (4*((1/(a*a))+((m*m)/(b*b)))*(((h*h)/(a*a))+((-2*k*n)/(b*b))+((k*k)/(b*b))+(-1)+((n*n)/(b*b)))))))
                    /(2*((1/(a*a))+((m*m)/(b*b)))));

           ylinha2 = (int)((m*xlinha2)+n);

        }
        else
        {
            
            n=x1;
            ylinha = (int) (v.getY()+v.getAltura());
            ylinha2 = (int) v.getY();
            xlinha =(int) n;
            xlinha2 =(int) n;
 
        }
        
        if(x2-x1<70&&x2-x1>-70)
        {
            if((x1>x2&&y1<y2)||(x1<x2&&y1>y2))
            {
                System.out.println("caso1");
                ylinha2 = (v.getY()+v.getAltura());
                ylinha = (int) v.getY();
            }
            else
            {
                System.out.println("caso2");
                ylinha = (v.getY()+v.getAltura());
                ylinha2 = (int) v.getY();
            }
        }
            //Calcular o ponto medio partes da reta
            double Xm;
            double Ym;
            if(x2>x1)
            {

                Xm= x2 - ((x2-x1)/2);

            }
            else
            {

                Xm= x2 + ((x1-x2)/2);

            }

            if(y2>y1)
            {
                Ym= y2 - ((y2-y1)/2);
            }
            else
            {
                Ym= y2 + ((y1-y2)/2);
            }

            int difXlinha = (int) Math.sqrt(Math.pow(Xm-xlinha,2));
            int difXlinha2 = (int) Math.sqrt(Math.pow(Xm-xlinha2,2));

            int difYlinha = (int) Math.sqrt(Math.pow(Ym-ylinha,2));
            int difYlinha2 = (int) Math.sqrt(Math.pow(Ym-ylinha2,2));

            if(difXlinha<difXlinha2)
            {
                //System.out.println("caso1");
                //System.out.println("xlinha="+xlinha);
                //System.out.println("ylinha="+ylinha);
                interseccao.add(xlinha);
                interseccao.add(ylinha);
            }
            else
            {
                if(difXlinha>difXlinha2)
                {
                    //System.out.println("caso2");
                    interseccao.add((int) Math.sqrt(Math.pow(xlinha2,2)));
                    interseccao.add((int) Math.sqrt(Math.pow(ylinha2,2)));                
                }
                else
                {
                    if(difYlinha<difYlinha2)
                    {
                        //System.out.println("caso3");
                        interseccao.add(xlinha);
                        interseccao.add(ylinha);
                    }
                    else
                    {
                        //System.out.println("caso4");
                        interseccao.add(xlinha2);
                        interseccao.add(ylinha2);
                    }
                }
            }
            
        //System.out.println("INTERSECCAO");
        //System.out.println(interseccao.get(0));
        //System.out.println(interseccao.get(1));
        return interseccao;

    }
   
}  