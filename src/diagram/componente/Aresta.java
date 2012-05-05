package diagram.componente;

import java.util.LinkedList;
import java.awt.Graphics;
import java.awt.Color;

/**
 * � uma classe abstrata b�sica utilizada no contexto do pacote diagram
 * para permitir a cria��o de novos modelos de arestas para as aplica��es.
 *
 * Na cria��o de um novo modelo de aresta, a classe <B>Aresta</B> dever� 
 * necessariamente ser herdada e os m�todos abstratos desenharAresta e 
 * coordenadaPertenceAresta devem ser sobrescritos. Ela tamb�m possui um atributo
 * c�digo dispon�vel para ser utilizado junto ao banco de dados.
 *
 * Para o funcionamento correto do editor, o m�todo desenharAresta dever� implementar
 * todo o c�digo necess�rio para desenhar uma aresta entre dois v�rtices e o m�todo
 * coordenadaPertenceAresta dever� implementar a �rea de ocupada pela aresta. Est�
 * �rea, por exemplo, s�o os pontos contidos em uma reta. Na classe Line2D, j� existe
 * implementa��es para determinar se um ponto est� contido em uma reta.
 *
 * @author Luis Henrique Castilho da Silva
 * @see java.awt.geom.Line2D
 * @see ArestaSimples
 */
public abstract class Aresta extends Componente
{
	/**
	 * O n�mero de quebras padr�o de uma inst�ncia de aresta.
	 */
	public static final int		NUMEROQUEBRAPADRAO = 0;
	
	/**
	 * O n�mero m�ximo de quebras da aresta permitido no editor.
	 */
	public static final int		NUMEROMAXIMOQUEBRAS = 50;
	
	/**
	 * A coordenada no eixo X padr�o para uma nova inst�ncia de aresta.
	 */
	public static final int		COORDENADAPADRAOX = 1;
	
	/**
	 * * A coordenada no eixo Y padr�o para uma nova inst�ncia de aresta.
	 */
	public static final int		COORDENADAPADRAOY = 1;
	
	private static final Color	COR_FUNDO_SELECAO = Color.blue,
								COR_BORDA_SELECAO = Color.black;
								
	private int					numeroQuebras;
	
	private LinkedList			pontosQuebraX,
								pontosQuebraY;
	
	private Vertice				verticeOrigem,
								verticeDestino;
	
	/**
	 * Inst�ncia uma nova aresta.
	 *
	 * Como a classe Aresta � abstrata, as aplica��es n�o podem chamar
	 * est� classe diretamente.
	 * 
	 * @param verticeOrigem o v�rtice inicial na qual a aresta est� associada
	 * @param verticeDestino o v�rtice final na qual a aresta est� associada
	 * @see diagram.Grafo#getAresta
	 */
	protected Aresta(Vertice verticeOrigem, Vertice verticeDestino)
	{
		setNumeroQuebras(NUMEROQUEBRAPADRAO);
		
		pontosQuebraX = new LinkedList();
		pontosQuebraY = new LinkedList();
		
		setVerticeOrigem(verticeOrigem);
		setVerticeDestino(verticeDestino);
                
                //Adiciona a aresta na lista de aresta dos vertices
                verticeOrigem.addArestas(this);
                verticeDestino.addArestas(this);
                
                //Adiciona um vertice na lista de associações do outro
                verticeOrigem.addVertices(verticeDestino);
                verticeDestino.addVertices(verticeOrigem);
	}
	
	/**
	 * Inst�ncia uma nova aresta alterando seu c�digo inicial.
	 *
	 * Como a classe Aresta � abstrata, as aplica��es n�o podem chamar
	 * est� classe diretamente.
	 * 
	 * @param codigo o novo c�digo da aresta
	 * @param verticeOrigem o v�rtice inicial na qual a aresta est� associada
	 * @param verticeDestino o v�rtice final na qual a aresta est� associada
	 * @see diagram.Grafo#getAresta
	 */
	protected Aresta(int codigo, Vertice verticeOrigem, Vertice verticeDestino) 
	{
		this(verticeOrigem, verticeDestino);
		setCodigo(codigo);
	}	

// M�todos Get...

