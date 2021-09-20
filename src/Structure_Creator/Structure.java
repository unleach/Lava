package Structure_Creator;

import LavaFiles.Base_Language;
import LavaFiles.Model;

import java.util.LinkedList;
import java.util.List;

public class Structure {
    protected String Name;
    protected Struct_Components components;
    protected List<Model> models = new LinkedList<>();
    protected List<Base_Language> BaseLanguageFiles = new LinkedList<>();

    public Structure(String Structure_name) {
        this.Name = Structure_name;
        this.components = new Struct_Components("test");
    }

    public String getName() {
        return this.Name;
    }

    public Struct_Components getComponents() {
        return this.components;
    }

    public void setComponents(Struct_Components components) {
        this.components = components;


    }

    public List<Model> getModels() {
        return this.models;
    }

    public void setModels(List<Model> models) {
        this.models = models;

    }

    public List<Base_Language> getBaseLanguageFiles() {
        return this.BaseLanguageFiles;
    }

    public void setBaseLanguageLinks(List<Base_Language> baseLanguageList) {
        this.BaseLanguageFiles = baseLanguageList;


    }
}
