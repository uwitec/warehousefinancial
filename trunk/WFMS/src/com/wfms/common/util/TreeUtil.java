package com.wfms.common.util;

import java.util.Iterator;
import java.util.List;

import com.wfms.common.attribute.Tree;

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
}
