package diagram;

import diagram.editor.EditorAplicativoGrafo;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.MissingResourceException;

import diagram.componente.Aresta;
import diagram.componente.Componente;
import diagram.componente.Vertice;
import diagram.componente.VerticeAtor;
import diagram.componente.VerticeCasoUso;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Est� classe � respons�vel pela cria��o dos grafos apresentados no editor. 
 * � importante observar que v�rtices e arestas duplicados n�o s�o permitidos
 * e portanto descartados quando identificados por esta classe. Ent�o novas
 * inst�ncias de v�rtices e arestas devem ser passados para que est� classe
 * reconhe�a a estrutura de grafo. Outra observa��o importante com rela��o as 
 * arestas � o fato delas n�o poderem ser armazenadas caso seus respectivos
 * v�rtices origem e destino n�o forem inseridos anteriormente.
 * 
 * A classe grafo utiliza a lista de adjac�ncia como forma de representa��o 
 * substituindo o vetor de v�rtices por uma outra lista encadeada.
 * 
 * Um exemplo de como criar um grafo no editor � mostrado no exemplo abaixo. <br>
 * <br>
 * ...<br>
 * ...<br>
 * Grafo exemplo01 = new Grafo();<br>
 * exemplo01.setAresta(new ArestaSimples(new VerticeRetangulo(), new VerticeRetangulo()));<br>
 * ...<br>
 * ...<br>
 * <br>
 * A classe tamb�m possui alguns m�todos conhecidos dos grafos tais como os percursos
 * em largura e profundidade e o algoritmo de menor caminho muito utilizado pela 
 * classe SpringModel para desenhar um grafo.
 *
 * @author Luis Henrique Castilho da Silva
 * @see diagram.graphdrawing.SpringModel
 */
public class Grafo extends Object
{
	private int					numeroTotalArestas;
	
	private LinkedList 			listaVertice,
								listaAresta[],
								filaVertices,
								pilhaVertices;
	
        private ArrayList<Vertice> ListaAtor, ListaCasoUso;
        
        private EditorAplicativoGrafo editor;
	/**
	 * Cria um grafo sem nenhum v�rtice e aresta.
	 */
	public Grafo()
	{
		listaVertice = new LinkedList();
		numeroTotalArestas = 0;
                ListaAtor = new ArrayList<Vertice>();
                ListaCasoUso = new ArrayList<Vertice>();
	}
	
	/**
	 * Cria um grafo com um n�mero de v�rtices e arestas passados pelos par�metros.
	 *
	 * @param vertices o vetor que cont�m todos os v�rtices
	 * @param arestas o vetor que cont�m todas as arestas
	 */
	public Grafo(Vertice vertices[], Aresta arestas[])
	{
		listaVertice = new LinkedList();
		numeroTotalArestas = 0;
		setVertices(vertices);
		setArestas(arestas);
	}
	
// M�todos Get...

	/**
	 * Retorna todos os v�rtices conmtidos no grafo.
	 *
	 * @return o vetor contendo todos os v�rtices
	 * @see Grafo#getTodasArestas
	 */
	public Vertice[] getTodosVertices()
	{
		int i;
		Vertice 	vertices[];
				
		if (listaVertice.size() == 0) 
			return null; //Caso n�o exista V�rtices
			
		vertices = new Vertice[listaVertice.size()];
		
		for (i = 0;i < listaVertice.size(); i++)
			vertices[i] = (Vertice) listaVertice.get(i);
			
		return vertices;
	}

	/**
	 * Retorna o v�rtice especificado pelo par�metro posi��o do v�rtice.
	 * 
	 * @param posicao a localiza��o do v�rtice na lista encadeada
	 * @return o v�rtice especificado na posi��o
	 * @see Grafo#getAresta
	 */
	public Vertice getVertice(int posicao)
	{
		if ((posicao >= 0) && (posicao < listaVertice.size()))
			return (Vertice) listaVertice.get(posicao);
		
		return null;
	}
	
	/**
	 * Identifica se o v�rtice est� ou n�o contido dentro da estrutura da clase
	 * grafo. Caso isto seja verdadeiro, o m�todo retorna verdadeiro e falso caso
	 * contr�rio.
	 *
	 * @param vertice o v�rtice que deseja verificar a sua exist�ncia
	 * @return em caso de existir ou n�o o v�rtice
	 * @see Grafo#existeAresta
	 */
	public boolean existeVertice(Vertice vertice)
	{
		int i;
		Vertice verticeTemp;
		
		for (i = 0; i < listaVertice.size() ; i++)
		{
			verticeTemp = (Vertice) listaVertice.get(i);
			
			if (verticeTemp.equals(vertice))
				return true;
		}
		
		return false;
	}
	
