package com.wfms.common.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.wfms.common.attribute.Tree;
import com.wfms.common.attribute.TreeNode;
import com.wfms.common.system.entity.ModuleGenInfo;

public class TreeUtil {
	private static int levelNumber = 0;

	public static String generateJsonStyle(String returnStr) {// 修饰一下才能满足Extjs的Json格式
		return ("[" + returnStr + "]").replaceAll(",]", "]");
	}

	public static void generateTreeBuffer(StringBuffer tempBuffer,
			boolean isAppendChild, boolean isAppendObj, List<Tree> treeList,
			Tree rootNode, int... appendLevel) {
		Tree parent = rootNode.getParent();
		boolean isLeaf = rootNode.isLeaf();
		boolean isFinal = true;
		if (isAppendObj && levelNumber < appendLevel[0] - 1) {
			isFinal = rootNode.isFinal();
		}
		if (!isAppendObj) {
			if (!isLeaf) {
				tempBuffer.append("{id:");
				tempBuffer.append("'" + rootNode.getId() + "'");
				tempBuffer.append(",text:");
				tempBuffer.append("'" + rootNode.getText() + "'");
				tempBuffer.append(",leaf:false");
				if (rootNode.isCheckBox()) {
					tempBuffer.append(",checked:");
					tempBuffer.append(rootNode.isChecked());
				}
				tempBuffer.append(",parentId:");
				if (parent == null) {
					tempBuffer.append("'0'");
				} else {
					tempBuffer.append("'" + parent.getId() + "'");
				}
				if (isAppendChild) {
					tempBuffer.append(",children:[");
					List<Tree> childList = rootNode.getChildNodes();
					Iterator<Tree> it = childList.iterator();
					while (it.hasNext()) {
						Tree subNode = it.next();
						subNode.setCheckBox(rootNode.isCheckBox());
						generateTreeBuffer(tempBuffer, isAppendChild,
								isAppendObj, treeList, subNode, appendLevel);
					}
					tempBuffer.append("]},");
				} else {
					tempBuffer.append("},");
				}
			} else {
				tempBuffer.append("{id:");
				tempBuffer.append("'" + rootNode.getId() + "'");
				tempBuffer.append(",text:");
				tempBuffer.append("'" + rootNode.getText() + "'");
				if (rootNode.isCheckBox()) {
					tempBuffer.append(",checked:");
					tempBuffer.append(rootNode.isChecked());
				}
				tempBuffer.append(",parentId:");
				if (parent == null) {
					tempBuffer.append("'0'");
				} else {
					tempBuffer.append("'" + parent.getId() + "'");
				}
				tempBuffer.append(",leaf:true},");
			}
		} else if (levelNumber < appendLevel[0]) {
			if (!isLeaf || !isFinal) {
				tempBuffer.append("{id:");
				tempBuffer.append("'" + rootNode.getId() + "'");
				tempBuffer.append(",text:");
				tempBuffer.append("'" + rootNode.getText() + "'");
				tempBuffer.append(",leaf:false");
				if (rootNode.isCheckBox()) {
					tempBuffer.append(",checked:");
					tempBuffer.append(rootNode.isChecked());
				}
				tempBuffer.append(",parentId:");
				if (parent == null) {
					tempBuffer.append("'0'");
				} else {
					tempBuffer.append("'" + parent.getId() + "'");
				}
				if (isAppendChild) {
					tempBuffer.append(",children:[");
					List<Tree> childList = null;
					if (!isLeaf) {
						childList = rootNode.getChildNodes();
						Iterator<Tree> it = childList.iterator();
						while (it.hasNext()) {
							Tree subNode = it.next();
							subNode.setCheckBox(rootNode.isCheckBox());
							generateTreeBuffer(tempBuffer, isAppendChild,
									isAppendObj, treeList, subNode, appendLevel);
						}
					}
					if (!isFinal) {
						childList = rootNode.getObjects();
						Iterator<Tree> it = childList.iterator();
						while (it.hasNext()) {
							levelNumber += 1;
							Tree subNode = it.next();
							subNode.setCheckBox(rootNode.isCheckBox());
							generateTreeBuffer(tempBuffer, isAppendChild,
									isAppendObj, treeList, subNode, appendLevel);
							levelNumber -= 1;
						}
					}
					tempBuffer.append("]},");
				} else {
					tempBuffer.append("},");
				}
			} else {
				tempBuffer.append("{id:");
				tempBuffer.append("'" + rootNode.getId() + "'");
				tempBuffer.append(",text:");
				tempBuffer.append("'" + rootNode.getText() + "'");
				if (rootNode.isCheckBox()) {
					tempBuffer.append(",checked:");
					tempBuffer.append(rootNode.isChecked());
				}
				tempBuffer.append(",parentId:");
				if (parent == null) {
					tempBuffer.append("'0'");
				} else {
					tempBuffer.append("'" + parent.getId() + "'");
				}
				tempBuffer.append(",leaf:true},");
			}
		}
	}

	public static String generateTreeTemplate(StringBuffer tempBuffer,
			List<Tree> list, Tree node) {
		if (tempBuffer == null) {
			return "";
		}
		generateTreeBuffer(tempBuffer, true, false, list, node);
		levelNumber = 0;
		return generateJsonStyle(String.valueOf(tempBuffer));
	}

	public static void resetLevelNumber() {
		levelNumber = 0;
	}

	/**
	 * 初始化树(功能模块必须齐全且正�?)
	 * 
	 * @param list
	 * @return List<TreeNode>
	 */
	public static List<TreeNode> initTree(String contextPath,
			List<ModuleGenInfo> list) {
		if (list == null) {
			return null;
		}
		List<TreeNode> resultTreeList = new ArrayList<TreeNode>();
		List<ModuleGenInfo> resultGnmkList = new ArrayList<ModuleGenInfo>();
		for (int i = 0; i < list.size(); i++) {
			ModuleGenInfo preantGnmk = list.get(i);
			if ("0".equals(preantGnmk.getParentid())) {
				resultGnmkList.add(preantGnmk);
				list.remove(i);
				i--;
			}
		}
		for (int i = 0; i < resultGnmkList.size(); i++) {
			resultTreeList.add(getChildrenNode(contextPath, list,
					resultGnmkList.get(i)));
		}
		return resultTreeList;
	}

	private static TreeNode getTree(String contextPath, ModuleGenInfo gnmk) {
		TreeNode tree = new TreeNode();
		tree.setId(gnmk.getId());
		tree.setText(gnmk.getModulename());
		tree.setQtip(gnmk.getDescription());
		if (gnmk.isLeaf()) {
			tree.setHref(contextPath + gnmk.getForwardpage());
		} else {
			tree.setHref("javascript:void(0);");
		}
		tree.setChecked(false);
		return tree;
	}

	// 递归方法
	private static TreeNode getChildrenNode(String contextPath,
			List<ModuleGenInfo> list, ModuleGenInfo gnmk, String... order) {
		TreeNode tree = getTree(contextPath, gnmk);
		// 递归标记
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getParentid().equals(gnmk.getId())) {
				// 递归调用
				TreeNode rstree = getChildrenNode(contextPath, list, list
						.get(i));
				// 递归标记
				tree.getChildren().add(rstree);
			}
		}
		return tree;
	}
}
