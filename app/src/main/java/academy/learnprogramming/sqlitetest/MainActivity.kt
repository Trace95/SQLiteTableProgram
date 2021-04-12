package academy.learnprogramming.sqlitetest

import android.content.ContentValues
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log


private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val database =
            baseContext.openOrCreateDatabase("sqlite-test-1.db", Context.MODE_PRIVATE, null)
        database.execSQL("DROP TABLE IF EXISTS contacts")

        var sql =
            "CREATE TABLE IF NOT EXISTS contacts(_id INTEGER PRIMARY KEY NOT NULL, name TEXT, phone INTEGER, email TEXT)"
        Log.d(TAG, "onCreate: sql is $sql")
        database.execSQL(sql)

        sql = "INSERT INTO contacts(name, phone, email) VALUES ('tim', 6556789, 'tim@email.com')"
        Log.d(TAG, "onCreate: sql is $sql")
        database.execSQL(sql)


        // content values is just a wrapper class for a hashmap storing information in a key/value pair
        val values = ContentValues().apply {
            put("name", "Fred")
            put("phone", 12345)
            put("email", "fred@nurk.com")
        }

        val generatedID = database.insert("contacts", null, values)
        Log.d(TAG, "onCreate: record added with id $generatedID")

        val query = database.rawQuery("SELECT * FROM contacts", null)
        query.use {
            while (it.moveToNext()) {
                // cycle through all records
                with(it) {
                    val id = getLong(0)
                    val name = getString(1)
                    val phone = getInt(2)
                    val email = getString(3)
                    val result = "ID: $id. Name = $name phone = $phone email = $email"
                    Log.d(TAG,"onCreate: reading data $result")
                }
            }
        }

        database.close()

        Log.d(TAG, "onCreate: query = $query")
        Log.d(TAG, "onCreate: record added with id $generatedID")

    }

}