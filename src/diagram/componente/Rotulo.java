package diagram.componente;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.IllegalComponentStateException;
import java.util.Locale;
import javax.accessibility.Accessible;
import javax.accessibility.AccessibleContext;
import javax.accessibility.AccessibleRole;
import javax.accessibility.AccessibleStateSet;

/**
 * A classe r�tulo � respons�vel pela cria��o de textos associados aos componentes 
 * do grafo. Tais textos s�o exibidos no editor sobrepondo seus respectivos 
 * v�rtices e arestas.
 *
 * O r�tulo � um componente importante dentro do editor, pois ele define o conte�do 
 * de um v�rtice ou a fun��o de uma aresta dentro do contexto do diagrama.
 * 
 * A classe atualmente n�o permite alterar o tipo de fonte, o tamanho e o
 * estilo. No entanto, � permitido modificar a cor utilizada pela fonte. J� as 
 * coordenadas dos r�tulos s�o calculadas automaticamente pelo GEDE baseado no 
 * componente a qual ele est� relacionado.
 *
 * @author Luis Henrique Castilho da Silva
 * @see Componente
 */
public class Rotulo extends AccessibleContext
{
	/**
	 * A cor padr�o da fonte do r�tulo. 
	 */
	public static final Color	COR_FONTE_PADRAO = Color.black;
	
	private final int	DISTANCIA_MAXIMA_BORDA = 35,
						ALTURA_FONTE_PADRAO = 12;
	
	private final Color	COR_FUNDO_SELECAO = Color.green,
						COR_BORDA_SELECAO = Color.black;
						
	private Color		corFonte;

	private String		texto;
	
	//Atributos automaticos, ou seja, o programador n�o
	//sabe de sua exist�ncia
	private int 		coordenadaX,
						coordenadaY,
						larguraRotulo,
						alturaRotulo,
						distanciaEixoCentral;

	private boolean		rotuloSelecionado;
	
	/**
	 * Cria um r�tulo em branco.
	 */
	public Rotulo()
	{
		coordenadaX = 0;
		coordenadaY = 0;
		larguraRotulo = 0;
		alturaRotulo = 0;
		distanciaEixoCentral = 0;
		
		corFonte = COR_FONTE_PADRAO;
		texto = "";
		rotuloSelecionado = false;
	}
	
	/**
	 * Cria um r�tulo com o respectivo texto passado pelo par�metro.
	 */
	public Rotulo(String texto)
	{
		this();
		if (texto != null)
			this.texto = texto;
	}
	
//Metodos Get

	/**
	 * Retorna a cor da fonte utilizada pelo rot�lo.
	 *
	 * @return a cor da fonte
	 */
	public Color getCorFonte()
	{
		return corFonte;
	}

	/**
	 * Retorna o texto atual do r�tulo.
	 *
	 * @return a string contendo o texto
	 */
	public String getTexto()
	{
		return texto;
	}
	
	/**
	 * Retorna a situa��o do r�tulo no contexto do GEDE. Caso o valor de retorno
	 * seja verdadeiro, o r�tulo estar� selecionado e falso caso contr�rio.
	 *
	 * @return se o r�tulo est� selecionado ou n�o
	 */
	public boolean getRotuloSelecionado()
	{
		return rotuloSelecionado;
	}
	
	/**
	 * Retorna a dist�ncia do r�tulo em rela��o ao ponto central. Este ponto
	 * define a localiza��o padr�o (inicial) para o r�tulo quando ele for 
	 * inst�nciado.
	 *
	 * @return o inteiro que representa a dist�ncia do eixo central
	 */
	public int getDistanciaEixoCentral()
	{
		return distanciaEixoCentral;
	}
	
	/**
	 * Retorna a coordenada do r�tulo no eixo X.
	 *
	 * @return o inteiro que representa a coordenada
	 * @see Vertice#getX
	 */
	public int getCoordenadaX()
	{
		return coordenadaX;
	}
	
