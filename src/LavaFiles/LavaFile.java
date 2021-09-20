package LavaFiles;

import Types.File_Type;

import java.io.File;

public class LavaFile {
    private final String Name;
    private final File_Type type;
    private File content;

    public LavaFile(String name, File_Type type, String container) {
        this.Name = name;
        this.type = type;
    }

    public static File_Type FileType(String Filetype) {
        File_Type file_type = File_Type.User_Structure_definition;
        switch (Filetype) {
            case "xml":
                file_type = File_Type.Structure_Components;
                break;
            case "BaseLanguage":
                file_type = File_Type.Base_Language;
                break;
            case "UserStructureDefinition":
                break;
            case "Model":
                file_type = File_Type.Model;
                break;
            case "Rule":
                file_type = File_Type.Rule;
            default:
                System.out.println("LavaFile type not found");
                break;
        }
        return file_type;
    }

    public static File_Type getFileTypeFromExtension(String fileName) {
        int lastIndexOfPoint = fileName.lastIndexOf(".") + 1;
        String extension = fileName.substring(lastIndexOfPoint);
        return LavaFile.FileType(LavaFile.getFileType_Name(extension));

    }

    public static String getFileType_Name(String extention) {
        return switch (extention) {
            case "xml" -> "xml";
            case "bsf" -> "BaseLanguage";
            case "usdf" -> "UserStructureDefinition";
            case "mdl" -> "Model";
            case "rl" -> "Rule";
            default -> "";
        };
    }

    public String getName() {
        return this.Name;
    }



    public File_Type getType() {
        return this.type;
    }

    public File getContent() {
        return content;
    }

    public void setContent(File file) {
        this.content = file;
    }
}
