easyzip4j
=========

single line directory zipping, supports war type zipping where the root folder is not included.

Usage
=====

    EasyZip.zip("./folderToZip", "./myZipFile.zip");

Or

    EasyZip.zip("./folderToZip", "./myZipFile.zip", 
        new EasyZipParameters().setZipFolderContentsNotFolder(true));
   
And
        
    String fileContents = EasyZip.getStringForEntry("myZipFile.zip",
		"subfolder/subfolderfull/file3.txt");