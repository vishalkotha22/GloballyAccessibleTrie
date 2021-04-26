# slingshottakehomechallenge
The server is hosted using Amazon Web Service EC2. On the EC2 instance, I've installed Tomcat 9 and deployed the TrieWeb application with servlets and configurations. 

The CLI uses HTTPClient with the get parameters in NodeJS. The get parameters are passed to the servlet which, based on the operations, tells TrieManager to initiate the appropriate calls. 

You can test the endpoints of the server using wget or curl with the following command: 
$curl "http://ec2-54-234-121-52.compute-1.amazonaws.com/TrieWeb/service/trie?operation=add&word=103484818831834818134" Note: the valid operations that cna be used after "operation=" are add, delete, search, autocomplete, and display. The word can be anything, and to change the word parameter the value after "word=" has to be changed.
