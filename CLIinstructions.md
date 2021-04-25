# Command Line Interface Documentation
Instructions:
1. Go to https://nodejs.org/en/download and download the file corresponding to your operating system
  * For Windows 64-bit, a download link is https://nodejs.org/dist/v14.16.1/node-v14.16.1-win-x64.zip
  * For Linux 64-bit, a download link is https://nodejs.org/dist/v14.16.1/node-v14.16.1-linux-x64.tar.xz
  * For MacOS 64-bit, a download link is https://nodejs.org/dist/v14.16.1/node-v14.16.1-darwin-x64.tar.gz
2. Extract the file and record the path of the location where it's extracted (for this example, the path is C:\Apps)
3. Press the windows key, type cmd, and hit enter
4. Into command prompt type: "set PATH=C:\Apps\node-v14.16.1-win-x64;%PATH%". Note that the path varies based on where you extracted the NodeJS download and the name of the folder varies depending on what type of NodeJS was downloaded based on your OS. Additionally, in some cases, the command may be "set PATH=C:\Apps\node-v14.16.1-win-x64\node-v14.16.1-win-x64;%PATH%" if the NodeJS was copied for some reason.
5. Download "trieclient.js" anywhere and record the path. For this example, the path is C:\Apps\test.
6. Type the following command: "cd C:\Apps\test". Note that the path varies based on where trieclient.js was downloaded.
7. Then, in command prompt type the following command: "node trieclient.js ec2-54-234-121-52.compute-1.amazonaws.com 80 <add|search|delete|autocomplete|display> <input string>". Note that the path at the beginning varies based on where trieclient.js was downloaded. Additionally, for the second to last word, an operation is selected from the following keywords: add, search, delete, autocomplete, and display. For the last word, the word is the word the operation is run on. For example, if I wanted to add the word "Lakers" to the trie, I would enter the following command without quotes:
"node trieclient.js ec2-54-234-121-52.compute-1.amazonaws.com 80 add Lakers". Finally, a note is that for the display argument the last word doesn't matter as it just displays the trie.
