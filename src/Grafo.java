import java.util.ArrayList;
import java.util.List;

public class Grafo {

	private double[][] matriz;
	private int[][] proximo;
	private List<Cidade> vertices;

	private int numeroVertices;
	double INF = Double.POSITIVE_INFINITY;

	public Grafo(int numeroVertices) {
		this.numeroVertices = numeroVertices;
		this.vertices = new ArrayList<Cidade>(numeroVertices);
		this.matriz = new double[numeroVertices][numeroVertices];
		this.proximo = new int[numeroVertices][numeroVertices];
		for (int i = 0; i < numeroVertices; i++) {
			for (int j = 0; j < numeroVertices; j++) {
				if (i == j) {
					matriz[i][j] = 0;
				} else {
					matriz[i][j] = INF;
				}
			}
		}
	}

	public int getIndiceVertice(Cidade c) {
		return this.vertices.indexOf(c);
	}

	public void adicionarVertice(Cidade c) {
		this.vertices.add(c);
	}

	public void adicionarAresta(Cidade origem, Cidade destino, double km) {
		int i = vertices.indexOf(origem);
		int j = vertices.indexOf(destino);
		if (i >= 0 && j >= 0) {
			matriz[i][j] = km;
			matriz[j][i] = km;
		}
	}

	public void imprimirMatriz() {
		for (int i = 0; i < numeroVertices; i++) {
			for (int j = 0; j < numeroVertices; j++) {
				System.out.print(matriz[i][j] + " ");
			}
			System.out.println();
		}
	}

	public void floydWarshall() {
		for (int i = 0; i < numeroVertices; i++) {
			for (int j = 0; j < numeroVertices; j++) {
				if (matriz[i][j] != INF) {
					proximo[i][j] = j;
				}
			}
		}

		for (int k = 0; k < numeroVertices; k++) {
			for (int i = 0; i < numeroVertices; i++) {
				for (int j = 0; j < numeroVertices; j++) {
					if (matriz[i][k] + matriz[k][j] < matriz[i][j]) {
						matriz[i][j] = matriz[i][k] + matriz[k][j];
						proximo[i][j] = proximo[i][k];
					}
				}
			}
		}
	}

	public void calcularValorViagem(Cidade origem, Cidade destino) {
		int i = vertices.indexOf(origem);
		int j = vertices.indexOf(destino);
		double valor = 0.0;

		while (i != j) {
			if (i == 0) {
				valor += matriz[i][proximo[i][j]];
			} else {
				valor += matriz[i - 1][i];
			}
			i = proximo[i][j];
		}
		valor = 0.5 * matriz[vertices.indexOf(origem)][vertices.indexOf(destino)];
		System.out.printf("Valor da viagem: R$ %.2f", valor);

	}

	public void imprimirCidades() {
		for (Cidade c : this.vertices)
			System.out.println(getIndiceVertice(c) + " - " + c.getNome());
	}

	public void imprimirMenorCaminho(Cidade origem, Cidade destino) {
		int i = vertices.indexOf(origem);
		int j = vertices.indexOf(destino);
		String caminho = "";

		if (matriz[i][j] == INF) {
			System.out.println("Não há caminho entre " + origem.getNome() + " e " + destino.getNome());
		} else {
			System.out.println("Viagem de " + origem.getNome() + " para " + destino.getNome() + "\n");
			caminho += origem.getNome();
			while (i != j) {
				i = proximo[i][j];
				caminho += " -> " + vertices.get(i).getNome();

			}
			System.out.println("Caminho: " + caminho);
			System.out.printf("Distância: %.2f Km\n", matriz[vertices.indexOf(origem)][vertices.indexOf(destino)]);
			calcularValorViagem(origem, destino);
			System.out.println();

		}
	}

	public double[][] getMatriz() {
		return matriz;
	}

	public void setMatriz(double[][] matriz) {
		this.matriz = matriz;
	}

	public List<Cidade> getVertices() {
		return vertices;
	}

	public void setVertices(List<Cidade> vertices) {
		this.vertices = vertices;
	}

	public int getNumeroVertices() {
		return numeroVertices;
	}

	public void setNumeroVertices(int numeroVertices) {
		this.numeroVertices = numeroVertices;
	}

}
