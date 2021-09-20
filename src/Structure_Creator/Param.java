package Structure_Creator;

import Types.Param_Type;
import Types.Primitive_Type;
import Types.Set_Type;

import java.util.Locale;

public class Param {
    protected String paramName;
    protected Param_Type param_type;
    protected String Param_Type_Name;

    public Param(String ParamName, String paramType) {
        this.paramName = ParamName;
        this.checkParamType(paramType);

    }
    public String getParamName(){
        return this.paramName;
    }

    public String getParam_Type_Name() {
        return Param_Type_Name;
    }

    private void checkParamType(String paramType) {
        if (this.IsPrimitive(paramType)) {
            this.param_type = Param_Type.primitive;
        } else if (this.IsSet(paramType)) {
            this.param_type = Param_Type.Set;
        } else {
            this.param_type = Param_Type.Custom;
        }

    }

    private boolean IsPrimitive(String paramType) {
        boolean isPrimitive = false;
        switch (paramType.toUpperCase(Locale.ROOT)) {
            case "INT", "BOOLEAN", "DOUBLE", "FLOAT", "CHAR", "STRING" -> {
                this.Param_Type_Name = Primitive_Type.String.name();
                isPrimitive = true;
            }
        }
        return isPrimitive;

    }

    private boolean IsSet(String paramType) {
        boolean isSet = false;
        switch (paramType.toUpperCase(Locale.ROOT)) {
            case "ARRAY", "SET", "LIST", "LINKED_LIST" -> {
                this.Param_Type_Name = Set_Type.Linked_List.name();
                isSet = true;
            }
        }
        return isSet;
    }

}
