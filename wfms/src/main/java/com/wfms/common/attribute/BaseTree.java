package com.wfms.common.attribute;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;


public abstract class BaseTree implements Tree, Serializable {
	private String icon;
	private String href;

	private boolean checkBox = false;
	private boolean singleClickExpand = true;
	private boolean checked = false;

	protected Object id;
	protected String text;
	protected String qtip;
	protected BaseTree parent;
	protected List childNodes = new ArrayList();
	protected Set objects = new HashSet();

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public boolean isSingleClickExpand() {
		return singleClickExpand;
	}

	public void setSingleClickExpand(boolean singleClickExpand) {
		this.singleClickExpand = singleClickExpand;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public void setId(Object id) {
		this.id = id;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setQtip(String qtip) {
		this.qtip = qtip;
	}

	public void setParent(BaseTree parent) {
		this.parent = parent;
	}

	public void setChildNodes(List childNodes) {
		this.childNodes = childNodes;
	}

	public void setObjects(Set objects) {
		this.objects = objects;
	}

	abstract public String getText();

	abstract public String getQtip();

	abstract public Tree getParent();

	abstract public Object getId();

	abstract public List getChildNodes();

	abstract public List getObjects();

	public boolean isCheckBox() {
		return this.checkBox;
	}

	public void setCheckBox(boolean checkBox) {
		this.checkBox = checkBox;
	}

	public boolean isRoot() {
		return (getParent() == null);
	}

	public boolean isLeaf() {
		return (this.getChildNodes() == null || this.getChildNodes().size() == 0);
	}

	public boolean isFinal() {
		return (this.getObjects() == null || this.getObjects().size() == 0);
	}

	public boolean isParentOf(Tree tree) {
		if (tree == null || ((BaseTree) tree).equals(this)) {
			/* 如果对方为空 */
			return false;
		} else if (this.isLeaf()) {
			/* 如果自己为叶子,则返回FALSE */
			return false;
		} else if (tree.isRoot()) {
			/* 如果对方为根,返回FALSE */
			return false;
		} else {
			BaseTree bt = (BaseTree) (tree.getParent());
			if (this.equals(bt)) {
				/* 如果对方的父节点是自己,则返回TRUE */
				return true;
			} else {
				/* 判断对方的父节点是否是自己的孩子,进行递归 */
				return isParentOf(bt);
			}
		}
	}

	public boolean isChildOf(Tree tree) {
		return (tree.isParentOf(this));
	}

	public void addChild(Tree tree) {
		childNodes.add(tree);
	}

	public void rmChild(Tree tree) {
		childNodes.remove(tree);
		((BaseTree) tree).setParent(null);
	}

	public List getAllLeaves() {
		List list_old = this.getAllChildNodes();
		List list = new ArrayList();
		list.addAll(list_old);
		Iterator itr = list_old.iterator();
		while (itr.hasNext()) {
			BaseTree bt = (BaseTree) itr.next();
			if (!bt.isLeaf()) {
				list.remove(bt);
			}
		}
		return list;
	}

	public Set getParents() {
		Set parents = new HashSet();
		Tree p = this.getParent();
		if (p != null) {
			parents.add(p);
			parents.addAll(p.getParents());
		}
		return parents;
	}

	public List getAllChildNodes() {
		List list = new ArrayList();
		Stack stack = new Stack();
		stack.push(this);
		while (!stack.empty()) {
			BaseTree bt = (BaseTree) stack.pop();
			if (bt != null) {
				list.add(bt);
				List<Tree> childList = bt.getChildNodes();
				if (childList != null) {
					Iterator itr = childList.iterator();
					while (itr.hasNext()) {
						BaseTree btchild = (BaseTree) itr.next();
						stack.push(btchild);
					}
				}
			}
		}
		list.remove(this);
		return list;
	}

	public void addObject(Object obj) {
		objects.add(obj);
	}

	public void rmObject(Object obj) {
		objects.remove(obj);
	}
}
