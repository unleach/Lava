package LavaFiles;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class FolderHandler {
    protected String DirectoryName;
    protected String DirectoryType;
    protected List<LavaFile> DirectoryContent = new LinkedList<>();
    protected Path DirectoryPath;

    /*
    the type will contains 3 values for now
    normal : normal directory
    structure directory :
    default structure directory:
     */
    public FolderHandler(String DirectoryName, String DirectoryType) {
        this.DirectoryName = DirectoryName;
        this.DirectoryType = DirectoryType;
        try {
            // # over paths will be added to handle the Directory that not stored on the Lava project folder
            String defaultLavaPath = "C:\\Users\\mohamed\\OneDrive\\Bureau\\Lava version 1.0.2";
            this.DirectoryPath = FileSystems.getDefault().getPath(defaultLavaPath + "\\default_Structure\\" + DirectoryName);

        } catch (FileSystemNotFoundException fileSystemNotFoundException) {
            System.out.println(fileSystemNotFoundException.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
        }
        this.FetchDirectory();

    }

    private void FetchDirectory() {

        String path = String.valueOf(this.DirectoryPath);
        try {
            Files.list(new File(path).toPath()).limit(10).forEach(path1 -> {
                System.out.println(path1);
                File file = new File(String.valueOf(path1));
                LavaFile lavaFile = new LavaFile(file.getName(), LavaFile.FileType(file.getName()), file.getParent());
                lavaFile.setContent(file);
                this.DirectoryContent.add(lavaFile);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
//
    }


}
