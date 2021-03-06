/*
 * Copyright (c) 2015-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

package com.facebook.imagepipeline.request;

import android.graphics.Bitmap;

import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory;

/**
 * Use an instance of this class to perform post-process operations on a bitmap.
 */
public interface Postprocessor {

    /**
     * Called by the pipeline after completing other steps.
     *
     * @param sourceBitmap  The source bitmap.
     * @param bitmapFactory The factory to create a destination bitmap.
     *                      <p>
     *                      <p> The Postprocessor must not modify the source bitmap as it may be shared by the other
     *                      clients. The implementation must create a new bitmap that is safe to be modified and return a
     *                      reference to it. To create a bitmap, use the provided <code>bitmapFactory</code>.
     */
    CloseableReference<Bitmap> process(Bitmap sourceBitmap, PlatformBitmapFactory bitmapFactory);

    /**
     * Returns the name of this postprocessor.
     * <p>
     * <p>Used for logging and analytics.
     */
    String getName();
}
