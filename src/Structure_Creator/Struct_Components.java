package Structure_Creator;

import java.util.LinkedList;
import java.util.List;

public class Struct_Components {
    private String HeadId;
    private List<Param> paramList = new LinkedList<>();
    private String separator;
    private String[] ParamBorder;
    private String[] BodyBorder;

    public Struct_Components(String HeadID) {
        this.HeadId = HeadID;
    }


    public void addParam(Param parameter) {
        this.paramList.add(parameter);
    }
    public void setParamList(List<Param> paramList){
        this.paramList =paramList;
    }

    public String getHeadId() {
        return this.HeadId;
    }

    public void setHeadId(String test) {
        this.HeadId = test;
    }

    public List<Param> getParamList() {
        return this.paramList;
    }

    public String getSeparator() {
        return this.separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public String[] getBodyBorder() {
        return BodyBorder;
    }

    public String[] getParamBorder() {
        return ParamBorder;
    }

    public void setBodyBorder(String[] bodyBorder) {
        BodyBorder = bodyBorder;
    }

    public void setParamBorder(String[] paramBorder) {
        ParamBorder = paramBorder;
    }
}
