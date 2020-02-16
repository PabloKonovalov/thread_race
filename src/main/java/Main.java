import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Pavel
 */
public class Main {

    private static boolean raceFinished = false;

    public static void main(String[] args) throws IOException {

        String welcomePhrase = "Добро пожаловать в приложение 'Гонка потоков'. Для того, чтобы запустить гонку напшитие Старт";
        System.out.println(welcomePhrase);
        BufferedReader startReader = new BufferedReader(new InputStreamReader(System.in));
        String userStartPhrase = null;
        try {
            userStartPhrase = startReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }


        if ("старт".equalsIgnoreCase(userStartPhrase)) {
            FileSystemManager.getInstance().createFile();

            Thread testThreadA = makeNewThread("Поток А");
            Thread testThreadB = makeNewThread("Поток B");

            startAllThreads(testThreadA, testThreadB);
            stopAllThreads(testThreadA, testThreadB);
        }
    }

    private static Thread makeNewThread(String name) {

        return new Thread(() -> {
            for (int index = 0; index <= 99; index++) {
                try {
                    FileSystemManager.getInstance().writeToFile(String.valueOf(index), name);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            finishRace(Thread.currentThread().getName());
        }, name);
    }

    private static void startAllThreads(Thread... threads) {
        for (Thread thread : threads) {
            thread.start();
        }
    }

    private static void stopAllThreads(Thread... threads) {
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    private static synchronized void finishRace(String name) {

        if (!raceFinished) {
            raceFinished = true;
            try {
                FileSystemManager.getInstance().writeToFile(null, name);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
