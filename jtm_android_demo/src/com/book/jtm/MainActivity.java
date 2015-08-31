
package com.book.jtm;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.activeandroid.query.Update;
import com.book.jtm.chap03.SimpleAsyncTask;
import com.book.jtm.chap06.StubLayoutActivity;
import com.book.jtm.chap06.trace.TraceActivity;
import com.book.jtm.chap5.DbCommand;
import com.book.jtm.chap5.SQLiteDbHelper;
import com.book.jtm.chap5.Student;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class MainActivity extends ActionBarActivity {

    SQLiteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);
        // setContentView(R.layout.activity_circle_img);
        // setContentView(R.layout.activity_normal_img);

        // new Thread() {
        // public void run() {
        // try {
        // HttpClientDemo.sendRequest("POST", "http://www.devtf.cn");
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        //
        // };
        // }.start();

        setContentView(R.layout.activity_vertical_layout);
        // setContentView(new CanvasView(getApplicationContext()));
        // startActivity(new Intent(this, AnimActivity.class));
        findViewById(R.id.root_layout).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // startActivity(new Intent(MainActivity.this,
                // LeakActivity.class));
                // startActivity(new Intent(MainActivity.this,
                // LayoutActivity.class));
                startActivity(new Intent(MainActivity.this, StubLayoutActivity.class));
            }
        });

        mDatabase = new SQLiteDbHelper(getApplicationContext()).getWritableDatabase();
        // insertStudents();

        // queryStudents();
        startActivity(new Intent(MainActivity.this, TraceActivity.class));

        // 插入数据
        // insertStudentsWithActive();
        // updateStudent();
        // deleteWithActive();
        // queryStudentWithActive();

        new DbCommand<List<Student>>() {
            @Override
            protected List<Student> doInBackground() {
                Log.e("", "### 执行 数据库的线程 " + Thread.currentThread().getName());
                return queryAllStudents();
            }

            @Override
            protected void onPostExecute(List<Student> result) {
                Log.e("", "### --> 获取 数据库操作结果的线程 " + Thread.currentThread().getName());
                for (Student student : result) {
                    Log.d("", "### 学生信息 : " + student);
                }
            }
        }.execute();

    }

    public List<Student> queryAllStudents() {
        // 查询数据
        Cursor cursor = mDatabase.rawQuery("select * from students", null);
        // 构建结果集
        List<Student> allStudents = new ArrayList<Student>(cursor.getCount());
        while (cursor.moveToNext()) {
            Student student = new Student();
            // 直接通过索引获取字段名
            student.id = cursor.getInt(0);
            // 先获取tel_no字段的索引，然后再通过索引获取字段值
            student.name = cursor.getString(cursor.getColumnIndex("name"));
            student.tel_no = cursor.getString(2);
            student.cls_id = cursor.getInt(3);
            // 将查询到的数据添加到结果列表中
            allStudents.add(student);
        }
        // 关闭光标
        cursor.close();
        return allStudents;
    }

    // 输出数据库中所有学生的信息
    private void printAllStudnetsInfo() {
        // 构建结果集
        List<Student> allStudents = new ArrayList<Student>();
        for (int i = 0; i < 5; i++) {
            Student student = new Student();
            student.id = i;
            student.name = "user - " + i;
            student.tel_no = String.valueOf(new Random().nextInt(200000));
            student.cls_id = new Random().nextInt(5);
        }

        Collections.sort(allStudents, new Comparator<Student>() {
            @Override
            public int compare(Student lhs, Student rhs) {
                return (int) (lhs.id - rhs.id);
            }
        });

        for (Student student : allStudents) {
            Log.d("", "### 学生信息 : " + student);
        }
    }

    // =============== ActiveAndroid ================
    public void insertStudentsWithActive() {
        for (int i = 0; i < 5; i++) {
            // 直接调用实体类的save函数存储数据到数据库
            mockStudent(i).save();
        }
    }

    public void deleteWithActive() {
        mockStudent(2).delete();
        // new Delete().from(Student.class).where("id=?", 2).execute();
    }

    public void updateStudent() {
        new Update(Student.class).set("name=?", "Mr.Simple").where("sid=?", 1).execute();
    }

    public void loadStudent() {
        Student student = Student.load(Student.class, 3);
        Log.e("", "### id = 3的用户信息 : " + student);

        Student student2 = new Select().from(Student.class).where("id=?", 3).executeSingle();
        Log.e("", "### id = 3的用户信息 : " + student2);
    }

    private void queryStudentWithActive() {
        List<Student> result = new Select().from(Student.class).where("sid>? and cls_id=3", "1")
                .execute();
        for (Student student : result) {
            Log.i("", "### 学生信息: " + student);
        }

        // new
        // Select().from(Student.class).innerJoin(Student.class).on("").where("sid=?",
        // "1").groupBy("").orderBy("").limit("").offset("").execute();
    }

    // =============== 原生代码 ================
    public void insertStudents() {
        for (int i = 0; i < 5; i++) {
            ContentValues values = student2ContentValues(mockStudent(i));
            mDatabase.insert(SQLiteDbHelper.TABLE_STUDENT,
                    null, values);
        }
    }

    private Student mockStudent(int i) {
        Student student = new Student();
        student.id = i;
        student.name = "user - " + i;
        student.tel_no = String.valueOf(new Random().nextInt(200000));
        student.cls_id = new Random().nextInt(5);
        return student;
    }

    private ContentValues student2ContentValues(Student student) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", student.id);
        contentValues.put("name", student.name);
        contentValues.put("tel_no", student.tel_no);
        contentValues.put("cls_id", student.cls_id);
        return contentValues;
    }

    public void queryStudents() {
        // 相当于select * from students ;
        // Cursor cursor = mDatabase.query(SQLiteDbHelper.TABLE_STUDENT, null,
        // "cls_id=? and id>=1", new String[]{"3"}, null, null,
        // null, null);

        // Cursor cursor = mDatabase.query(SQLiteDbHelper.TABLE_STUDENT, new
        // String[]{"id,name"}, "id>=?", new String[]{"1"}, "cls_id",
        // "cls_id>=2",
        // "id desc", "1");

        // Cursor cursor = mDatabase.query(SQLiteDbHelper.TABLE_STUDENT, new
        // String[]{"id,name"}, "id>=?", new String[]{"1"}, null, null,
        // "id desc", "3");

        Cursor cursor = mDatabase.query(SQLiteDbHelper.TABLE_STUDENT, new String[] {
                "count(*),cls_id"
        }, null, null, "cls_id", null, null, null);

        while (cursor.moveToNext()) {
            // // 直接通过索引获取字段名
            // int stuId = cursor.getInt(0);
            // // 先获取tel_no字段的索引，然后再通过索引获取字段值
            // String stuName = cursor.getString(cursor.getColumnIndex("name"));
            // Log.e("", "### id : " + stuId + ", name = " + stuName);

            // 直接通过索引获取字段名
            // int stuId = cursor.getInt(0);
            // 先获取tel_no字段的索引，然后再通过索引获取字段值
            // String stuName = cursor.getString(cursor.getColumnIndex("name"));
            Log.e("", "### count : " + cursor.getInt(0) + ", cls_id = " + cursor.getInt(1));
        }
        // 关闭光标
        cursor.close();
    }

    public void delete(String whereClause, String[] whereArgs) {
        mDatabase.delete(SQLiteDbHelper.TABLE_STUDENT, whereClause, whereArgs);
    }

    public void update(ContentValues values, String whereClause, String[] whereArgs) {
        mDatabase.updateWithOnConflict(SQLiteDbHelper.TABLE_STUDENT, values, whereClause,
                whereArgs, SQLiteDatabase.CONFLICT_REPLACE);
    }

    protected void async() {
        new SimpleAsyncTask<String>() {

            private void makeToast(String msg) {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            protected void onPreExecute() {
                makeToast("onPreExecute");
            }

            @Override
            protected String doInBackground() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "Hello";
            }

            @Override
            protected void onPostExecute(String result) {
                makeToast("onPostExecute : " + result);
            }
        }.execute();
    }
}
