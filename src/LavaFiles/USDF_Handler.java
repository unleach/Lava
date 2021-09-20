package LavaFiles;

import Structure_Creator.Structure;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class USDF_Handler {
    private final List<String> fileContent;
    public List<Structure> structureList = new LinkedList<>();

    public USDF_Handler(String TargetPath) {
        File file = new File(TargetPath);
        LavaFile USDF = new LavaFile(file.getName(),
                LavaFile.getFileTypeFromExtension(LavaFile.getFileType_Name(file.getName())), file.getParent());
        USDF.setContent(file);
        this.setFileContent();
        FileHandler handler = new FileHandler(USDF);
        this.fileContent = handler.read();
        int x = 0;
        for (String line : this.fileContent) {
            System.out.println("line " + x + " :" + line);
            x++;
        }

    }

    public void test() {
        this.getStructureHeadIDs();
        this.LoadStructureFromDefaultStructureDirectory();
        this.checkSyntax();
    }

    private List<String> getStructureHeadIDs() {
        List<String> headIds = new LinkedList<>();
        for (String line : this.getFileContent()) {
            line = line.strip();
            if (line.contains(" ")) {
                String headId = line.substring(0, line.indexOf(" "));
                System.out.println(headId);
                headIds.add(headId);
            }

        }
        return headIds;
    }

    private void LoadStructureFromDefaultStructureDirectory() {
        for (String HeadID : this.getStructureHeadIDs()) {
            System.out.println(HeadID);
            FolderHandler folderHandler = new FolderHandler(HeadID, "default");
            List<LavaFile> lavaFiles = folderHandler.DirectoryContent;
            System.out.println("default_Structure\\" + HeadID + "\\" + lavaFiles.get(0).getName());
            SCF_handler scfHandler = new SCF_handler("default_Structure\\" + HeadID + "\\" + lavaFiles.get(0).getName());
            Structure structure = scfHandler.getStructure();
            System.out.println("Struct Name : " + structure.getName());
            this.structureList.add(structure);
        }

    }

    private void checkSyntax() {


//        List<String> CheckerResult = checker.getCheckerResult();

    }


    private void setFileContent() {


    }


    public List<String> getFileContent() {
        return fileContent;
    }
}
