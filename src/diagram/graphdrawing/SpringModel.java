package diagram.graphdrawing;

import diagram.Grafo;

import diagram.componente.Aresta;
import diagram.componente.Vertice;

/**
 * A classe SpringModel utiliza uma versão aprimirada do algoritmo de Spring Model
 * desenvolvido por Tomihisa Kamada e Satoru Kawai para desenhar grafos automaticamente.
 * 
 * Na classe existem alguns métodos que permitem a alteração de algumas primitivas
 * do algoritmo para obter-se maiores resultados no desenho de grafos mais complexos
 * ou mais simples. Tais primitivas como a força da mola influencial no resultado 
 * final apresentado pelo algoritmo e eles podem ser alterados através de seus 
 * métodos respectivos.
 *
 * Um exemplo de como utilizar o algoritmo dentro do GEDE é mostrado abaixo. Observe a 
 * necessidade da chamada explícita ao método desenharGrafo para que o algoritmo de
 * Spring Model inicialize a sua execução.<br>
 * <br>
 *&nbsp;import diagram.graphdrawing.*;<br>
 *&nbsp;import diagram.*;<br>
 *&nbsp;import diagram.componente.*;<br>
 *&nbsp;import diagram.editor.*;<br>
 *&nbsp;<br>
 *&nbsp;public class Desenho<br>
 *&nbsp;{<br>
 * <br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;public Desenho()<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;VerticeElipse vertices[] = new VerticeElipse[6];<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;for (int i = 0; i < vertices.length; i++)<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;vertices[0] = new VerticeElipse();<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br>
 *<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ArestaSimples arestas[] = new ArestaSimples[6]<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;arestas[0] = new ArestaSimples(vertices[0], vertices[1]);<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;arestas[1] = new ArestaSimples(vertices[0], vertices[2]);<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;arestas[2] = new ArestaSimples(vertices[1], vertices[5]);<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;arestas[3] = new ArestaSimples(vertices[2], vertices[4]);<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;arestas[4] = new ArestaSimples(vertices[3], vertices[4]);<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;arestas[5] = new ArestaSimples(vertices[3], vertices[5]);<br>
 *<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Grafo gr = new Grafo();<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;gr.setVertices(vertices);<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;gr.setArestas(arestas);<br>
 *<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;SpringModel desenhoAutomatico = new SpringModel(gr);<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;desenhoAutomatico.setCoordenadaAleatoria();<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;desenhoAutomatico.desenharGrafo(); //Inicializa a execução do algoritmo<br>
 *<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;EditorAplicativoGrafo editor = new EditorAplicativoGrafo();<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;editor.setGrafo(gr);<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br>
 *<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;public static void main(String args[])<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;new Desenho();<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br>
 *&nbsp;}<br>
 *
 * @author Luis Henrique Castilho da Silva
 * @see Grafo
 */
public class SpringModel
{
	/**
	 * Tamanho padrão na qual a aresta ficará após o desenho automático.
	 */
	public static final int		TAMANHO_DESEJAVEL_ARESTA_PADRAO = 100;
	
	/**
	 * A distância padrão, em pixels, entre as componentes conexas.
	 */
	public static final int		DIST_COMPONENTE_CONEXA_PADRAO   = 100;
	
	/**
	 * A força padrão da mola utilizada pelo algoritmo.
	 */
	public static final double	FORCA_MOLA_PADRAO = 1.0;
	
	/**
	 * O valor mínimo possível para a energia final do sistema. 
	 */
	public static final double	TOLERANCIA_MINIMA_ERRO = 0.1;
	
	/**
	 * O valor padrão utilizado pelo algoritmo para a energia final do sistema.
	 */
	public static final double	TOLERANCIA_ERRO_PADRAO = 1.0;
	
	private static int			CONTADOR = 50;
	
	private Grafo				grafo;
	
	private int					tamanhoAresta,
								distEntreComponentesConexas;	
	
	private double				forcaMola,
								energiaMinima;
	
	//Variáveis internas fixas do algoritmo
	private int					dij[][],
								coordenadaX[],
								coordenadaY[];
	
	private double				lij[][],
								kij[][],
								restoX[],
								restoY[]; 

	//Construtor
	
