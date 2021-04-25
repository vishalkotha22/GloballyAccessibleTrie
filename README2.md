# Command Line Interface Documentation
Instructions:
1. Go to https://nodejs.org/dist/v14.16.1/ and download the zip file corresponding to your operating system (https://nodejs.org/dist/v14.16.1/node-v14.16.1-win-x64.zip for Windows 64 bit)
2. Extract the file and record the path of the location where it's extracted (for this example, the path is C:\Apps)
3. Press the windows key, type cmd, and hit enters
4. Into command prompt type: "set PATH=C:\Apps\node-v14.16.1-win-x64;%PATH%". Note that the path varies based on where you extracted the NodeJS download and the name of the folder varies dependong on what type of NodeJS was downloaded based on your OS. 
5. Download "trieclient.js" anywhere and record the path. For this example, the path is C:\Apps\test.
6. Then, in command prompt type the following command: "C:\Apps\test> node trieclient.js ec2-18-209-62-171.compute-1.amazonaws.com 80 <add|search|delete|autocomplete|display> <Word>". Note that the path at the beginning varies based on where trieclient.js was downloaded. Additionally, for the second to last word, an operation is selected from the following keywords: add, search, delete, autocomplete, and display. For the last word, the word is the word the operations is run on. For example, if I wanted to add the word "Lakers" to the trie, I would enter the following command: C:\Apps\test> node trieclient.js ec2-18-209-62-171.compute-1.amazonaws.com 80 add Lakers 
