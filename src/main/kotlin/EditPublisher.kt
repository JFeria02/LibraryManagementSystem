class EditPublisher(path: String, id: Int, name: String) {
    init {
        var publisherID = id;
        val publisherName = name;

        if(publisherID == -1 && DatabaseController.Companion.getPublisherIdFromDB(path, name) == -1) {
            // Create a new entry for the publisher
            DatabaseController.Companion.createNewPublisherInDB(path, publisherName)
        }
        else{
            // Edit entry
            DatabaseController.Companion.editPublisher(path, id, name);
        }
    }
}