	/**
	 * Cria um novo objeto responsável em desenhar o grafo passado pelo parâmetro
	 * automaticamente seguindo as caractériticas dos atributos da classe.
	 * 
	 * @param grafo o grafo a ser desenhado automaticamente
	 */
	public SpringModel(Grafo grafo)
	{
		this.grafo = grafo;
		tamanhoAresta = TAMANHO_DESEJAVEL_ARESTA_PADRAO;
		forcaMola = FORCA_MOLA_PADRAO;
		energiaMinima = TOLERANCIA_ERRO_PADRAO;
		distEntreComponentesConexas = DIST_COMPONENTE_CONEXA_PADRAO;
	}

//Métodos Get

	/**
	 * Retorna o grafo atual do editor.
	 *
	 * @return o grafo atual da classe
	 */
	public Grafo getGrafo()
	{
		return grafo;
	}
	
	/**
	 * Retorna o comprimento que o algoritmo tentará manter para as arestas 
	 * após o desenho automatico do grafo.
	 *
	 * @return o inteiro que representa o tamanho ideal das arestas
	 */
	public int getTamanhoAresta()
	{
		return tamanhoAresta;
	}
	
	/**
	 * Retorna a força da mola utilizada pelo algoritmo de Spring Model.
	 *
	 * @return o valor da força
	 */
	public double getForcaMola()
	{
		return forcaMola;
	}
	
	/**
	 * Retorna a energia mínima utilizada pelo algoritmo de Spring Model para 
	 * dispor o grafo no editor.
	 *
	 * @return a energia mínima utilizada pelo algoritmo 
	 */
	public double getEnergiaMinima()
	{
		return energiaMinima;
	}
	
	/**
	 * Retorna a distância que o algoritmo manterá entre as componentes conexas
	 * após desenha-lo.
	 *
	 * @return o inteiro que representa a distância entre as componentes conexas
	 */
	public int getDistanciaEntreComponentesConexas()
	{
		return distEntreComponentesConexas;
	}
	
//Métodos Set das variáveis

	/**
	 * Altera o grafo a ser desenhado no editor.
	 *
	 * @param grafo o novo grafo a ser desenhado
	 */
	public void setGrafo(Grafo grafo)
	{
		this.grafo = grafo;
	}
	
	/**
	 * Altera o comprimento que o algoritmo tentará manter para as arestas 
	 * após o desenho automático do grafo.
	 *
	 * @param tamanhoAresta o inteiro que representa o tamanho ideal das arestas
	 */
	public void setTamanhoAresta(int tamanhoAresta)
	{
		if (tamanhoAresta > 0)	
			this.tamanhoAresta = tamanhoAresta;
		else
			this.tamanhoAresta = TAMANHO_DESEJAVEL_ARESTA_PADRAO;
	}
	
	/**
	 * Altera a força da mola utilizada pelo algoritmo de Spring Model.
	 *
	 * @param forcaMola a nova força da mola
	 */
	public void setForcaMola(double forcaMola)
	{
		if ((forcaMola > 0) && (forcaMola <= 1.0))
			this.forcaMola = forcaMola;
		else
			this.forcaMola = FORCA_MOLA_PADRAO;			
	}
	
	/**
	 * Altera a energia mínima utilizada pelo algoritmo de Spring Model para 
	 * dispor o grafo no editor.
	 *
	 * @param energiaMinima a nova energia mínima
	 */
	public void setEnergiaMinima(double energiaMinima)
	{
		if (energiaMinima >= TOLERANCIA_MINIMA_ERRO)
			this.energiaMinima = energiaMinima;
		else
			this.energiaMinima = TOLERANCIA_ERRO_PADRAO;
	}

	/**
	 * Altera a distância entre as componentes conexas após o algoritmo desehar
	 * o grafo automaticamente.
	 *
	 * @param distEntreComponentesConexas a nova distância entre as componentes 
	 * conexas
	 */
	public void setDistanciaEntreComponentesConexas(int distEntreComponentesConexas)
	{
		if (distEntreComponentesConexas > 0)
			this.distEntreComponentesConexas = distEntreComponentesConexas;
		else
			this.distEntreComponentesConexas = DIST_COMPONENTE_CONEXA_PADRAO;	
	}
	
//Métodos Set...

