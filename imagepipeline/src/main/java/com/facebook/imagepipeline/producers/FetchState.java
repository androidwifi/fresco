/*
 * Copyright (c) 2015-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

package com.facebook.imagepipeline.producers;

import android.net.Uri;

import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.memory.PooledByteBuffer;

/**
 * Used by {@link NetworkFetcher} to encapsulate the state of one network fetch.
 * <p>
 * <p>Implementations can subclass this to store additional fetch-scoped fields.
 */
public class FetchState {

    private final Consumer<CloseableReference<PooledByteBuffer>> mConsumer;
    private final ProducerContext mContext;
    private long mLastIntermediateResultTimeMs;

    public FetchState(
            Consumer<CloseableReference<PooledByteBuffer>> consumer,
            ProducerContext context) {
        mConsumer = consumer;
        mContext = context;
        mLastIntermediateResultTimeMs = 0;
    }

    public Consumer<CloseableReference<PooledByteBuffer>> getConsumer() {
        return mConsumer;
    }

    public ProducerContext getContext() {
        return mContext;
    }

    public String getId() {
        return mContext.getId();
    }

    public ProducerListener getListener() {
        return mContext.getListener();
    }

    public Uri getUri() {
        return mContext.getImageRequest().getSourceUri();
    }

    public long getLastIntermediateResultTimeMs() {
        return mLastIntermediateResultTimeMs;
    }

    public void setLastIntermediateResultTimeMs(long lastIntermediateResultTimeMs) {
        mLastIntermediateResultTimeMs = lastIntermediateResultTimeMs;
    }
}
