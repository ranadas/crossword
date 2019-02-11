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
        val pattern = Pattern.compile("^$beginChars", Pattern.CASE_INSENSITIVE)
        return students.stream()
                .filter({ stu -> stu.id == id })
                .filter({ stu -> match(stu.name, pattern) })
                .collect(Collectors.toList<Student>())
    }

    //https://howtodoinjava.com/regex/word-boundary-starts-ends-with/
    fun search(beginChars: String): List<Student> {
        val pattern = Pattern.compile("^$beginChars", Pattern.CASE_INSENSITIVE)
        return students.stream()
                .filter({ stu -> match(stu.name, pattern) })
                .collect(Collectors.toList<Student>())
    }

    private fun match(word: String, pattern: Pattern): Boolean {
        val matcher = pattern.matcher(word)
        val found = matcher.find()
        return found
    }
}