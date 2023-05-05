package com.freeloop.juc.cas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * AtomicStampedDemo
 *
 * @author fj
 * @since 2023/5/5 10:34
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
class Book{
    private int id;
    private String bookName;
}
public class AtomicStampedDemo {
    public static void main(String[] args) {
        Book javaBook = new Book(1, "JavaBook");
        AtomicStampedReference<Book> stampedReference = new AtomicStampedReference<>(javaBook, 1);
        System.out.println(stampedReference.getReference()+"\t"+stampedReference.getStamp());
        Book mysqlBook = new Book(2, "MysqlBook");
        boolean b ;
        b= stampedReference.compareAndSet(javaBook, mysqlBook,
                stampedReference.getStamp(), stampedReference.getStamp() + 1);
        System.out.println(b+"\t"+stampedReference.getReference()+"\t"+stampedReference.getStamp());
        b = stampedReference.compareAndSet(mysqlBook, javaBook,
                stampedReference.getStamp(), stampedReference.getStamp() + 1);
        System.out.println(b+"\t"+stampedReference.getReference()+"\t"+stampedReference.getStamp());

    }
}
