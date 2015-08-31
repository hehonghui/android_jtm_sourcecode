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

package com.book.jtm.chap011.extractmethod;

import com.book.jtm.chap011.Student;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class TestV2 {

    public void main(String[] args) {

        Test test = new Test();
        test.printAllStudnetsInfo();
    }

    // 输出数据库中所有学生的信息
    protected void printAllStudnetsInfo() {
        // 从数据库中读取所有学生信息
        List<Student> allStudents = loadAllStudentsFromDB();
        // 排序
        sortStudents(allStudents);
        // 输出信息
        printStudentsInfo(allStudents);
    }

    private List<Student> loadAllStudentsFromDB() {
        List<Student> allStudents = new ArrayList<Student>();
        for (int i = 5; i > 0; i--) {
            Student student = new Student();
            student.id = i;
            student.name = "user - " + i;
            student.tel_no = String.valueOf(new Random().nextInt(200000));
            student.cls_id = new Random().nextInt(5);
            // 添加到集合中
            allStudents.add(student);
        }
        return allStudents;
    }

    private void sortStudents(List<Student> students) {
        Collections.sort(students, new Comparator<Student>() {
            @Override
            public int compare(Student lhs, Student rhs) {
                return (int) (lhs.id - rhs.id);
            }
        });
    }

    private void printStudentsInfo(List<Student> students) {
        for (Student student : students) {
            System.out.println("### 学生信息 : " + student);
        }
    }
}
