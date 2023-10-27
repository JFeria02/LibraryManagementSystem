import com.ddbb.AllBooksAuthors

object MergeSort {
    @JvmStatic
    fun sort(list: ArrayList<AllBooksAuthors>, left: Int, right: Int, sortBy: SortBy) {
        if(left >= right){
            return
        }

        var mid = (left + right) / 2

        //System.out.println("First Sort: left = " + left + ", mid = " + mid)
        sort(list, left, mid, sortBy)
        //System.out.println("Second Sort: mid+1 = " + (mid+1) + ", right = " + right)
        sort(list, mid+1, right, sortBy)
        merge(list, left, mid, right, sortBy)


    }

    private fun merge(list: ArrayList<AllBooksAuthors>, left: Int, mid: Int, right: Int, sortBy: SortBy){
        // Size of the two arrays to be merged
        var n1 = mid - left + 1
        var n2 = right - mid

        // Temp arrays
        var leftArray = mutableListOf<AllBooksAuthors>()
        var rightArray = mutableListOf<AllBooksAuthors>()

        // Copy date to temp arrays
        for(i in 0 until n1){
            leftArray.add(i, list[left + i])
        }
        for(j in 0 until n2){
            rightArray.add(j, list[mid + 1 + j])
        }

        // Merge the arrays

        // Initial index for both arrays
        var i1 = 0
        var i2 = 0

        // Initial index of merged array
        var i3 = left;
        while (i1 < n1 && i2 < n2){
            var doLeftMerge = false
            when (sortBy){
                SortBy.BookID -> if(leftArray[i1].id <= rightArray[i2].id) doLeftMerge = true
                SortBy.BookTitle -> if(leftArray[i1].TITLE.toString() <= rightArray[i2].TITLE.toString()) doLeftMerge = true
                SortBy.BookAuthor ->if(leftArray[i1].AUTHOR_NAMES.toString() <= rightArray[i2].AUTHOR_NAMES.toString()) doLeftMerge = true
                SortBy.Name -> println("NAME not valid for MergeSort")
            }

            if(doLeftMerge){
                list[i3] = leftArray[i1]
                i1++
            }
            else{
                list[i3] = rightArray[i2]
                i2++
            }
            i3++
        }

        // Copy remaining elements of leftArray
        while (i1 < n1){
            list[i3] = leftArray[i1]
            i1++
            i3++
        }

        // Copy remaining elements of rightArray
        while(i2 < n2){
            list[i3] = rightArray[i2]
            i2++
            i3++
        }
    }

}