package diagram.componente;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.Component;

/**
 * Cria um novo v�rtice dentro do contexto do editor de diagramas GEDE no 
 * formato de uma elipse.
 *
 * @author Luis Henrique Castilho da Silva
 * @see Vertice
 */
public class VerticeElipse extends Vertice
{
	/**
	 * A largura padr�o da borda do v�rtice.
	 */
	public static final int		LARGURABORDAPADRAO = 1;
	
	/**
	 * A largura m�xima permitida para a borda do v�rtice.
	 */
	public static final int		LARGURAMAXIMABORDA = 10;
								
	/**
	 * A cor da borda padr�o para o v�rtice.
	 */
	public static final Color	CORBORDAPADRAO = Color.black;
	
	/**
	 * A cor de fundo padr�o para o v�rtice.
	 */
	public static final Color	CORFUNDOPADRAO = Color.white;
		
	private int 	larguraBorda;
	
	private Color	corBorda,
					corFundo;
	
	/**
	 * Cria um novo v�rtice na forma de uma elipse.
	 *
	 * @see diagram.Grafo#getVertice
	 */
	public VerticeElipse() 
	{
		super();
		setLarguraBorda(LARGURABORDAPADRAO);
		setCorBorda(CORBORDAPADRAO);
		setCorFundo(CORFUNDOPADRAO);
	}
	
	/**
	 * Cria um novo v�rtice na forma de uma elipse localizado nas coordenadas
	 * passada pelo par�metro.
	 *
	 * @param x a ccordenada do v�rtice no eixo X
	 * @param y a ccordenada do v�rtice no eixo Y
	 * @see diagram.Grafo#getVertice
	 */
	public VerticeElipse(int x, int y)
	{
		super(x, y);
		setLarguraBorda(LARGURABORDAPADRAO);
		setCorBorda(CORBORDAPADRAO);
		setCorFundo(CORFUNDOPADRAO);
	}
	
	/**
	 * Cria um novo v�rtice na forma de uma elipse localizado nas coordenadas
	 * passada pelo par�metro.
	 *
	 * @param x a ccordenada do v�rtice no eixo X
	 * @param y a ccordenada do v�rtice no eixo Y
	 * @see diagram.Grafo#getVertice
	 */
	public VerticeElipse(int codigo, int x, int y, int largura, int altura)
	{
		super(codigo, x, y, largura, altura);
		setLarguraBorda(LARGURABORDAPADRAO);
		setCorBorda(CORBORDAPADRAO);
		setCorFundo(CORFUNDOPADRAO);
	}
	
	/**
	 * Cria um novo v�rtice na forma de uma elipse localizado nas coordenadas
	 * passada pelo par�metro com largura e altura definida al�m dos outros 
	 * par�metros alterados neste construtor.
	 *
	 * @param codigo o inteiro que define o c�digo para o v�rtice
	 * @param x a ccordenada do v�rtice no eixo X
	 * @param y a ccordenada do v�rtice no eixo Y
	 * @param largura o comprimento da largura do v�rtice
	 * @param altura o comprimento da altura do v�rtice
	 * @param larguraBorda a largura, em pixels, da borda do v�rtice
	 * @see diagram.Grafo#getVertice
	 */
	public VerticeElipse(int codigo, int x, int y, int largura, int altura, int larguraBorda)
	{
		super(codigo, x, y, largura, altura);
		setLarguraBorda(larguraBorda);
		setCorBorda(CORBORDAPADRAO);
		setCorFundo(CORFUNDOPADRAO);
	}
	
//	M�todos GET...
	
	/**
	 * Retorna a largura da borda atual do v�rtice.
	 *
	 * @return a largura da borda
	 */
	public int getLarguraBorda()
	{
		return larguraBorda;
	}
	
	/**
	 * Retorna a cor atual utilizada para a borda.
	 *
	 * @return o cor da borda
	 */
	public Color getCorBorda()
	{
		return corBorda;
	}
	
	/**
	 * Retorna a cor atual de fundo utilizada no v�rtice.
	 *
	 * @return a cor de fundo
	 */
	public Color getCorFundo()
	{
		return corFundo;	
	}

//	M�todos SET...
	
	/**
	 * Altera a largura de borda do v�rtice.
	 *
	 * @param larguraBorda a nova largura
	 */
	public void setLarguraBorda(int larguraBorda)
	{
		if ((larguraBorda > 0) && (larguraBorda <= LARGURAMAXIMABORDA))
			this.larguraBorda = larguraBorda;
		else
			this.larguraBorda = LARGURABORDAPADRAO;	
	}
	
	/**
	 * Altera a cor atual da borda.
	 *
	 * @param corBorda a nova cor
	 */
	public void setCorBorda(Color corBorda)
	{
		this.corBorda = corBorda;	
	}
	
	/**
	 * Altera a cor atual utilizada no fundo do v�rtice.
	 *
	 * @param corFundo a nova cor
	 */
	public void setCorFundo(Color corFundo)
	{
		this.corFundo = corFundo;	
	}
	
	/**
	 * Desenha uma elipse no contexto gr�fico passado pelo par�metro.
	 *
	 * @param desenho o contexto gr�fico na qual desenhar� o v�rtice
	 * @param componente o componente na qual o contexto gr�fico est� inserido
	 * @see diagram.editor.AreaApplet#paint
	 * @see diagram.editor.AreaAplicativo#paintComponent
	 */
	public void desenharVertice(Graphics desenho, Component componente)
	{
		int i, j;
				
		//Desenhar Borda...
		desenho.setColor(corBorda);
		desenho.fillOval(getX(), getY(), getLargura(), getAltura());
		
		//Desenha o fundo do v�rtice...
		desenho.setColor(corFundo);
		desenho.fillOval(getX() + larguraBorda, getY() + larguraBorda, getLargura() - larguraBorda * 2, getAltura() - larguraBorda * 2);
	
	}
	
	/**
	 * Identifica se a coordenada est� contida na �rea da elipse. O m�todo retorna 
	 * verdadeiro se o ponto estiver dentro dos limites caso contr�rio retorna-se
	 * falso. 
	 * 
	 * @param x a coordenada do eixo X
	 * @param y a coordenada do eixo Y
	 * @return se a coordenada est� contida no v�rtice
	 * @see java.awt.geom.Ellipse2D.Float
	 */
	public boolean coordenadaPertenceVertice(int x, int y)
	{
		Ellipse2D.Float area = new Ellipse2D.Float (getX(), getY(), getLargura(), getAltura());
		
		return area.contains(x, y);
	}
}