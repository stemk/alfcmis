/* 
 * Copyright (C) 2015 stemk
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.stemk.alfcmis.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

import org.stemk.alfcmis.cmis.NodeFolder;

@Named("browser")
@ViewScoped
public class BrowserBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private SessionBean session;
	
	private NodeFolder nodeFolder;

	private Map<String, Boolean> checkedNodes = new HashMap<String, Boolean>();

	private List<String> toPasteNodes;

	private String idParentPasteNodes;

	private boolean isCopy;
	
	private String newFolder;
	
	private Part newDocument;
	
	public NodeFolder getNodeFolder() {
		return nodeFolder;
	}

	public void setNodeFolder(NodeFolder nodeFolder) {
		this.nodeFolder = nodeFolder;
	}

	public Map<String, Boolean> getCheckedNodes() {
		return checkedNodes;
	}

	public void setCheckedNodes(Map<String, Boolean> checkedNodes) {
		this.checkedNodes = checkedNodes;
	}
	
	public List<String> getToPasteNodes() {
		return toPasteNodes;
	}

	public void setToPasteNodes(List<String> toPasteNodes) {
		this.toPasteNodes = toPasteNodes;
	}

	public String getIdParentPasteNodes() {
		return idParentPasteNodes;
	}

	public void setIdParentPasteNodes(String idParentPasteNodes) {
		this.idParentPasteNodes = idParentPasteNodes;
	}

	public boolean getIsCopy() {
		return isCopy;
	}

	public void setIsCopy(boolean isCopy) {
		this.isCopy = isCopy;
	}

	public String getNewFolder() {
		return newFolder;
	}

	public void setNewFolder(String newFolder) {
		this.newFolder = newFolder;
	}

	public Part getNewDocument() {
		return newDocument;
	}

	public void setNewDocument(Part newDocument) {
		this.newDocument = newDocument;
	}

	@PostConstruct
	public void init(){
		root();
	}
	
	public void root(){
		nodeFolder = session.getCmisService().getRootFolder();
	}

	public void loadFolder(String id){
		nodeFolder = session.getCmisService().getFolder(id);
		checkedNodes = new HashMap<String, Boolean>();
	}

	public void createFolder() {
		if (session.getCmisService().createFolder(newFolder, nodeFolder.getId()) != null)
			nodeFolder = session.getCmisService().getFolder(nodeFolder.getId());
		newFolder = "";
	}

	public void uploadDocument(){
		try {
			if (session.getCmisService().createDocument(newDocument.getSubmittedFileName(), null, newDocument.getInputStream(), nodeFolder.getId()) != null) 
				nodeFolder = session.getCmisService().getFolder(nodeFolder.getId());
		} catch (Exception e) {
			// TODO
		}
		newDocument = null;
	}

	public void delete() {
		checkedNodes.forEach((k, v) -> {if (v != null && v.booleanValue()) session.getCmisService().delete(k, true);});
		nodeFolder = session.getCmisService().getFolder(nodeFolder.getId());
		checkedNodes = new HashMap<String, Boolean>();
	}

	public void copy() {
		toPasteNodes = new ArrayList<String>();
		checkedNodes.forEach((k, v) -> {if (v != null && v.booleanValue()) toPasteNodes.add(k);});
		idParentPasteNodes = nodeFolder.getId();
		isCopy = true;
	}

	public void cut() {
		toPasteNodes = new ArrayList<String>();
		checkedNodes.forEach((k, v) -> {if (v != null && v.booleanValue()) toPasteNodes.add(k);});
		idParentPasteNodes = nodeFolder.getId();
		isCopy = false;
	}

	public void paste() {
		if (isCopy){
			for(String idNode : toPasteNodes)
				session.getCmisService().copy(idNode, nodeFolder.getId());				
		} else {
			for(String idNode : toPasteNodes)
				if(session.getCmisService().move(idNode, idParentPasteNodes, nodeFolder.getId()) != null)
					System.out.println(idNode + " is not cutted!");
		}
		toPasteNodes = null;
		nodeFolder = session.getCmisService().getFolder(nodeFolder.getId());
	}
	
	public String logout() {
		if (session.isCreated()){
			session.getCmisService().closeSession();
			session.setCreated(false);
		}
		return "index?faces-redirect=true";
	}
}