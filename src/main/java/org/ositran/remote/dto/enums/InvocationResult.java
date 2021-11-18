/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.remote.dto.enums;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author developeratc
 */
public enum InvocationResult {

    SUCCESS(1),
    FAILED(2);

    int id;

    private static final Map<Integer,InvocationResult> mapInvocationResult=new HashMap<Integer,InvocationResult>();

    static{
        mapInvocationResult.put(new Integer(1), SUCCESS);
        mapInvocationResult.put(new Integer(2), FAILED);
    }

    InvocationResult(int id){
        this.id=id;
    }

    public static InvocationResult getInvocationResultById(Integer id){
        if (id!=null){
            return mapInvocationResult.get(id);
        }
        return null;
    }

}
