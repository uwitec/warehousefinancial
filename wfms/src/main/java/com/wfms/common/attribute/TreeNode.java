package com.wfms.common.attribute;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
	private String id; // ID
	private String text; // 节点显示
	private String iconCls; // 图标
	private String icon;
	private String cls;
	private boolean leaf; // 是否叶子
	private String url;
	private String href; // 链接
	private String hrefTarget; // 链接指向
	private boolean expandable; // 是否展开
	private String description; // 描述信息
	private String qtip;
	private Object o;
	private String parentId;// 父节点
	// private boolean checked;
	private Integer sort_index = 0;
	private boolean hasSelect;// 作为下拉选择框时是否可以选择
	private List<TreeNode> children = new ArrayList<TreeNode>();
	private Boolean checked = null;

	/*
	 * public boolean getChecked() { return checked; }
	 * 
	 * public void setChecked(boolean checked) { this.checked = checked; }
	 */

	public boolean isHasSelect() {
		return hasSelect;
	}

	public void setHasSelect(boolean hasSelect) {
		this.hasSelect = hasSelect;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getHrefTarget() {
		return hrefTarget;
	}

	public void setHrefTarget(String hrefTarget) {
		this.hrefTarget = hrefTarget;
	}

	public boolean isExpandable() {
		return expandable;
	}

	public void setExpandable(boolean expandable) {
		this.expandable = expandable;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Object getO() {
		return o;
	}

	public void setO(Object o) {
		this.o = o;
	}

	public Integer getSort_index() {
		return sort_index;
	}

	public void setSort_index(Integer sort_index) {
		this.sort_index = sort_index;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getCls() {
		return cls;
	}

	public void setCls(String cls) {
		this.cls = cls;
	}

	public String getQtip() {
		return qtip;
	}

	public void setQtip(String qtip) {
		this.qtip = qtip;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

}
