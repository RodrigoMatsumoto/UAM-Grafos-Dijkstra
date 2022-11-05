package model.list;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import exception.VertexNotFoundException;
import model.Graph;
import model.Vertex;

public class ListAdj implements Graph {
    private HashMap<Vertex, LinkedList<Edge>> vertices;
    private boolean isDirected;

    public ListAdj(boolean isDirected) {
        vertices = new HashMap<>();
        this.isDirected = isDirected;
    }

    @Override
    public boolean adjacent(Vertex u, Vertex v) {
        return vertices.get(u).stream().filter(e -> e.getVertex().equals(v)).count() > 0;
    }

    @Override
    public void addVertex(Vertex v) throws VertexNotFoundException {
        if (vertices.get(v) != null) {
            throw new VertexNotFoundException("Vértice " + v.getName() + " não encontrado.");
        }
        vertices.put(v, new LinkedList<>());
    }

    @Override
    public boolean removeVertex(Vertex v) {
        // TODO implement
        return false;
    }

    @Override
    public boolean addEdge(Vertex u, Vertex v, double value) {
        if (vertices.get(u) == null || vertices.get(v) == null) {
            return false;
        }
        vertices.get(u).add(new Edge(v, value));
        if (notDirected()) {
            vertices.get(v).add(new Edge(u, value));
        }
        return true;
    }

    @Override
    public boolean removeEdge(Vertex u, Vertex v) {
        vertices.get(u).forEach(e -> {
            if (e.getVertex().getName().equals(v.getName())) {
                vertices.get(u).remove(e);
                return;
            }
        });
        if (notDirected()) {
            vertices.get(v).forEach(e -> {
                if (e.getVertex().getName().equals(u.getName())) {
                    vertices.get(v).remove(e);
                    return;
                }
            });
        }
        return true;
    }

    @Override
    public boolean isDirected() {
        return isDirected;
    }

    public boolean notDirected() {
        return !isDirected;
    }

    @Override
    public String toString() {
        String out = "";
        List<Map.Entry<Vertex, LinkedList<Edge>>> verticesStream = vertices.entrySet().stream()
                .collect(Collectors.toList());

        for (Map.Entry<Vertex, LinkedList<Edge>> item : verticesStream) {
            out += "from " + item.getKey();
            for (Edge edge : item.getValue()) {
                out += edge;
            }
            out += "\n";
        }

        return out;
    }

}