	/**
	 * Altera as coordenadas dos vértice do grafo a ser desenhado. As novas 
	 * coordenadas são modificadas com valores aleatórios.
	 */
	public void setCoordenadaAleatoria()
	{
		int 		i,
					posX,
					posY,
					minX,
					minY,
					maxValueX,
					maxValueY;
		Vertice		verticeTemp;
		
		maxValueX = (grafo.getNumeroTotalVertices() * Vertice.LARGURAMAXIMA) / 2;
		maxValueY = (grafo.getNumeroTotalVertices() * Vertice.ALTURAMAXIMA) / 2;
		minX = maxValueX;
		minY = maxValueY;
		
		for (i = 0; i < grafo.getNumeroTotalVertices(); i++)
		{
			verticeTemp = grafo.getVertice(i);
			
			//Gera os números aleatórios
			posX = 1 + (int) (Math.random() * maxValueX);
			posY = 1 + (int) (Math.random() * maxValueY);
			
			//Altero as posições do vértice
			verticeTemp.setX(posX);
			verticeTemp.setY(posY);
			
			if (posX < minX)
				minX = posX;
			
			if (posY < minY)
				minY = posY;
		}
		
		//Aproximação das localizações para as coordenadas proximas a (20, 20)
		minX -= 20;
		minY -= 20;
		for (i = 0; i < grafo.getNumeroTotalVertices(); i++)
		{
			verticeTemp = grafo.getVertice(i);
			
			verticeTemp.setX(verticeTemp.getX() - minX);
			verticeTemp.setY(verticeTemp.getY() - minY);
		}
	}
	
// ** Algoritmo de Kamada e Kawai **

	/**
	 * Executa o algoritmo de Spring Model alterando as coordenadas dos vértices.
	 * É importante dizer que este algoritmo simplesmente troca estas coordendas 
	 * dos vértices para novas localizações mais adequadas para serem visualizadas.
	 */
	public void desenharGrafo()
	{
		int 	i,
				localMaiorDeltaM,
				adicaoX, adicaoY, 
				menorCoordenadaX, menorCoordenadaY,
				contador, contadorExterno;
		double	deltaM[], 
				maiorDeltaM;
		
		if (grafo.getNumeroTotalVertices() != 0)
		{
			inicializarVetores();   //Inicializo as posições das coordenadas em um vetor...
			inicializarVariaveis(); //Inicializa dij, lij e kij
			removerQuebraArestas(); //Remove os pontos que interconectam os segmentos da aresta
			
			deltaM = new double[grafo.getNumeroTotalVertices()];
			
			//Identificar o maior deltaM
			localMaiorDeltaM = 0;
			maiorDeltaM = 0.0;
			for (i = 0; i < grafo.getNumeroTotalVertices(); i++)
			{
				deltaM[i] = calcularDeltaM(i);

				if (deltaM[i] > maiorDeltaM)
				{
					maiorDeltaM = deltaM[i];
					localMaiorDeltaM = i;
				}	
			}
			
			//Coração do algoritmo
			contadorExterno = grafo.getNumeroTotalVertices() * CONTADOR;
			while ((deltaM[localMaiorDeltaM] > energiaMinima) && (contadorExterno > 0))
			{
				//Método de Newton Raphson
				contador = CONTADOR;
				while ((deltaM[localMaiorDeltaM] > energiaMinima) && (contador > 0))
				{
					adicaoY = calcularAdicaoY(localMaiorDeltaM);
					adicaoX = calcularAdicaoX(localMaiorDeltaM, adicaoY);
					
					coordenadaY[localMaiorDeltaM] += adicaoY;
					coordenadaX[localMaiorDeltaM] += adicaoX;
					
					deltaM[localMaiorDeltaM] = calcularDeltaM(localMaiorDeltaM);
					
					contador--;
				}
				
				//Identificar o maior deltaM
				localMaiorDeltaM = 0;
				maiorDeltaM = 0.0;
				for (i = 0; i < grafo.getNumeroTotalVertices(); i++)
				{
					deltaM[i] = calcularDeltaM(i);

					if (deltaM[i] > maiorDeltaM)
					{
						maiorDeltaM = deltaM[i];
						localMaiorDeltaM = i;
					}	
				}
				
				contadorExterno--;
			}
			
			//Identificar a coordenada menor (X, Y)
			menorCoordenadaX = 999999999;
			menorCoordenadaY = 999999999;
			for (i = 0; i < grafo.getNumeroTotalVertices(); i++)
			{
				if (coordenadaX[i] < menorCoordenadaX)
					menorCoordenadaX = coordenadaX[i];
				
				if (coordenadaY[i] < menorCoordenadaY)
					menorCoordenadaY = coordenadaY[i];
			}
			
			//Incrementar para posições acima de 10 pixel
			//Retirar o problema de coordenadas menores que 1
			if (menorCoordenadaX <= 0)
				for (i = 0; i < grafo.getNumeroTotalVertices(); i++)
					coordenadaX[i] = coordenadaX[i] - menorCoordenadaX + distEntreComponentesConexas;
			else
			{
				menorCoordenadaX = distEntreComponentesConexas - menorCoordenadaX;
				for (i = 0; i < grafo.getNumeroTotalVertices(); i++)
					coordenadaX[i] = coordenadaX[i] + menorCoordenadaX;
			}
			
			if (menorCoordenadaY <= 0)
				for (i = 0; i < grafo.getNumeroTotalVertices(); i++)
					coordenadaY[i] = coordenadaY[i] - menorCoordenadaY + distEntreComponentesConexas;
			else
			{
				menorCoordenadaY = distEntreComponentesConexas - menorCoordenadaY;
				for (i = 0; i < grafo.getNumeroTotalVertices(); i++)
					coordenadaY[i] = coordenadaY[i] + menorCoordenadaY;
			}
			
			//Atribuo os novas coordendas aos Vertices
			for (i = 0; i < grafo.getNumeroTotalVertices(); i++)
			{
				grafo.getVertice(i).setX(coordenadaX[i]);
				grafo.getVertice(i).setY(coordenadaY[i]);
			}
			
			//Define as posições para as componentes conexas
			posicionarComponentesConexas();
			
		} //Fim do if caso não existe vértice
	}// Fim do método desenharGrafo

//Metodo privado auxiliar
	private void inicializarVisitas()
	{
		int i;
		
		for (i = 0; i < grafo.getNumeroTotalVertices(); i++)
			grafo.getVertice(i).setVisitado(false);
	}
	