	/**
	 * Retorna a coordenada do r�tulo no eixo Y.
	 *
	 * @return o inteiro que representa a coordenada
	 * @see Vertice#getY
	 */
	public int getCoordenadaY()
	{
		return coordenadaY;
	}
	
//Metodos Set

	/**
	 * Altera a cor da fonte do r�tulo.
	 *
	 * @param corFonte a nova cor do r�tulo
	 */
	public void setCorFonte(Color corFonte)
	{
		this.corFonte = corFonte;
	}
	
	/**
	 * Altera o texto atual do r�tulo.
	 *
	 * @param texto a string contendo o novo texto
	 */
	public void setTexto(String texto)
	{
		if (texto != null)
			this.texto = texto;
	}
	
	/**
	 * Altera se o r�tulo est� selecionado ou n�o. Um boleano verdadeiro
	 * dever� ser passado caso o componente esteja selecionado e falso caso
	 * contr�rio.
	 *
	 * @param rotuloSelecionado se o r�tulo est� ou n�o selecionado
	 */
	public void setRotuloSelecionado(boolean rotuloSelecionado)
	{
		this.rotuloSelecionado = rotuloSelecionado;
	}
	
	/**
	 * Altera a dist�ncia do r�tulo em rela��o ao ponto central. Este ponto
	 * define a localiza��o padr�o (inicial) para o r�tulo quando ele for 
	 * inst�nciado.
	 *
	 * @param distanciaEixoCentral o inteiro que define a dist�ncia, em pixels
	 */
	public void setDistanciaEixoCentral(int distanciaEixoCentral)
	{
		if ((distanciaEixoCentral <= DISTANCIA_MAXIMA_BORDA) && (distanciaEixoCentral >= - DISTANCIA_MAXIMA_BORDA))
			this.distanciaEixoCentral = distanciaEixoCentral;
	}
	
//M�todos internos da classe

	/**
	 * Identifica se a coordenada est� contida na �rea do r�tulo. Este m�todo 
	 * retorna verdadeiro se o ponto estiver sobre dentro dos limites da �rea 
	 * que envolve o r�tulo falso caso contr�rio.
	 * 
	 * @param x a coordenada do eixo X
	 * @param y a coordenada do eixo Y
	 * @return se a coordenada est� contida no r�tulo
	 */
	public boolean coordenadaPertenceRotulo(int x, int y)
	{
		int pontoX, pontoY;
		
		pontoX = coordenadaX + larguraRotulo;
		pontoY = coordenadaY + alturaRotulo;
		
		if ((x >= coordenadaX) && (x <= pontoX))
			if ((y >= coordenadaY) && (y <= pontoY))
				return true;
		
		return false;
	}
	
