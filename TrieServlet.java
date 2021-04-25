package test;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class TrieServlet extends HttpServlet {

    private static final String strClassName_ = TrieServlet.class.getName();
    private static final String TRIE_LIST = "get_list";
	private static final String TRIE_DETAILS = "details";
	private static final String TRIE_ADD = "add";
	private static final String TRIE_SEARCH = "search";
	private static final String TRIE_DELETE = "delete";
	private static final String TRIE_AUTO_COMPLETE = "autocomplete";
	private static final String TRIE_DISPLAY = "display";	

	private static final String BLOCKING = "blocking";
	private static final String INVALID_INPUT = "invalidInput";
	private static final String TRIE_SUCCESS = "trieSuccess";	
	private static final String TRIE_WORDS = "trieWords";
    private static final String SEARCH_OPER_RESULTS_KEY = "searchResults";
	private static final String AUTO_COMPLETE_OPER_RESULTS_KEY = "autoCompleteResults";
    private static final String TRIE_ERROR_CLI = "errorCLI";	
	
	
    private static final String OPERATION = "operation";
    private static final String WORD_KEY = "word";
	private static final String SUBMIT = "submit";
	
	private static final String RESPONSE_OPERATION_PAGE = "/trieOperation.jsp";
	private static final String RESPONSE_CLI_PAGE = "/trieOperationCLI.jsp";	
    private static final String ERROR_PAGE = "/error.jsp";	


	public void init(ServletConfig config) throws ServletException {
		super.init(config);	
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
							throws ServletException, IOException {
		doProcess(request, response);
	}	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
							throws ServletException, IOException {
		doProcess(request, response);
	}	
	
	protected void doProcess(HttpServletRequest request, HttpServletResponse response)
							throws ServletException, IOException {
		
		boolean isValid = false;
		String errorMsg = null;
		String displayRes = null;
		String strResPage = RESPONSE_OPERATION_PAGE;		
		String operation = request.getParameter(OPERATION);
		String word = request.getParameter(WORD_KEY);
		String submitVal = request.getParameter(SUBMIT);
		
		System.out.println(Thread.currentThread().getName() + " : IN doProcess operation ="+ operation  + " , word ="+ word + " ,  submitVal ="+submitVal);
		
		try {
			isValid = validateInput(request, operation, word);
			if(isValid){		
				if(operation != null && operation.equals(TRIE_ADD)) {
					TrieManager.getInstance().addKeyword(word);				
				} else if(operation != null && operation.equals(TRIE_SEARCH)) {
					boolean isMatched = TrieManager.getInstance().search(word);								
					request.setAttribute(SEARCH_OPER_RESULTS_KEY, String.valueOf(isMatched));
					
					System.out.println("search DONE isMatched ="+isMatched);
					
				} else if(operation != null && operation.equals(TRIE_DELETE)) {	
					TrieManager.getInstance().deleteKeyword(word);
					
					System.out.println("Delete ROWS DONE");
					
				} else if(operation != null && operation.equals(TRIE_AUTO_COMPLETE)) {	
					List<String> listWords = TrieManager.getInstance().autoComplete(word);
					if( listWords != null /*&& listWords.size() > 0*/ ) {
						request.setAttribute(AUTO_COMPLETE_OPER_RESULTS_KEY, listWords);
					}					
					System.out.println("autoComplete ROWS DONE listWords =" + listWords);
					
				} else if(operation != null && operation.equals(TRIE_DISPLAY)) {	
					displayRes = TrieManager.getInstance().display();
					
					System.out.println("display ROWS DONE displayRes ="+displayRes);
				}
				request.setAttribute(TRIE_SUCCESS, operation);	
			}			
		}
		catch(Exception ex) {
			strResPage=ERROR_PAGE;
			errorMsg = ex.toString();
			Logger.err(ex, strClassName_, " : doProcess : Exception : Failed to process "+ 
				operation + "request.  Because :::: "+ ex );			
		} finally {
			if(submitVal == null || submitVal.trim().isEmpty() ) {
				strResPage = RESPONSE_CLI_PAGE;				
				if( errorMsg != null && errorMsg.trim().length() > 0) {
					request.setAttribute(TRIE_ERROR_CLI, errorMsg);	
				}
			}	
			if( displayRes == null ) {
				displayRes = TrieManager.getInstance().display();
			}
			if( displayRes != null &&  displayRes.trim().length() > 0) {
				request.setAttribute(TRIE_WORDS, displayRes);
			}			
		}
		
		System.out.println(" Response Page strResPage = " +strResPage);

		
		forward(request, response, strResPage);
	}	
	
	private void forward(HttpServletRequest request, HttpServletResponse response, String strResPage) 
		throws ServletException, IOException {
		RequestDispatcher rd = getServletContext().getRequestDispatcher(strResPage);
		rd.forward(request, response);	
	}
	
	private boolean validateInput(HttpServletRequest request, String operation, String word) throws ServletException, IOException {
		boolean isValid = true;
		if( operation == null || operation.trim().isEmpty() ) {
			request.setAttribute(INVALID_INPUT, "Invalid 'opearation' input value - Null or Empty");
			Logger.err(strClassName_, "Invalid 'opearation' input value - Null or Empty" );		
			isValid = false;
		}
		if( (operation != null && !operation.equals(TRIE_DISPLAY))  && (word == null || word.trim().isEmpty())) {
			request.setAttribute(INVALID_INPUT, "Invalid 'Element Value' input value - Null or Empty");
			Logger.err(strClassName_, "Invalid 'word' input value - Null or Empty" );
			isValid = false;			
		}
		return isValid;
	}	
}