	private void posicionarComponentesConexas()
	{
		int 		i,
					maiorPosX, menorPosX, posX, menorPosY, posY,
					indiceVertice;
		Vertice		vertices[], verticeTemp;
		boolean		visitado;
		
		inicializarVisitas();
		indiceVertice = 0;
		maiorPosX = 0;
				
		while (indiceVertice < grafo.getNumeroTotalVertices())
		{	
			visitado = grafo.getVertice(indiceVertice).getVisitado();
			while ((visitado) && (indiceVertice != grafo.getNumeroTotalVertices()))
			{
				indiceVertice++;
				
				if (indiceVertice >= grafo.getNumeroTotalVertices())
					return;
					
				visitado = grafo.getVertice(indiceVertice).getVisitado();
			}

			//Faz o percurso em largura para descobrir as componentes conexas
			vertices = grafo.percursoLargura(indiceVertice);
			
			//Determina a menor posicao em X e Y
			menorPosX = 999999999;
			menorPosY = 999999999;
			for (i = 0; i < vertices.length; i++)
			{
				if (vertices[i].getX() < menorPosX)
				{
					menorPosX = vertices[i].getX();
				}
				
				if (vertices[i].getY() < menorPosY)
				{
					menorPosY = vertices[i].getY();
				}
			}
			
			//Setar a posição no eixoX e eixoY para as componentes conexas
			for (i = 0; i < vertices.length; i++)
			{
				posX = distEntreComponentesConexas + maiorPosX + vertices[i].getX() - menorPosX;	
				vertices[i].setX(posX);
				
				posY = distEntreComponentesConexas + vertices[i].getY() - menorPosY;
				vertices[i].setY(posY);
			}
			
			//Determinar a maior posição em X
			maiorPosX = 0;
			for (i = 0; i < vertices.length; i++)
			{			
				if (vertices[i].getX() > maiorPosX)
					maiorPosX = vertices[i].getX();
				
				vertices[i].setVisitado(true);
			}
			
			indiceVertice++;
		}
		
		menorPosX = 999999999;
		for (i = 0; i < grafo.getNumeroTotalVertices(); i++)
		{
			if (grafo.getVertice(i).getX() < menorPosX)
				menorPosX = grafo.getVertice(i).getX();
		}
		
		posX = distEntreComponentesConexas - menorPosX;
		for (i = 0; i < grafo.getNumeroTotalVertices(); i++)
		{
			grafo.getVertice(i).setX(grafo.getVertice(i).getX() + posX);
		}
	}
	
//Métodos privados da classe Spring Model
	private int calcularAdicaoX(int posicaoVertice, int adicaoY)
	{
		/*formula do sigmaX
		 * 
		 * adicaoX = d1 - (b * adicaoY)
		 * 		       -------------
		 *					 a
		 */
		double 	resultX, coeficienteA, coeficienteB, sigmaX, resto;
		Double	adicaoX;
		int		inteiro;
		
		resultX = - calcularResultanteX(posicaoVertice);
		
		coeficienteA = calcularCoeficienteA(posicaoVertice);
		coeficienteB = calcularCoeficienteB(posicaoVertice);
		
		sigmaX = (resultX - (coeficienteB * adicaoY)) / coeficienteA;
		
		adicaoX = new Double(sigmaX);
		inteiro = adicaoX.intValue();
		resto = sigmaX - inteiro;
		
		//Armazenar do vetor de resto, o resto do trucamento de um inteiro		
		restoX[posicaoVertice] += resto;
		
		if (restoX[posicaoVertice] >= 1.0)
		{
			inteiro++;
			restoX[posicaoVertice] -= 1.0; 
		}
		else if (restoX[posicaoVertice] <= -1.0)
		{
			inteiro--;
			restoX[posicaoVertice] += 1.0;	
		}

		return inteiro;
	}
	
