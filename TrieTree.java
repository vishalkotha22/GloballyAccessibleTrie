package test;

import java.io.*;
import java.util.*;
import java.util.logging.Level;

public class TrieTree {
	private TrieNode trieRoot = null;

	public TrieTree(){
		trieRoot = new TrieNode('#', false, null);
	}
	
	public synchronized void addKeyword(String word) {
		trieRoot.addKeyword(word);
	}
		
	public synchronized boolean search(String word) {
		return trieRoot.search(word);
	}

	public synchronized void deleteKeyword(String word) {
		trieRoot.deleteKeyword(word);
	}
	
	public synchronized List<String> autoComplete(String prefix) {
		return trieRoot.autoComplete(prefix);
	}
		
	public String display(){
		return trieRoot.display();
	}
	
	//////////////////////////////////////////////////////////////////////////////
	//Inner Class
	//////////////////////////////////////////////////////////////////////////////
	static class TrieNode {
		private char data;               
		private List<TrieNode> children; 
		private boolean isEnd;          
		private TrieNode parent;         
      
      /**
       * A four argument constructor to initialize a trie node.
       *
       * @param data the data which the trie node stores
       * @param isEnd whether the trie node is the end of a word
       * @param parent the trie node that's the parent of this trie node
       */
		private TrieNode(char data, boolean isEnd, TrieNode parent){
			this.data = data;
			children = Collections.synchronizedList(new ArrayList<TrieNode>());
			this.isEnd = isEnd;
			this.parent = parent;
		}
		
      /**
       * This method is a setter to change the value of the boolean.
       *
       * @param val the value to set the boolean to
       */
		private void setBool(boolean val){
			isEnd = val;
		}
		
      /**
       * This method is used to add a keyword to the trie. If the node of the character of the first string
       * is already in the trie, find the index of the node. If there's only one character in the word left,
       * and if the trie node with data equal to word isn't contained in children, add a new trie node 
       * storing word to the children. If the trie node containing the data of word is already in the 
       * children, set the boolean to true. Otherwise, if there's multiple characters left in the word 
       * string and the trie node that has the data of the first character of word is not in the children 
       * of tree node, add a new trie node that has the data of the first character of word to the children.
       * Then, in both cases, we run add keyword on the child node of word without the first character.  
       * 
       * @param word the word that will be added to the true
       */
		void addKeyword(String word){
			int index = findLocation(word.charAt(0)); 
			if(word.length() == 1){ 
				if(index == -1){ 
					children.add(new TrieNode(word.charAt(0), true, this)); 
				}else{ 
					children.get(index).setBool(true); 
				}
			}else{
				if(index == -1){
					children.add(new TrieNode(word.charAt(0), false, this));
					children.get(children.size()-1).addKeyword(word.substring(1));
            }else{
					children.get(index).addKeyword(word.substring(1));
				}
			}
		}
      
      /**
       * A method that determines whether a word is in the trie. First, we find the index of the node
       * containing the data of the first character of the word if it exists. Then, if there's only one
       * character left that we need to check, we need to check that index isn't -1 (there's a child node with
       * data equal to word) and that children.get(index).isEnd (the node with data that's equal to word is
       * the end of a word). Otherwise, if there's multiple characters left, we first check if there's a
       * node with the data of the first character of the word. If index is equal to -1, there is no such
       * node, so we can return false. Otherwise, we recursively check if the rest of the word is contained
       * by running search again on the child node using the substring excluding the first character, since
       * we just checked that the first character exists. 
       *
       * @param word the word that we are searching for
       * @return whether the word is contained in the trie
       */
		boolean search(String word){
			int index = findLocation(word.charAt(0));
			if(word.length() == 1){
				return index > -1 && children.get(index).isEnd;
			}else{
				if(index == -1){
					return false;
				}else{
					return children.get(index).search(word.substring(1));
				}
			}
		}
      
      /**
       * If the word isn't found, we can't delete it, so print the eerror. If tail has any children, 
       * we can't delete it since it stores by extension other words. Then, set a new TrieNode called 
       * target equal to tail and make tail equal to tail's parent. Target is the trie node that we 
       * will remove and tail is the parent of target. We can do this since we know now, after checking 
       * for whether it has children, we will remove at least one node (the node at the end of the word). 
       * While we can move further up (tail.parent != null checks for whether tail is at the root node), 
       * there's no other branches that will be affected if we remove the tail node (tail.children.size() 
       * less than 2 checks this), and the tail itself isn't a word that will be affected if we move target to tail
       * (!tail.isEnd checks for this), keep moving the tail and target up. Essentially, we check that we 
       * can remove tail, and if we can, we move target to tail and then we move tail up, and keep repeating
       * that process until the conditions aren't met or we hit the root, and can't go any further up.
       *    
       * @param word the word that will be deleted from the trie     
       */
		void deleteKeyword(String word){
			if(!search(word)){ 
				System.err.println("Word not found in the trie.");
				return;
			}
			TrieNode tail = traverse(word);
         if(tail.children.size() > 0){
				tail.setBool(false);
				return;
			}
			TrieNode target = tail;
			while(tail.parent != null && tail.children.size() < 2 && !tail.isEnd){
				target = tail;
				tail = tail.parent;
			}
			int index = tail.findLocation(target.data);
			tail.children.remove(index);
		}
      