	/**
	 * Retorna o n�mero total de pontos especiais contidos na aresta. Estes pontos
	 * especiais s�o as quebras existentes em uma aresta.
	 *
	 * @return o inteiro especificando o n�mero de quebras existente na aresta
	 */
	public int getNumeroQuebras()
	{
		return numeroQuebras;
	}
	
	/**
	 * Retorna as coordenadas no eixo X de todas as quebras existentes na aresta.
	 *
	 * @return o vetor de inteiro contendo todos as coordenadas
	 */
	public int[] getTodasQuebrasX()
	{
		int 		i, pontosQuebraX[];
		Integer		coordTemp;
		
		
		pontosQuebraX = new int[this.pontosQuebraX.size()];
		
		for (i = 0;i < this.pontosQuebraX.size(); i++)
		{
			coordTemp = (Integer) this.pontosQuebraX.get(i);
			pontosQuebraX[i] = coordTemp.intValue();
		}
			
		return pontosQuebraX;
	}
	
	/**
	 * Retorna as coordenadas no eixo Y de todas as quebras existentes na aresta.
	 *
	 * @return o vetor de inteiro contendo todos as coordenadas
	 */
	public int[] getTodasQuebrasY()
	{
		int 		i, pontosQuebraY[];
		Integer		coordTemp;
		
		
		pontosQuebraY = new int[this.pontosQuebraY.size()];
		
		for (i = 0;i < this.pontosQuebraY.size(); i++)
		{
			coordTemp = (Integer) this.pontosQuebraY.get(i);
			pontosQuebraY[i] = coordTemp.intValue();
		}
			
		return pontosQuebraY;
	}
	
	/**
	 * Retorna a coordenada no eixo X de uma quebra especificada no par�metro.
	 *
	 * @return o inteiro que representa a coordenada
	 */
	public int getCoordenadaQuebraX(int indiceCoordenada)
	{
		Integer coordTemp;
		
		if ((indiceCoordenada >= 0) && (indiceCoordenada < pontosQuebraX.size()))
		{
			coordTemp = (Integer) pontosQuebraX.get(indiceCoordenada);
			return coordTemp.intValue();
		}
		
		return -1;
	}

	/**
	 * Retorna a coordenada no eixo Y de uma quebra especificada no par�metro.
	 *
	 * @return um inteiro que representa a coordenada
	 */	
	public int getCoordenadaQuebraY(int indiceCoordenada)
	{
		Integer coordTemp;
		
		if ((indiceCoordenada >= 0) && (indiceCoordenada < pontosQuebraY.size()))
		{
			coordTemp = (Integer) pontosQuebraY.get(indiceCoordenada);
			return coordTemp.intValue();
		}
		
		return -1;
	}
	
	/**
	 * Retorna o objeto v�rtice origem atual da aresta.
	 *
	 * @return o objeto Vertice.
	 * @see Vertice#Vertice
	 */
	public Vertice getVerticeOrigem()
	{
		return verticeOrigem;
	}

	/**
	 * Retorna o objeto v�rtice destino atual da aresta.
	 *
	 * @return um objeto Vertice.
	 * @see Vertice#Vertice
	 */	
	public Vertice getVerticeDestino()
	{
		return verticeDestino;
	}
	
//M�todos Set...

	/**
	 * Seta o n�mero de quebras da aresta. Este m�todo dever� ser chamado sempre
	 * que houver a necessidade de criar os pontos especiais (quebras) para as arestas.
	 *
	 * @param numeroQuebras o n�mero de ponto especiais na aresta
	 * @see Aresta#setTodasQuebrasX
	 * @see Aresta#setTodasQuebrasY
	 */
	public void setNumeroQuebras(int numeroQuebras)
	{
		if ((numeroQuebras >= 0) && (numeroQuebras < NUMEROMAXIMOQUEBRAS))
			this.numeroQuebras = numeroQuebras;
		else
			this.numeroQuebras = NUMEROQUEBRAPADRAO;
	}
	
