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
    private HashMap<Vertex, Double> vertexNaoVisitados;
    private HashMap<Vertex, Double> vertexBase;
    private HashMap<Vertex, LinkedList<Edge>> vertex; // Q
    private HashMap<Vertex, Vertex> predecessores;

    public Dijkstra(HashMap<Vertex, LinkedList<Edge>> vertices, Vertex verticeInicial) {
        this.vertex = vertices;
        this.verticeInicial = verticeInicial;
        this.vertexNaoVisitados = new HashMap<>();
        this.vertexBase = new HashMap<>();
        this.predecessores = new HashMap<>();
        this.predecessores.put(this.verticeInicial, new Vertex(""));
    }

    public void encontrarMenorCaminho() {
        custoArestas(this.verticeInicial); // - todos as chaves são infinitas (∞) (linha 1)
        this.vertexBase.putAll(this.vertexNaoVisitados);
        ArrayList<Vertex> vertexJaVisitados = new ArrayList<>(); // - S = ∅ (linha 2)
        while (!vertexNaoVisitados.isEmpty()) {
            Vertex vertexAtual = menorDaLista(vertexNaoVisitados); // u ← MIN(Q)
            vertexJaVisitados.add(vertexAtual); // S ← S ∪ {u}
            updateCustoAresta(this.vertex.get(vertexAtual), vertexAtual); // para cada Adj[u]
            vertexNaoVisitados.remove(vertexAtual);
        }
    }

    public void updateCustoAresta(LinkedList<Edge> vertexAdjacentes, Vertex vertexAtual) {
        vertexAdjacentes.forEach(i -> {
            if (this.vertexBase.get(i.getVertex()).equals(Double.MAX_VALUE)){
                this.vertexNaoVisitados.put(i.getVertex(), vertexNaoVisitados.get(vertexAtual)+ i.getValue());
                this.vertexBase.put(i.getVertex(), vertexNaoVisitados.get(vertexAtual)+ i.getValue());
                this.predecessores.put(i.getVertex(), vertexAtual);
                System.out.println("Vertex " + i.getVertex() + " predecessor " + vertexAtual);

            } else {
                if((vertexNaoVisitados.get(vertexAtual) + i.getValue()) < this.vertexBase.get(i.getVertex())){
                    this.vertexNaoVisitados.put(i.getVertex(),(vertexNaoVisitados.get(vertexAtual) + i.getValue()));
                    this.vertexBase.put(i.getVertex(),(vertexNaoVisitados.get(vertexAtual) + i.getValue()));
                    this.predecessores.put(i.getVertex(), vertexAtual);
                    System.out.println(" --- Vertex " + i.getVertex() + " predecessor " + vertexAtual);
                }
            }

        });

    }

    public void custoArestas(Vertex quemInicia) {
        for (Vertex sigla : vertex.keySet()) {
            if (sigla.getName().equals(quemInicia.getName())) {
                this.vertexNaoVisitados.put(sigla, 0.0);
            } else {
                this.vertexNaoVisitados.put(sigla, Double.MAX_VALUE);
            }
        }
    }

    public Vertex menorDaLista(HashMap<Vertex, Double> custoArestas) {
        Double menorNumero = Double.MAX_VALUE;
        Vertex menorVertex = null;

        for (Map.Entry<Vertex, Double> entrada : custoArestas.entrySet()) {
            if (entrada.getValue() < menorNumero) {
                menorNumero = entrada.getValue();
                menorVertex = entrada.getKey();
            }
        }
        return menorVertex;
    }
    
    @Override
    public String toString() {
    	String out = "";
    	List<Map.Entry<Vertex, Double>> verticesStream = vertexBase.entrySet().stream()
    			.collect(Collectors.toList());
    	for (Map.Entry<Vertex, Double> item : verticesStream) {
            out += this.verticeInicial.getName() + " para " + item.getKey().getName() + " o menor custo eh: " + item.getValue();
            out += "\n";
        }
    	return out;
    }
}