	private int calcularAdicaoY(int posicaoVertice)
	{
		/*formula do calculo
		 *
		 *	adicaoY = (a * d2) - (b * d1)
		 *			 	---------------
		 * 			   (a * c) - (b * b)
		 */
		double		resultX, resultY, coeficienteA, coeficienteB, coeficienteC,
					sigmaY, resto;
		int			inteiro;			
		Double		adicaoY;
		
		resultX = - calcularResultanteX(posicaoVertice);
		resultY = - calcularResultanteY(posicaoVertice);
		
		coeficienteA = calcularCoeficienteA(posicaoVertice);
		coeficienteB = calcularCoeficienteB(posicaoVertice);
		coeficienteC = calcularCoeficienteC(posicaoVertice);
		
		sigmaY = ((coeficienteA * resultY) - (coeficienteB * resultX)) / ((coeficienteA * coeficienteC) - (coeficienteB * coeficienteB));
		
		adicaoY = new Double(sigmaY);
		inteiro = adicaoY.intValue();
		resto = sigmaY - inteiro;

		//Armazenar do vetor de resto, o resto do trucamento de um inteiro		
		restoY[posicaoVertice] += resto;
		
		if (restoY[posicaoVertice] >= 1.0)
		{
			inteiro++;
			restoY[posicaoVertice] -= 1.0; 
		}
		else if (restoY[posicaoVertice] <= -1.0)
		{
			inteiro--;
			restoY[posicaoVertice] += 1.0;
		}
		
		return inteiro;
	}
	
	private double calcularCoeficienteA(int posicaoVertice)
	{
		//Calcula o resultado da formula (13)
		int 		i, diferencaX, diferencaY;
		double		lmi, kmi, denom, aux, coeficienteA;
		
		coeficienteA = 0.0;
		
		for (i = 0; i < grafo.getNumeroTotalVertices(); i++)
		{
			if (i != posicaoVertice)
			{
				lmi = lij[posicaoVertice][i];
				kmi = kij[posicaoVertice][i];
				
				diferencaX = coordenadaX[posicaoVertice] - coordenadaX[i];
				diferencaY = coordenadaY[posicaoVertice] - coordenadaY[i];
				
				denom = (diferencaX * diferencaX) + (diferencaY * diferencaY);
				denom = Math.sqrt(denom);
				denom = denom * denom * denom;
				
				aux = (1 - ((lmi * diferencaY * diferencaY)/denom)) * kmi;
				
				coeficienteA += aux;
			}	
		}
		
		return coeficienteA;
	}
	
