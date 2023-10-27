import com.ddbb.AUTHORS
import com.ddbb.AllBooksAuthors
import com.ddbb.PUBLISHERS

object BubbleSort {
    @JvmStatic

    fun sortBooks(list: ArrayList<AllBooksAuthors>, sortBy: SortBy) {
        var swap = true
        while (swap){
            swap = false
            for(i in 0 until list.size-1){
                var doSwap = false
                when (sortBy){
                    SortBy.BookID -> if(list[i].id > list[i+1].id) doSwap = true
                    SortBy.BookTitle -> if(list[i].TITLE.toString() > list[i+1].TITLE.toString()) doSwap = true
                    SortBy.BookAuthor -> if(list[i].AUTHOR_NAMES.toString() > list[i+1].AUTHOR_NAMES.toString()) doSwap = true
                    else -> {}
                }
                if(doSwap){
                    val temp = list[i]
                    list[i] = list[i+1]
                    list[i+1] = temp
                    swap = true
                }

            }
        }
    }

    @JvmStatic
    fun sortIDNamePair(list: ArrayList<IDNamePair>, sortBy: SortBy) {
        var swap = true
        while (swap){
            swap = false
            for(i in 0 until list.size-1){
                var doSwap = false
                when (sortBy){
                    SortBy.Name-> if(list[i].name > list[i+1].name) doSwap = true
                    else -> {}
                }
                if(doSwap){
                    val temp = list[i]
                    list[i] = list[i+1]
                    list[i+1] = temp
                    swap = true
                }

            }
        }
    }
}