	/**
	 * Este m�todo desenha o r�tulo dentro do contexto gr�fico passado pelo par�metro
	 * desenho e em rela��o ao v�rtice.
	 *
	 * O par�metro ladoQuadradoSelecao define o comprimento do quadrado que ser� 
	 * tamb�m desenhado no contexto gr�fico caso o r�tulo esteja marcado como 
	 * selecionado atrav�s do m�todo setRotuloSelecionado.
	 *
	 * @param desenho o contexto gr�fico onde o r�tulo ser� desenhado
	 * @param vertice o vertice na qual o rotulo est� associado
	 * @param ladoQuadradoSelecao o comprimento do lado do quadrado de sele��o 
	 * @see Rotulo#setRotuloSelecionado
	 * @see Rotulo#desenharRotuloAresta
	 */
	public void desenharRotuloVertice(Graphics desenho, Vertice vertice, int ladoQuadradoSelecao)
	{
		int coordenadaRotuloX, coordenadaRotuloY, 
			coordenadaVerticeX, coordenadaVerticeY, larguraVertice, alturaVertice;
		
		//Define a coordenadas e comprimento do v�rtice
		coordenadaVerticeX = vertice.getX();
		coordenadaVerticeY = vertice.getY();
		larguraVertice = vertice.getLargura();
		alturaVertice = vertice.getAltura();
		
		//C�lculo o eixoCentral do Vertice
		calcularAreaRotulo();
		coordenadaRotuloX = coordenadaVerticeX + larguraVertice / 2;
		coordenadaRotuloY = coordenadaVerticeY + alturaVertice / 2;	

		//Calcula a coordena real de X		
		coordenadaRotuloX = coordenadaRotuloX - (larguraRotulo / 2);
		
		//Calcula a coordena real de Y e a varia��o
		if (distanciaEixoCentral >= 0)
		{
			//Descendo a altura do rotulo
			coordenadaRotuloY += distanciaEixoCentral;
		}
		else
		{
			//Subindo a altura do rotulo
			coordenadaRotuloY += distanciaEixoCentral;
		}
		coordenadaRotuloY += ALTURA_FONTE_PADRAO / 2;
		
		coordenadaX = coordenadaRotuloX;
		coordenadaY = coordenadaRotuloY - ALTURA_FONTE_PADRAO;
		
		desenho.fillRect(coordenadaRotuloX, coordenadaRotuloY - ALTURA_FONTE_PADRAO, larguraRotulo, alturaRotulo);
		desenho.setColor(corFonte);
		desenho.drawString(texto, coordenadaRotuloX + 1, coordenadaRotuloY);
		
		//Verifica se o rotulo est� selecionado
		selecionarRotulo(desenho, coordenadaRotuloX, coordenadaRotuloY - ALTURA_FONTE_PADRAO, larguraRotulo, alturaRotulo, ladoQuadradoSelecao);
	}
	
