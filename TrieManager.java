package test;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

public final class TrieManager {

    private static final String strClassName_ = TrieManager.class.getName();
	private static TrieManager trieMgr = new TrieManager();
	private TrieTree trieTree = null;
	
	private TrieManager() {
		trieTree = new TrieTree();
	}
	
	public static TrieManager getInstance( ) {
		return trieMgr;
	}
	
	public void addKeyword(String word) {
		trieTree.addKeyword(word);
	}
		
	public boolean search(String word) {	
		return trieTree.search(word);
	}

	public void deleteKeyword(String word) {	
		trieTree.deleteKeyword(word);
	}
	
	public List<String> autoComplete(String prefix) {	
		return trieTree.autoComplete(prefix);
	}
		
	public String display(){	
		return trieTree.display();
	}	
}
