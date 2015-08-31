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

package com.book.jtm.chap09.mock;

import static org.mockito.Mockito.*;

import junit.framework.TestCase;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class StuControllerTest extends TestCase {

    StuController mController;
    StudentDAO mStuDao;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mController = new StuController();
        mStuDao = mock(StudentDAO.class);
        mController.setStudentDAO(mStuDao);
    }

    public void testGetStudentInfo() {
        Student returnStudent = new Student();
        returnStudent.id = 123;
        returnStudent.name = "mock-user";

        // 调用getStudentFromDB时返回returnStudent对象
        when(mStuDao.getStudentFromDB(anyInt())).thenReturn(returnStudent);
        // 调用getStudentInfo
        Student student = mController.getStudentInfo(123);
        // 验证数据
        assertEquals(student.id, 123);
        assertEquals(student.name, "mock-user");
    }
    
    public void testGetStudentInfoFromServer() {
        // 调用getStudentFromDB时返回null
        when(mStuDao.getStudentFromDB(anyInt())).thenReturn(null);
        // 调用getStudentInfo
        Student student = mController.getStudentInfo(456);
        // 验证数据
        assertEquals(student.id, 456);
        assertEquals(student.name, "server-user");
    }
    
    public void testCaptureParam() {
        // 注意: 创建一个mock对象
        StuController mockController = mock(StuController.class) ;
        
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                int studentId = (Integer)invocation.getArguments()[0] ;
                System.out.println("学生id : " + studentId);
                assertEquals(666, studentId);
                return null;
            }
        }).when(mockController).getStudentInfo(anyInt()) ;
        
        mockController.getStudentInfo(666) ;
    }

}