	/**
	 * Altera as coordenadas do eixo X dos pontos especiais (quebras da aresta) da aresta. Os valores
	 * que excederem o n�mero m�ximo de quebras ser�o descartados. 
	 *
	 * @param pontosQuebraX o vetor contendo as coordenadas do eixo X de todos os pontos
	 * @see Aresta#setNumeroQuebras
	 */
	public void setTodasQuebrasX(int pontosQuebraX[])
	{
		int i;
		
		for (i = 0; i < numeroQuebras; i++)
		{
			if (pontosQuebraX.length > i)
			{
				if (pontosQuebraX[i] > 0)
					this.pontosQuebraX.add(new Integer(pontosQuebraX[i]));
				else
					this.pontosQuebraX.add(new Integer(COORDENADAPADRAOX));
			}
			else
				this.pontosQuebraX.add(new Integer(COORDENADAPADRAOX));
		}
	}

	/**
	 * Altera as coordenadas do eixo Y dos pontos especiais (quebras da aresta) da aresta. Os valores
	 * que excederem o n�mero m�ximo de quebras ser�o descartados. 
	 *
	 * @param pontosQuebraY o vetor contendo as coordenadas do eixo Y de todos os pontos
	 * @see Aresta#setNumeroQuebras
	 */
	public void setTodasQuebrasY(int pontosQuebraY[])
	{
		int i;
		
		for (i = 0; i < numeroQuebras; i++)
		{
			if (pontosQuebraY.length > i)
			{
				if (pontosQuebraY[i] > 0)
					this.pontosQuebraY.add(new Integer(pontosQuebraY[i]));
				else
					this.pontosQuebraY.add(new Integer(COORDENADAPADRAOY));	
			}
			else
				this.pontosQuebraY.add(new Integer(COORDENADAPADRAOY));				
		}
	}
	
	/**
	 * Altera a coordenada do eixo X do ponto especial (quebra da aresta) da 
	 * aresta especificada pelo par�metro. 
	 *
	 * @param indice a localiza��o na lista encadeada do ponto especial a ser alterado
	 * @param coordenadaX a coordenada do eixo X do ponto
	 * @see Aresta#setNumeroQuebras
	 */
	public void setQuebraX(int indice, int coordenadaX)
	{
		if ((indice >= 0) && (indice < numeroQuebras))
			if (coordenadaX > 0)
				pontosQuebraX.add(indice, new Integer(coordenadaX));
	}
	
	/**
	 * Altera a coordenada do eixo Y do ponto especial (quebra da aresta) da 
	 * aresta especificada pelo par�metro. 
	 *
	 * @param indice a localiza��o na lista encadeada do ponto especial a ser alterado
	 * @param coordenadaY a coordenada do eixo Y do ponto
	 * @see Aresta#setNumeroQuebras
	 */
	public void setQuebraY(int indice, int coordenadaY)
	{
		if ((indice >= 0) && (indice < numeroQuebras))
			if (coordenadaY > 0)
				pontosQuebraY.add(indice, new Integer(coordenadaY));
	}
	
	/**
	 * Altera o v�rtice origem da aresta.
	 *
	 * @param verticeOrigem o objeto Vertice
	 * @see Vertice#Vertice
	 */
	public void setVerticeOrigem(Vertice verticeOrigem)
	{
		this.verticeOrigem = verticeOrigem;
	}
	
	/**
	 * Altera o v�rtice destino da aresta.
	 *
	 * @param verticeDestino o objeto Vertice
	 * @see Vertice#Vertice
	 */
	public void setVerticeDestino(Vertice verticeDestino)
	{
		this.verticeDestino = verticeDestino;
	}
	