	private double calcularCoeficienteB(int posicaoVertice)
	{
		//Calcula o resultado da formula (13)
		int 		i, diferencaX, diferencaY;
		double		lmi, kmi, denom, aux, coeficienteB;
		
		coeficienteB = 0.0;
		
		for (i = 0; i < grafo.getNumeroTotalVertices(); i++)
		{
			if (i != posicaoVertice)
			{
				lmi = lij[posicaoVertice][i];
				kmi = kij[posicaoVertice][i];
				
				diferencaX = coordenadaX[posicaoVertice] - coordenadaX[i];
				diferencaY = coordenadaY[posicaoVertice] - coordenadaY[i];
				
				denom = (diferencaX * diferencaX) + (diferencaY * diferencaY);
				denom = Math.sqrt(denom);
				denom = denom * denom * denom;
				
				aux = ((lmi * diferencaX * diferencaY)/denom) * kmi;
				
				coeficienteB += aux;
			}	
		}
		
		return coeficienteB;
	}
	
	private double calcularCoeficienteC(int posicaoVertice)
	{
		//Calcula o resultado da formula (13)
		int 		i, diferencaX, diferencaY;
		double		lmi, kmi, denom, aux, coeficienteC;
		
		coeficienteC = 0.0;
		
		for (i = 0; i < grafo.getNumeroTotalVertices(); i++)
		{
			if (i != posicaoVertice)
			{
				lmi = lij[posicaoVertice][i];
				kmi = kij[posicaoVertice][i];
				
				diferencaX = coordenadaX[posicaoVertice] - coordenadaX[i];
				diferencaY = coordenadaY[posicaoVertice] - coordenadaY[i];
				
				denom = (diferencaX * diferencaX) + (diferencaY * diferencaY);
				denom = Math.sqrt(denom);
				denom = denom * denom * denom;
				
				aux = (1 - ((lmi * diferencaX * diferencaX)/denom)) * kmi;
				
				coeficienteC += aux;
			}	
		}
		
		return coeficienteC;
	}
	
	private double calcularDeltaM(int posicaoVertice)
	{
		//Calcula o deltaM (9)
		double resultX, resultY, soma;
		
		resultX = calcularResultanteX(posicaoVertice);
		resultX *= resultX;
		
		resultY = calcularResultanteY(posicaoVertice);
		resultY *= resultY;
		
		soma = resultX + resultY;
		
		return Math.sqrt(soma);
	}
	
	private double calcularResultanteX(int posicaoVertice)
	{
		//Calcula a resultante em X (Formula 7 do artigo)
		int			i, diferencaX, diferencaY;
		double		lmi, kmi, denom, aux, somaEnergia;
		
		somaEnergia = 0;
		
		for (i = 0; i < grafo.getNumeroTotalVertices(); i++)
		{
			if (i != posicaoVertice)
			{
				lmi = lij[posicaoVertice][i];
				kmi = kij[posicaoVertice][i];
				
				diferencaX = coordenadaX[posicaoVertice] - coordenadaX[i];
				diferencaY = coordenadaY[posicaoVertice] - coordenadaY[i];
				
				denom = (diferencaX * diferencaX) + (diferencaY * diferencaY);
				denom = Math.sqrt(denom);
				
				aux = (diferencaX - ((lmi * diferencaX) / denom)) * kmi;
				
				somaEnergia += aux;
			}
		}	
		
		return somaEnergia;
	}
	
	private double calcularResultanteY(int posicaoVertice)
	{
		//Calcula a resultante em Y (Formula 8 do artigo)
		int			i, diferencaX, diferencaY;
		double		lmi, kmi, denom, aux, somaEnergia;
		
		somaEnergia = 0;
		
		for (i = 0; i < grafo.getNumeroTotalVertices(); i++)
		{
			if (i != posicaoVertice)
			{
				lmi = lij[posicaoVertice][i];
				kmi = kij[posicaoVertice][i];
				
				diferencaX = coordenadaX[posicaoVertice] - coordenadaX[i];
				diferencaY = coordenadaY[posicaoVertice] - coordenadaY[i];
				
				denom = (diferencaX * diferencaX) + (diferencaY * diferencaY);
				denom = Math.sqrt(denom);
				
				aux = (diferencaY - ((lmi * diferencaY) / denom)) * kmi;
				
				somaEnergia += aux;
			}
		}	
		
		return somaEnergia;
	}
	
