package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import exception.VertexNotFoundException;
import model.Vertex;
import model.list.ListAdj;
import model.matriz.GraphMatriz;

public class LoadData {
    public static GraphMatriz loadAdj(String fileName) {
        boolean directed = false;
        int amountVertices;

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader buffer = new BufferedReader(fileReader);

            String line = buffer.readLine();
            if (line.equals("S") || line.equals("s"))
                directed = true;

            line = buffer.readLine();
            amountVertices = Integer.parseInt(line);

            GraphMatriz graph = new GraphMatriz(amountVertices, directed);

            for (int i = 0; i < amountVertices; i++) {
                graph.addVertex(new Vertex(buffer.readLine()));
            }

            while (buffer.ready()) {
                line = buffer.readLine();
                String[] partes = line.split(",");
                graph.addEdge(new Vertex(partes[0]), new Vertex(partes[1]), Double.parseDouble(partes[2]));
            }

            buffer.close();
            fileReader.close();

            return graph;

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return null;
        } catch (VertexNotFoundException ex) {
            System.err.println("Erro: Tentativa de adicionar uma aresta com vértice que não existe");
            System.err.println(ex.getMessage());
            return null;
        }
    }

    public static ListAdj loadList(String fileName) {
        boolean directed = false;
        int amountVertices;

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader buffer = new BufferedReader(fileReader);

            String line = buffer.readLine();

            if (line.equals("S") || line.equals("s"))
                directed = true;

            line = buffer.readLine();
            amountVertices = Integer.parseInt(line);

            ListAdj graph = new ListAdj(directed);

            for (int i = 0; i < amountVertices; i++) {
                graph.addVertex(new Vertex(buffer.readLine()));
            }

            while (buffer.ready()) {
                line = buffer.readLine();
                String[] partes = line.split(",");
                graph.addEdge(new Vertex(partes[0]), new Vertex(partes[1]), Double.parseDouble(partes[2]));
            }

            buffer.close();
            fileReader.close();

            return graph;

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return null;
        } catch (VertexNotFoundException ex) {
            System.err.println("Erro: Tentativa de adicionar uma aresta com vértice que não existe");
            System.err.println(ex.getMessage());
            return null;
        }
    }

}
