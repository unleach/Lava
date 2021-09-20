package Syntax;


import Structure_Creator.Structure;

import java.util.List;

public class Checker {
    List<Structure> structureUsedInCode;
    List<String> code;

    public Checker(List<String> code, List<Structure> structureUsedInCode) {
        this.structureUsedInCode = structureUsedInCode;
        this.code = code;

    }
}

// this class we be used to facilitate the indexing of the element of the structure for facilitate the checking process
