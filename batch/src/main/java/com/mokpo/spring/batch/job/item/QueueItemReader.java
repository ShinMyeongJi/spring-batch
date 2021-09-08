package com.mokpo.spring.batch.job.item;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

public class QueueItemReader<T> implements ItemReader<T> {

    private Queue<T> queue;

    public QueueItemReader(Queue<T> queue) {
        this.queue = new LinkedList<>(queue);
    }


    @Override
    public T read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        return queue.poll();
    }
}