	private void inicializarVetores()
	{
		int i, j, 
			novaPosicao, 
			coordenadasUtilizadasX[],
			coordenadasUtilizadasY[];
		
		coordenadasUtilizadasX = new int[grafo.getNumeroTotalVertices()];
		coordenadasUtilizadasY = new int[grafo.getNumeroTotalVertices()];
		
		coordenadaX = new int[grafo.getNumeroTotalVertices()];
		coordenadaY = new int[grafo.getNumeroTotalVertices()];
				
		restoX = new double[grafo.getNumeroTotalVertices()];
		restoY = new double[grafo.getNumeroTotalVertices()];
		
		for (i = 0; i < grafo.getNumeroTotalVertices(); i++)
		{
			coordenadasUtilizadasX[i] = 0;
			coordenadasUtilizadasY[i] = 0;
			
			restoX[i] = 0.0;
			restoY[i] = 0.0;
		}
		
		//Modificar localizações com coordenadas iguais
		for (i = 0; i < grafo.getNumeroTotalVertices() - 1; i++)
		{
			coordenadasUtilizadasX[i] = grafo.getVertice(i).getX();
			coordenadasUtilizadasY[i] = grafo.getVertice(i).getY();
			
			for (j = i + 1; j < grafo.getNumeroTotalVertices(); j++)
			{
				//Modifica para X
				while (grafo.getVertice(i).getX() == grafo.getVertice(j).getX())
				{
					novaPosicao = 1 + (int) (Math.random() * grafo.getNumeroTotalVertices() * Vertice.LARGURAMAXIMA);
					if (!coordenadaUtilizada(coordenadasUtilizadasX, novaPosicao))
						grafo.getVertice(j).setX(novaPosicao);
					
				}
								
				//Modifica para Y
				while (grafo.getVertice(i).getY() == grafo.getVertice(j).getY())
				{
					novaPosicao = 1 + (int) (Math.random() * grafo.getNumeroTotalVertices() * Vertice.ALTURAMAXIMA);
					if (!coordenadaUtilizada(coordenadasUtilizadasY, novaPosicao))
						grafo.getVertice(j).setY(novaPosicao);
					
				}
			}	
		}
		
		//Atribuo as novas posições para os vertices
		for (i = 0; i < grafo.getNumeroTotalVertices(); i++)
		{
			coordenadaX[i] = grafo.getVertice(i).getX();
			coordenadaY[i] = grafo.getVertice(i).getY();
		}
	}
	
	private void removerQuebraArestas()
	{
		Aresta	arestas[];
		int 	i;
		
		arestas = grafo.getTodasArestas();
		for (i = 0; i < grafo.getNumeroTotalArestas(); i++)
			arestas[i].removerTodasQuebras();
	}
	
	private boolean coordenadaUtilizada(int coordenadasUtilizadas[], int novaCoordenada)
	{
		int i;
		
		for (i = 0; i < coordenadasUtilizadas.length; i++)
			if (coordenadasUtilizadas[i] == novaCoordenada)
				return true;
		return false;
	}
	
	private void inicializarVariaveis()
	{
		int i, j;
		
		dij = new int[grafo.getNumeroTotalVertices()][grafo.getNumeroTotalVertices()];
		lij = new double[grafo.getNumeroTotalVertices()][grafo.getNumeroTotalVertices()];
		kij = new double[grafo.getNumeroTotalVertices()][grafo.getNumeroTotalVertices()];
		
		for (i = 0; i < grafo.getNumeroTotalVertices(); i++)
		{
			for (j = 0; j < grafo.getNumeroTotalVertices(); j++)
			{
				dij[i][j] = calcularMenorCaminho(grafo.getVertice(i), grafo.getVertice(j));
				lij[i][j] = calcularDistancia(dij[i][j]);
				kij[i][j] = calcularForca(dij[i][j]);
			}
		}
	}
	
	private int calcularMenorCaminho(Vertice origem, Vertice destino)
	{
		//Calculo do Dij
		return grafo.distanciaMenorCaminho(origem, destino);
	}
	
	private int calcularDistancia(int dij)
	{
		//Calcular Lij do algoritmo através da formula
		// Lij = L * dij
		int distVert;
		
		return tamanhoAresta * dij;
	}
	
	private double calcularForca(int dij)
	{
		//Calcula a força da mola atraves da formula
		// Kij = K / (dij * dij)
		double 	forca, menorCaminho;
		
		if (dij == 0)
			return 0;
			
		menorCaminho = dij;
		forca = forcaMola / (menorCaminho * menorCaminho);
		
		return forca;  
	}
}