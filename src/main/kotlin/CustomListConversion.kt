import com.ddbb.AUTHORS
import com.ddbb.AllBooksAuthors
import com.ddbb.PUBLISHERS
import com.ddbb.SearchBooksAuthors

class CustomListConversion {

    companion object{
        // Converts search results into an AllBooksAuthors list that which is the type of list used by the rest of the program
        fun searchBooksAuthorsToAllBooksAuthors(list: List<SearchBooksAuthors>) : List<AllBooksAuthors> {
            var newList = mutableListOf<AllBooksAuthors>()
            var entryIndex: Int = 0
            for(entry in list){
                val newEntry = AllBooksAuthors(
                        entry.id,
                        entry.TITLE,
                        entry.AUTHOR_NAMES,
                        entry.YEAR_OF_PUBLICATION,
                        entry.PUBLISHER_NAME,
                        entry.SUBJECT
                )
                newList.add(newEntry)
            }
            return newList.toList()
        }

        fun authorsToPair(list: List<AUTHORS>) : List<IDNamePair> {
            var newList = mutableListOf<IDNamePair>()
            var entryIndex: Int = 0
            for(entry in list){
                val newEntry = IDNamePair(
                        entry.AUTHOR_ID,
                        entry.NAME
                )
                newList.add(newEntry)
            }
            return newList.toList()
        }

        fun publishersToPair(list: List<PUBLISHERS>) : List<IDNamePair> {
            var newList = mutableListOf<IDNamePair>()
            var entryIndex: Int = 0
            for(entry in list){
                val newEntry = IDNamePair(
                        entry.PUBLISHER_ID,
                        entry.PUBLISHER_NAME.toString()
                )
                newList.add(newEntry)
            }
            return newList.toList()
        }
    }

}