	/**
	 * Retorna todos os v�rtices adjacentes em rela��o ao v�rtice passado pelo
	 * par�metro.
	 *
	 * @param vertice o vertice que deseja identificar seus adjacentes
	 * @return o vetor contendo todos os v�rtices adjacentes
	 */
	public Vertice[] getVerticesAdjacentes(Vertice vertice)
	{
		int 		i;
		Vertice		vertices[];
		Aresta		arestas[];
		
		arestas = getAresta(vertice);
		
		if (arestas == null)
			return null;
		
		vertices = new Vertice[arestas.length];
		
		for (i = 0; i < arestas.length; i++)
		{
			if (arestas[i].getVerticeOrigem().equals(vertice))
				vertices[i] = arestas[i].getVerticeDestino();
			else
				vertices[i] = arestas[i].getVerticeOrigem();
		}
		
		return vertices;
	}
	
	/**
	 * Retorna todos as arestas contidas no grafo.
	 *
	 * @return o vetor contendo todas as arestas
	 */
	public Aresta[] getTodasArestas()
	{
		int 		i, j, k, contador;
		boolean 	existeAresta;
		Vertice 	verticeTemp;
		Aresta		arestas[],
					arestaTemp;
		
		if (numeroTotalArestas <= 0)	
			return null;
		
		contador = 0;
		existeAresta = false;
		arestas = new Aresta[numeroTotalArestas];
		
		for (j = 0; j < listaVertice.size(); j++)
		{
			//Pecorrer todos os V�rtices	
			verticeTemp = (Vertice) listaVertice.get(j);

			for (k = 0; k < listaAresta[j].size(); k++)
			{
				arestaTemp = (Aresta) listaAresta[j].get(k);
				
				for (i = 0; i < arestas.length; i++)
				{
					//Percorre o vetor de retorno para identificar as arestas nao incluidas
					if (arestaTemp.equals(arestas[i]))
						existeAresta = true;
				}
				
				if (!existeAresta)
				{
					arestas[contador] = arestaTemp;	
					contador++;
				}
				
				existeAresta = false;
			}
		}
		
		return arestas;
	}
	
	/**
	 * Retorna a(s) aresta(s) na qual o v�rtice passado pelo par�metro est� 
	 * diretamente conectado a aresta, ou seja, � um v�rtice origem ou destino.
	 * 
	 * @param vertice o v�rtice onde a aresta est� conectada
	 * @return o vetor contendo as arestas ligadas ao v�rtice
	 * @see Grafo#getVertice
	 */
	public Aresta[] getAresta(Vertice vertice)
	{
		//Retorna nulo em caso de n�o existir o vertice, n�o existir nenhuma arestas em todo o grafo
		//Para o caso de existir o vertice mas n�o exitir a aresta ser� retornado nulo.
		int i, j;
		Vertice verticeTemp;
		Aresta	arestas[];
		
		if (numeroTotalArestas <= 0)
			return null;
			
		for (i = 0; i < listaVertice.size(); i++)
		{
			verticeTemp = (Vertice) listaVertice.get(i);
			
			if (verticeTemp.equals(vertice))
			{
				if (listaAresta[i].size() == 0)
					return null;
					
				arestas = new Aresta[listaAresta[i].size()];
				
				for (j = 0; j < arestas.length; j++)
				{
					arestas[j] = (Aresta) listaAresta[i].get(j);
				}
				
				return arestas;
			}
		}
		
		return null;
	}
	
	/**
	 * Retorna a aresta na qual o v�rtice passado pelo par�metro est� 
	 * diretamente conectado a aresta e o indice identifica a localiza��o extata 
	 * dentro da lista encadeada do v�rtice.
	 * 
	 * @param vertice o v�rtice onde a aresta est� conectada
	 * @param localiza��o a localiza��o da aresta na lista encadeada do v�rtice
	 * @return a aresta diretamente ligada ao v�rtice
	 * @see Grafo#getVertice
	 */
	public Aresta getAresta(Vertice vertice, int indice)
	{
		int i;
		Vertice verticeTemp;
		
		for (i = 0; i < listaVertice.size(); i++)
		{
			verticeTemp = (Vertice) listaVertice.get(i);
			
			if (verticeTemp.equals(vertice))
			{
				//O vetice � igual ao passado pelo par�metro
				if ((indice < listaAresta[i].size()) && (indice >= 0))
					return (Aresta) listaAresta[i].get(indice);
			}
		}
		
		return null;
	}
	
