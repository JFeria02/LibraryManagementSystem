class EditBook(path: String, id: Int, title: String, authorNames: String, yearOfPublication: Int, publisher: String, subject: String) {
   init {
       var bookID = id
       val bookTitle = title
       val bookYearOfPublication = yearOfPublication
       val bookPublisher = publisher
       val bookSubject = subject

       if(getPublisherIDFromDatabase(publisher, path) == -1){ // Publisher does not exist in database
            createNewPublisher(publisher, path)
       }

       val bookPublisherID = getPublisherIDFromDatabase(publisher, path)

       if(bookID == -1){ // bookID = -1 when a new book entry is being created
           DatabaseController.Companion.newBookInDB(path, title, yearOfPublication, bookPublisherID, subject);
           bookID = DatabaseController.Companion.getBookIdFromDB(path, title, yearOfPublication, bookPublisherID, subject)
       }



       val authorList = getIndividualAuthors(authorNames)
       val authorIDs = getAuthorIDsFromDatabase(authorList, path)

       createBookAuthorsRelation(bookID, authorIDs, path)
       DatabaseController


   }

    private fun getIndividualAuthors(names: String): List<String>{
        var firstCharIndex = 0
        var authorList = mutableListOf<String>()
        for(c in 0..names.length-1){
            if(names.toCharArray().get(c) == ','){
                authorList.add(names.substring(firstCharIndex, c))
                firstCharIndex = c + 1
            }
            else if (c + 1 == names.length-1){
                authorList.add(names.substring(firstCharIndex))
            }
        }
        return authorList.toList();
    }

    private fun getAuthorIDsFromDatabase(authors: List<String>, path: String): List<Int>{
        var authorIDList = mutableListOf<Int>()
        for(i in 0..authors.size-1){
            val currentID = DatabaseController.Companion.getAuthorIdInDB(path, authors[i])
            if(currentID == -1){
                // Create new entry in AUTHORS table then add new id to list
                DatabaseController.Companion.createAuthorInDB(path, authors[i])
                authorIDList.add(DatabaseController.Companion.getAuthorIdInDB(path, authors[i]))
            }
            else{
                // add to list
                authorIDList.add(currentID)
            }
        }
        return authorIDList;
    }

    private fun getPublisherIDFromDatabase(publisher: String, path: String): Int {
        return DatabaseController.Companion.getPublisherIdFromDB(path, publisher)
    }

    private fun createNewPublisher(publisher: String, path: String){
        DatabaseController.Companion.createNewPublisherInDB(path, publisher)
    }

    private fun createBookAuthorsRelation(bookID: Int, authorIDs: List<Int>, path: String){
        // Delete all previous relations for book id
        DatabaseController.Companion.deleteBookAuthorsInDB(path, bookID)

        // Create new relations for book id
        for(i in 0..authorIDs.size-1){
            DatabaseController.Companion.newBookAuthorRelationInDB(path, bookID, authorIDs[i], i + 1)
        }
    }

}