	/**
	 * Remove o ponto especial (quebra) da aresta especificado no 
	 * par�metro.
	 *
	 * @param o inteiro que define a localiza��o do ponto na lista
	 */
	public void removerQuebra(int indiceQuebra)
	{
		if ((indiceQuebra >= 0) && (indiceQuebra < numeroQuebras))
		{
			pontosQuebraX.remove(indiceQuebra);
			pontosQuebraY.remove(indiceQuebra);
			numeroQuebras--;
		}
	}
	
	/**
	 * Remove todos os pontos especiais (quebras) da aresta.
	 */
	public void removerTodasQuebras()
	{
		pontosQuebraX.clear();
		pontosQuebraY.clear();
		numeroQuebras = 0;
	}
//Metodo para selecionar a Aresta

	/**
	 * Este m�todo dever� ser chamado sempre que houver a necessidade de desenhar
	 * quadrados vermelhos indicando que a aresta est� selecionada.
	 *
	 * @param desenho o contexto gr�fico onde desenhar� os simbolos que representam a sele��o da aresta
	 * @see diagram.editor.AreaApplet#paint
	 * @see diagram.editor.AreaAplicativo#paintComponent
	 */
	public void selecionarAresta(Graphics desenho, int ladoQuadradoSelecao)
	{
		int 		i, pontoX, pontoY, pontoXInicial, pontoYInicial, pontoXFinal, pontoYFinal;
		
		if (getSelecionado())
		{
			pontoXInicial = verticeOrigem.getX() + verticeOrigem.getLargura() / 2;
			pontoYInicial = verticeOrigem.getY() + verticeOrigem.getAltura() / 2;
			if (numeroQuebras == 0)
			{
				//O ponto est� relacionado com o v�rtice destino
				pontoXFinal = verticeDestino.getX() + verticeDestino.getLargura() / 2;
				pontoYFinal = verticeDestino.getY() + verticeDestino.getAltura() / 2;
			}
			else
			{
				//O ponto est� relacionado com um ponto intermedi�rio
				pontoXFinal = getCoordenadaQuebraX(0);
				pontoYFinal = getCoordenadaQuebraY(0);
			}
			desenharQuadradoSelecao(desenho, pontoXInicial, pontoYInicial, pontoXFinal, pontoYFinal, verticeOrigem.getLargura(), verticeOrigem.getAltura(), ladoQuadradoSelecao);
			
			pontoXInicial = verticeDestino.getX() + verticeDestino.getLargura() / 2;
			pontoYInicial = verticeDestino.getY() + verticeDestino.getAltura() / 2;
			if (numeroQuebras == 0)
			{
				//O ponto est� relacionado com o v�rtice origem
				pontoXFinal = verticeOrigem.getX() + verticeOrigem.getLargura() / 2;
				pontoYFinal = verticeOrigem.getY() + verticeOrigem.getAltura() / 2;
			}
			else
			{
				//O ponto est� relacionado com um ponto intermediario
				pontoXFinal = getCoordenadaQuebraX(numeroQuebras - 1);
				pontoYFinal = getCoordenadaQuebraY(numeroQuebras - 1);
			}
			desenharQuadradoSelecao(desenho, pontoXInicial, pontoYInicial, pontoXFinal, pontoYFinal, verticeDestino.getLargura(), verticeDestino.getAltura(), ladoQuadradoSelecao);			
			
			for (i = 0; i < numeroQuebras; i++)	
			{
				pontoX = getCoordenadaQuebraX(i);
				pontoY = getCoordenadaQuebraY(i);
				
				//Desenha o fundo dos quadrados de sele��o
				desenho.setColor(COR_FUNDO_SELECAO);
				desenho.fillRect(pontoX - ladoQuadradoSelecao/2, pontoY - ladoQuadradoSelecao/2, ladoQuadradoSelecao, ladoQuadradoSelecao);
				
				//Desenha a borda dos quadrados de sele��o
				desenho.setColor(COR_BORDA_SELECAO);
				desenho.drawRect(pontoX - ladoQuadradoSelecao/2, pontoY - ladoQuadradoSelecao/2, ladoQuadradoSelecao, ladoQuadradoSelecao);
			}
		}
	}
	
