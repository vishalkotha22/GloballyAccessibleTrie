<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page trimDirectiveWhitespaces="true" %>

<c:if test ="${not empty invalidInput}" >
	<c:out value="${invalidInput}" />
</c:if>				
<c:if test ="${not empty trieSuccess}" >
Successfully processed the Trie Data Structure '<c:out value="${trieSuccess}"/>' operation.
	<c:if test ="${not empty searchResults}" >
Search operation against the Trie Data Structure operation - '<c:out value="${searchResults}"/>'.
	</c:if>	
	<c:if test ="${not empty autoCompleteResults}" >
Auto Complete operation against the Trie Data Structure operation - '<c:out value="${autoCompleteResults}"/>'.
	</c:if>		
</c:if>

<c:if test ="${not empty errorCLI}" >
Some error occured while processing this request. Please try after sometime.
</c:if>

<c:if test ="${not empty requestScope.trieWords}" >
Trie Display -> <c:out value="${requestScope.trieWords}"></c:out>		
</c:if>