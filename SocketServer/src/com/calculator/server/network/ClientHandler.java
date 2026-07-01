class ClientHandler implements Runnable {

    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
            );
            PrintWriter out = new PrintWriter(
                socket.getOutputStream(), true
            );
        ) {
            String expression;

            while ((expression = in.readLine()) != null) {
                try {
                    double result = ExpressionEvaluator.evaluate(expression);
                    out.println(result);
                } catch (Exception e) {
                    out.println("ERROR");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
