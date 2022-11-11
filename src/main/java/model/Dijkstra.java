package model;

import model.list.Edge;

import javax.swing.text.html.parser.Parser;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Dijkstra {

    private HashMap<Vertex, Double> vertexNaoVisitados;
    private HashMap<Vertex, Double> vertexBase;
    private HashMap<Vertex, LinkedList<Edge>> vertex; // Q

    public Dijkstra(HashMap<Vertex, LinkedList<Edge>> vertices) {
        this.vertex = vertices;
        this.vertexNaoVisitados = new HashMap<>();
        this.vertexBase = new HashMap<>();
    }

    public void encontrarMenorCaminho(Vertex quemInicia) {
        custoArestas(quemInicia); // - todos as chaves são infinitas (∞) (linha 1)
        this.vertexBase.putAll(this.vertexNaoVisitados);
        ArrayList<Vertex> vertexJaVisitados = new ArrayList<>(); // - S = ∅ (linha 2)
        while (!vertexNaoVisitados.isEmpty()) {
            Vertex vertexAtual = menorDaLista(vertexNaoVisitados); // u ← MIN(Q)
            vertexJaVisitados.add(vertexAtual); // S ← S ∪ {u}
            updateCustoAresta(this.vertex.get(vertexAtual), vertexAtual); // para cada Adj[u]
            vertexNaoVisitados.remove(vertexAtual);
            System.out.println("Eliminando os vertex" + vertexNaoVisitados.toString());
        }

        System.out.println("=========================================");
        System.out.println(vertexBase.toString());
        System.out.println("=========================================");
    }

    public void updateCustoAresta(LinkedList<Edge> vertexAdjacentes, Vertex vertexAtual) {
        System.out.println(vertexAdjacentes.toString());
        vertexAdjacentes.forEach(i -> {
            if (this.vertexBase.get(i.getVertex()).equals(Double.MAX_VALUE)){
                this.vertexNaoVisitados.put(i.getVertex(), vertexNaoVisitados.get(vertexAtual)+ i.getValue());
                this.vertexBase.put(i.getVertex(), vertexNaoVisitados.get(vertexAtual)+ i.getValue());

            } else {
                if((vertexNaoVisitados.get(vertexAtual) + i.getValue()) < this.vertexBase.get(i.getVertex())){
                    this.vertexNaoVisitados.put(i.getVertex(),(vertexNaoVisitados.get(vertexAtual) + i.getValue()));
                    this.vertexBase.put(i.getVertex(),(vertexNaoVisitados.get(vertexAtual) + i.getValue()));
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

        System.out.println(vertexNaoVisitados.toString());
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

}
