package com.wfms.common.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.wfms.common.entity.OtherTreeEntity;
import com.wfms.common.entity.Tree;
import com.wfms.common.entity.TreeEntity;
import com.wfms.model.rsgl.RyEntity;
import com.wfms.model.system.DepartGenInfo;
import com.wfms.model.system.MemberGenInfo;
import com.wfms.model.system.ModuleGenInfo;

public class TreeUtil {
	private static int levelNumber = 0;
	/**
	 * 初始化树(功能模块必须齐全且正�?)
	 * 
	 * @param list
	 * @return List<TreeEntity>
	 */
	public static List<TreeEntity> initTree(String contextPath,
			List<ModuleGenInfo> list,String... order) {
		if (list == null) {
			return null;
		}
		// 初始化返回的�?
		List<TreeEntity> resultTreeList = new ArrayList<TreeEntity>();
		List<ModuleGenInfo> resultGnmkList = new ArrayList<ModuleGenInfo>();
		// 遍历�?有功能模�?
		for (int i = 0; i < list.size(); i++) {
			// 获取功能模块实体
			ModuleGenInfo preantGnmk = list.get(i);
			if (preantGnmk.getParentId() == 0) {
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

	private static TreeEntity getTree(String contextPath, ModuleGenInfo gnmk) {
		TreeEntity tree = new TreeEntity();
		// 给树赋�??
		tree.setId(gnmk.getModuleId());
		tree.setText(gnmk.getName());
		tree.setQtip(gnmk.getDescription());
		boolean isLeaf = "0".equals(gnmk.getHasChild()) ? true : false;
		if(isLeaf)
		{
			tree.setHref(contextPath + gnmk.getForwardPage());
		}
		else
		{
			tree.setHref("javascript:void(0);");
		}
		tree.setLeaf(isLeaf);
		tree.setChecked(false);
		return tree;
	}

	// 递归方法
	private static TreeEntity getChildrenNode(String contextPath,
			List<ModuleGenInfo> list, ModuleGenInfo gnmk,String... order) {
		TreeEntity tree = getTree(contextPath, gnmk);
		// 递归标记
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getParentId() == gnmk.getModuleId()) {
				// 递归调用
				TreeEntity rstree = getChildrenNode(contextPath, list, list
						.get(i));
				// 递归标记
				tree.getChildren().add(rstree);
			}
		}
		return tree;
	}

	@SuppressWarnings("unchecked")
	public static List<ModuleGenInfo> filterGnmk(List<ModuleGenInfo> list,
			String text) {
		if (list == null || list.size() == 0) {
			return null;
		}
		List<ModuleGenInfo> rsList = new ArrayList();
		if ("".equals(text) || text == null) {
			return list;
		}
		text = text.trim();
		for (int i = 0; i < list.size(); i++) {
			ModuleGenInfo gnmk = list.get(i);
			if (gnmk.getName().indexOf(text) != -1
					|| gnmk.getHoneticize().indexOf(text) != -1) {
				rsList.add(gnmk);
			}
		}
		return rsList;
	}

	public static List<OtherTreeEntity> getTreeByBm(List<DepartGenInfo> bmList) {
		List<OtherTreeEntity> list = new ArrayList<OtherTreeEntity>();
		for (DepartGenInfo bm : bmList) {
			OtherTreeEntity tree = new OtherTreeEntity();
			tree.setId(String.valueOf(bm.getId()));
			tree.setText(bm.getName());
			tree.setQtip(bm.getSynopsis());
			tree.setLeaf(false);
			list.add(tree);
		}
		return list;
	}

	public static List<OtherTreeEntity> getTreeByRy(String contextPath,
			List<RyEntity> ryList) {
		List<OtherTreeEntity> list = new ArrayList<OtherTreeEntity>();
		for (RyEntity ry : ryList) {
			OtherTreeEntity tree = new OtherTreeEntity();
			tree.setId(String.valueOf(ry.getId()));
			tree.setText(ry.getRyxm());
			tree.setQtip("姓名：" + ry.getRyxm() + "，出生日期："
					+ (ry.getCsrq() == null ? "暂无" : ry.getCsrq())
					+ "<br/>在职状态：" + ("0".equals(ry.getZt()) ? "离职" : "在职"));
			if ("1".equals(ry.getXb())) {
				tree.setIcon(contextPath + "/imgs/boy.gif");
			} else {
				tree.setIcon(contextPath + "/imgs/girl.gif");
			}
			tree.setLeaf(true);
			list.add(tree);
		}
		return list;
	}

	/**
	 * 用户树
	 * 
	 * @param yhList
	 * @return
	 */
	public static List<OtherTreeEntity> getTreeByYh(List<MemberGenInfo> yhList) {
		List<OtherTreeEntity> list = new ArrayList<OtherTreeEntity>();
		for (MemberGenInfo yh : yhList) {
			OtherTreeEntity tree = new OtherTreeEntity();
			tree.setId(yh.getMemid());
			tree.setText(yh.getUsername());
			tree.setQtip(yh.getLoginid());
			tree.setLeaf(true);
			list.add(tree);
		}
		return list;
	}

	public static List<OtherTreeEntity> getTreeByYhs(List<MemberGenInfo> yhList) {
		List<OtherTreeEntity> list = new ArrayList<OtherTreeEntity>();
		OtherTreeEntity tree = null;
		tree = new OtherTreeEntity();
		tree.setId("00000");
		tree.setText("所有人");
		tree.setQtip("10000");
		tree.setLeaf(true);
		list.add(tree);
		for (int i = 0; i < yhList.size(); i++) {
			tree = new OtherTreeEntity();
			tree.setId(yhList.get(i).getMemid());
			tree.setText(yhList.get(i).getUsername());
			tree.setQtip(yhList.get(i).getLoginid());
			tree.setLeaf(true);
			list.add(tree);
		}
		return list;
	}

	public static String generateJsonStyle(String returnStr) {// 修饰一下才能满足Extjs的Json格式
		return ("[" + returnStr + "]").replaceAll(",]", "]");
	}

	public static void generateTreeBuffer(StringBuffer tempBuffer,
			boolean isAppendChild,boolean isAppendObj,List<Tree> treeList, Tree rootNode,int...appendLevel) {
		Tree parent = rootNode.getParent();
		boolean isLeaf = rootNode.isLeaf();
		boolean isFinal = true;
		if (isAppendObj && levelNumber<appendLevel[0]-1) {
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
				if(isAppendChild)
				{
					tempBuffer.append(",children:[");
					List<Tree> childList = rootNode.getChildNodes();
					Iterator<Tree> it = childList.iterator();
					while (it.hasNext()) {
						Tree subNode = it.next();
						subNode.setCheckBox(rootNode.isCheckBox());
						generateTreeBuffer(tempBuffer,isAppendChild, isAppendObj,treeList,
								subNode,appendLevel);
					}
					tempBuffer.append("]},");
				}
				else
				{
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
		} else if(levelNumber<appendLevel[0]){
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
				if(isAppendChild)
				{
					tempBuffer.append(",children:[");
					List<Tree> childList = null;
					if (!isLeaf) {
						childList = rootNode.getChildNodes();
						Iterator<Tree> it = childList.iterator();
						while (it.hasNext()) {
							Tree subNode = it.next();
							subNode.setCheckBox(rootNode.isCheckBox());
							generateTreeBuffer(tempBuffer, isAppendChild,isAppendObj, treeList,
									subNode,appendLevel);
						}
					}
					if (!isFinal) {
						childList = rootNode.getObjects();
						Iterator<Tree> it = childList.iterator();
						while (it.hasNext()) {
							levelNumber += 1;
							Tree subNode = it.next();
							subNode.setCheckBox(rootNode.isCheckBox());
							generateTreeBuffer(tempBuffer, isAppendChild , isAppendObj, treeList,
									subNode,appendLevel);
							levelNumber -= 1;
						}
					}
					tempBuffer.append("]},");
				}
				else
				{
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
		generateTreeBuffer(tempBuffer, true , false, list, node);
		levelNumber =0;
		return generateJsonStyle(String.valueOf(tempBuffer));
	}
	
	public static void resetLevelNumber(){
		levelNumber = 0;
	}
}
