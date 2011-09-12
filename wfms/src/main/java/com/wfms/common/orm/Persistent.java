package com.wfms.common.orm;

import java.io.Serializable;

public abstract class Persistent implements Serializable {

	public abstract String getId();
    public boolean equals (Object x){
        if (x == null) return false;
        
        return ((Persistent) x).getId() == getId();
    }
}
