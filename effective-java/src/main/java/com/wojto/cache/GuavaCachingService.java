package com.wojto.cache;

import com.google.common.cache.*;
import com.wojto.model.Message;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

//@NoArgsConstructor
@RequiredArgsConstructor(staticName = "getGuavaCache")
@Slf4j
public class GuavaCachingService implements CachingService {

    CacheLoader<String, Message> loader;
    RemovalListener<String, Message> listener;
    LoadingCache<String, Message> cache;

    {
        loader = new CacheLoader<String, Message>() {
            @Override
            public Message load(String key) {
                return Message.of(key);
            }
        };

        listener = new RemovalListener<String, Message>() {
            @Override
            public void onRemoval(RemovalNotification<String, Message> m) {
                if (m.wasEvicted()) {
                    log.info("Evicted message: " + m.toString());
                    System.out.println("Evicted message: " + m.toString());
                }
            }
        };

        cache = CacheBuilder.newBuilder()
                .maximumSize(100000)
                .expireAfterAccess(5, TimeUnit.SECONDS)
                .removalListener(listener)
                .recordStats()
                .build(loader);

    }

    @Override
    public Optional<Message> getMessage(String message) {
            return Optional.ofNullable(cache.getIfPresent(message));
    }

    @Override
    public void postMessage(Message message) {
        cache.put(message.getContent(), message);
    }

    @Override
    public void printCacheStats() {
        CacheStats stats = cache.stats();
        System.out.printf("Hit count: %d, Miss count: %d, Hit rate: %.2f, Miss rate: %.2f, Eviction count: %d%n",
                stats.hitCount(), stats.missCount(), stats.hitRate(), stats.missRate(), stats.evictionCount());
    }
}