	/**
	 * Define se ocorreu um clique sobre os desenho dos quadrados que representam
	 * a aresta selecionada. Estes s�o os quadrados azuis que aparecem quando o
	 * usu�rio clica sobre uma aresta com o bot�o esquerdo do mouse.
	 *
	 * Caso as coordenadas n�o pertecem a �rea de qualquer quadrado de sele��o
	 * um interio -1 � retornado, caso contr�rio a localiza��o na lista encadeada
	 * � retornado.
	 *
	 * @param x a coordenada do ponto no eixo x
	 * @param y a coordenada do ponto no eixo y 
	 * @param ladoQuadradoSelecao o comprimento do lado quadrado de sele��o
	 * @return a localiza��o do quadrado clicado na lista encadeada
	 */
	public int cliqueSobreQuebraAresta(int x, int y, int ladoQuadradoSelecao)
	{
		int			i, pontoQuebraX, pontoQuebraY;
		
		for (i = 0; i < getNumeroQuebras(); i++)
		{
			pontoQuebraX = getCoordenadaQuebraX(i) ;
			pontoQuebraY = getCoordenadaQuebraY(i) - ladoQuadradoSelecao / 2;
			
			if ((x >= (pontoQuebraX - ladoQuadradoSelecao / 2)) && (x <= (pontoQuebraX + ladoQuadradoSelecao / 2)))
				if ((y >= (pontoQuebraY - ladoQuadradoSelecao / 2)) && (y <= (pontoQuebraY + ladoQuadradoSelecao / 2)))
					return i;
		}
		
		return -1;
	}
	
	/*
	 * Desenha a aresta no contexto gr�fico do editor.
	 *
	 * @param desenho o contexto gr�fico na qual desenhar� a aresta
	 * @see AreaApplet#paint
	 * @see AreaAplicativo#paintComponent
	 */
	public abstract void desenharAresta(Graphics desenho);
	
	/**
	 * Identifica se a coordenada est� contida na aresta, ou seja, na reta.
	 * Retorna verdadeiro se o ponto estiver sobre a aresta e falso caso contr�rio. 
	 * 
	 * @param x a coordenada do eixo X
	 * @param y a coordenada do eixo Y
	 * @return se a coordenada est� contida na aresta
	 */
	public abstract boolean coordenadaPertenceAresta(int x, int y);
	
