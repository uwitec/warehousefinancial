package com.wfms.common.attribute;

import java.util.List;
import java.util.Set;

public interface Tree {
	
    public Object getId();  
    public String getText();  
    public String getQtip();  
    public String getIcon();
    public String getHref();
    //public boolean isTarget();
    //public String getHrefTaget();
    public boolean isChecked();
    
    public Tree getParent();  
    public Set  getParents();  
    public List getAllChildNodes();  
    public List getChildNodes();  
    public List getAllLeaves();  
    
    public boolean isRoot();  
    public boolean isLeaf();  
    public boolean isParentOf(Tree tree);  
    public boolean isChildOf(Tree tree);  
    
    public void addChild(Tree tree);  
    public void rmChild(Tree tree);  
    
    public boolean isFinal();
    public void addObject(Object obj);  
    public void rmObject(Object obj);  
    public List getObjects();  
    
    public void setCheckBox(boolean checkBox);
    public boolean isCheckBox();
    public boolean isSingleClickExpand();
}  
