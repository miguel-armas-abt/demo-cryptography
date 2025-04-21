package com.demo.poc.commons.core.errors.exceptions;

import com.demo.poc.commons.custom.exceptions.ErrorDictionary;
import lombok.Getter;

@Getter
public class NoSuchCacheConfigException extends GenericException {

    public NoSuchCacheConfigException() {
        super(ErrorDictionary.NO_SUCH_CACHE_CONFIG.getMessage(), ErrorDictionary.parse(NoSuchCacheConfigException.class));
    }
}
