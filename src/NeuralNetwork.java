import java.util.Scanner;

public class NeuralNetwork {
    int numLayers;
    int[] nodeLength;

    Node[][] nodes;
    int maxNodes;

    NeuralNetwork(int numLayers) {
        this.numLayers = numLayers;
        this.nodeLength = new int[numLayers];
        int maxNodes = 0;
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < numLayers; i++) {
            int layer = i + 1;
            System.out.println("Enter the number of nodes in Layer #" + layer + ": ");
            int numNodes = sc.nextInt();
            this.nodeLength[i] = numNodes;
            maxNodes = Math.max(maxNodes, numNodes);
        }
        this.maxNodes = maxNodes;
        this.nodes = new Node[numLayers][maxNodes];

        for (int i = 0; i < numLayers - 1; i++) {
            int layer = i + 1;
            int nextNodeLength = nodeLength[i + 1];
            System.out.println("Enter weights for edges from nodes in Layer #" + layer + " (" + nextNodeLength + ") values:");
            for (int j = 0; j < nodeLength[i]; j++) {
                Node node = new Node(i + 1, j + 1, nextNodeLength);
                this.nodes[i][j] = node;
            }
        }

        for (int i = 0; i < nodeLength[numLayers-1]; i++) {
            this.nodes[numLayers-1][i] = new Node(numLayers, i + 1, 0);
        }
    }

    Node getNode(int layer, int node) throws Exception {
        if (layer > this.numLayers || layer < 1 || node > this.nodeLength[layer - 1] || node < 1) {
            throw new Exception("Invalid node or layer");
        }
        return this.nodes[layer - 1][node - 1];
    }

    int getShortestDistance(int distance, Node current, Node end) {
        int nextLayer = current.layer + 1;

        if (nextLayer == end.layer) {
            int d = current.getDistance(end.nodeIndex);
            return d + distance;
        }

        int minDistance = Integer.MAX_VALUE;

        for (int i = 0; i < this.nodeLength[nextLayer]; i++) {
            Node n = this.nodes[nextLayer][i];
            if (n == null)
                continue;
            int dx = distance + current.getDistance(i);
            int d = getShortestDistance(dx, n, end);
            minDistance = Math.min(minDistance, d);
        }

        return minDistance;
    }

}
