package diagram.componente;

import java.awt.Graphics;
import java.awt.Color;

import java.awt.geom.Line2D;

/**
 * Cria uma aresta simples dentro do editor de diagramas GEDE. Esssa aresta tem o 
 * formato de uma linha reta sem nenhuma seta em suas extremidades. 
 *
 * @author Luis Henrique Castilho da Silva
 * @see Aresta
 */
public class ArestaSimples extends Aresta
{
	/**
	 * A cor padr�o da aresta utilizada pelo editor GEDE,
	 */
	public static final Color		CORPADRAO = Color.black;

	private static final int		DISTANCIA_CLIQUE_MOUSE = 3;
		
	private Color					corAresta;
	
	/**
	 * Inst�ncia uma nova aresta simples dentro do editor com os seus respectivos v�rtices.
	 * 
	 * @param verticeOrigem o v�rtice inicial na qual a aresta est� associada
	 * @param verticeDestino o v�rtice final na qual a aresta est� associada
	 * @see diagram.Grafo#getAresta
	 */
	public ArestaSimples(Vertice verticeOrigem, Vertice verticeDestino) 
	{
		super(verticeOrigem, verticeDestino);
                Rotulo rotulo = new Rotulo();
                rotulo.setTexto("");
                this.setRotulo(rotulo);
		setCorArestaSimples(CORPADRAO);
                this.setDescricao("Aresta de "+verticeOrigem.getRotulo().getTexto()+" à "+verticeDestino.getRotulo().getTexto());
	}
	
	/**
	 * Inst�ncia uma nova aresta simples dentro do editor com os seus respectivos v�rtices e seu c�digo.
	 * 
	 * @param codigo o novo c�digo da aresta.
	 * @param verticeOrigem o v�rtice inicial na qual a aresta est� associada
	 * @param verticeDestino o v�rtice final na qual a aresta est� associada
	 * @see diagram.Grafo#getAresta
	 */
	public ArestaSimples(int codigo, Vertice verticeOrigem, Vertice verticeDestino) 
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
	public final void desenharAresta(Graphics desenho)
	{
		int i,
			pontoXInicial,
			pontoYInicial,
			pontoXFinal,
			pontoYFinal;

		desenho.setColor(corAresta);
		
		//Define o ponto inicial da aresta (O centro da do verticeOrigem)
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
		
		desenho.drawLine(pontoXInicial, pontoYInicial, pontoXFinal, pontoYFinal);
		
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
	}
	
	/**
	 * Identifica se a coordenada passada pelo par�metro est� contida em algum dos
	 * pontos pertencentes a aresta.
	 *
	 * @param x a coordenada do eixo X
	 * @param y a coordenada do eixo X
	 * @return se a coordenada est� contida na aresta
	 */
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

}