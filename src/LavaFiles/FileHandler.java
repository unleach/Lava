package LavaFiles;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class FileHandler {
    private LavaFile lavaFile;

    public FileHandler(LavaFile file) {
        this.lavaFile = file;

    }


    public List<String> read() {
        List<String> content = new LinkedList<>();
        File USD_file = this.lavaFile.getContent();
        try {
            Scanner scanner = new Scanner(USD_file);
            while (scanner.hasNext()) {
                content.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        return content;
    }

    public void Rewrite(String[] newContent) {

    }

    public void add(String[] newLines) {

    }
}
