package diagram.componente;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.RoundRectangle2D;
import java.awt.Component;

/**
 * Cria um novo v�rtice dentro do contexto do editor de diagramas GEDE no 
 * formato de um ret�ngulo com as bordas arredondadas.
 *
 * @author Luis Henrique Castilho da Silva
 * @see Vertice
 */
public class VerticeRetanguloBordaArredondado extends Vertice
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
	 * A largura padr�o do arco v�rtice.
	 */
	public static final int		LARGURAARCO = 30;
	
	/**
	 * A altura padr�o do arco do v�rtice.
	 */
	public static final int		ALTURAARCO = 30;
									
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
	 * Cria um novo v�rtice na forma de um ret�ngulo com bordas arredondadas.
	 *
	 * @see diagram.Grafo#getVertice
	 */		
	public VerticeRetanguloBordaArredondado()
	{
		super();
		setLarguraBorda(LARGURABORDAPADRAO);
		setCorBorda(CORBORDAPADRAO);
		setCorFundo(CORFUNDOPADRAO);
	}
	
	/**
	 * Cria um novo v�rtice na forma de um ret�ngulo com bordas arredondadas
	 * localizado nas coordenadas passada pelo par�metro.
	 *
	 * @param x a ccordenada do v�rtice no eixo X
	 * @param y a ccordenada do v�rtice no eixo Y
	 * @see diagram.Grafo#getVertice
	 */
	public VerticeRetanguloBordaArredondado(int x, int y)
	{
		super(x, y);
		setLarguraBorda(LARGURABORDAPADRAO);
		setCorBorda(CORBORDAPADRAO);
		setCorFundo(CORFUNDOPADRAO);
	}
	
	/**
	 * Cria um novo v�rtice na forma de um ret�ngulo com bordas arredondadas
	 * localizado nas coordenadas passada pelo par�metro, com largura e altura
	 * definida na chamada deste construtor e um c�digo inicial.
	 * 
	 * @param codigo o inteiro que define o c�digo para o v�rtice
	 * @param x a ccordenada do v�rtice no eixo X
	 * @param y a ccordenada do v�rtice no eixo Y
	 * @param largura o comprimento da largura do v�rtice
	 * @param altura o comprimento da altura do v�rtice
	 * @see diagram.Grafo#getVertice
	 */
	public VerticeRetanguloBordaArredondado(int codigo, int x, int y, int largura, int altura)
	{
		super(codigo, x, y, largura, altura);
		setLarguraBorda(LARGURABORDAPADRAO);
		setCorBorda(CORBORDAPADRAO);
		setCorFundo(CORFUNDOPADRAO);
	}
	
	/**
	 * Cria um novo v�rtice na forma de um ret�ngulo com bordas arredondadas
	 * localizado nas coordenadas passada pelo par�metro al�m de outros atributos
	 * definidos neste construtor.
	 * 
	 * @param codigo o inteiro que define o c�digo para o v�rtice
	 * @param x a ccordenada do v�rtice no eixo X
	 * @param y a ccordenada do v�rtice no eixo Y
	 * @param largura o comprimento da largura do v�rtice
	 * @param altura o comprimento da altura do v�rtice
	 * @param larguraBorda a largura, em pixels, da borda do v�rtice
	 * @see diagram.Grafo#getVertice
	 */
	public VerticeRetanguloBordaArredondado(int codigo, int x, int y, int largura, int altura, int larguraBorda)
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
	 * Desenha um ret�ngulo com bordas arredondadas no contexto gr�fico passado
	 * pelo par�metro.
	 *
	 * @param desenho o contexto gr�fico na qual desenhar� o v�rtice
	 * @param componente o componente na qual o contexto gr�fico est� inserido
	 * @see diagram.editor.AreaApplet#paint
	 * @see diagram.editor.AreaAplicativo#paintComponent
	 */
	public final void desenharVertice(Graphics desenho, Component componente)
	{
		int i;
		
		//Desenhar Borda...
		desenho.setColor(corBorda);
		desenho.fillRoundRect(getX(), getY(), getLargura(), getAltura(), LARGURAARCO, ALTURAARCO);
		
		//Desenha o fundo do v�rtice...
		desenho.setColor(corFundo);
		desenho.fillRoundRect(getX() + larguraBorda, getY() + larguraBorda, getLargura() - 2 * larguraBorda, getAltura() - 2 * larguraBorda, LARGURAARCO, ALTURAARCO);		
	}
	
	/**
	 * Identifica se a coordenada est� contida na �rea do ret�ngulo arredondado.
	 * O m�todo retorna verdadeiro se o ponto estiver dentro dos limites caso 
	 * contr�rio retorna-se falso.
	 * 
	 * @param x a coordenada do eixo X
	 * @param y a coordenada do eixo Y
	 * @return se a coordenada est� contida no v�rtice
	 * @see java.awt.geom.RoundRectangle2D.Float
	 */
	public final boolean coordenadaPertenceVertice(int x, int y)
	{	
		RoundRectangle2D.Float area = new RoundRectangle2D.Float(getX(), getY(), getLargura(), getAltura(), LARGURAARCO, ALTURAARCO);
		
		return area.contains(x, y);
	}
}