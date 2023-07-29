package com.wojto.cache;

import com.wojto.model.Message;

import java.util.Optional;

public interface CachingService {

    public Optional<Message> getMessage(String message);
    public void postMessage(Message message);
    public void printCacheStats();

}
