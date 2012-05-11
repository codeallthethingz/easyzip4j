easyzip4j
=========

single line directory zipping, supports war type zipping where the root folder is not included.

Usage
=====

    EasyZip.zip("./folderToZip", "./myZipFile.zip");

OR 

    EasyZip.zip("./folderToZip", "./myZipFile.zip", 
        new EasyZipParameters().setZipFolderContentsNotFolder(true));