/*
 * Copyright 2015-2016 Canoo Engineering AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.canoo.dolphin.server.impl;

import com.canoo.dolphin.event.Subscription;
import com.canoo.dolphin.server.DolphinSession;
import com.canoo.dolphin.server.context.DolphinContextException;
import com.canoo.dolphin.server.context.DolphinSessionLifecycleHandler;
import com.canoo.dolphin.util.Callback;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class DolphinSessionLifecycleHandlerImpl implements DolphinSessionLifecycleHandler {

    private final List<Callback<DolphinSession>> onCreateCallbacks = new CopyOnWriteArrayList<>();

    private final List<Callback<DolphinSession>> onDestroyCallbacks = new CopyOnWriteArrayList<>();

    @Override
    public Subscription addSessionCreatedListener(final Callback<DolphinSession> listener) {
        onCreateCallbacks.add(listener);
        return new Subscription() {
            @Override
            public void unsubscribe() {
                onCreateCallbacks.remove(listener);
            }
        };
    }

    @Override
    public Subscription addSessionDestroyedListener(final Callback<DolphinSession> listener) {
        onDestroyCallbacks.add(listener);
        return new Subscription() {
            @Override
            public void unsubscribe() {
                onDestroyCallbacks.remove(listener);
            }
        };
    }

    @Override
    public void onSessionCreated(final DolphinSession session) {
        for (Callback<DolphinSession> listener : onCreateCallbacks) {
            try {
                listener.call(session);
            } catch (Exception e) {
                throw new DolphinContextException("Error while handling onSessionCreated listener", e);
            }
        }
    }

    @Override
    public void onSessionDestroyed(final DolphinSession session) {
        for (Callback<DolphinSession> listener : onDestroyCallbacks) {
            try {
                listener.call(session);
            } catch (Exception e) {
                throw new DolphinContextException("Error while handling onSessionDestroyed listener", e);
            }
        }
    }
}
