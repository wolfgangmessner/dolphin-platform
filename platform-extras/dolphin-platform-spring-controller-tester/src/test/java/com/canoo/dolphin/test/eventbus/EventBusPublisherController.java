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
package com.canoo.dolphin.test.eventbus;

import com.canoo.dolphin.server.DolphinAction;
import com.canoo.dolphin.server.DolphinController;
import com.canoo.dolphin.server.DolphinModel;
import com.canoo.dolphin.server.event.DolphinEventBus;
import org.springframework.beans.factory.annotation.Autowired;

@DolphinController(EventBusTestConstants.EVENT_BUS_PUBLISHER_CONTROLLER_NAME)
public class EventBusPublisherController {

    @DolphinModel
    private EventBusTestModel model;

    @Autowired
    private DolphinEventBus eventBus;

    @DolphinAction(EventBusTestConstants.CALL_ACTION)
    public void call() {
        eventBus.publish(EventBusTestConstants.TEST_TOPIC, model.valueProperty().get());
    }
}