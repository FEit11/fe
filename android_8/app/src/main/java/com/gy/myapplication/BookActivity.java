package com.gy.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class BookActivity extends AppCompatActivity {

    private ListView viewById;
    private MyHelper myHelper;
    private SimpleCursorAdapter adapter;
    private Cursor cursor;
    //SimpleCursorAdapter所需要的参数
    String from[] = new String[]{"bookName", "category", "price"};
    int[] to = new int[]{R.id.tv_book_name, R.id.tv_category, R.id.tv_price};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        myHelper = new MyHelper(this);
        viewById = (ListView) findViewById(R.id.list_view);
        //进入程序时显示数据库中的数据
        show();
    }

    public void save(View v) {
        //获得可读写数据库对象
        SQLiteDatabase db = myHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        EditText et_book_name = (EditText) findViewById(R.id.et_book_name);
        EditText et_book_price = (EditText) findViewById(R.id.et_book_price);
        values.put("name", et_book_name.getText().toString().trim());
        values.put("price", et_book_price.getText().toString());
        long q = db.insert("Book", "name", values);
        Toast.makeText(this, "数据存入成功", Toast.LENGTH_SHORT).show();
        // 数据库发生变化时更新listview
        String[] columns = {"name", "category_id", "price"};
        cursor = db.query("Book", columns, null, null, null, null, null);
        String[] columns2 = {"_id", "category_name"};
        Cursor cursor1 = db.query("Category", columns2, null, null, null, null, null);
        adapter = new SimpleCursorAdapter(this, R.layout.list_item, cursor, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        viewById.setAdapter(adapter);
        db.close();
    }

    public void clear(View v) {
        SQLiteDatabase db = myHelper.getWritableDatabase();
        db.delete("Book", null, null);
        //使自增的_id归零
        db.delete("sqlite_sequence", null, null);
        Toast.makeText(this, "数据库清除成功", Toast.LENGTH_SHORT).show();
        cursor.requery();
        adapter.notifyDataSetChanged();
        db.close();
    }

    public void show() {
        SQLiteDatabase db = myHelper.getWritableDatabase();
        //数据库发生变化时更新listview
        String[] columns = {"b.name", "c.category_name", "b.price"};
        // String selection = "";
        // String[] selectionArgs = {"0"};
        cursor = db.query("Book b join Category c on b.category_id = c._id", columns, null, null, null, null, null);
        adapter = new SimpleCursorAdapter(this, R.layout.list_item, cursor, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        viewById.setAdapter(adapter);
        db.close();
    }
}