package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import model.list.Edge;

public class Dijkstra {

	private Vertex verticeInicial;
    private HashMap<Vertex, Double> verticesNaoVisitados;
    private HashMap<Vertex, Double> verticesBase;
    private HashMap<Vertex, LinkedList<Edge>> vertice; // Q
    private HashMap<Vertex, Vertex> predecessores;

    public Dijkstra(HashMap<Vertex, LinkedList<Edge>> vertices, Vertex verticeInicial) {
        this.vertice = vertices;
        this.verticeInicial = verticeInicial;
        this.verticesNaoVisitados = new HashMap<>();
        this.verticesBase = new HashMap<>();
        this.predecessores = new HashMap<>();
        this.predecessores.put(this.verticeInicial, new Vertex(""));
    }

    public void encontrarMenorCaminho() {
        inicializarCustoDasArestas(this.verticeInicial); // - todos as chaves são infinitas (∞) (linha 1)
        this.verticesBase.putAll(this.verticesNaoVisitados);
        ArrayList<Vertex> verticesJaVisitados = new ArrayList<>(); // - S = ∅ (linha 2)
        while (!verticesNaoVisitados.isEmpty()) {
            Vertex verticeAtual = pegarVerticeComArestaDeMenorCusto(verticesNaoVisitados); // u ← MIN(Q)
            verticesJaVisitados.add(verticeAtual); // S ← S ∪ {u}
            atualizarCustoDasArestas(this.vertice.get(verticeAtual), verticeAtual); // para cada Adj[u]
            verticesNaoVisitados.remove(verticeAtual);
        }
    }

    public void inicializarCustoDasArestas(Vertex verticeInicial) {
        for (Vertex vertice : vertice.keySet()) {
            if (vertice.getName().equals(verticeInicial.getName())) {
                this.verticesNaoVisitados.put(vertice, 0.0);
            } else {
                this.verticesNaoVisitados.put(vertice, Double.MAX_VALUE);
            }
        }
    }

    public Vertex pegarVerticeComArestaDeMenorCusto(HashMap<Vertex, Double> verticesNaoVisitados) {
        Double menorCusto = Double.MAX_VALUE;
        Vertex verticeComMenorCusto = null;

        for (Map.Entry<Vertex, Double> vertice : verticesNaoVisitados.entrySet()) {
            if (vertice.getValue() < menorCusto) {
                menorCusto = vertice.getValue();
                verticeComMenorCusto = vertice.getKey();
            }
        }
        return verticeComMenorCusto;
    }
    public void atualizarCustoDasArestas(LinkedList<Edge> verticesAdjacentes, Vertex verticeAtual) {
        verticesAdjacentes.forEach(i -> {
            if (this.verticesBase.get(i.getVertex()).equals(Double.MAX_VALUE)){
                this.verticesNaoVisitados.put(i.getVertex(), verticesNaoVisitados.get(verticeAtual)+ i.getValue());
                this.verticesBase.put(i.getVertex(), verticesNaoVisitados.get(verticeAtual)+ i.getValue());
                this.predecessores.put(i.getVertex(), verticeAtual);

            } else {
                if((verticesNaoVisitados.get(verticeAtual) + i.getValue()) < this.verticesBase.get(i.getVertex())){
                    this.verticesNaoVisitados.put(i.getVertex(),(verticesNaoVisitados.get(verticeAtual) + i.getValue()));
                    this.verticesBase.put(i.getVertex(),(verticesNaoVisitados.get(verticeAtual) + i.getValue()));
                    this.predecessores.put(i.getVertex(), verticeAtual);
                }
            }
        });
    }

    public String imprimirPredecessores(HashMap<Vertex, Vertex> predecessores, Vertex vertice) {

    	ArrayList<Vertex> caminho = new ArrayList<Vertex>();
    	
    	do {
    		caminho.add(0, vertice);
    		vertice = predecessores.get(vertice);
    	}
    	while(vertice.getName() != "");
    	
    	return caminho.toString();
    }
    
    @Override
    public String toString() {
    	String out = "";
    	List<Map.Entry<Vertex, Double>> verticesStream = verticesBase.entrySet().stream()
    			.collect(Collectors.toList());
    	for (Map.Entry<Vertex, Double> item : verticesStream) {
            out += this.verticeInicial.getName() + " para " + item.getKey().getName() + " o menor custo eh: " + item.getValue() + " caminho " + imprimirPredecessores(this.predecessores, item.getKey());
            out += "\n";   
        }
    	return out;
    }
}
