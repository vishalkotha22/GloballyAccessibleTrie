# Globally Accessible Trie
The Github repository storing everything for Vishal Kotha's implementation of a Trie that can be accessed globally through command prompt if the server is running. Command Line Interface instructions are located at CLIinstructions.md and Server Documentation is located at serverdocumentation.md. 

Descriptions of Files under the test package:

TrieServlet.java - This class handles all requests coming from the client and calls TrieManager.

TrieManager.java - This class executes the operations requested onto TrieTree.

TrieTree.java - The actual trie class with all the methods and parameters.

trieOperationCLI.jsp - This jsp page is used to create the output message to the CLI.

web.xml - The configuration for the web application of the servlet

Logger.java - Logger method to log messages