      /**
       * Given a string prefix, find all words in the trie that contain the prefix. First, a list of strings
       * of declared. Then, a try-catch loop is used to try to autocomplete the prefix, and to catch the error
       * where the traverse method attempts to traverse a word that's not in the trie. After traversing to the
       * node, if possible, we check the case where the prefix is a word in the trie, add it if true, then run
       * a depth-first search algorithm to add all the words. In the case where there's an error, we return a
       * list of size 1 which contains a string of the error message.
       *
       * @param prefix the prefix for which the trie is vetted for
       * @return the list of words in the trie which start with the prefix
       */
		List<String> autoComplete(String prefix){
			ArrayList<String> list = null;
			try{
				list = new ArrayList<String>();
				TrieNode head = traverse(prefix);
				if(head.isEnd){
					list.add(prefix);
				}
				fill(head, list, new StringBuilder(prefix));
			}catch(Exception e){
				list = new ArrayList<String>(1);
				list.add("No valid matching words for \"" + prefix + "\".");
			} 
			return list;
		}
      
		/**
       * Initialize a StringBuilder which is what will be returned and is where the display will be added.
       * A note that # signifies the end of a word is added and the StringBuilder goes to a new line. 
       * A list of strings to store all the words is initalized. Then, for each trie node at level 1 of
       * the trie, we add the visual display to the StringBuilder using a helper method and call
       * autocomplete on child's data to get all words stemming from that trie node and add all of those 
       * words to the list that was created earlier.
       *
       * @return the visual representation of the trie
       */ 
		String display(){
			StringBuilder ret = new StringBuilder();
			ret.append("# signifies that the character is the end of a word." + "\n");
			ArrayList<String> words = new ArrayList<String>();
			for(TrieNode child : children){
				print(0, child, ret);
				words.addAll(autoComplete(child.data+""));
			}
			ret.append("The list of words is: " + words + "\n");
			return ret.toString();
		}
		
      /**
       * A helper that traverses to the node given a key, assuming that it exists. First, we find the
       * index of the child that stores data equal to the first character of key. If the key only has
       * one character, we don't need to recurse anymore, so we just return the child that stores data
       * equal to the first character of key. Otherwise, we recurse, so call traverse on the child but
       * the argument is key where the first character is removed since we accounted for it by finding
       * the child. 
       *
       * @param key the key for which we traverse to find the node at the end of this key
       * @return the trie node which is obtained after traversing the trie based on the key
       */
		private TrieNode traverse(String key){
			int index = findLocation(key.charAt(0));
			if(key.length() == 1){
				return children.get(index);
			}else{
				return children.get(index).traverse(key.substring(1));
			}
		}
		
      /**
       * Given a target, search for that target in the children. Start off by initializing an int index
       * to -1. Then, we iterate through all the children and if the child we are currently checking
       * has data equal to the target data, set the index equal to -1 and break the for loop since we are
       * done. At the end, return the value of index.
       *
       * @param target the character we are searching for
       * @return the index of the child trie node with the same data as the target, -1 if not contained
       */
		private int findLocation(char target){
			int index = -1; 
			for(int i = 0; i < children.size(); i++){
				if(children.get(i).data == target){
					index = i;
					break;
				}
			}
			return index;
		}		
		
      /**
       * A method that runs a depth-first search starting from the trie node given. It iterates through
       * all the children of the trie node, updates the StringBuilder accordingly for each, and if the
       * trie node is a word we add it to the list storing all the words. Then, for each child, we make
       * use of recursion, so call fill on the child, the same list, and the new StringBuilder.
       *
       * @param start the trie node which the method is run on
       * @param adder the list to which the words are added to
       * @param build the StringBuilder where the string construction from the traversal is stored
       */
		private void fill(TrieNode start, ArrayList<String> adder, StringBuilder build){
			for(TrieNode child : start.children){
				StringBuilder temp = new StringBuilder(build.toString() + child.data);
				if(child.isEnd){
					adder.add(temp.toString());
				}
				fill(child, adder, temp);
			}
		}
      
      /**
       * A method that's used to construct the visual representation of a node and all its children. A 
       * StringBuilder is initialized, and based on the value given in spacing, that many indentations
       * are added. The default spacing is a space.Then, if the node is the end of a word, the last 
       * character in the indentation is replaced with a # to signify that. The spacing string and the
       * actual data is added to the StringBuilder where the whole visual representation is stored. 
       * The method is called on the children of the trie node and all the arguments are the same
       * except spacing, which is increased by one as each child has one more indentation than its parent.
       *
       * @param spacing the number of indentations for which the trie node's data will have
       * @param t the trie ndoe that will be printed
       * @param build the StringBuilder where everything is added
       */
		private void print(int spacing, TrieNode t, StringBuilder build){
			StringBuilder space = new StringBuilder(" ");
			for(int i = 0; i < spacing; i++){
				space.append("     ");
			}
			if(t.isEnd){
				space.setCharAt(space.length()-1, '#');
			}
			build.append(space.toString() + t.data + "\n");
			for(TrieNode child : t.children){
				print(spacing+1, child, build);
			}
		}
	}
}