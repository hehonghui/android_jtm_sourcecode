/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2015 Umeng, Inc
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.book.jtm.chap5;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Random;

public final class StudentDAO {

    private SQLiteDatabase mDatabase;

    public StudentDAO(SQLiteDbHelper helper) {
        mDatabase = helper.getWritableDatabase();
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

    public void insert() {
        for (int i = 0; i < 5; i++) {
            ContentValues values = student2ContentValues(mockStudent(i));
            mDatabase.insert(SQLiteDbHelper.TABLE_STUDENT,
                    null, values);
        }

    }

    public void queryStudents() {
        // 相当于select * from students ;
        Cursor cursor = mDatabase.query(SQLiteDbHelper.TABLE_STUDENT, null, null, null, null, null,
                null, null);
        while (cursor.moveToNext()) {
            // 直接通过索引获取字段名
            int stuId = cursor.getInt(0);
            // 先获取tel_no字段的索引，然后再通过索引获取字段值
            String stuName = cursor.getString(cursor.getColumnIndex("name"));
            Log.e("", "### id : " + stuId + ", name = " + stuName);
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
}
