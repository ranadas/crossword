package com.rdas.repo

import com.rdas.entity.Student
import org.apache.ibatis.annotations.*

@Mapper
interface StudentMyBRepository {
    @Select("SELECT * FROM student WHERE id = #{id}")
    fun findById(id: Long): Student

    @Delete("DELETE FROM student WHERE id = #{id}")
    fun deleteById(id: Long): Int

    @Insert("INSERT INTO student(id, name, passport_number) VALUES (#{id}, #{name}, #{passport})")
    fun insert(student: Student): Int

    @Update("Update student set name=#{name}, passport_number=#{passport} where id=#{id}")
    fun update(student: Student): Int

    @Select("select * from student")
    fun findAll(): List<Student>

    //https://community.oracle.com/thread/235143
    /**
    Select *
    From table
    Where Col IN (123,123,222,....)
    or Col IN (456,878,888,....)
     */
    //https://stackoverflow.com/questions/46987916/redshift-large-in-clause-best-practices
    @Select("<script>", "select", " * ", "FROM Student", "WHERE  id IN  " +
    "<foreach item='item' index='index' collection='list' open='(' separator=',' close=')'> #{item} </foreach>" +
    "</script>")
    @Results
    fun selectByKeys(@Param("list") ids:List<Long>):List<Student>
}