package kernel;

import py4j.GatewayServer;

public class Main {
    public static void main(String[] args) {

        Main app = new Main();
        // py4j服务
        GatewayServer gatewayServer = new GatewayServer(app);
        gatewayServer.start();
        System.out.println("Gateway Server Started");
    }

    public int addInt(int a, int b) {
        return a + b;
    }
}