	/**
	 * Este m�todo desenha o r�tulo dentro do contexto gr�fico passado pelo par�metro
	 * desenho e em rela��o a aresta.
	 *
	 * O par�metro ladoQuadradoSelecao define o comprimento do quadrado que ser� 
	 * tamb�m desenhado no contexto gr�fico caso o r�tulo esteja marcado como 
	 * selecionado atrav�s do m�todo setRotuloSelecionado.
	 *
	 * @param desenho o contexto gr�fico onde o r�tulo ser� desenhado
	 * @param aresta a aresta na qual o rotulo est� associado
	 * @param ladoQuadradoSelecao o comprimento do lado do quadrado de sele��o 
	 * @see Rotulo#setRotuloSelecionado
	 * @see Rotulo#desenharRotuloVertice
	 */
	public void desenharRotuloAresta(Graphics desenho, Aresta aresta, int ladoQuadradoSelecao)
	{
		int 		numeroSegmentosAresta,
					pontoXInicial, pontoYInicial, pontoXFinal, pontoYFinal, auxX, auxY, quebrasX[], quebrasY[],
					coordenadaRotuloX, coordenadaRotuloY;
		Vertice		verticeOrigem, verticeDestino;
		
		pontoXInicial = 0;
		pontoYInicial = 0;
		pontoXFinal = 0;
		pontoYFinal = 0;
		coordenadaRotuloX = 0;
		coordenadaRotuloY = 0;
		
		calcularAreaRotulo();
		numeroSegmentosAresta = aresta.getNumeroQuebras() + 1;
		if (numeroSegmentosAresta == 1)
		{
			//Liga��o direta entre o v�rtice Origem e destino
			verticeOrigem = aresta.getVerticeOrigem();
			verticeDestino = aresta.getVerticeDestino();
			
			pontoXInicial = verticeOrigem.getX() + verticeOrigem.getLargura() / 2;
			pontoYInicial = verticeOrigem.getY() + verticeOrigem.getAltura() / 2;
			pontoXFinal = verticeDestino.getX() + verticeDestino.getLargura() / 2;
			pontoYFinal = verticeDestino.getY() + verticeDestino.getAltura() / 2;
		}
		else
		{
			//Existe segmentos extra entre o vertice origem e destino
						
			quebrasX = aresta.getTodasQuebrasX();
			quebrasY = aresta.getTodasQuebrasY();
			
			if (numeroSegmentosAresta == 2)
			{
				//O ponto Inicial � o vertice Origem
				verticeOrigem = aresta.getVerticeOrigem();
				verticeDestino = aresta.getVerticeDestino();
				
				pontoXInicial = verticeOrigem.getX() + verticeOrigem.getLargura() / 2;
				pontoYInicial = verticeOrigem.getY() + verticeOrigem.getAltura() / 2;
				
				pontoXFinal = quebrasX[0];
				pontoYFinal = quebrasY[0];
			}
			else
			{
				numeroSegmentosAresta = numeroSegmentosAresta / 2;
				//Os ponto intermediarios...
				pontoXInicial = quebrasX[numeroSegmentosAresta - 1];
				pontoYInicial = quebrasY[numeroSegmentosAresta - 1];
				
				pontoXFinal = quebrasX[numeroSegmentosAresta];
				pontoYFinal = quebrasY[numeroSegmentosAresta];
			}
		}
		
		auxX = pontoXInicial - pontoXFinal;
		if (auxX >= 0)
		{
			//Inicial est� a esquerda do Final ou no mesmo eixo X(Vertical)
			coordenadaRotuloX = pontoXInicial - (auxX / 2) - (larguraRotulo/ 2);
		}
		else
		{
			//Final est� a esquerda do Inicial
			coordenadaRotuloX = pontoXFinal + (auxX / 2) - (larguraRotulo / 2);
		}
		
		auxY = pontoYInicial - pontoYFinal;
		if (auxY >= 0)
		{
			//Inicial est� abaixo do final ou no mesmo eixo Y(Horizontal)
			coordenadaRotuloY = pontoYInicial - (auxY / 2) + (alturaRotulo / 2);
		}
		else
		{
			//Final est� abaixo do inicial
			coordenadaRotuloY = pontoYFinal + (auxY / 2) + alturaRotulo / 2;
		}
		
		coordenadaX = coordenadaRotuloX;
		coordenadaY = coordenadaRotuloY - ALTURA_FONTE_PADRAO;
		
		//Desenha o rotulo
		desenho.fillRect(coordenadaRotuloX, coordenadaRotuloY - ALTURA_FONTE_PADRAO, larguraRotulo, alturaRotulo);
		desenho.setColor(corFonte);
		desenho.drawString(texto, coordenadaRotuloX, coordenadaRotuloY);
		
		//Verifica se o rotulo est� selecionado
		selecionarRotulo(desenho, coordenadaRotuloX, coordenadaRotuloY - 10, larguraRotulo, alturaRotulo, ladoQuadradoSelecao);
	}
	
//Metodos privados auxiliares
	private void selecionarRotulo(Graphics desenho, int x, int y, int largura, int altura, int ladoQuadradoSelecao)
	{
		if (rotuloSelecionado)
		{
			//Desenha o fundo dos quadrados de sele��o
			desenho.setColor(COR_FUNDO_SELECAO);
			//Superior Esquerdo
			desenho.fillRect(x - ladoQuadradoSelecao, y - ladoQuadradoSelecao, ladoQuadradoSelecao, ladoQuadradoSelecao);
			//Superior Direito
			desenho.fillRect(x + largura, y - ladoQuadradoSelecao, ladoQuadradoSelecao, ladoQuadradoSelecao);
			//Inferior Esquerdo
			desenho.fillRect(x - ladoQuadradoSelecao, y + altura, ladoQuadradoSelecao, ladoQuadradoSelecao);
			//Inferior Direito
			desenho.fillRect(x + largura, y + altura, ladoQuadradoSelecao, ladoQuadradoSelecao);
			
			//Desenha a borda dos quadrados de sele��o
			desenho.setColor(COR_BORDA_SELECAO);
			//Superior Esquerdo
			desenho.drawRect(x - ladoQuadradoSelecao, y - ladoQuadradoSelecao, ladoQuadradoSelecao, ladoQuadradoSelecao);
			//Superior Direito
			desenho.drawRect(x + largura, y - ladoQuadradoSelecao, ladoQuadradoSelecao, ladoQuadradoSelecao);
			//Inferior Esquerdo
			desenho.drawRect(x - ladoQuadradoSelecao, y + altura, ladoQuadradoSelecao, ladoQuadradoSelecao);
			//Inferior Direito
			desenho.drawRect(x + largura, y + altura, ladoQuadradoSelecao, ladoQuadradoSelecao);
		}	
	}
	
