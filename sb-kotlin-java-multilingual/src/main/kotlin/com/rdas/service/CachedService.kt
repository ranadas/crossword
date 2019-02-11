package com.rdas.service

import com.hazelcast.core.HazelcastInstance
import com.rdas.entity.Student
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import java.util.regex.Pattern
import java.util.stream.Collectors

@Service
class CachedService(@Qualifier("crosswordCacheHzInstance") val hazelcast: HazelcastInstance) {
    private val students: List<Student> = hazelcast.getList("students-list")

    fun search(id: Long, beginChars: String): List<Student> {

        return students.stream()
                .filter({ stu -> stu.id == id })
                //.filter({ stu -> match(stu.name, pattern) })
                .collect(Collectors.toList<Student>())
    }

    private fun match(word: String, pattern: Pattern): Boolean {
        //log.info("\ttesting {} with {} ", word, pattern.toString())
        val matcher = pattern.matcher(word)
        val found = matcher.find()
        //log.info("\t {}  is found ? {} \n", word, found)
        return found
    }
}