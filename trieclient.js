'use strict';

for (let j = 0; j < process.argv.length; j++) {
    console.log(j + ' -> ' + (process.argv[j]));
}

if(process.argv.length !== 6){
	console.log('Sorry, invalid input data provided. Usage: node trieclient.js <host> <port> <add|seach|delete|autocomplete|display> <Word>');
	process.exit(1)
}

//Check Operation value
const args = process.argv.slice(2)

switch (args[2]) {
case 'add':
    console.log('Add Keyword Common Operation.');
    break;
case 'search':
    console.log('Search Keyword Common Operation.');
    break;
case 'delete':
    console.log('Delete Keyword Common Operation.');
    break;	
case 'autocomplete':
    console.log('Auto Complete Keyword Common Operation.');
    break;	
case 'display':
    console.log('Display Common Operation.');
    break;		
default:
    console.log('Sorry, that is not something known map operation. Enter a valid input insert|find|delete');
	process.exit(1)
} 

//HTTP Connection
const httpConn = require('http');

httpConn.request(
    {
	  host: `${args[0]}`,
	  port: `${args[1]}`,
	  path: `/TrieWeb/service/trie?operation=${args[2]}&word=${args[3]}`
    },
    httpRes => {
      let httpResData = ""
      httpRes.on("data", bytesRead => {
        httpResData += bytesRead
      })
      httpRes.on("end", () => {
        console.log(httpResData)
      })
    }
).end()

