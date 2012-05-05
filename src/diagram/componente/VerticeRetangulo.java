package diagram.componente;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Component;

/**
 * Cria um novo v�rtice dentro do contexto do editor de diagramas GEDE no 
 * formato de um ret�ngulo.
 *
 * @author Luis Henrique Castilho da Silva
 * @see Vertice
 */
public class VerticeRetangulo extends Vertice
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
	 * Cria um novo v�rtice na forma de uma ret�ngulo.
	 *
	 * @see diagram.Grafo#getVertice
	 */	
	public VerticeRetangulo() 
	{
		super();
		setLarguraBorda(LARGURABORDAPADRAO);
		setCorBorda(CORBORDAPADRAO);
		setCorFundo(CORFUNDOPADRAO);
	}
	
	/**
	 * Cria um novo v�rtice na forma de um ret�ngulo localizado nas coordenadas
	 * passada pelo par�metro.
	 *
	 * @param x a ccordenada do v�rtice no eixo X
	 * @param y a ccordenada do v�rtice no eixo Y
	 * @see diagram.Grafo#getVertice
	 */
	public VerticeRetangulo(int x, int y)
	{
		super(x, y);
		setLarguraBorda(LARGURABORDAPADRAO);
		setCorBorda(CORBORDAPADRAO);
		setCorFundo(CORFUNDOPADRAO);
	}
	
	/**
	 * Cria um novo v�rtice na forma de um ret�ngulo localizado nas coordenadas
	 * passada pelo par�metro e com largura e altura definida na chamada deste
	 * construtor e com um c�digo inicial.
	 * 
	 * @param codigo o inteiro que define o c�digo para o v�rtice
	 * @param x a ccordenada do v�rtice no eixo X
	 * @param y a ccordenada do v�rtice no eixo Y
	 * @param largura o comprimento da largura do v�rtice
	 * @param altura o comprimento da altura do v�rtice
	 * @see diagram.Grafo#getVertice
	 */
	public VerticeRetangulo(int codigo, int x, int y, int largura, int altura)
	{
		super(codigo, x, y, largura, altura);
		setLarguraBorda(LARGURABORDAPADRAO);
		setCorBorda(CORBORDAPADRAO);
		setCorFundo(CORFUNDOPADRAO);
	}
	
	/**
	 * Cria um novo v�rtice na forma de um ret�ngulo localizado nas coordenadas
	 * passada pelo par�metro al�m de outros atributos definidos neste construtor.
	 * 
	 * @param codigo o inteiro que define o c�digo para o v�rtice
	 * @param x a ccordenada do v�rtice no eixo X
	 * @param y a ccordenada do v�rtice no eixo Y
	 * @param largura o comprimento da largura do v�rtice
	 * @param altura o comprimento da altura do v�rtice
	 * @param larguraBorda a largura, em pixels, da borda do v�rtice
	 * @see diagram.Grafo#getVertice
	 */
	public VerticeRetangulo(int codigo, int x, int y, int largura, int altura, int larguraBorda)
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
	 * Desenha um ret�ngulo no contexto gr�fico passado pelo par�metro.
	 *
	 * @param desenho o contexto gr�fico na qual desenhar� o v�rtice
	 * @param componente o componente na qual o contexto gr�fico est� inserido
	 * @see diagram.editor.AreaApplet#paint
	 * @see diagram.editor.AreaAplicativo#paintComponent
	 */
	public final void desenharVertice(Graphics desenho, Component componente)
	{
		int i;
		
		//Desenha o fundo do v�rtice...
		desenho.setColor(corFundo);
		desenho.fillRect(getX(), getY(), getLargura(), getAltura());
		
		//Desenhar Borda...
		desenho.setColor(corBorda);
		for (i = 0;i < larguraBorda; i++)
			desenho.drawRect(getX() + i, getY() + i, getLargura() - i * 2, getAltura() - i * 2);
	}
	
	/**
	 * Identifica se a coordenada est� contida na �rea do ret�ngulo. O m�todo 
	 * retorna verdadeiro se o ponto estiver dentro dos limites caso contr�rio 
	 * retorna-se falso. 
	 * 
	 * @param x a coordenada do eixo X
	 * @param y a coordenada do eixo Y
	 * @return se a coordenada est� contida no v�rtice
	 */
	public final boolean coordenadaPertenceVertice(int x, int y)
	{
		if ((x >= getX()) && (x <= getLargura() + getX()))
			if ((y >= getY()) && (y <= getAltura() + getY()))
				return true;
			
		return false;
	}
}