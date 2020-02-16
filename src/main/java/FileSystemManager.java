import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

/**
 * Класс, отвечающий за работу с файловой системой
 *
 * @author Pavel
 */
public class FileSystemManager {

    /**
     * Имя папки
     */
    private static String FOLDER_NAME = "data";

    /**
     * Имя файла
     */
    private static String FILE_NAME = "text.txt";

    private static FileWriter fileWriter;

    private static final FileSystemManager inst = new FileSystemManager();

    private FileSystemManager() {
        super();
    }

    public synchronized void writeToFile(String value, String name) throws IOException {
        fileWriter = new FileWriter(FOLDER_NAME + "/" + FILE_NAME, true);

        if (value == null) {
            fileWriter.append(name).append(" Выиграл\n");

            fileWriter.flush();
            fileWriter.close();

            Set<Thread> setOfThread = Thread.getAllStackTraces().keySet();

            for (Thread thread : setOfThread) {
                if (thread.getName().equals("Поток А") || thread.getName().equals("Поток B")) {
                    thread.interrupt();
                }
            }

            System.exit(0);
        } else {
            String asd = name + " : " + value + "\n";
            fileWriter.write(asd);
        }

        fileWriter.flush();
        //fileWriter.append(name).append(" : ").append(value).append("\n");
    }

    public void createFile() throws IOException {

        boolean folderFlag = true;

        File folder = new File(FOLDER_NAME);
        File file = null;

        if (!folder.exists()) {
            folderFlag = folder.mkdir();
        } else {
            FileUtils.cleanDirectory(folder);
        }

        if (folderFlag) {
            file = new File(FOLDER_NAME + "/" + FILE_NAME);
            if (!file.createNewFile()) {
                throw new IOException("");
            }
        }
    }

    public static FileSystemManager getInstance() {
        return inst;
    }

    public static FileWriter getFileWriter() {
        return fileWriter;
    }

    public static void closeFileWriter() {
        try {
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