	//M�todos privados da classe aresta
	private void desenharQuadradoSelecao(Graphics desenho, int pontoXInicial, int pontoYInicial, int pontoXFinal, int pontoYFinal, int larguraInicial, int alturaInicial, int ladoQuadradoSelecao)
	{
		int 	auxX, auxY, distPontoInicial;
		double	anguloVertice, anguloAresta;
		
		auxX = pontoXInicial - pontoXFinal;
		auxY = pontoYInicial - pontoYFinal;
		
		if (auxX == 0)
		{
			//Aresta na vertical
			if (pontoYInicial > pontoYFinal)
			{
				//Pontos na mesma vertical com inicial abaixo do final
				
				//Desenha o fundo dos quadrados de sele��o
				desenho.setColor(COR_FUNDO_SELECAO);
				desenho.fillRect(pontoXInicial - ladoQuadradoSelecao / 2, pontoYInicial - (alturaInicial / 2) - (ladoQuadradoSelecao / 2), ladoQuadradoSelecao, ladoQuadradoSelecao);
				
				//Desenha a borda dos quadrados de sele��o
				desenho.setColor(COR_BORDA_SELECAO);
				desenho.drawRect(pontoXInicial - ladoQuadradoSelecao / 2, pontoYInicial - (alturaInicial / 2) - (ladoQuadradoSelecao / 2), ladoQuadradoSelecao, ladoQuadradoSelecao);
			}
			else
			{
				//Pontos na mesma vertical com inicial acima do final
				
				//Desenha o fundo dos quadrados de sele��o
				desenho.setColor(COR_FUNDO_SELECAO);
				desenho.fillRect(pontoXInicial - ladoQuadradoSelecao / 2, pontoYInicial + (alturaInicial / 2) - (ladoQuadradoSelecao / 2), ladoQuadradoSelecao, ladoQuadradoSelecao);
				
				//Desenha a borda dos quadrados de sele��o
				desenho.setColor(COR_BORDA_SELECAO);
				desenho.drawRect(pontoXInicial - ladoQuadradoSelecao / 2, pontoYInicial + (alturaInicial / 2) - (ladoQuadradoSelecao / 2), ladoQuadradoSelecao, ladoQuadradoSelecao);
			}
		}
		else
		{
			if (auxY == 0)
			{
				//Aresta na horizontal
				if (pontoXInicial > pontoXFinal)
				{
					//Ponto na mesma horizontal mas com inicial a direita de final
					
					//Desenha o fundo dos quadrados de sele��o
					desenho.setColor(COR_FUNDO_SELECAO);
					desenho.fillRect(pontoXInicial - (larguraInicial / 2) - (ladoQuadradoSelecao / 2), pontoYInicial - (ladoQuadradoSelecao / 2), ladoQuadradoSelecao, ladoQuadradoSelecao);
					
					//Desenha a borda dos quadrados de sele��o
					desenho.setColor(COR_BORDA_SELECAO);
					desenho.drawRect(pontoXInicial - (larguraInicial / 2) - (ladoQuadradoSelecao / 2), pontoYInicial - (ladoQuadradoSelecao / 2), ladoQuadradoSelecao, ladoQuadradoSelecao);
				}
				else
				{
					//Ponto na mesma horizontal mas com final a direita de inicial
					
					//Desenha o fundo dos quadrados de sele��o				
					desenho.setColor(COR_FUNDO_SELECAO);
					desenho.fillRect(pontoXInicial + (larguraInicial / 2) - (ladoQuadradoSelecao / 2), pontoYInicial - (ladoQuadradoSelecao / 2), ladoQuadradoSelecao, ladoQuadradoSelecao);
					
					//Desenha a borda dos quadrados de sele��o
					desenho.setColor(COR_BORDA_SELECAO);
					desenho.drawRect(pontoXInicial + (larguraInicial / 2) - (ladoQuadradoSelecao / 2), pontoYInicial - (ladoQuadradoSelecao / 2), ladoQuadradoSelecao, ladoQuadradoSelecao);
				}
			}
			else
			{
				//Aresta inclinada
				anguloVertice = (double) (alturaInicial / 2) / (double) (larguraInicial / 2);
				anguloAresta = (double) auxY / (double) auxX;
				
				if (pontoXInicial > pontoXFinal)
				{
					//Ponto inicial est� a direita e o ponto final a esquerda
					if (pontoYInicial > pontoYFinal)
					{
						//Ponto inicial est� abaixo e o ponto final acima (4 Quadrante para o ponto Inicial)
						//Cobrir a faixa de 0 a 90 graus

						if (anguloVertice > anguloAresta)
						{
							//Angulo da aresta menor do que o vertice
							distPontoInicial = (auxY * (larguraInicial / 2)) / auxX;
							
							//Desenha o fundo dos quadrados de sele��o
							desenho.setColor(COR_FUNDO_SELECAO);
							desenho.fillRect(pontoXInicial - (larguraInicial / 2) - (ladoQuadradoSelecao / 2), pontoYInicial - distPontoInicial - (ladoQuadradoSelecao / 2), ladoQuadradoSelecao, ladoQuadradoSelecao);
							
							//Desenha a borda dos quadrados de sele��o
							desenho.setColor(COR_BORDA_SELECAO);
							desenho.drawRect(pontoXInicial - (larguraInicial / 2) - (ladoQuadradoSelecao / 2), pontoYInicial - distPontoInicial - (ladoQuadradoSelecao / 2), ladoQuadradoSelecao, ladoQuadradoSelecao);
						}
						else
						{
							//Angulo da aresta maior do que o vertice
							distPontoInicial = (auxX * (alturaInicial / 2)) / auxY;
							
							//Desenha o fundo dos quadrados de sele��o
							desenho.setColor(COR_FUNDO_SELECAO);
							desenho.fillRect(pontoXInicial - distPontoInicial - (ladoQuadradoSelecao / 2), pontoYInicial - (alturaInicial / 2) - (ladoQuadradoSelecao / 2), ladoQuadradoSelecao, ladoQuadradoSelecao);
							
							//Desenha a borda dos quadrados de sele��o
							desenho.setColor(COR_BORDA_SELECAO);
							desenho.drawRect(pontoXInicial - distPontoInicial - (ladoQuadradoSelecao / 2), pontoYInicial - (alturaInicial / 2) - (ladoQuadradoSelecao / 2), ladoQuadradoSelecao, ladoQuadradoSelecao);
						}
					}
					else
					{
						//Ponto inicial est� acima e o ponto final abaixo (1 Quadrante para o ponto Inicial)
						//Cobrir a faixa de 0 a 90 graus
						anguloAresta *= -1;
						
						if (anguloVertice > anguloAresta)
						{
							//Angulo da aresta menor do que o vertice
							distPontoInicial = (auxY * (larguraInicial / 2)) / auxX;
							
							//Desenha o fundo dos quadrados de sele��o
							desenho.setColor(COR_FUNDO_SELECAO);
							desenho.fillRect(pontoXInicial - (larguraInicial / 2) - (ladoQuadradoSelecao / 2), pontoYInicial - distPontoInicial - (ladoQuadradoSelecao / 2), ladoQuadradoSelecao, ladoQuadradoSelecao);
							
							//Desenha a borda dos quadrados de sele��o
							desenho.setColor(COR_BORDA_SELECAO);
							desenho.drawRect(pontoXInicial - (larguraInicial / 2) - (ladoQuadradoSelecao / 2), pontoYInicial - distPontoInicial - (ladoQuadradoSelecao / 2), ladoQuadradoSelecao, ladoQuadradoSelecao);
						}
						else
						{
							//Angulo da aresta maior do que o vertice
							distPontoInicial = (auxX * (alturaInicial / 2)) / auxY;
							
							//Desenha o fundo dos quadrados de sele��o
							desenho.setColor(COR_FUNDO_SELECAO);
							desenho.fillRect(pontoXInicial + distPontoInicial - (ladoQuadradoSelecao / 2), pontoYInicial + (alturaInicial / 2) - (ladoQuadradoSelecao / 2), ladoQuadradoSelecao, ladoQuadradoSelecao);
							
							//Desenha a borda dos quadrados de sele��o
							desenho.setColor(COR_BORDA_SELECAO);
							desenho.drawRect(pontoXInicial + distPontoInicial - (ladoQuadradoSelecao / 2), pontoYInicial + (alturaInicial / 2) - (ladoQuadradoSelecao / 2), ladoQuadradoSelecao, ladoQuadradoSelecao);
						}
					}
				}
				else
				{
					//Ponto inicial est� a esquerda e o ponto final a direita
					if (pontoYInicial > pontoYFinal)
					{
						//Ponto inicial est� abaixo e o ponto final acima (3 Quadrante para o ponto Inicial)
						//Cobrir a faixa de 0 a 90 graus
						anguloAresta *= -1; 

						if (anguloVertice > anguloAresta)
						{
							//Angulo da aresta menor do que o vertice
							distPontoInicial = (auxY * (larguraInicial / 2)) / auxX;
							
							//Desenha o fundo dos quadrados de sele��o
							desenho.setColor(COR_FUNDO_SELECAO);
							desenho.fillRect(pontoXInicial + (larguraInicial / 2) - (ladoQuadradoSelecao / 2), pontoYInicial + distPontoInicial - (ladoQuadradoSelecao / 2), ladoQuadradoSelecao, ladoQuadradoSelecao);
							
							//Desenha a borda dos quadrados de sele��o
							desenho.setColor(COR_BORDA_SELECAO);
							desenho.drawRect(pontoXInicial + (larguraInicial / 2) - (ladoQuadradoSelecao / 2), pontoYInicial + distPontoInicial - (ladoQuadradoSelecao / 2), ladoQuadradoSelecao, ladoQuadradoSelecao);
						}
						else
						{
							//Angulo da aresta maior do que o vertice
							distPontoInicial = (auxX * (alturaInicial / 2)) / auxY;
							
							//Desenha o fundo dos quadrados de sele��o
							desenho.setColor(COR_FUNDO_SELECAO);
							desenho.fillRect(pontoXInicial - distPontoInicial - (ladoQuadradoSelecao / 2), pontoYInicial - (alturaInicial / 2) - (ladoQuadradoSelecao / 2), ladoQuadradoSelecao, ladoQuadradoSelecao);
							
							//Desenha a borda dos quadrados de sele��o
							desenho.setColor(COR_BORDA_SELECAO);
							desenho.drawRect(pontoXInicial - distPontoInicial - (ladoQuadradoSelecao / 2), pontoYInicial - (alturaInicial / 2) - (ladoQuadradoSelecao / 2), ladoQuadradoSelecao, ladoQuadradoSelecao);
						}
					}
					else
					{
						//Ponto inicial est� acima e o ponto final abaixo (2 Quadrante para o ponto Inicial)
						//Cobrir a faixa de 0 a 90 graus

						if (anguloVertice > anguloAresta)
						{
							//Angulo da aresta menor do que o vertice
							distPontoInicial = (auxY * (larguraInicial / 2)) / auxX;
							
							//Desenha o fundo dos quadrados de sele��o
							desenho.setColor(COR_FUNDO_SELECAO);
							desenho.fillRect(pontoXInicial + (larguraInicial / 2) - (ladoQuadradoSelecao / 2), pontoYInicial + distPontoInicial - (ladoQuadradoSelecao / 2), ladoQuadradoSelecao, ladoQuadradoSelecao);
							
							//Desenha a borda dos quadrados de sele��o
							desenho.setColor(COR_BORDA_SELECAO);
							desenho.drawRect(pontoXInicial + (larguraInicial / 2) - (ladoQuadradoSelecao / 2), pontoYInicial + distPontoInicial - (ladoQuadradoSelecao / 2), ladoQuadradoSelecao, ladoQuadradoSelecao);
						}
						else
						{
							//Angulo da aresta maior do que o vertice
							distPontoInicial = (auxX * (alturaInicial / 2)) / auxY;
							
							//Desenha o fundo dos quadrados de sele��o
							desenho.setColor(COR_FUNDO_SELECAO);
							desenho.fillRect(pontoXInicial + distPontoInicial - (ladoQuadradoSelecao / 2), pontoYInicial + (alturaInicial / 2) - (ladoQuadradoSelecao / 2), ladoQuadradoSelecao, ladoQuadradoSelecao);
							
							//Desenha a borda dos quadrados de sele��o
							desenho.setColor(COR_BORDA_SELECAO);
							desenho.drawRect(pontoXInicial + distPontoInicial - (ladoQuadradoSelecao / 2), pontoYInicial + (alturaInicial / 2) - (ladoQuadradoSelecao / 2), ladoQuadradoSelecao, ladoQuadradoSelecao);							
						}
						
					}//Fim do if (verificar o quadrante)
					
				}//Fim do if (verificar o lado dos pontos inicial e final)

			}//Fim do if (verificar aresta horizontal e inclinada)

		}//Fim do if (verificar aresta vertical)
	
	}//Fim do m�todo desenharQuadradoSelecao
}