	private void calcularAreaRotulo()
	{
		int 	i;
		char	caracteres[];
		
		caracteres = new char[texto.length()];
		caracteres = texto.toCharArray();
		larguraRotulo = 0;
		alturaRotulo = 0;
		for (i = 0; i < texto.length(); i++)
		{
			if (caracteres[i] == 'a')
				larguraRotulo += 5;
			else if (caracteres[i] == 'b')
				larguraRotulo += 5;
			else if (caracteres[i] == 'c')
				larguraRotulo += 4;
			else if (caracteres[i] == 'd')
				larguraRotulo += 5;
			else if (caracteres[i] == 'e')
				larguraRotulo += 5;
			else if (caracteres[i] == 'f')
				larguraRotulo += 3;
			else if (caracteres[i] == 'g')
			{
				larguraRotulo += 5;
				if (alturaRotulo < 2)
				  	alturaRotulo = 2;
			}
			else if (caracteres[i] == 'h')
				larguraRotulo += 5;
			else if (caracteres[i] == 'i')
				larguraRotulo += 1;
			else if (caracteres[i] == 'j')
				larguraRotulo += 1;
			else if (caracteres[i] == 'k')
				larguraRotulo += 5;
			else if (caracteres[i] == 'l')
				larguraRotulo += 1;
			else if (caracteres[i] == 'm')
				larguraRotulo += 9;
			else if (caracteres[i] == 'n')
				larguraRotulo += 5;
			else if (caracteres[i] == 'o')
				larguraRotulo += 5;
			else if (caracteres[i] == 'p')
			{
				larguraRotulo += 5;
				if (alturaRotulo < 2)
					alturaRotulo = 2;
			}
			else if (caracteres[i] == 'q')
			{
				larguraRotulo += 5;
				if (alturaRotulo < 2)
					alturaRotulo = 2;
			}
			else if (caracteres[i] == 'r')
				larguraRotulo += 3;
			else if (caracteres[i] == 's')
				larguraRotulo += 5;
			else if (caracteres[i] == 't')
				larguraRotulo += 2;
			else if (caracteres[i] == 'u')
				larguraRotulo += 5;
			else if (caracteres[i] == 'v')
				larguraRotulo += 5;
			else if (caracteres[i] == 'w')
				larguraRotulo += 9;
			else if (caracteres[i] == 'x')
				larguraRotulo += 5;
			else if (caracteres[i] == 'y')
			{
				larguraRotulo += 5;
				if (alturaRotulo < 2)
					alturaRotulo = 2;
			}
			else if (caracteres[i] == 'z')
				larguraRotulo += 5;
			else if (caracteres[i] == 'A')
				larguraRotulo += 7;
			else if (caracteres[i] == 'B')
				larguraRotulo += 6;
			else if (caracteres[i] == 'C')
				larguraRotulo += 7;
			else if (caracteres[i] == 'D')
				larguraRotulo += 7;
			else if (caracteres[i] == 'E')
				larguraRotulo += 6;
			else if (caracteres[i] == 'F')
				larguraRotulo += 5;
			else if (caracteres[i] == 'G')
				larguraRotulo += 7;
			else if (caracteres[i] == 'H')
				larguraRotulo += 7;
			else if (caracteres[i] == 'I')
				larguraRotulo += 1;
			else if (caracteres[i] == 'J')
				larguraRotulo += 5;
			else if (caracteres[i] == 'K')
				larguraRotulo += 7;
			else if (caracteres[i] == 'L')
				larguraRotulo += 6;
			else if (caracteres[i] == 'M')
				larguraRotulo += 7;
			else if (caracteres[i] == 'N')
				larguraRotulo += 7;
			else if (caracteres[i] == 'O')
				larguraRotulo += 7;
			else if (caracteres[i] == 'P')
				larguraRotulo += 6;
			else if (caracteres[i] == 'Q')
				larguraRotulo += 7;
			else if (caracteres[i] == 'R')
				larguraRotulo += 7;
			else if (caracteres[i] == 'S')
				larguraRotulo += 6;
			else if (caracteres[i] == 'T')
				larguraRotulo += 7;
			else if (caracteres[i] == 'U')
				larguraRotulo += 7;
			else if (caracteres[i] == 'V')
				larguraRotulo += 7;
			else if (caracteres[i] == 'W')
				larguraRotulo += 10;
			else if (caracteres[i] == 'X')
				larguraRotulo += 7;
			else if (caracteres[i] == 'Y')
				larguraRotulo += 7;
			else if (caracteres[i] == 'Z')
				larguraRotulo += 7;
			else if (caracteres[i] == '1')
				larguraRotulo += 3;
			else if (caracteres[i] == '2')
				larguraRotulo += 5;
			else if (caracteres[i] == '3')
				larguraRotulo += 5;
			else if (caracteres[i] == '4')
				larguraRotulo += 5;
			else if (caracteres[i] == '5')
				larguraRotulo += 5;
			else if (caracteres[i] == '6')
				larguraRotulo += 5;
			else if (caracteres[i] == '7')
				larguraRotulo += 5;
			else if (caracteres[i] == '8')
				larguraRotulo += 5;
			else if (caracteres[i] == '9')
				larguraRotulo += 5;
			else if (caracteres[i] == '0')
				larguraRotulo += 5;
			
			else if (caracteres[i] == '@')
			{
				larguraRotulo += 11;
				if (alturaRotulo < 3)
					alturaRotulo  = 3;
			}
			else if (caracteres[i] == '%')
				larguraRotulo += 4;
			else if (caracteres[i] == '&')
				larguraRotulo += 7;
			else if (caracteres[i] == '#')
				larguraRotulo += 7;
			else if (caracteres[i] == '(')
			{
				larguraRotulo += 3;
				if (alturaRotulo < 2)
					alturaRotulo = 2;
			}
			else if (caracteres[i] == '[')
			{
				larguraRotulo += 3;
				if (alturaRotulo < 2)
					alturaRotulo = 2;
			}
			else if (caracteres[i] == '{')
			{
				larguraRotulo += 3;
				if (alturaRotulo < 2)
					alturaRotulo = 2;
			}
			else if (caracteres[i] == ')')
			{
				larguraRotulo += 3;
				if (alturaRotulo < 2)
					alturaRotulo = 2;
			}
			else if (caracteres[i] == ']')
			{
				larguraRotulo += 3;
				if (alturaRotulo < 2)
					alturaRotulo = 2;
			}
			else if (caracteres[i] == '}')
			{
				larguraRotulo += 3;
				if (alturaRotulo < 2)
					alturaRotulo = 2;
			}
			else if ((caracteres[i] == '.') || (caracteres[i] == '|') || (caracteres[i] == '!'))
				larguraRotulo += 1;
			else
				larguraRotulo += 5;
			
			//Soma dois pixels de margem para cada letra.
			larguraRotulo += 2;
			
		} //Fim do For
		
		alturaRotulo += ALTURA_FONTE_PADRAO;
		
	} //Fim  do m�todo DesenharRotulo

    @Override
    public AccessibleRole getAccessibleRole() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public AccessibleStateSet getAccessibleStateSet() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getAccessibleIndexInParent() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getAccessibleChildrenCount() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Accessible getAccessibleChild(int i) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Locale getLocale() throws IllegalComponentStateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}