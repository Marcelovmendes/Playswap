package com.example.spotify.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;
import java.text.ParseException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public abstract class ExternalServiceAdapter {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    protected final SpotifyApi spotifyApi;
    protected final SpotifyApiExceptionTranslator exceptionTranslator;

    protected ExternalServiceAdapter(SpotifyApi spotifyApi, SpotifyApiExceptionTranslator exceptionTranslator) {
        this.spotifyApi = spotifyApi;
        this.exceptionTranslator = exceptionTranslator;
    }


    protected <T> T executeSync(SpotifyOperation<T> operation, String operationName) {
        try {
            return operation.execute();
        } catch (Throwable exception) {
            log.error("Error {}: {}", operationName, exception.getMessage());
            throw exceptionTranslator.translate(exception);
        }
    }

    protected <T> T executeAsync(CompletableFuture<T> future, String operation) {
        return future
                .orTimeout(15, TimeUnit.SECONDS)
                .exceptionally(throwable -> {
                    log.error("Error {}: {}", operation, throwable.getMessage());
                    throw exceptionTranslator.translate(throwable);
                })
                .join();
    }
    @FunctionalInterface
    public interface SpotifyOperation<T> {
        T execute() throws IOException, SpotifyWebApiException, ParseException, org.apache.hc.core5.http.ParseException;
    }


}
