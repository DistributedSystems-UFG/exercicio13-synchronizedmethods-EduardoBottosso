public class CounterTest {

    static class CounterSemSincronizacao {
        private int c = 0;

        public void increment() {
            c++;
        }

        public void decrement() {
            c--;
        }

        public int value() {
            return c;
        }
    }

    static class CounterComSincronizacao {
        private int c = 0;

        public synchronized void increment() {
            c++;
        }

        public synchronized void decrement() {
            c--;
        }

        public synchronized int value() {
            return c;
        }
    }

    static class IncrementadorSemSync implements Runnable {
        private CounterSemSincronizacao counter;
        private int repeticoes;

        public IncrementadorSemSync(CounterSemSincronizacao counter, int repeticoes) {
            this.counter = counter;
            this.repeticoes = repeticoes;
        }

        public void run() {
            for (int i = 0; i < repeticoes; i++) {
                counter.increment();
            }
        }
    }

    static class DecrementadorSemSync implements Runnable {
        private CounterSemSincronizacao counter;
        private int repeticoes;

        public DecrementadorSemSync(CounterSemSincronizacao counter, int repeticoes) {
            this.counter = counter;
            this.repeticoes = repeticoes;
        }

        public void run() {
            for (int i = 0; i < repeticoes; i++) {
                counter.decrement();
            }
        }
    }

    static class IncrementadorComSync implements Runnable {
        private CounterComSincronizacao counter;
        private int repeticoes;

        public IncrementadorComSync(CounterComSincronizacao counter, int repeticoes) {
            this.counter = counter;
            this.repeticoes = repeticoes;
        }

        public void run() {
            for (int i = 0; i < repeticoes; i++) {
                counter.increment();
            }
        }
    }

    static class DecrementadorComSync implements Runnable {
        private CounterComSincronizacao counter;
        private int repeticoes;

        public DecrementadorComSync(CounterComSincronizacao counter, int repeticoes) {
            this.counter = counter;
            this.repeticoes = repeticoes;
        }

        public void run() {
            for (int i = 0; i < repeticoes; i++) {
                counter.decrement();
            }
        }
    }

    public static void testarSemSincronizacao() throws InterruptedException {
        CounterSemSincronizacao counter = new CounterSemSincronizacao();

        int repeticoes = 1_000_000;

        Thread t1 = new Thread(new IncrementadorSemSync(counter, repeticoes), "Incrementador");
        Thread t2 = new Thread(new DecrementadorSemSync(counter, repeticoes), "Decrementador");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Resultado sem sincronização: " + counter.value());
    }

    public static void testarComSincronizacao() throws InterruptedException {
        CounterComSincronizacao counter = new CounterComSincronizacao();

        int repeticoes = 1_000_000;

        Thread t1 = new Thread(new IncrementadorComSync(counter, repeticoes), "Incrementador");
        Thread t2 = new Thread(new DecrementadorComSync(counter, repeticoes), "Decrementador");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Resultado com sincronização: " + counter.value());
    }

    public static void main(String[] args) throws InterruptedException {
        testarSemSincronizacao();
        testarComSincronizacao();
    }
}
