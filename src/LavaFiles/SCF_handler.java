package LavaFiles;

import Structure_Creator.Param;
import Structure_Creator.Struct_Components;
import Structure_Creator.Structure;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class SCF_handler {
    private Structure structure;
    private Document dom;
    private DocumentBuilder builder;
    private Node root;

    public SCF_handler(Structure structure) {
        this.structure = structure;
        try {
            this.builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (Exception e) {
            e.printStackTrace();
        }
        buildDomFromComponents();
    }

    public SCF_handler(String FileName) {
        try {
            this.builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            this.dom = this.builder.parse(new File(FileName));
            this.dom.normalize();
            this.root = this.dom.getDocumentElement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void buildDomFromComponents() {
        this.dom = this.builder.newDocument();
        // creating the root
        Element root = this.dom.createElement("Struct");
        root.setAttribute("Name", this.structure.getName());
        this.dom.appendChild(root);
        // setting the components
        Element Struct_Components = this.dom.createElement("Struct_Components");
        // setting HeadID
        Element HeadID = dom.createElement("Head_id");
        HeadID.setAttribute("value", this.structure.getComponents().getHeadId());
        Struct_Components.appendChild(HeadID);
        // setting the params
        Element Params = dom.createElement("Params");
        Params.setAttribute("count", Integer.toString(this.structure.getComponents().getParamList().size()));
        int paramIndex = 0;
        for (Param para : this.structure.getComponents().getParamList()) {
            Element parameter = dom.createElement("Param");
            parameter.setAttribute("Name", para.getParamName());
            parameter.setAttribute("Type", para.getParam_Type_Name());
            Params.appendChild(parameter);
            paramIndex++;
        }
        Struct_Components.appendChild(Params);
        Element separator = dom.createElement("Separator");
        separator.setAttribute("value", this.structure.getComponents().getSeparator());
        Struct_Components.appendChild(separator);

        //setting the parameter border
        Element paramBorder = dom.createElement("ParamBorder");
        paramBorder.setAttribute("Opener", this.structure.getComponents().getParamBorder()[0]);
        paramBorder.setAttribute("Closer", this.structure.getComponents().getParamBorder()[1]);
        Struct_Components.appendChild(paramBorder);
        // setting the body border
        Element bodyBorder = dom.createElement("BodyBorder");
        bodyBorder.setAttribute("Opener", this.structure.getComponents().getBodyBorder()[0]);
        bodyBorder.setAttribute("Closer", this.structure.getComponents().getBodyBorder()[1]);
        Struct_Components.appendChild(bodyBorder);
        root.appendChild(Struct_Components);
        // setting the Model
        Element Model = dom.createElement("Model");
        for (Model model : this.structure.getModels()) {
            Element modelElement = dom.createElement("model");
            modelElement.setAttribute("id", model.ModelID);
            modelElement.setAttribute("Link", model.ModelLink);
            Model.appendChild(modelElement);
        }
        root.appendChild(Model);
        // setting the base language links
        Element base_Language = dom.createElement("base_Language");
        for (Base_Language base_language : this.structure.getBaseLanguageFiles()) {
            Element file = dom.createElement("BSF");
            file.setAttribute("language", base_language.LanguageName);
            file.setAttribute("link", base_language.fileLink);
            base_Language.appendChild(file);
        }
        root.appendChild(base_Language);
    }

    public void parse() {
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(this.dom), new StreamResult(new File(this.structure.getName() + ".xml")));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Structure getStructure() {
        this.getHeadID();
        Structure structure = new Structure(this.getHeadID());
        Struct_Components struct_components = new Struct_Components(this.getHeadID());
        struct_components.setParamList(this.getParams());
        struct_components.setParamBorder(this.getParamBorder());
        struct_components.setBodyBorder(this.getBodyBorder());
        struct_components.setSeparator(this.getSeparator());
        structure.setComponents(struct_components);
        structure.setBaseLanguageLinks(this.getBaseLanguage());
        structure.setModels(this.getModel());
        return structure;
    }

    private String getHeadID() {
        String headID = "";
        // searching for the Head ID element in the root Node
        NodeList List = this.root.getChildNodes();
        Node Target = root;
        // searching the Struct_Components node
        for (int i = 0; i < List.getLength(); i++) {
            Node curNode = List.item(i);
            if (Objects.equals(curNode.getNodeName(), "Struct_Components")) {
                Target = curNode;
                break;
            }
        }
        // searching the HeadId node in the Struct_Components node
        NodeList targetList = Target.getChildNodes();
        for (int y = 0; y < targetList.getLength(); y++) {
            Node HNode = targetList.item(y);
            if (HNode.getNodeName().equalsIgnoreCase("Head_id")) {
                headID = HNode.getAttributes().getNamedItem("value").getNodeValue();
            }
        }
        return headID;
    }

    private List<Param> getParams() {
        NodeList List = this.root.getChildNodes();
        Node Ta = this.root;
        // searching the Struct_Components node
        for (int i = 0; i < List.getLength(); i++) {
            Node curNode = List.item(i);
            if (Objects.equals(curNode.getNodeName(), "Struct_Components")) {
                Ta = curNode;
                break;
            }
        }
        // locating the params Element
        NodeList list = Ta.getChildNodes();
        Node Target = Ta;
        for (int i = 0; i < list.getLength(); i++) {
            Node curNode = list.item(i);
            if (curNode.getNodeName().equalsIgnoreCase("Params")) {
                Target = curNode;
                break;
            }
        }
        List<Param> ParamsList = new LinkedList<>();
        // getting the Number of params
        int ParamsCount = Integer.parseInt(Target.getAttributes().getNamedItem("count").getNodeValue());
        NodeList paraList = Target.getChildNodes();
        for (int l = 0; l < paraList.getLength(); l++) {
            Node curNode = paraList.item(l);
            if (curNode.getNodeName().equalsIgnoreCase("Param")) {
                String Name = curNode.getAttributes().getNamedItem("Name").getNodeValue();
                String Type = curNode.getAttributes().getNamedItem("Type").getNodeValue();
                Param param = new Param(Name, Type);
                ParamsList.add(param);
            }
        }
        return ParamsList;
    }

    private String[] getBodyBorder() {
        String[] border = new String[2];
        // locating the params Element
        NodeList list = this.root.getChildNodes();
        Node Target = this.root;
        for (int i = 0; i < list.getLength(); i++) {
            Node curNode = list.item(i);
            if (curNode.getNodeName().equalsIgnoreCase("Struct_Components")) {
                Target = curNode;
                break;
            }
        }
        // locating the Body border element
        NodeList list1 = Target.getChildNodes();
        for (int k = 0; k < list1.getLength(); k++) {
            Node curNode = list1.item(k);
            if (curNode.getNodeName().equalsIgnoreCase("BodyBorder")) {
                border[0] = curNode.getAttributes().getNamedItem("Opener").getNodeValue();
                border[1] = curNode.getAttributes().getNamedItem("Closer").getNodeValue();

            }
        }
        return border;
    }

    private String getSeparator() {
        String separator = "";
        // locating the params Element
        NodeList list = this.root.getChildNodes();
        Node Target = this.root;
        for (int i = 0; i < list.getLength(); i++) {
            Node curNode = list.item(i);
            if (curNode.getNodeName().equalsIgnoreCase("Struct_Components")) {
                Target = curNode;
                break;
            }
        }
        NodeList tList = Target.getChildNodes();
        for (int u = 0; u < tList.getLength(); u++) {
            Node cuNode = tList.item(u);
            if (cuNode.getNodeName().equalsIgnoreCase("Separator")) {
                separator = cuNode.getAttributes().getNamedItem("value").getNodeValue();
            }
        }
        return separator;
    }

    private String[] getParamBorder() {
        String[] border = new String[2];
        // locating the params Element
        NodeList list = this.root.getChildNodes();
        Node Target = this.root;
        for (int i = 0; i < list.getLength(); i++) {
            Node curNode = list.item(i);
            if (curNode.getNodeName().equalsIgnoreCase("Struct_Components")) {
                Target = curNode;
                break;
            }
        }
        // locating the Body border element
        NodeList list1 = Target.getChildNodes();
        for (int k = 0; k < list1.getLength(); k++) {
            Node curNode = list1.item(k);
            if (curNode.getNodeName().equalsIgnoreCase("ParamBorder")) {
                border[0] = curNode.getAttributes().getNamedItem("Opener").getNodeValue();
                border[1] = curNode.getAttributes().getNamedItem("Closer").getNodeValue();

            }
        }
        return border;

    }

    private List<Base_Language> getBaseLanguage() {
        List<Base_Language> baseLanguageList = new LinkedList<>();
        // locating the base language node
        NodeList list = this.root.getChildNodes();
        Node Target = this.root;
        for (int i = 0; i < list.getLength(); i++) {
            Node curNode = list.item(i);
            if (curNode.getNodeName().equalsIgnoreCase("base_Language")) {
                Target = curNode;
                break;
            }
        }
        NodeList list1 = Target.getChildNodes();
        for (int j = 0; j < list1.getLength(); j++) {
            Node node = list1.item(j);
            if (node.getNodeName().equalsIgnoreCase("BSF")) {
                String link = node.getAttributes().getNamedItem("link").getNodeValue();
                String Language = node.getAttributes().getNamedItem("language").getNodeValue();
                Base_Language Bl = new Base_Language(Language, link);
                baseLanguageList.add(Bl);
            }
        }

        return baseLanguageList;
    }

    private List<Model> getModel() {
        List<Model> modelList = new LinkedList<>();
        // locating the base language node
        NodeList list = this.root.getChildNodes();
        Node Target = this.root;
        for (int i = 0; i < list.getLength(); i++) {
            Node curNode = list.item(i);
            if (curNode.getNodeName().equalsIgnoreCase("Model")) {
                Target = curNode;
                break;
            }
        }
        NodeList list1 = Target.getChildNodes();
        for (int j = 0; j < list1.getLength(); j++) {
            Node node = list1.item(j);
            if (node.getNodeName().equalsIgnoreCase("model")) {
                String id = node.getAttributes().getNamedItem("id").getNodeValue();
                String link = node.getAttributes().getNamedItem("Link").getNodeValue();
                Model model = new Model(id, link);
                modelList.add(model);
            }
        }
        return modelList;
    }


}

