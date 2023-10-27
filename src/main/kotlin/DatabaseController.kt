import com.ddbb.*
import com.example.Database
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.asJdbcDriver
import com.zaxxer.hikari.HikariDataSource
import java.lang.IndexOutOfBoundsException
import javax.xml.crypto.Data

class DatabaseController {

    companion object {
        private fun getSqlDriver(path: String = "src/main/resources/books.sqlite"): SqlDriver {
            val ds = HikariDataSource()
            ds.jdbcUrl = "jdbc:sqlite:" + path
            ds.driverClassName = "org.sqlite.JDBC"
            ds.username = ""
            ds.password = ""
            return ds.asJdbcDriver()
        }

        fun checkConnection(path: String): Boolean{
            return HikariDataSource().isRunning
        }

        fun allBooksFromDB(path: String = "src/main/resources/books.sqlite"): List<BOOKS> {
            val database = Database(getSqlDriver(path))
            val bookQueries = database.booksQueries
            return bookQueries.allBooks().executeAsList()
        }

        fun allBooksWithAuthorsFromDB(path: String): List<AllBooksAuthors> {
            val database = Database(getSqlDriver(path))
            val bookQueries = database.booksQueries
            return bookQueries.allBooksAuthors().executeAsList()
        }

        fun allAuthorsFromDB(path: String): List<AUTHORS> {
            val database = Database(getSqlDriver(path))
            val authorQueries = database.authorsQueries
            return authorQueries.allAuthors().executeAsList()
        }

        fun searchBooksInDB(path: String, searchTerm: String): List<SearchBooksAuthors> {
            val database = Database(getSqlDriver(path))
            val bookQueries = database.booksQueries
            val finalTerm = "%$searchTerm%"
            return bookQueries.searchBooksAuthors(finalTerm, finalTerm, finalTerm, finalTerm, finalTerm).executeAsList()
        }

        fun getAuthorIdInDB(path: String, name: String): Int {
            val database = Database(getSqlDriver(path))
            val authorsQueries = database.authorsQueries
            return try{
                authorsQueries.getAuthorID(name).executeAsList()[0].toInt()
            } catch (e: IndexOutOfBoundsException){
                -1
            }

        }

        fun createAuthorInDB(path: String, name: String){
            val database = Database(getSqlDriver(path))
            val authorsQueries = database.authorsQueries
            authorsQueries.newAuthor(name)
            HikariDataSource().close()
        }

        fun deleteBookAuthorsInDB(path: String, bookID: Int){
            val database = Database(getSqlDriver(path))
            val bookAuthorsQueries = database.bookAuthorsQueries
            bookAuthorsQueries.deleteAllBookAuthors(bookID.toLong())
            HikariDataSource().close()

        }

        fun newBookAuthorRelationInDB(path: String, bookID: Int, authorID: Int, order: Int){
            val database = Database(getSqlDriver(path))
            val bookAuthorsQueries = database.bookAuthorsQueries
            bookAuthorsQueries.newBookAuthor(bookID.toLong(), authorID.toLong(), order.toLong())
            HikariDataSource().close()

        }

        fun newBookInDB(path: String, title: String, yearOfPublication: Int, publisherID: Int, subject: String){
            val database = Database(getSqlDriver(path))
            val booksQueries = database.booksQueries
            booksQueries.newBook(title, yearOfPublication.toLong(), publisherID.toLong(), subject)
            HikariDataSource().close()

        }

        fun getBookIdFromDB(path: String, title: String, yearOfPublication: Int, publisherID: Int, subject: String): Int{
            val database = Database(getSqlDriver(path))
            val booksQueries = database.booksQueries
            return booksQueries.getBookID(title, yearOfPublication.toLong(), publisherID.toLong(), subject).executeAsList()[0].toInt()

        }

        fun getPublisherIdFromDB(path: String, name: String): Int{
            val database = Database(getSqlDriver(path))
            val publishersQueries = database.publishersQueries
            return try {
                publishersQueries.getPublisherID(name).executeAsList()[0].toInt()
            } catch (e: IndexOutOfBoundsException){
                -1
            }

        }

        fun createNewPublisherInDB(path: String, name: String){
            val database = Database(getSqlDriver(path))
            val publishersQueries = database.publishersQueries
            publishersQueries.newPublisher(name)
        }

        fun allPublishersFromDB(path: String): List<PUBLISHERS>{
            val database = Database(getSqlDriver(path))
            val publishersQueries = database.publishersQueries
            return publishersQueries.allPublishers().executeAsList()
        }

        fun editAuthor(path: String, id: Int, name: String){
            val database = Database(getSqlDriver(path))
            val authorsQueries = database.authorsQueries
            authorsQueries.editAuthor(name, id.toLong())
        }

        fun editPublisher(path: String, id: Int, name: String){
            val database = Database(getSqlDriver(path))
            val publishersQueries = database.publishersQueries
            publishersQueries.editPublisher(name, id.toLong())
        }

    }
}
