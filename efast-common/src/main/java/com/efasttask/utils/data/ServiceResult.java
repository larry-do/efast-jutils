package com.efasttask.utils.data;

import com.efasttask.utils.message.Message;

import java.util.List;

public class ServiceResult<E> {
    private final boolean successfully;
    private E data;
    private List<Message> messages;

    public ServiceResult(boolean successfully) {
        this.successfully = successfully;
    }

    public ServiceResult(boolean successfully, List<Message> messages) {
        this.successfully = successfully;
        this.messages = messages;
    }

    public ServiceResult(boolean successfully, E data) {
        this.successfully = successfully;
        this.data = data;
    }

    public ServiceResult(boolean successfully, E data, List<Message> messages) {
        this.successfully = successfully;
        this.data = data;
        this.messages = messages;
    }

    public boolean isSuccessfully() {
        return successfully;
    }

    public E getData() {
        return data;
    }

    public List<Message> getMessages() {
        return messages;
    }
}