	/**
	 * Identifica se a aresta est� ou n�o contido dentro da estrutura da clase
	 * grafo. Caso isto seja verdadeiro, o m�todo retorna verdadeiro e falso caso
	 * contr�rio.
	 *
	 * @param aresta a aresta que deseja verificar a sua exist�ncia
	 * @return em caso de existir ou n�o a aresta
	 * @see Grafo#existeVertice
	 */
	public boolean existeAresta(Aresta aresta)
	{
		int i, j;
		Aresta arestaTemp;
		
		if (listaAresta == null)
			return false;
			
		for (i = 0; i < listaAresta.length; i++)
		{
			for (j = 0; j < listaAresta[i].size(); j++)
			{
				arestaTemp = (Aresta) listaAresta[i].get(j);
				
				if (arestaTemp.equals(aresta))
					return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Retorna o n�mero total de aresta conectadas ao v�rtice passado pelo
	 * par�metro.
	 *
	 * @param vertice o v�rtice contido no grafo
	 * @return o n�mero de arestas conectadas ao v�rtice
	 * @see Grafo#getNumeroTotalArestas
	 * @see Grafo#getNumeroTotalVertices
	 */
	public int getNumeroTotalArestasDoVertice(Vertice vertice)
	{
		int i;
		Vertice verticeTemp;
		
		for (i = 0; i < listaVertice.size(); i++)
		{
			verticeTemp = (Vertice) listaVertice.get(i);
			if (verticeTemp.equals(vertice))
				return listaAresta[i].size();
		}
		
		return -1;
	}
	
	/**
	 * Retorna a localiza��o extaa do v�rtice na estrutura de lista encadeada.
	 *
	 * @param vertice o v�rtice que deseja verficar sua posi��o na lista
	 * @return a localiza��o do v�rtice na lista encadeada
	 */
	public int getLocalizacaoVertice(Vertice vertice)
	{
		//Retorna a localiza��o do v�rtice na lista de v�rtices
		int i;
		Vertice		verticeTemp;
		
		for (i = 0; i < listaVertice.size(); i++)
		{
			verticeTemp = (Vertice) listaVertice.get(i);
			
			if (verticeTemp.equals(vertice))
				return i;
		}
		
		return 0;
	}

	/**
	 * Retorna o n�mero total de arestas contidas no grafo.
	 *
	 * @return o n�mero total de arestas
	 * @see Grafo#getNumeroTotalVertices
	 * @see Grafo#getNumeroTotalArestasDoVertice
	 */
	public int getNumeroTotalArestas()
	{
		return numeroTotalArestas;
	}
	
	/**
	 * Retorna o n�mero total de v�rtices contido no grafo.
	 *
	 * @return o n�mero total de v�rtices
	 * @see Grafo#getNumeroTotalArestas
	 */
	public int getNumeroTotalVertices()
	{
		return listaVertice.size();
	}
	
// M�todos Set...

	/**
	 * Acrescenta um conjunto de v�rtice ao grafo.
	 *
	 * @param vertices o vetor de v�rtices
	 */
	public void setVertices(Vertice vertices[]) throws NullPointerException
	{
		int i;
		
		if (vertices == null)
			throw new NullPointerException("**Excecao**\ndiagram.GrafoException: Vertice nulo.");

		for (i = 0;i < vertices.length; i++)
		{
			if (!existeVertice(vertices[i]))
				listaVertice.add(vertices[i]);
		}
		
		criarListaAresta();
	}
	
	/**
	 * Acrescenta somente um v�rtice ao grafo.
	 *
	 * @param vertice o v�rtices a ser inserido
	 */
	public void setVertice(Vertice vertice) throws NullPointerException
	{
		if (vertice == null)
			throw new NullPointerException("**Excecao**\ndiagram.GrafoException: Vertice nulo.");
			
		if (!existeVertice(vertice))
		{	
			listaVertice.add(vertice);
			criarListaAresta();
		}
	}
	
	/**
	 * Acrescenta um conjunto de arestas ao grafo.
	 *
	 * @param arestas o vetor de arestas ser inserida
	 */	
	public void setArestas(Aresta arestas[]) throws MissingResourceException
	{
		int i;
		
		if (arestas != null)
		{
			i = 0;
			try{
				for (i = 0; i < arestas.length; i++)
					setAresta(arestas[i]);
			}catch (MissingResourceException e){
				MissingResourceException faltaVertice = new MissingResourceException("\n*** Excecao ***\ndiagram.GrafoException: O vertice de origem ou destino nao existe.", "Grafo", arestas[i].toString());	
				throw faltaVertice;	
			}
		}
	}
	
	/**
	 * Acrescenta uma arestas ao grafo.
	 *
	 * @param aresta a nova aresta a ser inserida
	 */	
	public void setAresta(Aresta aresta) throws MissingResourceException
	{
		int i;
		
		if (!existeAresta(aresta))
		{
			if (existeVertice(aresta.getVerticeOrigem()) && existeVertice(aresta.getVerticeDestino()))
			{
				//O vertice de Origem e o vertice de Destino est�o contidos na lista...
				//Pode ser inserido a aresta...
				for (i = 0; i < listaVertice.size(); i++)
				{
					if (getVertice(i).equals(aresta.getVerticeOrigem()))
					{
						listaAresta[i].add(aresta);
						numeroTotalArestas++;
					}
					
					if (getVertice(i).equals(aresta.getVerticeDestino()))
						listaAresta[i].add(aresta);
				}
			}
			else
			{
				//Gera uma exce��o identificando que n�o existe uma aresta...
				MissingResourceException faltaVertice = new MissingResourceException("\n*** Excecao ***\ndiagram.GrafoException: O vertice de origem ou destino nao existe.", "Grafo", aresta.toString());	
				throw faltaVertice;
			}
		}
	}
	
	/**
	 * Remove um v�rtice do grafo especificado pelo par�metro.
	 *
	 * @param vertice o v�rtice a ser removido
	 * @see Grafo#removerTodasArestas
	 * @see Grafo#removerAresta
	 * @see Grafo#removerGrafo
	 */
	public void removerVertice(Vertice vertice)
	{
		int			i,
					contTemp,
					localVertice;
		LinkedList	listaArestaTemp[];
		
		contTemp = 0;
		localVertice = getLocalizacaoVertice(vertice);
		listaArestaTemp = new LinkedList[getNumeroTotalVertices() - 1];
				
		//Deve-se remover todas as arestas antes
		removerTodasArestas(vertice);
		
		//Remover o campo da lista de aresta referente ao vertice
		for (i = 0;i < listaAresta.length; i++)
		{
			if (i != localVertice)
			{
				listaArestaTemp[contTemp] = listaAresta[i];
				contTemp++;
			}
		}
		
		listaAresta = listaArestaTemp;
		
		//Remover o v�rtice		
		listaVertice.remove(vertice);
	}
	
	/**
	 * Remove uma aresta do grafo especificado pelo par�metro.
	 *
	 * @param aresta a aresta a ser removida
	 * @see Grafo#removerTodasArestas
	 * @see Grafo#removerVertice
	 * @see Grafo#removerGrafo
	 */
	public void removerAresta(Aresta aresta)
	{
		boolean 	removido;
		int 		i, j;
		Aresta 		arestaTemp;
		
		removido = false;
		for (i = 0; i < listaVertice.size(); i++)
		{
			for (j = 0 ; j < listaAresta[i].size(); j++)
			{
				arestaTemp = (Aresta) listaAresta[i].get(j);
				if (arestaTemp.equals(aresta))
				{
					listaAresta[i].remove(j);
					removido = true;	
				}
			}
		}

		if (removido)
			numeroTotalArestas--;
	}
	
	/**
	 * Remove todas as arestas que est�o diretamente conectadas ao v�rtice 
	 * especificado pelo par�metro.
	 *
	 * @param vertice o v�rtice na qual deseja remover suas respectivas arestas
	 * @see Grafo#removerAresta
	 * @see Grafo#removerVertice
	 * @see Grafo#removerGrafo
	 */
	public void removerTodasArestas(Vertice vertice)
	{
		int 	i,
				contMax,
				localVertice;
		Aresta	arestaTemp;
		
		localVertice = getLocalizacaoVertice(vertice);
		contMax = listaAresta[localVertice].size();
		
		for (i = 0; i < contMax; i++)
		{
			arestaTemp = (Aresta) listaAresta[localVertice].get(0);
			removerAresta(arestaTemp);
		}
	}
	
	/**
	 * Remove todos os componentes do grafo, ou seja, os v�rtice e as arestas.
	 * 
	 * @see Grafo#removerTodasArestas
	 * @see Grafo#removerAresta
	 * @see Grafo#removerVertice
	 */
	public void removerGrafo()
	{
		listaVertice.clear();
		listaAresta = null;
		numeroTotalArestas = 0;
	}
	
	/**
	 * Retorna a dist�ncia do menor caminho entre dois v�rtices quaisquer contidos
	 * no grafo. Caso n�o exista um caminho entre estes v�rtice, o valor 0 ser� 
	 * retornado. 
	 *
	 * A dist�ncia do menor caminho � calculada baseado na quantidade de arestas
	 * m�nimas para alcan�ar o v�rtice de destino.
	 *
	 * @param origem o v�rtice de inicial do caminho
	 * @param destino o v�rtice final do caminho
	 * @return o n�mero da dist�ncia do menor caminho
	 */
	public int distanciaMenorCaminho(Vertice origem, Vertice destino)
	{
		int			i, contador, custo[];
		LinkedList	lista;
		Vertice		verticesAdj[], verticeAtual, verticePosterior;
		boolean 	adjacente;
		
		lista = new LinkedList();
		contador = 1;
		
		desmarcarTodosVerticeVisitados();
		
		lista.add(origem);
		origem.setVisitado(true);

		//Insere todos os vertice em uma lista encadeada pelo percurso em largura
		verticeAtual = origem;
		while (!existeVertice(lista, destino))
		{
			verticesAdj = getVerticesAdjacentes(verticeAtual);
			
			if (verticesAdj != null)
			{
				for (i = 0; i < verticesAdj.length; i++)	
				{
					if (!verticesAdj[i].getVisitado())
					{
						verticesAdj[i].setVisitado(true);
						lista.add(verticesAdj[i]);
					}	
				}
			}
			else
				break;
			
			if (contador == lista.size())
				return 0;
				
			verticeAtual = (Vertice) lista.get(contador);
			contador++;
		}
		
		custo = new int[getNumeroTotalVertices()];
		for (i = 0; i < getNumeroTotalVertices(); i++)
			custo[i] = 1000000; //custo muito alto infinito
		
		contador = 0;
		verticeAtual = (Vertice) lista.get(contador);
		custo[getLocalizacaoVertice(verticeAtual)] = contador;
		contador++;
		i = 1;
		adjacente = false;
		while (lista.size() > i)
		{
			//Altera o vetor de custo
			verticePosterior = (Vertice) lista.get(i);
			
			if (adjacencia(verticePosterior, verticeAtual))
			{
				custo[getLocalizacaoVertice(verticePosterior)] = custo[getLocalizacaoVertice(verticeAtual)] + 1;
				adjacente = false;
			}
			else
			{
				verticeAtual = (Vertice) lista.get(contador);
				contador++;
				adjacente = true;
			}
			
			if (!adjacente)
				i++;
		}		
		
		return custo[getLocalizacaoVertice(destino)];
	}
	
	/**
	 * Retorna todos os v�rtice visitados durante um percurso em largura
	 * considerando o sentido da aresta. A forma como esses v�rtices est�o dispostos 
	 * no vetor � relativo ao forma como eles s�o visitados, ou seja, o v�rtice
	 * localizado na posi��o 0 do vetor foi o primeiro v�rtice visitado enquanto que
	 * o v�rtice localizado na posi��o 1 foi o segundo v�rtice visitado a assim ocorre
	 * com todos as outros.
	 *
	 * @param indiceInicial a localiza��o na lista do v�rtice inicial do percurso
	 */
	public Vertice[] percursoLarguraOrientado(int indiceInicial)
	{
		int			i,
					contadorVertice;
		Vertice		verticeTemp,
					verticesRetorno[];
		Aresta		arestaTemp;
		LinkedList	listaRetorno;
		
		listaRetorno = new LinkedList();
		contadorVertice = indiceInicial;
		
		if ((indiceInicial >= 0) && (indiceInicial < listaVertice.size()))
		{
			//O valor do indice est� correto
			
			desmarcarTodosVerticeVisitados();
			
			//Inicializa a fila de V�rtices
			inicializarFila();
			
			//Visita o v�rtice...
			verticeTemp = (Vertice) listaVertice.get(indiceInicial);
			
			//Marcar o v�rtice
			verticeTemp.setVisitado(true);
			listaRetorno.add(verticeTemp);
			
			//Inserir v�rtice na fila
			inserirFila(verticeTemp);
			
			while(!filaVazia())
			{
				//Remover da fila
				verticeTemp = removerFila();
				contadorVertice = getLocalizacaoVertice(verticeTemp);
					
				for (i = 0; i < listaAresta[contadorVertice].size(); i++)
				{
					arestaTemp = (Aresta) listaAresta[contadorVertice].get(i);

					if (!(arestaTemp.getVerticeDestino()).getVisitado())
					{
						//Visita o v�rtice...
						verticeTemp = (Vertice) arestaTemp.getVerticeDestino();
						
						//Marcar o v�rtice
						verticeTemp.setVisitado(true);
						listaRetorno.add(verticeTemp);
						
						//Inserir v�rtice na fila
						inserirFila(verticeTemp);
					}
				}
			}
			
			destruirFila();
			verticesRetorno = new Vertice[listaRetorno.size()];
			for (i = 0; i < listaRetorno.size(); i++)
				verticesRetorno[i] = (Vertice) listaRetorno.get(i);
						
			return verticesRetorno;
		}
		
		return null;
	}
	
	/**
	 * Retorna todos os v�rtice visitados durante um percurso em largura n�o
	 * considerando o sentido da aresta. A forma como esses v�rtices est�o dispostos 
	 * no vetor � relativo ao forma como eles s�o visitados, ou seja, o v�rtice
	 * localizado na posi��o 0 do vetor foi o primeiro v�rtice visitado enquanto que
	 * o v�rtice localizado na posi��o 1 foi o segundo v�rtice visitado a assim ocorre
	 * com todos as outros.
	 *
	 * @param indiceInicial a localiza��o na lista do v�rtice inicial do percurso
	 */
	public Vertice[] percursoLargura(int indiceInicial)
	{
		int			i,
					contadorVertice;
		Vertice		verticeTemp,
					verticesRetorno[];
		Aresta		arestaTemp;
		LinkedList	listaRetorno;
		
		listaRetorno = new LinkedList();
		contadorVertice = indiceInicial;
		
		if ((indiceInicial >= 0) && (indiceInicial < listaVertice.size()))
		{
			//O valor do indice est� correto
			desmarcarTodosVerticeVisitados();
			
			//Inicializa a fila de V�rtices
			inicializarFila();
			
			//Visita o v�rtice...
			verticeTemp = (Vertice) listaVertice.get(indiceInicial);
			
			//Marcar o v�rtice
			verticeTemp.setVisitado(true);
			listaRetorno.add(verticeTemp);
			
			//Inserir v�rtice na fila
			inserirFila(verticeTemp);
			
			while(!filaVazia())
			{
				//Remover da fila
				verticeTemp = removerFila();
				contadorVertice = getLocalizacaoVertice(verticeTemp);
					
				for (i = 0; i < listaAresta[contadorVertice].size(); i++)
				{
					arestaTemp = (Aresta) listaAresta[contadorVertice].get(i);

					if (!(arestaTemp.getVerticeOrigem()).getVisitado())
					{
						//Visita o v�rtice...
						verticeTemp = (Vertice) arestaTemp.getVerticeOrigem();
						
						//Marcar o v�rtice
						verticeTemp.setVisitado(true);
						listaRetorno.add(verticeTemp);
						
						//Inserir v�rtice na fila
						inserirFila(verticeTemp);
					}
					
					if (!(arestaTemp.getVerticeDestino()).getVisitado())
					{
						//Visita o v�rtice...
						verticeTemp = (Vertice) arestaTemp.getVerticeDestino();
						
						//Marcar o v�rtice
						verticeTemp.setVisitado(true);
						listaRetorno.add(verticeTemp);
						
						//Inserir v�rtice na fila
						inserirFila(verticeTemp);
					}
				}
			}
			
			destruirFila();
			verticesRetorno = new Vertice[listaRetorno.size()];
			for (i = 0; i < listaRetorno.size(); i++)
				verticesRetorno[i] = (Vertice) listaRetorno.get(i);
			
			return verticesRetorno;
		}
		
		return null;
	}
	
	/**
	 * Retorna todos os v�rtice visitados durante um percurso em profundidade
	 * considerando o sentido da aresta. A forma como esses v�rtices est�o dispostos 
	 * no vetor � relativo ao forma como eles s�o visitados, ou seja, o v�rtice
	 * localizado na posi��o 0 do vetor foi o primeiro v�rtice visitado enquanto que
	 * o v�rtice localizado na posi��o 1 foi o segundo v�rtice visitado a assim ocorre
	 * com todos as outros.
	 *
	 * @param indiceInicial a localiza��o na lista do v�rtice inicial do percurso
	 */
	public Vertice[] percursoProfundidadeOrientado(int indiceInicial)
	{
		int			i,
					contadorVertice;
		Vertice		verticeTemp,
					verticesRetorno[];
		Aresta		arestaTemp;
		LinkedList	listaRetorno;
		
		listaRetorno = new LinkedList();
		contadorVertice = indiceInicial;
		
		if ((indiceInicial >= 0) && (indiceInicial < listaVertice.size()))
		{
			//O valor do indice est� correto
			desmarcarTodosVerticeVisitados();
			
			//Inicializa a fila de V�rtices
			inicializarPilha();
			
			//Visita o v�rtice...
			verticeTemp = (Vertice) listaVertice.get(indiceInicial);
			
			//Marcar o v�rtice
			verticeTemp.setVisitado(true);
			listaRetorno.add(verticeTemp);
			
			//Inserir v�rtice na pilha
			inserirPilha(verticeTemp);
			inserirPilha(verticeTemp);
			
			while(!pilhaVazia())
			{
				//Remover da pilha
				verticeTemp = removerPilha();
				contadorVertice = getLocalizacaoVertice(verticeTemp);
				i = 0;
				
				while (listaAresta[contadorVertice].size() > i)
				{
					arestaTemp = (Aresta) listaAresta[contadorVertice].get(i);
					i++;
					
					if (!arestaTemp.getVerticeDestino().getVisitado())
					{
						//Visita o v�rtice...
						verticeTemp = (Vertice) arestaTemp.getVerticeDestino();
						
						//Marcar o v�rtice
						verticeTemp.setVisitado(true);
						listaRetorno.add(verticeTemp);
						
						//Inserir v�rtice na pilha
						inserirPilha(verticeTemp);
						
						contadorVertice = getLocalizacaoVertice(verticeTemp);
						i = 0;
					}
				}
			}
			
			destruirPilha();
			verticesRetorno = new Vertice[listaRetorno.size()];
			for (i = 0; i < listaRetorno.size(); i++)
				verticesRetorno[i] = (Vertice) listaRetorno.get(i);
			
			return verticesRetorno;
		}
		
		return null;
	}
	
	/**
	 * Retorna todos os v�rtice visitados durante um percurso em profuindidade n�o
	 * considerando o sentido da aresta. A forma como esses v�rtices est�o dispostos 
	 * no vetor � relativo ao forma como eles s�o visitados, ou seja, o v�rtice
	 * localizado na posi��o 0 do vetor foi o primeiro v�rtice visitado enquanto que
	 * o v�rtice localizado na posi��o 1 foi o segundo v�rtice visitado a assim ocorre
	 * com todos as outros.
	 *
	 * @param indiceInicial a localiza��o na lista do v�rtice inicial do percurso
	 */
	public Vertice[] percursoProfundidade(int indiceInicial)
	{
		int			i,
					contadorVertice;
		Vertice		verticeTemp,
					verticesRetorno[];
		Aresta		arestaTemp;
		LinkedList	listaRetorno;
		
		listaRetorno = new LinkedList();
		contadorVertice = indiceInicial;
		
		if ((indiceInicial >= 0) && (indiceInicial < listaVertice.size()))
		{
			//O valor do indice est� correto
			desmarcarTodosVerticeVisitados();
			
			//Inicializa a fila de V�rtices
			inicializarPilha();
			
			//Visita o v�rtice...
			verticeTemp = (Vertice) listaVertice.get(indiceInicial);
			
			//Marcar o v�rtice
			verticeTemp.setVisitado(true);
			listaRetorno.add(verticeTemp);
			
			//Inserir v�rtice na pilha
			inserirPilha(verticeTemp);
			inserirPilha(verticeTemp);
			
			while(!pilhaVazia())
			{
				//Remover da pilha
				verticeTemp = removerPilha();
				contadorVertice = getLocalizacaoVertice(verticeTemp);
				i = 0;
				
				while (listaAresta[contadorVertice].size() > i)
				{
					arestaTemp = (Aresta) listaAresta[contadorVertice].get(i);
					i++;
					
					if (!arestaTemp.getVerticeOrigem().getVisitado())
					{
						//Visita o v�rtice...
						verticeTemp = (Vertice) arestaTemp.getVerticeOrigem();
						
						//Marcar o v�rtice
						verticeTemp.setVisitado(true);
						listaRetorno.add(verticeTemp);
						
						//Inserir v�rtice na pilha
						inserirPilha(verticeTemp);
						
						contadorVertice = getLocalizacaoVertice(verticeTemp);
						i = 0;
					}
					
					if (!arestaTemp.getVerticeDestino().getVisitado())
					{
						//Visita o v�rtice...
						verticeTemp = (Vertice) arestaTemp.getVerticeDestino();
						
						//Marcar o v�rtice
						verticeTemp.setVisitado(true);
						listaRetorno.add(verticeTemp);
						
						//Inserir v�rtice na pilha
						inserirPilha(verticeTemp);
						
						contadorVertice = getLocalizacaoVertice(verticeTemp);
						i = 0;
					}
				}
			}
			
			destruirPilha();
			verticesRetorno = new Vertice[listaRetorno.size()];
			for (i = 0; i < listaRetorno.size(); i++)
				verticesRetorno[i] = (Vertice) listaRetorno.get(i);
			
			return verticesRetorno;
		}
		
		return null;
	}

//M�todos privados da classe Grafo
	private boolean existeVertice(LinkedList lista, Vertice vertice)
	{
		int 		i;
		Vertice		verticeTemp;
		
		for (i = 0; i < lista.size(); i++)
		{
			verticeTemp = (Vertice) lista.get(i);
			
			if (verticeTemp.equals(vertice))
				return true;
		}
		
		return false;
	}
	
	private boolean adjacencia(Vertice origem, Vertice destino)
	{
		int 	i;
		Vertice	vertices[];
		
		vertices = getVerticesAdjacentes(origem);
		
		if (vertices == null)
			return false;
			
		for (i = 0; i < vertices.length; i++)
		{
			if (vertices[i].equals(destino))
				return true;	
		}
		
		return false;
	}
	
	private void criarListaAresta()
	{
		int 		i;
		LinkedList 	listaArestaTemp[];
		
		if (listaAresta == null)
		{
			//Criado pela primeira vez uma lista de arestas
			listaAresta = new LinkedList[listaVertice.size()];
			
			for (i = 0; i < listaAresta.length; i++)
				listaAresta[i] = new LinkedList();
		}
		else
		{
			if (listaVertice.size() > listaAresta.length)
			{
				//Foram criados novos v�rtices que n�o possuem uma lista de arestas
				listaArestaTemp = new LinkedList[listaVertice.size()];
				
				for (i = 0; i < listaArestaTemp.length; i++)
				{
					listaArestaTemp[i] = new LinkedList();
					
					if (i < listaAresta.length)
						listaArestaTemp[i] = listaAresta[i];
				}
				
				listaAresta = null; //Seta para o coletor de lixo limpar da mem�ria
				
				listaAresta = listaArestaTemp;
			}
		}
	}
	
	private void desmarcarTodosVerticeVisitados()
	{
		//Ele percorre por todo o grafo desmarca os vertices visitados.
		int 		i, j;
		Vertice		verticeTemp;
		Aresta		arestaTemp;
		
		for (i = 0; i < listaVertice.size(); i++)
		{
			verticeTemp = (Vertice) listaVertice.get(i);
			verticeTemp.setVisitado(false);
			
			for (j = 0; j < listaAresta[i].size(); j++)
			{
				arestaTemp = (Aresta) listaAresta[i].get(j);
				arestaTemp.setVisitado(false);
			}	
		}
	}
	
//Metodos da fila interna para o percurso em amplitude
	private void inicializarFila()
	{
		filaVertices = new LinkedList();
	}
	
	private void inserirFila(Vertice vertice)
	{
		filaVertices.addLast(vertice);
	}
	
	private Vertice removerFila()
	{
		return (Vertice) filaVertices.removeFirst();
	}
	
	private boolean filaVazia()
	{
		if (filaVertices.size() > 0)
			return false;
		
		return true;
	}
	
	private void destruirFila()
	{
		filaVertices = null;	
	}
	
//M�tdos da pilha interna para o percurso em profundidade
	private void inicializarPilha()
	{
		pilhaVertices = new LinkedList();
	}
	
	private void inserirPilha(Vertice vertice)
	{
		pilhaVertices.addLast(vertice);
	}
	
	private Vertice removerPilha()
	{
		return (Vertice) pilhaVertices.removeLast();	
	}
	
	private boolean pilhaVazia()
	{
		if (pilhaVertices.size() > 0)
			return false;
		
		return true;	
	}
	
	private void destruirPilha()
	{
		pilhaVertices = null;
	}

    //Teste do André
        
    public void setVertice(Vertice vertice, EditorAplicativoGrafo editor) {
        if (vertice == null)
            throw new NullPointerException("**Excecao**\ndiagram.GrafoException: Vertice nulo.");
			
		if (!existeVertice(vertice))
		{	
			listaVertice.add(vertice);
			criarListaAresta();
		}
          editor.setGrafo(this);
    }

    public void setAresta(Aresta aresta, EditorAplicativoGrafo editor) {
        int i;
	if (!existeAresta(aresta))
	{
            if (existeVertice(aresta.getVerticeOrigem()) && existeVertice(aresta.getVerticeDestino()))
            {
                //O vertice de Origem e o vertice de Destino est�o contidos na lista...
		//Pode ser inserido a aresta...
		for (i = 0; i < listaVertice.size(); i++)
		{
                    if (getVertice(i).equals(aresta.getVerticeOrigem()))
                    {
                        listaAresta[i].add(aresta);
			numeroTotalArestas++;
                    }
		
                    if (getVertice(i).equals(aresta.getVerticeDestino()))
                        listaAresta[i].add(aresta);
		}
            }
            else
            {
                //Gera uma exce��o identificando que n�o existe uma aresta...
		MissingResourceException faltaVertice = new MissingResourceException("\n*** Excecao ***\ndiagram.GrafoException: O vertice de origem ou destino nao existe.", "Grafo", aresta.toString());	
		throw faltaVertice;
            }
	}
        editor.setGrafo(this);       
    }
    
    public LinkedList getListaArestas()
    {
        return listaVertice;
   
    }
    
    //Retornar todos os vertices organizadados de cima para baixo, da esquerda para direita.
    public ArrayList<Vertice> getVerticesOrdenados()
    {
        ArrayList<Vertice> vertices = new ArrayList<Vertice>();
        Vertice[] v = this.getTodosVertices();
        if (this.getNumeroTotalVertices() != 0 )
        {
            vertices.addAll(Arrays.asList(v));
            Collections.sort(vertices, new organizarVertices());
        }
        return vertices;
    }

    //Retornar todos as arestas organizadadas de cima para baixo,  esquerda para direita.
    public ArrayList<Aresta> getArestasOrdenadas()
    {
        ArrayList<Aresta> arestas = new ArrayList<Aresta>();
        Aresta[] a = this.getTodasArestas();
        if (this.getNumeroTotalArestas() != 0 )
        {
            arestas.addAll(Arrays.asList(a));
            Collections.sort(arestas, new organizarArestas());
        }
        System.out.println("aretas: "+arestas.size());
        return arestas;
    }
    
    //Retornar todos os objetos organizadados de cima para baixo,  esquerda para direita.
    public ArrayList<Componente> getComponentesOrdenados()
    {
        ArrayList<Aresta> arestas = new ArrayList<Aresta>();
        Aresta[] a = this.getTodasArestas();
        if (this.getNumeroTotalArestas() != 0 )
        {
            arestas.addAll(Arrays.asList(a));
            Collections.sort(arestas, new organizarArestas());
        }
        
        ArrayList<Componente> componentes = new ArrayList<Componente>(); 
        for (int i = 0; i < arestas.size(); i++) {
            componentes.add((Componente)arestas.get(i).getVerticeOrigem());
            componentes.add(arestas.get(i));
            componentes.add((Componente)arestas.get(i).getVerticeDestino());
        }
        ArrayList<Vertice> vertices = this.getVerticesOrdenados();
        for (int i = 0; i < vertices.size(); i++) {
            int igual = 0;
            String rotuloV = vertices.get(i).getRotulo().getTexto();
            for (int j = 0; j < componentes.size(); j++) {
                String rotuloC = componentes.get(j).getRotulo().getTexto();
                if(rotuloV.contentEquals(rotuloC))
                {
                    igual = 1;
                    j=componentes.size();
                }
            }
            if(igual == 0)
            {
                componentes.add(vertices.get(i));
            }
        }
        return componentes;
    }
    
    public void addAtor(VerticeAtor ator)
    {
        ListaAtor.add(ator);
    }
    
    public ArrayList<Vertice> getListaAtor()
    {
        if (!(this.ListaAtor.isEmpty()))
        {
            Collections.sort(ListaAtor, new organizarVertices());
        }
        return ListaAtor;
    }

    public void addCasoUso(VerticeCasoUso caso)
    {
        ListaCasoUso.add(caso);
    }
    
    public ArrayList<Vertice> getListaCasoUso()
    {
        if (!(this.ListaCasoUso.isEmpty()))
        {
            Collections.sort(ListaCasoUso, new organizarVertices());
        }
        return ListaCasoUso;
    }
    public void SelecionarComponente(Componente ComponenteSelecionado) {
        
        //Seleciona o componente
        ComponenteSelecionado.selecionarComponente();
        
        Vertice v;
        
        try{
            v = (Vertice) ComponenteSelecionado;
            editor.setAresta(0);
        }catch(java.lang.ClassCastException er)
        {
            v = editor.getVerticeFocado();
            editor.setAresta(1);
        }
        editor.setVerticeFocado(v);
        editor.repaint();
        editor.leitura(ComponenteSelecionado.getDescricao());
    }

    /**
     * @param editor the editor to set
     */
    public void setEditor(EditorAplicativoGrafo editor) {
        this.editor = editor;
    }
}