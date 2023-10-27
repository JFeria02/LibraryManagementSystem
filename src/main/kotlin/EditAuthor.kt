class EditAuthor(path: String, id: Int, name: String) {
    init {
        var authorID = id;
        val authorName = name;

        if(authorID == -1 && DatabaseController.Companion.getAuthorIdInDB(path, authorName) == -1) {
            // Create a new entry for the author
            DatabaseController.Companion.createAuthorInDB(path, authorName)
            authorID = DatabaseController.Companion.getAuthorIdInDB(path, authorName)
        }
        else{
            // Edit entry
            DatabaseController.Companion.editAuthor(path, id, name)
        }

    }
}