/*
 * Copyright (c) 2015-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

package com.facebook.imagepipeline.bitmaps;

import java.util.List;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Build;

import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.memory.PooledByteBuffer;

/**
 * Bitmap factory optimized for the platform.
 */
public class PlatformBitmapFactory {

    private final GingerbreadBitmapFactory mGingerbreadBitmapFactory;
    private final DalvikBitmapFactory mDalvikBitmapFactory;
    private final ArtBitmapFactory mArtBitmapFactory;

    public PlatformBitmapFactory(
            GingerbreadBitmapFactory gingerbreadBitmapFactory,
            DalvikBitmapFactory dalvikBitmapFactory,
            ArtBitmapFactory artBitmapFactory) {
        mGingerbreadBitmapFactory = gingerbreadBitmapFactory;
        mDalvikBitmapFactory = dalvikBitmapFactory;
        mArtBitmapFactory = artBitmapFactory;
    }

    /**
     * Creates a bitmap of the specified width and height.
     *
     * @param width  the width of the bitmap
     * @param height the height of the bitmap
     * @return a reference to the bitmap
     * @throws TooManyBitmapsException    if the pool is full
     * @throws java.lang.OutOfMemoryError if the Bitmap cannot be allocated
     */
    @SuppressLint("NewApi")
    public CloseableReference<Bitmap> createBitmap(int width, int height) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return mArtBitmapFactory.createBitmap(width, height);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return mDalvikBitmapFactory.createBitmap((short) width, (short) height);
        } else {
            return mGingerbreadBitmapFactory.createBitmap(width, height);
        }
    }

    /**
     * Associates bitmaps with the bitmap counter.
     * <p>
     * <p>If this method throws TooManyBitmapsException, the code will have called
     * {@link Bitmap#recycle} on the bitmaps.</p>
     *
     * @param bitmaps the bitmaps to associate
     * @return the references to the bitmaps that are now tied to the bitmap pool
     * @throws TooManyBitmapsException if the pool is full
     */
    public synchronized List<CloseableReference<Bitmap>> associateBitmapsWithBitmapCounter(
            final List<Bitmap> bitmaps) {
        // Refactoring note, this code path always used ICS pool. Should this be a no-op on Lollipop?
        return mDalvikBitmapFactory.associateBitmapsWithBitmapCounter(bitmaps);
    }

    /**
     * Creates a bitmap from encoded bytes. Supports JPEG but callers should use
     * {@link #decodeJPEGFromPooledByteBuffer} for partial JPEGs.
     *
     * @param pooledByteBufferRef the reference to the encoded bytes
     * @return the bitmap
     * @throws TooManyBitmapsException    if the pool is full
     * @throws java.lang.OutOfMemoryError if the Bitmap cannot be allocated
     */
    public CloseableReference<Bitmap> decodeFromPooledByteBuffer(
            final CloseableReference<PooledByteBuffer> pooledByteBufferRef) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return mArtBitmapFactory.decodeFromPooledByteBuffer(pooledByteBufferRef);
        } else {
            return mDalvikBitmapFactory.decodeFromPooledByteBuffer(pooledByteBufferRef);
        }
    }

    /**
     * Creates a bitmap from encoded JPEG bytes. Supports a partial JPEG image.
     *
     * @param pooledByteBufferRef the reference to the encoded bytes
     * @param length              the number of encoded bytes in the buffer
     * @return the bitmap
     * @throws TooManyBitmapsException    if the pool is full
     * @throws java.lang.OutOfMemoryError if the Bitmap cannot be allocated
     */
    public CloseableReference<Bitmap> decodeJPEGFromPooledByteBuffer(
            CloseableReference<PooledByteBuffer> pooledByteBufferRef,
            int length) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return mArtBitmapFactory.decodeJPEGFromPooledByteBuffer(pooledByteBufferRef, length);
        } else {
            return mDalvikBitmapFactory.decodeJPEGFromPooledByteBuffer(pooledByteBufferRef, length);
        }
    }
}
